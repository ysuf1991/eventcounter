package task;

class Event {
    private final Type type;
    private final long timeStamp;

    Event(Type type, long timeStamp) {
        this.type = type;
        this.timeStamp = timeStamp;
    }

    Type getType() {
        return type;
    }

    long getTimeStamp() {
        return timeStamp;
    }

    enum Type {
        SAMPLE
    }

    @Override
    public String toString() {
        return String.format("Event(type = %s; timeStamp = %d)", type, timeStamp);
    }
}
