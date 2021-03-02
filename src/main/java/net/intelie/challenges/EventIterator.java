package net.intelie.challenges;

import java.io.Closeable;
// The following imports was used to perform events lists inside the iterator.
import java.util.ArrayList;
import java.util.List;

public class EventIterator implements EventIteratorInterface {

    private final int START_INDEX = 0;
    private int currentIndex = START_INDEX;
    private boolean empty = true;
    private List<Event> filtered = new ArrayList<>();

    public EventIterator(List<Event> filtered) {
        this.empty = filtered.size() == 0;
        this.filtered = filtered;
    }

    /**
     * Move the iterator to the next event, if any.
     *
     * @return false if the iterator has reached the end, true otherwise.
     */
    @Override
    public boolean moveNext() {
        if (currentIndex < filtered.size()-1) {
            currentIndex++;
            return true;
        }
        return false;
    }

    /**
     * Gets the current event ref'd by this iterator.
     *
     * @return the event itself.
     * @throws IllegalStateException if {@link #moveNext} was never called
     *                               or its last result was {@code false}.
     */
    @Override
    public Event current() {
        if (!empty) {
            return filtered.get(currentIndex);
        }
        return null;
    }

    /**
     * Remove current event from its store.
     *
     * @throws IllegalStateException if {@link #moveNext} was never called
     *                               or its last result was {@code false}.
     */
    @Override
    public void remove() {
        if (!empty) {
            // Once we need to change data at this point, is necessary to synchronize this action!
            synchronized (this.filtered) {
                this.filtered.remove(current());
                if (this.currentIndex == filtered.size()) {
                    this.currentIndex--;
                }
                if(this.filtered.size() == 0){
                    this.empty = true;
                    this.currentIndex = 0;
                }
            }
        }
    }

    /**
     * Go back iterator to the first position
     * This method was created to test this class
     */
    public void first() {
        // reset current index to start value
        currentIndex = START_INDEX;
    }
    /**
     * Go to the last position if not empty
     * This method was created to test this class
     */
    public void last() {
        // if not empty, update currentIndex with the last position.
        if (!empty) {
            currentIndex = filtered.size() - 1;
        } else {
            currentIndex = 0;
        }
    }

    /**
     * Returns the iterator size.
     * This method was created to test this class
     */
    public int size() {
        return filtered.size();
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     *
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {

    }
}
