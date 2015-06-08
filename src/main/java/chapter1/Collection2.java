package chapter1;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Collection2<T> extends Collection {

    default void forEachIf(Consumer<T> action, Predicate<T> filter) {
        removeIf(filter);
        forEach(action);
    }

    // It seems to be a encapsulated implementation of filter -> map
}
