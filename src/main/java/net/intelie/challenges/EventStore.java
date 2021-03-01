package net.intelie.challenges;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class EventStore implements EventStoreInterface{
    final private Map<String, List<Event>> storedEvents = new ConcurrentHashMap<>();
    final private List<Event> eventList = new ArrayList<>();

    /**
     * Stores an event
     *
     * @param event
     */
    public void insert(Event event) {
        // Creates a List with the new event
        eventList.add(event);
        this.storedEvents.put(event.type(), eventList);
    }

    /**
     * Removes all events of specific type.
     *
     * @param type
     */
    public void removeAll(String type) {
        eventList.removeIf(e -> e.type().equals(type));
        this.storedEvents.put(type, eventList);
    }

    /**
     * Retrieves an iterator for events based on their type and timestamp.
     *
     * @param type      The type we are querying for.
     * @param startTime Start timestamp (inclusive).
     * @param endTime   End timestamp (exclusive).
     * @return An iterator where all its events have same type as
     * {@param type} and timestamp between {@param startTime}
     * (inclusive) and {@param endTime} (exclusive).
     */

    public EventIteratorInterface query(String type, long startTime, long endTime) {
        //Filtering by type
        List<Event> filtered =
                eventList.stream()
                        .filter(
                                t -> t.type().equals(type) &&
                                        t.timestamp() >= startTime &&
                                        t.timestamp() < endTime)
                        .sorted(comparing(Event::timestamp))
                        .collect(toList());

        return new EventIterator(filtered);
    }
}
