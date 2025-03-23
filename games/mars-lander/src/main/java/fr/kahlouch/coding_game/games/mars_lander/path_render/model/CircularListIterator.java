package fr.kahlouch.coding_game.games.mars_lander.path_render.model;

import java.util.List;
import java.util.NoSuchElementException;

public class CircularListIterator<T> {
    private final List<T> list;
    private int currentIndex;

    public CircularListIterator(List<T> list) {
        this.list = list;
        this.currentIndex = 0;
    }

    public T next() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        T element = list.get(currentIndex);
        currentIndex = (currentIndex + 1) % list.size();
        return element;
    }

    public T previous() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }

        T element = list.get(currentIndex);
        currentIndex = (currentIndex - 1 + list.size()) % list.size();
        return element;
    }

    public T first() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.getFirst();
    }

    public T last() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.getLast();
    }


}
