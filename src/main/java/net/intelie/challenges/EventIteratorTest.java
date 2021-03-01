package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EventIteratorTest {

    @Test
    public void NextTest() throws Exception {
        EventStore eventStore = new EventStore();
        long start1 = System.currentTimeMillis()+1111;
        long start2 = System.currentTimeMillis()+2222;
        long start3 = System.currentTimeMillis()+3333;
        long start4 = System.currentTimeMillis()+4444;

        eventStore.insert(new Event("tipo1", start1));
        eventStore.insert(new Event("tipo1", start2));
        eventStore.insert(new Event("tipo1", start3));
        eventStore.insert(new Event("tipo2", start4));

        EventIteratorInterface iterator = eventStore.query("tipo1", start1, System.currentTimeMillis()+8888);

        assertEquals(start1, iterator.current().timestamp());
        iterator.moveNext();
        assertEquals(start2, iterator.current().timestamp());
        iterator.moveNext();
        assertEquals(start3, iterator.current().timestamp());
        iterator.moveNext();
        assertFalse(iterator.moveNext());
    }
}