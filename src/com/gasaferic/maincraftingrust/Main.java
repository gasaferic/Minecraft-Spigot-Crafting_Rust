package com.gasaferic.maincraftingrust;

import java.io.File;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gasaferic.api.RustAPI;
import com.gasaferic.commands.CraftingC;
import com.gasaferic.events.menusevents.SelectCraftingTypeEvent;
import com.gasaferic.events.playerlistener.OpenCraftingMenu;
import com.gasaferic.managers.RecipeLoader;
import com.gasaferic.managers.RustRecipesCrafter;

public class Main extends JavaPlugin {

	private static Main instance;

	private static RustAPI rustAPI;
	private static RustRecipesCrafter rustRecipesCrafter;
	private static RecipeLoader recipeLoader;

	@Override
	public void onEnable() {

		instance = this;

		PluginDescriptionFile pdfFile = getDescription();

		rustAPI = new RustAPI();

		if (rustAPI.isPluginEnabled()) {

			rustRecipesCrafter = new RustRecipesCrafter();
			recipeLoader = new RecipeLoader();

			registerEvents();
			registerCommands();
			registerConfig();

			File recipesJSON = new File(this.getDataFolder(), "recipes.json");
			try {
				if (recipesJSON.exists()) {
					recipeLoader.loadFromFile(recipesJSON);
				} else {
					recipesJSON.createNewFile();
					recipeLoader.loadFromFile(recipesJSON);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			rustRecipesCrafter.buildMenus();

			Bukkit.getConsoleSender().sendMessage("§8[§9§l" + pdfFile.getName() + "§8]§r"
					+ " §a§lhas been enabled on version " + pdfFile.getVersion());

		} else {
			Bukkit.getConsoleSender().sendMessage("§8[§9§l" + pdfFile.getName() + "§8]§r"
					+ " §a§lImpossibile avviare RustCrafting (Rust non avviato)");
		}

	}

	@Override
	public void onDisable() {

		PluginDescriptionFile pdfFile = getDescription();

		Bukkit.getConsoleSender().sendMessage("§8[§9§l" + pdfFile.getName() + "§8]§r" + " §c§lhas been disabled");
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new OpenCraftingMenu(), this);
		pm.registerEvents(new SelectCraftingTypeEvent(), this);
		pm.registerEvents(rustRecipesCrafter, this);

	}

	public void registerCommands() {

		getCommand("c").setExecutor(new CraftingC());

	}

	public static Main getInstance() {
		return instance;
	}

	public static RustAPI getRustAPI() {
		return rustAPI;
	}

	public static RustRecipesCrafter getRustRecipesCrafter() {
		return rustRecipesCrafter;
	}

	public static RecipeLoader getRecipeLoader() {
		return recipeLoader;
	}

	public static Location getRandomLocation() {
		World world = Bukkit.getWorld("world");
		Random rand = new Random();
		// Adjust the range max for the maximum X and Z value, and the range min for the
		// minimum X and Z value.
		int rangeMax = 500;
		int rangeMin = -500;

		int X = rand.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
		int Z = rand.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
		int Y = world.getHighestBlockYAt(X, Z);

		return new Location(world, X, Y, Z).add(0.5, 50, 0.5);
	}

	public void registerConfig() {

		saveDefaultConfig();
		saveConfig();
	}

	public Location fromString(String loc) {
		loc = loc.substring(loc.indexOf("{") + 1);
		loc = loc.substring(loc.indexOf("{") + 1);
		String worldName = loc.substring(loc.indexOf("=") + 1, loc.indexOf("}"));
		loc = loc.substring(loc.indexOf(",") + 1);
		String xCoord = loc.substring(loc.indexOf("=") + 1, loc.indexOf(","));
		loc = loc.substring(loc.indexOf(",") + 1);
		String yCoord = loc.substring(loc.indexOf("=") + 1, loc.indexOf(","));
		loc = loc.substring(loc.indexOf(",") + 1);
		String zCoord = loc.substring(loc.indexOf("=") + 1, loc.indexOf(","));
		loc = loc.substring(loc.indexOf(",") + 1);
		String pitch = loc.substring(loc.indexOf("=") + 1, loc.indexOf(","));
		loc = loc.substring(loc.indexOf(",") + 1);
		String yaw = loc.substring(loc.indexOf("=") + 1, loc.indexOf("}"));
		return new Location(Bukkit.getWorld(worldName), Double.parseDouble(xCoord), Double.parseDouble(yCoord),
				Double.parseDouble(zCoord), Float.parseFloat(yaw), Float.parseFloat(pitch));
	}
}
