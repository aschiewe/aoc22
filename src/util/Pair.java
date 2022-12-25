package util;

import java.util.Objects;

public class Pair<T, V> {

    private final T firstEntry;
    private final V secondEntry;

    public Pair(T firstEntry, V secondEntry) {
        this.firstEntry = firstEntry;
        this.secondEntry = secondEntry;
    }

    public T getFirstEntry() {
        return firstEntry;
    }

    public V getSecondEntry() {
        return secondEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(firstEntry, pair.firstEntry) && Objects.equals(secondEntry, pair.secondEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstEntry, secondEntry);
    }
}
