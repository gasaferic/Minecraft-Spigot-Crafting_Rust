package com.gasaferic.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftingInventoryEng {

	public static Inventory openCrafting(Player player) {
		Inventory menucasa = Bukkit.createInventory(player, 9, "§4§lCrafting");

		addItem(339, 0, menucasa, "§8[§cHealing§8]");
		addItem(275, 1, menucasa, "§8[§cTools§8]");
		addItem(324, 2, menucasa, "§8[§cShelter§8]");
		addItem(311, 3, menucasa, "§8[§cEquipment§8]");
		addItem(290, 4, menucasa, "§8[§cGuns§8]");
		addItem(362, 5, menucasa, "§8[§cAmmo§8]");
		addItem(46, 6, menucasa, "§8[§cLooting§8]");
		addItem(280, 7, menucasa, "§8[§cMaterials§8]");
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
