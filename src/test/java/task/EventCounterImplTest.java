package task;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static task.EventCounterImpl.*;

public class EventCounterImplTest {

    private EventCounterImpl eventCounterIml;

    @Before
    public void init() {
        eventCounterIml = new EventCounterImpl();
    }

    @Test
    public void getLastMinuteEventsCount() {
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        int lastMinuteEventsCount = eventCounterIml.getLastMinuteEventsCount();
        assertThat(lastMinuteEventsCount, equalTo(1));
    }

    @Test
    public void getLastMinuteEventsCount2() {
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        int lastMinuteEventsCount = eventCounterIml.getLastMinuteEventsCount();
        assertThat(lastMinuteEventsCount, equalTo(2));
    }

    @Test
    public void getLastHourEventsCount() {
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        int lastHourEventsCount = eventCounterIml.getLastHourEventsCount();
        assertThat(lastHourEventsCount, equalTo(1));
    }

    @Test
    public void getLastDayEventsCount() {
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        int lastDayEventsCount = eventCounterIml.getLastDayEventsCount();
        assertThat(lastDayEventsCount, equalTo(1));
    }

    @Test
    public void getZeroCountInLastMinute() {
        eventCounterIml.logEvent(Instant.now().minus(MINUTE).minusSeconds(1L), Event.Type.SAMPLE);
        int lastMinuteEventsCount = eventCounterIml.getLastMinuteEventsCount();
        assertThat(lastMinuteEventsCount, equalTo(0));
    }

    @Test
    public void getZeroCountInLastHour() {
        eventCounterIml.logEvent(Instant.now().minus(HOUR).minusSeconds(1L), Event.Type.SAMPLE);
        int lastHourEventsCount = eventCounterIml.getLastHourEventsCount();
        assertThat(lastHourEventsCount, equalTo(0));
    }

    @Test
    public void getZeroCountInLastDay() {
        eventCounterIml.logEvent(Instant.now().minus(DAY).minusSeconds(1L), Event.Type.SAMPLE);
        int lastDayEventsCount = eventCounterIml.getLastDayEventsCount();
        assertThat(lastDayEventsCount, equalTo(0));
    }
}