package net.intelie.challenges;


/**
 * This is just a Client class to have fun with the challenge and make some manual tests!
 * Do not consider it as implementation. But feel free to change it and make some tests.
 */
public class EventStoreClient {
    public static void main(String[] args) {

        EventStore eventStore = new EventStore();
        long start = System.currentTimeMillis();

        eventStore.insert(new Event("tipo1", System.currentTimeMillis()));
        eventStore.insert(new Event("tipo2", System.currentTimeMillis()+1111));
        eventStore.insert(new Event("tipo2", System.currentTimeMillis()+2222));
        eventStore.insert(new Event("tipo3", System.currentTimeMillis()+2222));
        eventStore.insert(new Event("tipo1", System.currentTimeMillis()+3333));
        eventStore.insert(new Event("tipo1", System.currentTimeMillis()+4444));
        eventStore.removeAll("tipo1");
        EventIteratorInterface iterator = eventStore.query("tipo1", start, System.currentTimeMillis()+5555);
        System.out.println(iterator.size());
        iterator.moveNext();
        System.out.println(iterator.size());
        iterator.first();
        iterator.remove();
        iterator.remove();
        iterator.remove();


    }
}
