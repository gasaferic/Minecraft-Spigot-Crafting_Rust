/*
 * THIS MADE BY GASAFERIC MADAFAKAS
 */

package com.gasaferic.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gasaferic.enums.RecipeType;
import com.gasaferic.maincraftingrust.Main;
import com.gasaferic.model.RustRecipe;

import net.md_5.bungee.api.ChatColor;

public class RecipeLoader {

	RustRecipesCrafter rustRecipesCrafter = Main.getRustRecipesCrafter();

	private JSONParser parser = new JSONParser();

	public void loadFromFile(File file) throws FileNotFoundException, IOException, ParseException {

		JSONObject jsonObj = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(file)));

		JSONArray recipesJSONObj = (JSONArray) jsonObj.get("Recipes");

		for (Object recipeObj : recipesJSONObj) {
			rustRecipesCrafter.registerRustRecipe(rustRecipeFromJSONObj((JSONObject) recipeObj));
		}

	}

	public RustRecipe rustRecipeFromJSONObj(JSONObject recipeJSONObj) {

		RecipeType recipeType = RecipeType.valueOf((String) recipeJSONObj.get("recipeType"));

		String recipePermission = (String) recipeJSONObj.get("recipePermission");

		ItemStack recipeItem = itemStackFromJSONObj((JSONObject) recipeJSONObj.get("recipeItem"));

		ArrayList<ItemStack> recipeMaterials = new ArrayList<ItemStack>();

		JSONObject recipeMaterialsObj = (JSONObject) recipeJSONObj.get("recipeMaterials");

		for (Object recipeMaterialObj : recipeMaterialsObj.keySet()) {
			recipeMaterials.add(itemStackFromJSONObj((JSONObject) recipeMaterialsObj.get(recipeMaterialObj)));
		}

		ItemStack recipeResultItem;

		boolean useRecipeItemAsResult = (boolean) recipeJSONObj.get("useRecipeItemAsResult");
		if (useRecipeItemAsResult) {
			recipeResultItem = recipeItem;
		} else {
			if (recipeJSONObj.get("resultItem") == null) {
				recipeResultItem = new ItemStack(recipeItem.getType(), recipeItem.getAmount(),
						recipeItem.getDurability());
			} else {
				ItemStack resultItem = itemStackFromJSONObj((JSONObject) recipeJSONObj.get("resultItem"));
				recipeResultItem = resultItem;
			}
		}

		boolean isCraftable = true;
		if (recipeJSONObj.get("isCraftable") != null) {
			isCraftable = (boolean) recipeJSONObj.get("isCraftable");
		}
		
		if (isCraftable) {
			return new RustRecipe(recipeType, recipePermission, recipeItem, recipeMaterials, recipeResultItem);
		} else {
			return new RustRecipe(recipeType, recipePermission, recipeItem, recipeMaterials, recipeResultItem, isCraftable);
		}
	}

	public ItemStack itemStackFromJSONObj(JSONObject recipeJSONObj) {

		Material material = Material.valueOf((String) recipeJSONObj.get("Material"));

		long amount = (long) recipeJSONObj.get("Amount");

		long durability = (long) recipeJSONObj.get("Durability");

		ItemStack itemStack = new ItemStack(material, (int) amount, (short) durability);

		ItemMeta itemMeta = itemStack.getItemMeta();

		Object nameObj = recipeJSONObj.get("Name");

		if (nameObj != null) {
			itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', (String) nameObj));
		}

		JSONArray loreObj = (JSONArray) recipeJSONObj.get("Lore");

		if (loreObj != null) {
			ArrayList<String> lore = new ArrayList<String>();

			for (Object obj : loreObj) {
				lore.add(ChatColor.translateAlternateColorCodes('&', (String) obj));
				itemMeta.setLore(lore);
			}
		}

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@Deprecated
	public RustRecipe rustRecipeFromString(String recipeString) {

		// Example String
		String recipeExampleString = "RustRecipe(RecipeType=SHELTER,RecipePermission=shelter.shelter,"
				+ "InteractItem={Material=JUKEBOX,Amount=1,Durability=0,hasDisplayName=true,DisplayName=§9§lCasa,hasLore=true,Lore=§6300 Legno[l_sep]§69 Ferro},"
				+ "RecipeMaterials={{Material=LOG,Amount=300,Durability=0,hasDisplayName=false,DisplayName=,hasLore=false,Lore=}[m_sep]"
				+ "{Material=IRON_INGOT,Amount=9,Durability=0,hasDisplayName=false,DisplayName=,hasLore=false,Lore=}})";
		recipeExampleString = recipeExampleString + "";

		recipeString = recipeString.substring(recipeString.indexOf("(") + 1, recipeString.lastIndexOf(")"));
		RecipeType recipeType = RecipeType
				.valueOf(recipeString.substring(recipeString.indexOf("=") + 1, recipeString.indexOf(",")));

		recipeString = recipeString.substring(recipeString.indexOf(",") + 1);

		String recipePermission = recipeString.substring(recipeString.indexOf("=") + 1, recipeString.indexOf(","));

		recipeString = recipeString.substring(recipeString.indexOf(",") + 1);

		ItemStack interactItem = itemStackFromString(
				recipeString.substring(recipeString.indexOf("=") + 1, recipeString.indexOf("}") + 1));

		System.out.println(interactItem);

		recipeString = recipeString.substring(recipeString.indexOf("}") + 1);

		ArrayList<ItemStack> recipeMaterials = new ArrayList<ItemStack>();
		for (String itemStackStrings : recipeString
				.substring(recipeString.indexOf("{") + 1, recipeString.lastIndexOf("}")).replace("[m_sep]", "msep")
				.split("msep")) {
			recipeMaterials.add(itemStackFromString(itemStackStrings));
		}

		ItemStack resultItem = new ItemStack(interactItem.getType(), interactItem.getAmount(),
				interactItem.getDurability());

		return new RustRecipe(recipeType, recipePermission, interactItem, recipeMaterials, resultItem);
	}

	@Deprecated
	public ItemStack itemStackFromString(String itemStackString) {

		ItemStack item;

		itemStackString = itemStackString.substring(itemStackString.indexOf("{") + 1, itemStackString.lastIndexOf("}"));

		Material material = Material
				.valueOf(itemStackString.substring(itemStackString.indexOf("=") + 1, itemStackString.indexOf(",")));

		itemStackString = itemStackString.substring(itemStackString.indexOf(",") + 1);

		int amount = Integer
				.valueOf(itemStackString.substring(itemStackString.indexOf("=") + 1, itemStackString.indexOf(",")));

		itemStackString = itemStackString.substring(itemStackString.indexOf(",") + 1);

		int durability = Integer
				.valueOf(itemStackString.substring(itemStackString.indexOf("=") + 1, itemStackString.indexOf(",")));

		itemStackString = itemStackString.substring(itemStackString.indexOf(",") + 1);

		boolean hasDisplayName = Boolean
				.valueOf(itemStackString.substring(itemStackString.indexOf("=") + 1, itemStackString.indexOf(",")));

		itemStackString = itemStackString.substring(itemStackString.indexOf(",") + 1);

		String displayName = itemStackString.substring(itemStackString.indexOf("=") + 1, itemStackString.indexOf(","));

		itemStackString = itemStackString.substring(itemStackString.indexOf(",") + 1);

		boolean hasLore = Boolean
				.valueOf(itemStackString.substring(itemStackString.indexOf("=") + 1, itemStackString.indexOf(",")));

		itemStackString = itemStackString.substring(itemStackString.indexOf(",") + 1);

		String lores = itemStackString.substring(itemStackString.indexOf("=") + 1);
		ArrayList<String> lore = new ArrayList<String>();

		for (String loreString : lores.replace("[l_sep]", "sep").split("sep")) {
			lore.add(loreString);
		}

		System.out.println(lores);

		item = new ItemStack(material, amount, (short) durability);

		if (item != null) {
			ItemMeta itemMeta = item.getItemMeta();

			if (hasDisplayName) {
				System.out.println(displayName);
				itemMeta.setDisplayName(displayName);
			}

			if (hasLore) {
				System.out.println(lore);
				itemMeta.setLore(lore);
			}

			item.setItemMeta(itemMeta);
		}

		return item;

	}

}