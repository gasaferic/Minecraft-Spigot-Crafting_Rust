package com.gasaferic.model;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import com.gasaferic.enums.RecipeType;

public class RustRecipe {

	private RecipeType recipeType;
	private String recipePermission;
	private ItemStack interactItem;
	private ArrayList<ItemStack> recipeMaterials;
	private ItemStack resultItem;
	private boolean isCraftable = true;
	
	public RustRecipe(RecipeType recipeType, String recipePermission, ItemStack interactItem, ArrayList<ItemStack> recipeMaterials, ItemStack resultItem) {
		this.recipeType = recipeType;
		this.recipePermission = recipePermission;
		this.interactItem = interactItem;
		this.recipeMaterials = recipeMaterials;
		this.resultItem = resultItem;
	}
	
	public RustRecipe(RecipeType recipeType, String recipePermission, ItemStack interactItem, ArrayList<ItemStack> recipeMaterials, ItemStack resultItem, boolean isCraftable) {
		this.recipeType = recipeType;
		this.recipePermission = recipePermission;
		this.interactItem = interactItem;
		this.recipeMaterials = recipeMaterials;
		this.resultItem = resultItem;
		this.isCraftable = isCraftable;
	}
	
	public RecipeType getRecipeType() {
		return this.recipeType;
	}
	
	public String getRecipePermission() {
		return this.recipePermission;
	}
	
	public ItemStack getInteractItem() {
		return this.interactItem;
	}
	
	public ArrayList<ItemStack> getRecipeMaterials() {
		return this.recipeMaterials;
	}
	
	public ItemStack getResultItem() {
		return this.resultItem;
	}
	
	public boolean isCraftable() {
		return this.isCraftable;
	}

}