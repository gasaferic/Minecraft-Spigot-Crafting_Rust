package com.gasaferic.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gasaferic.api.RustAPI;
import com.gasaferic.enums.RecipeType;
import com.gasaferic.inventories.CraftingInventory;
import com.gasaferic.inventories.CraftingInventoryEng;
import com.gasaferic.maincraftingrust.Main;
import com.gasaferic.model.Language;
import com.gasaferic.model.RustRecipe;

public class InventoryUtils {

	static RustAPI rustAPI = Main.getRustAPI();
	
	static Main plugin = Main.getInstance();

	public static boolean isCraftingMenu(String menuTitle, Language lang) {

		boolean isCraftingMenu = false;

		for (RecipeType recipeType : RecipeType.values()) {
			String recipeTypeTitle = Main.getInstance().getConfig()
					.getString(recipeType.getTypePlaceholder() + lang.getLang());
			if (menuTitle.equals(recipeTypeTitle)) {
				isCraftingMenu = true;
			}
		}

		return isCraftingMenu;
	}

	public static RustRecipe getRecipeByItem(ItemStack clickedItem) {
		for (RustRecipe recipe : Main.getRustRecipesCrafter().getRustRecipes()) {
			if (recipe.getInteractItem().equals(clickedItem)) {
				return recipe;
			}
		}

		return null;
	}

	public static ItemStack getMainMenuItem() {
		ItemStack mainMenuItem = new ItemStack(Material.WOOL, 1, (short) 14);
		ItemMeta mainMenuItemMeta = mainMenuItem.getItemMeta();
		mainMenuItemMeta.setDisplayName("§cMain Menu");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§6Go back to the main crafting menu");
		mainMenuItemMeta.setLore(lore);
		mainMenuItem.setItemMeta(mainMenuItemMeta);

		return mainMenuItem;
	}

	public static boolean isMainMenuItem(ItemStack clickedItem) {
		if (clickedItem.equals(getMainMenuItem())) {
			return true;
		}

		return false;
	}

	public static void goToMainMenu(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (rustAPI.getLanguage(player).equals(Language.IT)) {
					CraftingInventory.openCraftMenu(player);
				} else if (rustAPI.getLanguage(player).equals(Language.ENG)) {
					CraftingInventoryEng.openCraftMenu(player);
				}
			}
		}, 2L);
	}

}