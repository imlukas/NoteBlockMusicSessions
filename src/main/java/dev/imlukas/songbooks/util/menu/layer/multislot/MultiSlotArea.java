package dev.imlukas.songbooks.util.menu.layer.multislot;

import dev.imlukas.songbooks.util.menu.base.BaseMenu;
import dev.imlukas.songbooks.util.menu.buttons.button.Button;
import dev.imlukas.songbooks.util.menu.element.MenuElement;
import dev.imlukas.songbooks.util.menu.element.Renderable;
import dev.imlukas.songbooks.util.menu.selection.Selection;
import lombok.Getter;
import lombok.Setter;
import net.ottersmp.ottercore.util.text.placeholder.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class MultiSlotArea extends Renderable {

    private final List<Integer> slots;
    private final List<MenuElement> elements = new ArrayList<>();
    @Setter
    private MenuElement emptyElement = new Button(new ItemStack(Material.AIR));

    public MultiSlotArea(BaseMenu menu, Selection selection) {
        super(menu);
        this.slots = selection.getSlots();
    }

    public MultiSlotArea(BaseMenu menu, Selection selection, MenuElement emptyElement) {
        this(menu, selection);
        this.emptyElement = emptyElement;
    }

    public void clear() {
        elements.clear();
    }

    public void addElement(MenuElement element) {
        if (elements.size() < slots.size()) {
            elements.add(element);
        }
    }

    public void removeElement(MenuElement element) {
        elements.remove(element);
    }


    public void forceUpdate(BaseMenu menu) {

        for (int index = 0; index < slots.size(); index++) {
            int slot = slots.get(index);

            if (index >= elements.size()) {
                menu.setElement(slot, emptyElement);
            } else {
                menu.setElement(slot, elements.get(index));
            }
        }
    }

    @Override
    public void setItemPlaceholders(Collection<Placeholder<Player>> placeholders) {
        elements.forEach(element -> element.setItemPlaceholders(placeholders));
    }

    @Override
    public void forceUpdate() {
        forceUpdate(menu);
    }
}
