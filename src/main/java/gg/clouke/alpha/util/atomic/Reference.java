package gg.clouke.alpha.util.atomic;

import lombok.NoArgsConstructor;

/**
 * @author Lucanius
 * @since June 01, 2022.
 * <p>
 * Taken from my Label project.
 */
@NoArgsConstructor
public class Reference<V> {

    private V value;

    public Reference(V value) {
        this.value = value;
    }

    public V get() {
        return value;
    }

    public V set(V value) {
        return this.value = value;
    }
}
