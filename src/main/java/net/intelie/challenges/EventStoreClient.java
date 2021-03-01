package net.intelie.challenges;

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
//        System.out.println(iterator.current().timestamp());
        iterator.moveNext();
//        System.out.println(iterator.current().timestamp());
        System.out.println(iterator.size());
//        System.out.println(iterator.current().timestamp());
        iterator.first();
//        System.out.println(iterator.current().timestamp());
//        iterator.last();
//        System.out.println(iterator.current().timestamp());
        iterator.remove();
        iterator.remove();
        iterator.remove();
//        System.out.println(iterator.current().timestamp());

    }
}
