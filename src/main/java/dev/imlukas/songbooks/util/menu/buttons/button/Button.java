package dev.imlukas.songbooks.util.menu.buttons.button;

import dev.imlukas.songbooks.util.menu.element.MenuElement;
import dev.imlukas.songbooks.util.runnables.AttachableConsumer;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Button implements MenuElement {

    private boolean allowRemoval = false;
    private ItemStack displayItem;

    private AttachableConsumer<InventoryClickEvent> clickTask;
    @Setter
    private Consumer<ItemStack> clickWithItemTask;

    private Collection<Placeholder<Player>> placeholders;

    public Button(ItemStack displayItem) {
        this.displayItem = displayItem;
        this.clickTask = new AttachableConsumer<>(event -> {
        });
    }

    public Button(ItemStack displayItem, Consumer<InventoryClickEvent> clickTask) {
        this.displayItem = displayItem;
        this.clickTask = new AttachableConsumer<>(clickTask);
    }

    public <T extends Button> T setClickAction(Consumer<InventoryClickEvent> clickTask) {
        this.clickTask = new AttachableConsumer<>(clickTask);
        return (T) this;
    }

    public <T extends Button> T onRightClick(Runnable clickTask) {
        this.clickTask.attach(event -> {
            if (event.getClick() == ClickType.RIGHT) {
                clickTask.run();
            }
        });

        return (T) this;
    }

    public <T extends Button> T onLeftClick(Runnable clickTask) {
        this.clickTask.attach(event -> {
            if (event.getClick() == ClickType.LEFT) {
                clickTask.run();
            }
        });

        return (T) this;
    }

    public <T extends Button> T onMiddleClick(Runnable clickTask) {
        this.clickTask.attach(event -> {
            if (event.getClick() == ClickType.MIDDLE) {
                clickTask.run();
            }
        });

        return (T) this;
    }

    @Override
    public void handle(InventoryClickEvent event) {
        if (!event.getCursor().getType().isAir()) {
            if (event.getCurrentItem() != null && !event.getCurrentItem().getType().isAir()) {
                if (clickWithItemTask != null) {
                    clickWithItemTask.accept(event.getCursor());
                    return;
                }
            }
        }

        if (clickTask == null) {
            return;
        }

        clickTask.accept(event);

    }

    public void setAllowRemoval(boolean allowRemoval) {
        this.allowRemoval = allowRemoval;
    }

    @Override
    public boolean canRemove() {
        return allowRemoval;
    }

    @Override
    public void setDisplayItem(ItemStack displayItem) {
        this.displayItem = displayItem;
    }

    @Override
    public void setItemMaterial(Material material) {
        displayItem.setType(material);
    }

    @Override
    public Button copy() {
        return new Button(false, displayItem.clone(), clickTask, clickWithItemTask, placeholders);
    }

    @Override
    public Collection<Placeholder<Player>> getItemPlaceholders() {
        return placeholders;
    }

    @Override
    public Button setItemPlaceholders(Collection<Placeholder<Player>> placeholders) {
        this.placeholders = placeholders;
        return this;
    }

    @SafeVarargs
    @Override
    public final MenuElement setItemPlaceholders(Placeholder<Player>... placeholders) {
        this.placeholders = Arrays.stream(placeholders).toList();
        return this;
    }
}
