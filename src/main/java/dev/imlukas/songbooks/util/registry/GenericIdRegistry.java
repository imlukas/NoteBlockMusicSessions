package dev.imlukas.songbooks.util.registry;

import java.util.ArrayList;
import java.util.List;

public class GenericIdRegistry<T extends Identifiable> extends GenericRegistry<String, T> {

    public void register(T item) {
        super.register(item.getIdentifier(), item);
    }

    public List<String> getIds() {
        return new ArrayList<>(getRegistry().keySet());
    }
}
