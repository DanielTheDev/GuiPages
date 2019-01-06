package com.gui.pages;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PluginClass extends JavaPlugin {

    GuiManager guiManager = new GuiManager();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(guiManager, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AbstractGui gui = new ContentGui((Player) sender, 18);

        List<ItemStack> list = new ArrayList();
        for(int i = 0; i < 20; i++) {
            int finalI = i;
            list.add(new ItemStack(Material.WATCH) {{
                ItemMeta meta = this.getItemMeta();
                meta.setDisplayName("item: " + finalI);
                this.setItemMeta(meta);
            }});
        }

        ((ContentGui) gui).setContent(list);

        guiManager.registerGui((Player) sender, gui);
        return true;
    }


}
