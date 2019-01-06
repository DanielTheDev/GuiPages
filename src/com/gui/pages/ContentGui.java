package com.gui.pages;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ContentGui extends AbstractGui {

    private boolean init = true;

    private int page = 1;
    private int display_offset = 0;
    private int display_size = 9;

    private List<ItemStack> content;
    private int max_page;

    public ContentGui(Player player, int size) {
        super(player, size, "");
    }

    public List<ItemStack> getContent() {
        return this.content;
    }

    public boolean setPage(int page) {
        if(page < 1 || page > this.max_page) {
            return false;
        }
        this.page = page;
        this.update();
        return true;
    }

    private void updateName(){
        this.setName("page: " + this.page+"/"+this.max_page);
    }

    private void update() {
        this.updateName();
        this.updateResetInventory();
    }

    public void setContent(List<ItemStack> content) {
        this.content = content;
        int size = content.size();
        this.max_page = (size % display_size > 0 ? ((size / display_size)+1) : size / display_size);
    }

    @Override
    public void build() {
        if(content != null) {
            if(this.page == this.max_page && this.page > 1) {
                this.getInventory().setItem(this.getSize()-9, ItemList.previous_page);
            } else if(this.page == 1) {
                this.getInventory().setItem(this.getSize()-1, ItemList.next_page);
            } else if(this.page != this.max_page){
                this.getInventory().setItem(this.getSize()-1, ItemList.next_page);
                this.getInventory().setItem(this.getSize()-9, ItemList.previous_page);
            }

            int size = this.getContent().size();
            int loc = 0;
            for(int i = this.display_offset; i < this.display_offset+this.display_size; i++) {
                loc = i + ((this.display_size*page)-this.display_size);
                if(size == loc) {
                    break;
                } else {
                    this.getInventory().setItem(i, this.content.get(loc));
                }
            }
        }
    }

    public void setDisplay_offset(int display_offset) {
        this.display_offset = display_offset;
    }

    public void setDisplay_size(int display_size) {
        this.display_size = display_size;
    }

    @Override
    public void onInventoryCloseEvent() {
        this.content.clear();
    }

    @Override
    public void onInventoryOpenEvent() {
        if(init) {
            this.init = false;
            this.update();
        }
    }

    @Override
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if(e.getSlot() == this.getSize()-9) {
            this.setPage(this.page - 1);
        } else if(e.getSlot() == this.getSize() -1) {
            this.setPage(this.page + 1);
        }
        e.setCancelled(true);
    }

    @Override
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onInventoryDragEvent(InventoryDragEvent e) {
        e.setCancelled(true);
    }

    public int getPage() {
        return page;
    }

    public int getDisplay_offset() {
        return display_offset;
    }

    public int getDisplay_size() {
        return display_size;
    }

    public int getMax_page() {
        return max_page;
    }
}
