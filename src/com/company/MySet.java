package com.company;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MySet<E> extends HashSet<E> {

    public MySet() {

    }

    public MySet(int initialCapacity) {
        super(initialCapacity);
    }

    public MySet(Collection<E> collection) {
        super(collection);
    }

    public Set<E> intersection(Set<E> secondSet) {
        return this.stream()
                .filter(secondSet::contains)
                .collect(Collectors.toSet());
    }
}
