package com.gasaferic.managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gasaferic.api.RustAPI;
import com.gasaferic.enums.RecipeType;
import com.gasaferic.maincraftingrust.Main;
import com.gasaferic.model.Language;
import com.gasaferic.model.RustRecipe;
import com.gasaferic.utils.InventoryUtils;

public class RustRecipesCrafter implements Listener {

	RustAPI rustAPI = Main.getRustAPI();

	public ArrayList<RustRecipe> rustRecipes = new ArrayList<RustRecipe>();
	public HashMap<RecipeType, Inventory> inventories = new HashMap<RecipeType, Inventory>();

	public RustRecipesCrafter() {
	}

	public void registerRustRecipe(RustRecipe rustRecipe) {
		if (!rustRecipes.contains(rustRecipe)) {
			rustRecipes.add(rustRecipe);
		}
	}
	
	public ArrayList<RustRecipe> getRustRecipes() {
		return rustRecipes;
	}

	public void buildMenus() {
		for (RecipeType recipeType : RecipeType.values()) {
			ArrayList<RustRecipe> recipesForType = new ArrayList<RustRecipe>();
			for (RustRecipe recipe : rustRecipes) {
				if (recipe.getRecipeType() == recipeType) {
					recipesForType.add(recipe);
				}
			}
			inventories.put(recipeType, buildMenu(recipeType, recipesForType));
		}

	}

	public Inventory buildMenu(RecipeType recipeType, ArrayList<RustRecipe> recipes) {
		Inventory inventory = Bukkit.createInventory(null, (recipes.size() + 9 - 1) / 9 * 9, "§4§lCrafting");

		int inventorySlot = 0;

		for (RustRecipe recipe : recipes) {
			inventory.setItem(inventorySlot, recipe.getInteractItem());
			inventorySlot++;
		}

		return inventory;
	}

	public Inventory buildPlayerMenu(RecipeType recipeType, Player player) {

		Inventory inventory = this.inventories.get(recipeType);

		Inventory finalInventory = Bukkit.createInventory(player, inventory.getSize(), Main.getInstance().getConfig()
				.getString(recipeType.getTypePlaceholder() + rustAPI.getLanguage(player).getLang()));

		ItemStack noPermissionItem = new ItemStack(Material.GHAST_TEAR, 1);
		ItemMeta noPermItemMeta = noPermissionItem.getItemMeta();
		noPermItemMeta.setDisplayName("§f§l?");
		noPermissionItem.setItemMeta(noPermItemMeta);

		int inventorySlot = 0;

		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				for (RustRecipe recipe : rustRecipes) {
					if (item.equals(recipe.getInteractItem())) {
						String recipePermission = recipe.getRecipePermission();
						if (recipePermission != null) {
							if (player.hasPermission(recipe.getRecipePermission())) {
								finalInventory.setItem(inventorySlot, item);
							} else {
								finalInventory.setItem(inventorySlot, noPermissionItem);
							}
						} else {
							finalInventory.setItem(inventorySlot, item);
						}
					}
				}
				inventorySlot++;
			}
		}

		finalInventory.setItem(finalInventory.getSize() - 1, InventoryUtils.getMainMenuItem());

		return finalInventory;
	}

	@EventHandler
	public void onRecipeClick(InventoryClickEvent e) {

		if (e.getInventory() == null || e.getCurrentItem() == null
				|| e.getInventory().getType() == InventoryType.CREATIVE) {
			return;
		}

		Player player;
		if (e.getWhoClicked() instanceof Player) {
			player = (Player) e.getWhoClicked();
		} else {
			return;
		}

		Language lang = rustAPI.getLanguage(player);

		Inventory inventory = e.getInventory();
		ItemStack clickedItem = e.getCurrentItem();

		if (e.getRawSlot() == e.getSlot()) {
			if (InventoryUtils.isCraftingMenu(inventory.getName(), lang)) {

				RustRecipe recipe = InventoryUtils.getRecipeByItem(clickedItem);

				if (recipe != null) {

					boolean hasCraftingMaterials = true;

					if (recipe.isCraftable()) {
						for (ItemStack recipeItem : recipe.getRecipeMaterials()) {
							if (!player.getInventory().containsAtLeast(recipeItem, recipeItem.getAmount())) {
								player.closeInventory();
								player.sendMessage(Main.getInstance().getConfig()
										.getString("rust_not_enough_materials_" + lang.getLang()));
								hasCraftingMaterials = false;
								break;
							}
						}

						if (hasCraftingMaterials) {
							for (ItemStack recipeItem : recipe.getRecipeMaterials()) {
								player.getInventory().removeItem(recipeItem);
							}

							player.getInventory().addItem(recipe.getResultItem());
						}
					}

				} else if (InventoryUtils.isMainMenuItem(clickedItem)) {
					InventoryUtils.goToMainMenu(player);
				}

				e.setCancelled(true);
			}
		}

	}

}