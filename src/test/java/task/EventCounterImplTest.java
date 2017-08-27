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
    public void getLastMinuteEventsCount() throws InterruptedException {
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        Thread.sleep(1000L);

        int lastMinuteEventsCount = eventCounterIml.getLastMinuteEventsCount();
        assertThat(lastMinuteEventsCount, equalTo(1));
    }

    @Test
    public void getLastMinuteEventsCount2() throws InterruptedException {
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        Thread.sleep(1000L);

        int lastMinuteEventsCount = eventCounterIml.getLastMinuteEventsCount();
        assertThat(lastMinuteEventsCount, equalTo(2));
    }

    @Test
    public void getLastHourEventsCount() throws InterruptedException {
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        Thread.sleep(1000L);

        int lastHourEventsCount = eventCounterIml.getLastHourEventsCount();
        assertThat(lastHourEventsCount, equalTo(1));
    }

    @Test
    public void getLastDayEventsCount() throws InterruptedException {
        eventCounterIml.logEvent(Event.Type.SAMPLE);
        Thread.sleep(1000L);

        int lastDayEventsCount = eventCounterIml.getLastDayEventsCount();
        assertThat(lastDayEventsCount, equalTo(1));
    }

    @Test
    public void getZeroCountInLastMinute() throws InterruptedException {
        eventCounterIml.logEvent(Instant.now().minus(MINUTE).minusSeconds(1L), Event.Type.SAMPLE);
        Thread.sleep(1000L);

        int lastMinuteEventsCount = eventCounterIml.getLastMinuteEventsCount();
        assertThat(lastMinuteEventsCount, equalTo(0));
    }

    @Test
    public void getZeroCountInLastHour() throws InterruptedException {
        eventCounterIml.logEvent(Instant.now().minus(HOUR).minusSeconds(1L), Event.Type.SAMPLE);
        Thread.sleep(1000L);

        int lastHourEventsCount = eventCounterIml.getLastHourEventsCount();
        assertThat(lastHourEventsCount, equalTo(0));
    }

    @Test
    public void getZeroCountInLastDay() throws InterruptedException {
        eventCounterIml.logEvent(Instant.now().minus(DAY).minusSeconds(1L), Event.Type.SAMPLE);
        Thread.sleep(1000L);

        int lastDayEventsCount = eventCounterIml.getLastDayEventsCount();
        assertThat(lastDayEventsCount, equalTo(0));
    }
}