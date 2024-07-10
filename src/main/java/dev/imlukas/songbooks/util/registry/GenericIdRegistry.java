package dev.imlukas.songbooks.util.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericIdRegistry<T extends Identifiable> extends GenericRegistry<String, T>{

    public void register(T item) {
        super.register(item.getIdentifier(), item);
    }

    public List<String> getIds() {
        return new ArrayList<>(getRegistry().keySet());
    }
}
