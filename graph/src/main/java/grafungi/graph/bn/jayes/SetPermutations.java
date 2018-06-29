package grafungi.graph.bn.jayes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class SetPermutations {

    private SetPermutations() {
        // no state.
    }

    public static <T> List<List<T>> getSetPermutations(final Collection<? extends Collection<T>> input) {
        if (input == null) {
            throw new IllegalArgumentException("Input not provided!");
        }
        final List<List<T>> saved = new ArrayList<>();
        for (Collection<T> c : input) {
            List<T> s = new ArrayList<>(c);
            c.remove(null);
            if (c.size() >= 1) {
                saved.add(s);
            } else {
                throw new IllegalArgumentException("Input includes null/empty collection!");
            }
        }

        return permute(new ArrayList<T>(), saved);
    }

    private static <T> List<List<T>> permute(final List<T> initial, final List<List<T>> itemSets) {

        if (itemSets.isEmpty()) {
            return Collections.singletonList(initial);
        }

        final List<T> items = itemSets.get(0);
        final List<List<T>> remaining = itemSets.subList(1, itemSets.size());
        final int computedSetSize = initial.size() * items.size() * remaining.size();
        final List<List<T>> computed = new ArrayList<>(computedSetSize);

        for (T item : items) {
            if (!initial.contains(item)) {
                List<T> permutation = new ArrayList<>(initial);
                permutation.add(item);
                computed.addAll(permute(permutation, remaining));
            }
        }

        return computed;
    }

}
