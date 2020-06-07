package com.gasaferic.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gasaferic.api.RustAPI;
import com.gasaferic.inventories.CraftingInventory;
import com.gasaferic.inventories.CraftingInventoryEng;
import com.gasaferic.model.Language;

public class CraftingC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§4[Rust] - §6Devi essere un player.");
			return false;
		}
		Player player = (Player) sender;
		RustAPI api = new RustAPI();
		if (api.getLanguage(player).equals(Language.IT)) {
			CraftingInventory.openCraftMenu(player);
		} else if (api.getLanguage(player).equals(Language.ENG)) {
			CraftingInventoryEng.openCraftMenu(player);
		}
		return false;
	}
}