package com.gasaferic.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftingInventory {

	public static Inventory openCrafting(Player player) {
		Inventory menucasa = Bukkit.createInventory(player, 9, "§4§lCrafting");

		addItem(339, 0, menucasa, "§8[§cCure§8]");
		addItem(275, 1, menucasa, "§8[§cAttrezzi§8]");
		addItem(324, 2, menucasa, "§8[§cRifugio§8]");
		addItem(311, 3, menucasa, "§8[§cEquipaggiamento§8]");
		addItem(290, 4, menucasa, "§8[§cArmi§8]");
		addItem(362, 5, menucasa, "§8[§cMunizioni§8]");
		addItem(46, 6, menucasa, "§8[§cSaccheggio§8]");
		addItem(280, 7, menucasa, "§8[§cMateriali§8]");
		return menucasa;

	}

	@SuppressWarnings("deprecation")
	public static void addItem(int id, int slot, Inventory inventory, String name) {
		ItemStack item = new ItemStack(id);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		inventory.setItem(slot, item);
	}

	public static void openCraftMenu(Player player) {

		Inventory menucasa = openCrafting(player);
		;
		player.openInventory(menucasa);

	}
}
