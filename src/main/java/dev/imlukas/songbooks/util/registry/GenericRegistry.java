package dev.imlukas.songbooks.util.registry;

import java.util.HashMap;
import java.util.Map;

public class GenericRegistry <K, T> {

    private final Map<K, T> registry = new HashMap<>();

    public void register(K key, T item) {
        registry.put(key, item);
    }

    public void unregister(K key) {
        registry.remove(key);
    }

    public T get(K key) {
        return registry.get(key);
    }

    public Map<K, T> getRegistry() {
        return registry;
    }
}
