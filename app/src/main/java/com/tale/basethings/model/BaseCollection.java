package com.tale.basethings.model;

import com.google.common.collect.Lists;
import com.pedrogomez.renderers.AdapteeCollection;

import java.util.Collection;
import java.util.List;

/**
 * Created by TALE on 9/4/2014.
 */
public class BaseCollection<T> implements AdapteeCollection<T> {
    private List<T> items;

    public BaseCollection(List<T> items) {
        this.items = items;
    }

    @Override
    public int size() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public T get(int index) {
        return items == null || items.size() <= index || index < 0 ? null: items.get(index);
    }

    @Override
    public void add(T element) {
        if (element == null) {
            return;
        }
        if (items == null) {
            items = Lists.newArrayList();
        }
        items.add(element);
    }

    @Override
    public void remove(T element) {
        if (items != null && element != null) {
            items.remove(element);
        }
    }

    @Override
    public void addAll(Collection<T> elements) {
        if (items != null && elements != null) {
            items.addAll(elements);
        }
    }

    @Override
    public void removeAll(Collection<T> elements) {
        if (items != null && elements != null) {
            items.removeAll(elements);
        }
    }

    @Override
    public void clear() {

    }
}
