package com.company;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Extension of HashSet to handle intersection with other sets.
 * @param <E> the generic type of the Set
 */
public class MySet<E> extends HashSet<E> {

    /**
     * Base constructor of the class
     */
    public MySet() {

    }

    /**
     * Constructor for setting the initial capacity of the Set.
     * @param initialCapacity of the Set
     */
    public MySet(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor with an already existing collection.
     *
     * @param collection to add to the Set.
     */
    public MySet(Collection<E> collection) {
        super(collection);
    }

    /**
     * Calculate the intersection between this and another Set
     *
     * @param secondSet The other set to get the intersection with
     * @return The intersection as a Set
     */
    public Set<E> intersection(Set<E> secondSet) {
        return this.stream()
                .filter(secondSet::contains)
                .collect(Collectors.toSet());
    }
}
