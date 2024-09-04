package com.iridium.iridiumcore.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class PagedGUI<T> implements GUI {

    @Getter
    private int page;
    private final int size;
    private final Background background;
    private final Item previousPage;
    private final Item nextPage;
    private final Inventory previousInventory;
    private final Item backButton;
    private final Map<Integer, T> items = new HashMap<>();

    public PagedGUI(int page, int size, Background background, Item previousPage, Item nextPage) {
        this.page = page;
        this.size = size;
        this.background = background;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.previousInventory = null;
        this.backButton = null;
    }

    public PagedGUI(int page, int size, Background background, Item previousPage, Item nextPage, Player player, Item backButton) {
        this.page = page;
        this.size = size;
        this.background = background;
        this.previousPage = previousPage;
        this.nextPage = nextPage;

        if (player == null) {
            this.previousInventory = null;
        } else {
            Inventory previousInventory = IridiumCore.getInstance().getIridiumInventory().getTopInventory(player);
            this.previousInventory = previousInventory.getType() == InventoryType.CHEST ? previousInventory : null;
        }

        this.backButton = backButton;
    }

    @Override
    public void addContent(Inventory inventory) {
        items.clear();
        InventoryUtils.fillInventory(inventory, background);

        if (isPaged()) {
            inventory.setItem(inventory.getSize() - 3, ItemStackUtils.makeItem(nextPage));
            inventory.setItem(inventory.getSize() - 7, ItemStackUtils.makeItem(previousPage));
        }

        int elementsPerPage = inventory.getSize() - (isPaged() || previousInventory != null ? 9 : 0);
        List<T> objects = getPageObjects().stream()
                .skip((long) (page - 1) * elementsPerPage)
                .limit(elementsPerPage)
                .collect(Collectors.toList());
        AtomicInteger slot = new AtomicInteger(0);
        for (T t : objects) {
            int currentSlot = slot.getAndIncrement();
            items.put(currentSlot, t);
            inventory.setItem(currentSlot, getItemStack(t));
        }
        if (previousInventory != null && backButton != null) {
            inventory.setItem(inventory.getSize() + backButton.slot, ItemStackUtils.makeItem(backButton));
        }
    }

    public abstract Collection<T> getPageObjects();

    public abstract ItemStack getItemStack(T t);

    public T getItem(int slot) {
        return items.get(slot);
    }

    public Optional<Integer> getSlot(T t) {
        return items.keySet().stream().filter(slot -> getItem(slot).equals(t)).findFirst();
    }

    public int getSize() {
        int newSize = size;
        if (size <= 0) {
            newSize = (int) (Math.ceil(getPageObjects().size() / 9.0) * 9);
        }
        return Math.max(Math.min(newSize + 9, 54), 9);
    }

    public boolean isPaged() {
        return getPageObjects().size() > getSize();
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (previousInventory != null && event.getSlot() == (event.getInventory().getSize() + backButton.slot)) {
            event.getWhoClicked().openInventory(previousInventory);
            return;
        }

        if (isPaged()) {
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
}
