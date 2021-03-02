package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EventIteratorTest {

    /**
     * Test 'EventIterator.moveNext()' method.
     */
    @Test
    public void MoveNextTest() throws Exception {
        EventStore eventStore = new EventStore();
        // define 4 timestamps for the items will be inserted
        long start1 = System.currentTimeMillis()+1111;
        long start2 = System.currentTimeMillis()+2222;
        long start3 = System.currentTimeMillis()+3333;
        long start4 = System.currentTimeMillis()+4444;

        //Insert 4 items
        eventStore.insert(new Event("tipo1", start1));
        eventStore.insert(new Event("tipo1", start2));
        eventStore.insert(new Event("tipo1", start3));
        eventStore.insert(new Event("tipo2", start4));

        //query for 'tipo1' items;
        EventIteratorInterface iterator = eventStore.query("tipo1", start1, System.currentTimeMillis()+8888);

        // Check current timestamp
        assertEquals(start1, iterator.current().timestamp());
        iterator.moveNext();
        //check timestamp assert after move next
        assertEquals(start2, iterator.current().timestamp());
        //check timestamp assert after move next
        iterator.moveNext();
        assertEquals(start3, iterator.current().timestamp());
        //check timestamp assert after move next
        iterator.moveNext();
        //check timestamp assert after move next in the last position (keep in the last position)
        assertEquals(start3, iterator.current().timestamp());
        //check if move next returns false
        assertFalse(iterator.moveNext());
    }

    /**
     * Test 'EventIterator.current()' method.
     */
    @Test
    public void CurrentTest() throws Exception {

        // Insert 5 items with different timestamps
        EventStore eventStore = new EventStore();
        long start0 = System.currentTimeMillis();
        long start1 = System.currentTimeMillis()+1111;
        long start2 = System.currentTimeMillis()+2222;
        long start3 = System.currentTimeMillis()+3333;
        long start4 = System.currentTimeMillis()+4444;

        eventStore.insert(new Event("tipo0", start0));
        eventStore.insert(new Event("tipo1", start1));
        eventStore.insert(new Event("tipo1", start2));
        eventStore.insert(new Event("tipo1", start3));
        eventStore.insert(new Event("tipo2", start4));

        // Query tipo1 items
        EventIteratorInterface iterator = eventStore.query("tipo1", start1, System.currentTimeMillis()+8888);

        // check if the first iterator item is the first tipo1 event
        assertEquals(start1, iterator.current().timestamp());
        iterator.moveNext();
        // check current after after moveNext()
        assertEquals(start2, iterator.current().timestamp());
        iterator.remove();
        // check current after remove()
        assertEquals(start3, iterator.current().timestamp());
        iterator.moveNext();
        // check current after moveNext() in the last position
        assertEquals(start3, iterator.current().timestamp());
        iterator.first();
        // check current after first()
        assertEquals(start1, iterator.current().timestamp());
    }

    @Test
    public void RemoveTest() throws Exception {

        // create 4 items
        EventStore eventStore = new EventStore();
        long start1 = System.currentTimeMillis()+1111;
        long start2 = System.currentTimeMillis()+2222;
        long start3 = System.currentTimeMillis()+3333;
        long start4 = System.currentTimeMillis()+4444;

        eventStore.insert(new Event("tipo1", start1));
        eventStore.insert(new Event("tipo1", start2));
        eventStore.insert(new Event("tipo1", start3));
        eventStore.insert(new Event("tipo2", start4));
        //Query for tipo1 items
        EventIteratorInterface iterator = eventStore.query(
                "tipo1", start1, System.currentTimeMillis()+8888);

        // check initial iterator size
        assertEquals(3, iterator.size());
        iterator.remove();
        // check iterator size after remove()
        assertEquals(2, iterator.size());
        // back to first position and check
        iterator.first();
        assertEquals(start2, iterator.current().timestamp());
        iterator.remove();
        // check size after remove fists
        assertEquals(1, iterator.size());
        // check if the first position is correct after operations above.
        assertEquals(start3, iterator.current().timestamp());
        iterator.remove();
        // check size after remove the last one
        assertEquals(0, iterator.size());
        iterator.remove();
        // check size after remove with empty iterator
        assertEquals(0, iterator.size());
    }

    @Test
    public void first() {

        // store 4 events
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

        // moveNext two times
        iterator.moveNext();
        iterator.moveNext();
        // check if goes to expected position
        assertEquals(start3, iterator.current().timestamp());
        iterator.first();
        // check assert after first()
        assertEquals(start1, iterator.current().timestamp());
    }

    @Test
    public void last() {
        // store 4 events
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

        // check assert of the last position
        iterator.last();
        assertEquals(start3, iterator.current().timestamp());
        iterator.moveNext();
        // check assert after move next to the last position.
        assertEquals(start3, iterator.current().timestamp());
        iterator.first();
        iterator.remove();
        iterator.last();
        // check assert after remove the first position and going back to last()
        assertEquals(start3, iterator.current().timestamp());
    }

    @Test
    public void size() {
        // store 4 events
        EventStore eventStore = new EventStore();
        long start1 = System.currentTimeMillis()+1111;
        long start2 = System.currentTimeMillis()+2222;
        long start3 = System.currentTimeMillis()+3333;
        long start4 = System.currentTimeMillis()+4444;

        eventStore.insert(new Event("tipo1", start1));
        eventStore.insert(new Event("tipo1", start2));
        eventStore.insert(new Event("tipo1", start3));
        eventStore.insert(new Event("tipo2", start4));

        // query for tipo1 types
        EventIteratorInterface iterator = eventStore.query("tipo1", start1, System.currentTimeMillis()+8888);

        // assert iterator initial size
        assertEquals(3, iterator.size());
        iterator.remove();
        // assert iterator size after remove()
        assertEquals(2, iterator.size());
        iterator.remove();
        // assert iterator size after remove() again
        assertEquals(1, iterator.size());
        iterator.remove();
        // assert iterator size after remove() again
        assertEquals(0, iterator.size());
        iterator.remove();
        // assert iterator size after remove() empty iterator
        assertEquals(0, iterator.size());
    }
}