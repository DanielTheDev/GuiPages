package com.gui.pages;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.LinkedHashMap;
import java.util.Map;

public class GuiManager implements Listener {

    private final Map<Player, AbstractGui> guiMap = new LinkedHashMap<>();

    public void registerGui(Player player, AbstractGui gui) {
        if(this.guiMap.containsKey(player)) {
            player.closeInventory();
        }
        this.guiMap.put(player, gui);
        gui.open();
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event){
        if (this.validatePlayer(event.getPlayer())) {
            AbstractGui gui = guiMap.get(event.getPlayer());
            guiMap.remove(gui);
            gui.close();
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event){
        if(event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            if (this.validatePlayer(player)) {
                AbstractGui gui = guiMap.get(player);
                if(!gui.ifOverride()) {
                    gui.onInventoryCloseEvent();
                    guiMap.remove(player);
                    gui.close();
                }
            }
        }
    }

    @EventHandler
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent event){
        if(event.getSource().getHolder() instanceof Player) {
            Player player = (Player) event.getSource().getHolder();
            if(this.validatePlayer(player)) {
                if(this.validateInventoryHolder(event.getDestination().getHolder()) || this.validateInventoryHolder(event.getSource().getHolder()) || this.validateInventoryHolder(event.getInitiator().getHolder())) {
                    AbstractGui gui = this.getGui(player);
                    gui.onInventoryMoveItemEvent(event);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent event){
        if(this.validateInventoryHolder(event.getInventory().getHolder())) {
            if(event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if(this.validatePlayer(player)) {
                    AbstractGui gui = this.getGui(player);
                    gui.onInventoryDragEvent(event);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        if(this.validateInventoryHolder(event.getInventory().getHolder())) {
            if(event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if(this.validatePlayer(player)) {
                    AbstractGui gui = this.getGui(player);
                    gui.onInventoryClickEvent(event);
                }
            }
        }
    }

    public AbstractGui getGui(Player player) {
        return this.guiMap.getOrDefault(player, null);
    }

    public boolean validatePlayer(Player player) {
        return this.guiMap.containsKey(player);
    }

    public boolean validateInventoryHolder(InventoryHolder holder) {
        for(AbstractGui gui : this.guiMap.values()) {
            if(gui.getHolder().equals(holder)) {
                return true;
            }
        }
        return false;
    }
}
