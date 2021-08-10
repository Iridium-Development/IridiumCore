package com.iridium.iridiumcore.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortedList<T> extends ArrayList<T> {
    private final Comparator<T> comparator;

    public SortedList(final Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean add(T t) {
        int index = Collections.binarySearch(this, t, comparator);
        if (index < 0) index = ~index;
        super.add(index, t);
        return true;
    }
}
