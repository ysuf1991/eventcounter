package task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

class EventQueueRepository {
    private final ExecutorService producer = Executors.newSingleThreadExecutor();
    private final ExecutorService consumer = Executors.newSingleThreadExecutor();
    private final BlockingQueue<Event> queue = new LinkedBlockingDeque<>();
    private final List<Event> eventsRepository;

    EventQueueRepository(List<Event> eventsHolder) {
        eventsRepository = eventsHolder;
        consumer.submit(this::takeFromQueue);
    }

    void submit(Event element) {
        producer.submit(() -> putToQueue(element));
    }

    private void putToQueue(Event element) {
        try {
            queue.put(element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void takeFromQueue() {
        while (true) {
            try {
                Event take = queue.take();
                int i = Collections.binarySearch(eventsRepository, take, Comparator.comparingLong(Event::getTimeStamp));
                int position = i >= 0 ? i : -i - 1;
                eventsRepository.add(position, take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
