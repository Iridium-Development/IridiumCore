package com.iridium.iridiumcore.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class PagedGUI<T> implements GUI {

    private final int page;
    private final Background background;
    private final Item previousPage;
    private final Item nextPage;

    @Override
    public void addContent(Inventory inventory) {
        InventoryUtils.fillInventory(inventory, background);

        inventory.setItem(inventory.getSize() - 3, ItemStackUtils.makeItem(nextPage));
        inventory.setItem(inventory.getSize() - 7, ItemStackUtils.makeItem(previousPage));

        int elementsPerPage = inventory.getSize() - 9;
        List<T> objects = getPageObjects().stream()
                .skip((long) (page - 1) * elementsPerPage)
                .limit(elementsPerPage)
                .collect(Collectors.toList());
        AtomicInteger slot = new AtomicInteger(0);
        for (T t : objects) {
            inventory.setItem(slot.getAndIncrement(), getItemStack(t));
        }
    }

    public abstract List<T> getPageObjects();

    public abstract ItemStack getItemStack(T t);
}
