package ca.qc.johnabbott.cs4p6.collections;

/**
 * Set interface
 * - operations of the Set ADT
 *
 * @author Ian Clement
 */
public interface Set<T> extends Traversable<T> {

    /**
     * Determine if the set contains a specific element.
     *
     * @param elem the element to find in the set.
     * @return true if the element is in the set, false otherwise.
     * @preconditions None.
     */
    boolean contains(T elem);

    /**
     * Determine if the set contains all the elements of the provided set, or, that the provided
     * set is a subset of the current set.
     *
     * @param rhs the set of elements to find in the current set.
     * @return true if the elements are all in the set, false otherwise.
     * @preconditions None.
     */
    boolean containsAll(Set<T> rhs);

    /**
     * Add an element into the set.
     *
     * @param elem the element to add to the set.
     * @return The method returns `true` if the element is added to the set, and `false` otherwise.
     * @preconditions The  set is not full.
     * @postconditions If there is no element in the  set that equals `element`, then the element is inserted
     * into the  set, otherwise the set is unchanged
     */
    boolean add(T elem);

    /**
     * Remove an element from the set.
     *
     * @param elem the element to remove from the set.
     * @return The method returns `true` if the element is added to the set, and `false` otherwise.
     * @preconditions None.
     * @postconditions If there is an element in the  set equal to `element`, then it is removed from the set.
     * Otherwise, the  set is unchanged.
     */
    boolean remove(T elem);

    /**
     * Determine the number of elements in the  set.
     *
     * @return the number of elements in the set.
     * @preconditions None.
     */
    int size();

    /**
     * Determine if the  set is empty.
     *
     * @return `true` if the  set is empty, `false` otherwise.
     * @preconditions None.
     */
    boolean isEmpty();

    /**
     * Determines if the set is full.
     *
     * @return `true` if the  set is full, `false` otherwise.
     * @preconditions None.
     */
    boolean isFull();

}
