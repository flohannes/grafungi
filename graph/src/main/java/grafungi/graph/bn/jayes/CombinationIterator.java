package grafungi.graph.bn.jayes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CombinationIterator<T>
        implements Iterable<List<T>>,
                   Iterator<List<T>> {
    private List<T> items;
    private int choose;
    private boolean started;
    private boolean finished;
    private int[] current;

    public CombinationIterator(List<T> items, int choose) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items");
        }
        if (choose <= 0 || choose > items.size()) {
            throw new IllegalArgumentException("choose");
        }
        this.items = items;
        this.choose = choose;
        this.finished = false;
    }

    @Override
    public Iterator<List<T>> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return !finished;
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (current == null) {
            current = new int[choose];
            for (int i = 0; i < choose; i++) {
                current[i] = i;
            }
        }

        List<T> result = new ArrayList<>(choose);
        for (int i = 0; i < choose; i++) {
            result.add(items.get(current[i]));
        }

        int n = items.size();
        finished = true;
        for (int i = choose - 1; i >= 0; i--) {
            if (current[i] < n - choose + i) {
                current[i]++;
                for (int j = i + 1; j < choose; j++) {
                    current[j] = current[i] - i + j;
                }
                finished = false;
                break;
            }
        }

        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}