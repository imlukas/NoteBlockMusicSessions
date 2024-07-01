package dev.imlukas.songbooks.util.menu.layer.pagination;

import dev.imlukas.songbooks.util.menu.base.BaseMenu;
import dev.imlukas.songbooks.util.menu.buttons.button.Button;
import dev.imlukas.songbooks.util.menu.element.MenuElement;
import dev.imlukas.songbooks.util.menu.selection.Selection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class PaginableArea {

    private final List<Integer> slots;
    private final List<MenuElement> elements = new ArrayList<>();
    @Setter
    private MenuElement emptyElement = new Button(new ItemStack(Material.AIR));


    public PaginableArea(Selection selection) {
        this.slots = selection.getSlots();
    }

    public PaginableArea(Selection selection, MenuElement emptyElement) {
        this(selection);
        this.emptyElement = emptyElement;
    }

    public void clear() {
        elements.clear();
    }

    public void addElement(MenuElement element) {
        elements.add(element);
    }


    public void addElement(Collection<? extends MenuElement> element) {
        for (MenuElement menuElement : element) {
            addElement(menuElement);
        }
    }

    public void removeElement(MenuElement element) {
        elements.remove(element);
    }

    public void forceUpdate(BaseMenu menu, int page) {
        int startIdx = (page - 1) * slots.size();
        int endIdx = startIdx + slots.size();

        for (int index = startIdx; index < endIdx; index++) {
            int slot = slots.get(index - startIdx);

            if (index >= elements.size()) {
                menu.setElement(slot, emptyElement);
            } else {
                menu.setElement(slot, elements.get(index));
            }
        }
    }

    public List<ItemStack> getItemsForPage(int page) {
        List<ItemStack> items = new ArrayList<>();
        int startIdx = (page - 1) * slots.size();
        int endIdx = startIdx + slots.size();
        endIdx = Math.min(endIdx, elements.size());

        for (int index = startIdx; index < endIdx; index++) {
            MenuElement element = elements.get(index);

            if (element == null) {
                continue;
            }

            ItemStack displayItem = element.getDisplayItem();

            if (displayItem == null) {
                continue;
            }

            if (displayItem.getType().isAir()) {
                continue;

            }

            items.add(displayItem);
        }
        return items;
    }

    public int getPageCount() {
        return (int) Math.ceil((double) elements.size() / slots.size());
    }

}
