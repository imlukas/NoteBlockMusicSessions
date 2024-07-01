package dev.imlukas.songbooks.util.menu.layer.multislot;

import dev.imlukas.songbooks.util.menu.base.BaseMenu;
import dev.imlukas.songbooks.util.menu.element.Renderable;
import net.ottersmp.ottercore.util.text.placeholder.Placeholder;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Similar to paginable layer but doesn't have paginable features.
 * Simply works with multiple slots that have the same character
 */
public class MultiSlotLayer extends Renderable {

    private MultiSlotArea area;

    public MultiSlotLayer(BaseMenu menu, MultiSlotArea area) {
        super(menu);
        this.area = area;
    }

    public MultiSlotLayer(BaseMenu menu) {
        super(menu);
    }

    @Override
    public void setItemPlaceholders(Collection<Placeholder<Player>> placeholders) {
        area.getElements().forEach(element -> element.setItemPlaceholders(placeholders));
    }

    @Override
    public void forceUpdate() {
        area.forceUpdate(menu);
    }

    public void setArea(MultiSlotArea area) {
        this.area = area;
    }
}
