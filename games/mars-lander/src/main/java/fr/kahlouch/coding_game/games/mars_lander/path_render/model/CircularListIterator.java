package fr.kahlouch.coding_game.games.mars_lander.path_render.model;

import java.util.List;
import java.util.NoSuchElementException;

public class CircularListIterator<T> {
    private final List<T> list;
    private Integer currentIndex;

    public CircularListIterator(List<T> list) {
        this.list = list;
        this.currentIndex = null;
    }

    public T next() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (this.currentIndex == null) {
            this.currentIndex = 0;
        } else {
            this.currentIndex = (currentIndex + 1) % list.size();
        }
        return list.get(currentIndex);
    }

    public T previous() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (this.currentIndex == null) {
            this.currentIndex = 0;
        } else {
            this.currentIndex = (currentIndex - 1 + list.size()) % list.size();
        }
        return list.get(currentIndex);
    }

    public T first() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.currentIndex = 0;
        return list.get(currentIndex);
    }

    public T last() {
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.currentIndex = list.size() - 1;
        return list.get(currentIndex);
    }
}
