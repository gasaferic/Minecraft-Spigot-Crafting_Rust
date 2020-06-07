package com.gasaferic.events.menusevents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.gasaferic.enums.RecipeType;
import com.gasaferic.maincraftingrust.Main;

public class SelectCraftingTypeEvent implements Listener {

	private static Main plugin = Main.getInstance();

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory() == null || event.getInventory().getType() == InventoryType.CREATIVE
				|| event.getCurrentItem() == null) {
			return;
		}

		ItemStack clicked = event.getCurrentItem();
		if (event.getInventory().getTitle().equalsIgnoreCase("§4§lCrafting")) {
			event.setCancelled(true);
			switch (clicked.getType()) {
			case WOOD_HOE:
				openTypeMenu(player, RecipeType.WEAPON);
				break;
			case STONE_AXE:
				openTypeMenu(player, RecipeType.TOOL);
				break;
			case PAPER:
				openTypeMenu(player, RecipeType.HEALING);
				break;
			case DIAMOND_CHESTPLATE:
				openTypeMenu(player, RecipeType.EQUIPMENT);
				break;
			case STICK:
				openTypeMenu(player, RecipeType.MATERIAL);
				break;
			case MELON_SEEDS:
				openTypeMenu(player, RecipeType.AMMUNITION);
				break;
			case WOOD_DOOR:
				openTypeMenu(player, RecipeType.SHELTER);
				break;
			case TNT:
				openTypeMenu(player, RecipeType.LOOTING);
				break;
			default:
				player.closeInventory();
				break;
			}
		}
	}

	public void openTypeMenu(Player player, RecipeType recipeType) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				player.openInventory(Main.getRustRecipesCrafter().buildPlayerMenu(recipeType, player));
			}
		}, 1L);
	}

}