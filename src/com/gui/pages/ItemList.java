package com.gui.pages;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemList {

    public static final ItemStack previous_page = new ItemStack(Material.ARROW) {{
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName("Next Page");
        this.setItemMeta(meta);
    }};

    public static final ItemStack next_page = new ItemStack(Material.ARROW) {{
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName("Next Page");
        this.setItemMeta(meta);
    }};

}
