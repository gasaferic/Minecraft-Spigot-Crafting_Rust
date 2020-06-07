package com.gasaferic.enums;

public enum RecipeType {

	WEAPON("weapon_"), TOOL("tool_"), HEALING("healing_"), EQUIPMENT("equipment_"), MATERIAL("material_"),
	AMMUNITION("ammunition_"), SHELTER("shelter_"), LOOTING("looting_");

	private String typePlaceholder;

	RecipeType(String typePlaceholder) {
		this.typePlaceholder = typePlaceholder;
	}

	public String getTypePlaceholder() {
		return this.typePlaceholder;
	}

}