package task;

interface EventCounter {
    void logEvent(Event.Type type);

    int getLastMinuteEventsCount();

    int getLastHourEventsCount();

    int getLastDayEventsCount();
}
