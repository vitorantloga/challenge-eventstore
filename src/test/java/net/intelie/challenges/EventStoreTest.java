package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventStoreTest {

    /*
     * Test insert in in-memory store
     */
    @Test
    public void InsertTest() throws Exception {
        // declare first event Store
        EventStore eventStore = new EventStore();
        // add 6 events
        eventStore.insert(new Event("tipo1", System.currentTimeMillis()));
        eventStore.insert(new Event("tipo2", System.currentTimeMillis()+1111));
        eventStore.insert(new Event("tipo2", System.currentTimeMillis()+2222));
        eventStore.insert(new Event("tipo3", System.currentTimeMillis()+2222));
        eventStore.insert(new Event("tipo1", System.currentTimeMillis()+3333));
        eventStore.insert(new Event("tipo1", System.currentTimeMillis()+4444));
        // assert eventStore size after inserts
        assertEquals(6, eventStore.getSize());

        //declare other event store
        EventStore eventStore2 = new EventStore();
        // add 3 events
        eventStore2.insert(new Event("tipo1", System.currentTimeMillis()));
        eventStore2.insert(new Event("tipo2", System.currentTimeMillis()+1111));
        eventStore2.insert(new Event("tipo2", System.currentTimeMillis()+2222));
        // assert size of the new event store after inserts.
        assertEquals(3, eventStore2.getSize());
    }

    @Test
    public void removeAllTest() throws Exception {
        // declare eventStore
        EventStore eventStore = new EventStore();

        // add 6 events, being 3 of 'tipo1' and 3 of other types.
        eventStore.insert(new Event("tipo1", System.currentTimeMillis()));
        eventStore.insert(new Event("tipo2", System.currentTimeMillis()+1111));
        eventStore.insert(new Event("tipo2", System.currentTimeMillis()+2222));
        eventStore.insert(new Event("tipo3", System.currentTimeMillis()+2222));
        eventStore.insert(new Event("tipo1", System.currentTimeMillis()+3333));
        eventStore.insert(new Event("tipo1", System.currentTimeMillis()+4444));
        // assert eventStore size after inserts
        assertEquals(6, eventStore.getSize());
        eventStore.removeAll("tipo1");
        // assert eventStore size after remove 'tipo1' events.
        assertEquals(3, eventStore.getSize());
    }

}