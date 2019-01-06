package com.gui.pages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class AbstractGui implements InventoryHolder {

    private Player player;
    private Inventory inventory;

    private int size = 9;
    private String name = "gui";
    private boolean override = false;

    public AbstractGui(Player player, int size, String name) {
        this.player = player;
        if(size != -1 && size % 9 == 0) this.size = size;
        if(name != null) this.name = name;
        this.inventory = Bukkit.createInventory(this, this.size, this.name);
    }

    public AbstractGui(Player player, int size) {
        this(player, size, null);
    }

    public AbstractGui(Player player, String name) {
        this(player, -1, name);
    }

    public AbstractGui(Player player) {
        this(player, null);
    }

    public final void open() {
        this.build();
        this.player.openInventory(this.inventory);
        this.onInventoryOpenEvent();
    }

    public final void close() {
        this.player.closeInventory();
    }

    public void build() {}

    public abstract void onInventoryCloseEvent();

    public abstract void onInventoryOpenEvent();

    public abstract void onInventoryClickEvent(InventoryClickEvent e);

    public abstract void onInventoryMoveItemEvent(InventoryMoveItemEvent e);

    public abstract void onInventoryDragEvent(InventoryDragEvent e);

    public Player getPlayer() {
        return player;
    }

    @Override
    public final Inventory getInventory() {
        return this.inventory;
    }

    public final int getSize() {
        return size;
    }

    public final String getName() {
        return name;
    }

    public final InventoryHolder getHolder() {
        return this;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean ifOverride() {
        if(this.override) {
            this.override = false;
            return true;
        } else return false;
    }

    protected void updateResetInventory() {
        this.override = true;
        this.inventory.clear();
        this.inventory = Bukkit.createInventory(this.getHolder(), this.size, this.name);
        this.open();
    }
}
