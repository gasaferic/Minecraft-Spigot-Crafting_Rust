package com.gasaferic.events.playerlistener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gasaferic.utils.InventoryUtils;

public class OpenCraftingMenu implements Listener {

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		if (e.getClickedBlock() != null) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getType() == Material.WORKBENCH) {
					Player player = (Player) e.getPlayer();
					InventoryUtils.goToMainMenu(player);
				}
			}
		}
	}
}