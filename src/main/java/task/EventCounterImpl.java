package task;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * EventCounterImpl реализует шаблон singleton с фабричным
 * методом getInstance(), чтобы собрать и обрабатывать события в одном месте.
 * События добавляются в возрастающем порядке(по метке времени в классе Event)
 * в массив events. Для поиска количества событий используется бинарный поиск.
 * Для оптимизации можно добавить слой который реализует очередь на запись событий,
 * и в отдельном потоке обрабатывать накопившиеся записи(в случает когда добавление
 * в events происходит долго и потоки начинают висеть на блокировке).
 */
public class EventCounterImpl implements EventCounter {
    private static final int INITIAL_CAPACITY = 100000;
    static final Duration MINUTE = Duration.ofMinutes(1L);
    static final Duration HOUR = Duration.ofHours(1L);
    static final Duration DAY = Duration.ofDays(1L);

    private final Object mutex;
    private final List<Event> events;

    /**
     * Конструктор предназначен для тестов.
     * В других случаях нужно использовать метод getInstance()
     */
    EventCounterImpl() {
        mutex = this;
        events = new ArrayList<>(INITIAL_CAPACITY);
    }

    @Override
    public void logEvent(Event.Type type) {
        logEvent(Instant.now(), type);
    }

    /**
     * Метод создан для удобства тестирования. Не доступен в интерфейсе EventCounter.
     */
    void logEvent(Instant instant, Event.Type type) {
        synchronized (mutex) {
            Event event = new Event(type, instant.getEpochSecond());
            events.add(event);
        }
    }

    @Override
    public int getLastMinuteEventsCount() {
        return binarySearch(Instant.now().getEpochSecond() - MINUTE.getSeconds());
    }

    @Override
    public int getLastHourEventsCount() {
        return binarySearch(Instant.now().getEpochSecond() - HOUR.getSeconds());
    }

    @Override
    public int getLastDayEventsCount() {
        return binarySearch(Instant.now().getEpochSecond() - DAY.getSeconds());
    }

    private int binarySearch(long timestamp) {
        int size = events.size();
        if (size > 0 && events.get(size - 1).getTimeStamp() < timestamp) {
            return 0;
        }

        int left = 0;
        int right = size - 1;

        while (left < right) {
            int mid = (left + right) / 2;
            if (events.get(mid).getTimeStamp() > timestamp) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return size - right;
    }

    public static EventCounter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        static final EventCounter INSTANCE = new EventCounterImpl();
    }
}
