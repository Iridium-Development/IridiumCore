package com.iridium.iridiumcore.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import lombok.AllArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class PagedGUI<T> implements GUI {

    private int page;
    private final int size;
    private final Background background;
    private final Item previousPage;
    private final Item nextPage;

    @Override
    public void addContent(Inventory inventory) {
        InventoryUtils.fillInventory(inventory, background);

        if (isPaged()) {
            inventory.setItem(inventory.getSize() - 3, ItemStackUtils.makeItem(nextPage));
            inventory.setItem(inventory.getSize() - 7, ItemStackUtils.makeItem(previousPage));
        }

        int elementsPerPage = inventory.getSize() - (isPaged() ? 9 : 0);
        List<T> objects = getPageObjects().stream()
                .skip((long) (page - 1) * elementsPerPage)
                .limit(elementsPerPage)
                .collect(Collectors.toList());
        AtomicInteger slot = new AtomicInteger(0);
        for (T t : objects) {
            inventory.setItem(slot.getAndIncrement(), getItemStack(t));
        }
    }

    public abstract Collection<T> getPageObjects();

    public abstract ItemStack getItemStack(T t);

    public int getSize() {
        int newSize = size;
        if (size <= 0) {
            newSize = (int) (Math.ceil(getPageObjects().size() / 9.0) * 9);
        }
        return Math.max(Math.min(newSize, 54), 9);
    }

    public boolean isPaged() {
        return getPageObjects().size() > getSize();
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (!isPaged()) return;
        if (event.getSlot() == getInventory().getSize() - 7) {
            if (page > 1) {
                page--;
                event.getWhoClicked().openInventory(getInventory());
            }
        } else if (event.getSlot() == getInventory().getSize() - 3) {
            if ((event.getInventory().getSize() - 9) * page < getPageObjects().size()) {
                page++;
                event.getWhoClicked().openInventory(getInventory());
            }
        }
    }
}
