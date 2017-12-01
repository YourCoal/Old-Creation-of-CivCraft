package com.avrgaming.civcraft.items.components;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import com.avrgaming.civcraft.exception.CivException;
import com.avrgaming.civcraft.main.CivGlobal;
import com.avrgaming.civcraft.main.CivMessage;
import com.avrgaming.civcraft.object.Resident;
import com.avrgaming.civcraft.siege.Cannon;
import com.avrgaming.civcraft.util.CivColor;
import com.avrgaming.civcraft.war.War;

import gpl.AttributeUtil;

public class BuildCannon extends ItemComponent {

	public void onInteract(PlayerInteractEvent event) {
		try {
			if (!War.isWarTime()) {
				throw new CivException("Cannons can only be deployed during WarTime.");
			}
			
			if (event.getPlayer().getInventory().getItemInOffHand().getType() != Material.AIR) {
				CivMessage.sendError(event.getPlayer(), "You cannot have items in your offhand!");
				return;
			}
			
			Resident resident = CivGlobal.getResident(event.getPlayer());
			Cannon.newCannon(resident);
			
			CivMessage.sendCiv(resident.getCiv(), "We've deployed a cannon at "+
					event.getPlayer().getLocation().getBlockX()+","+
					event.getPlayer().getLocation().getBlockY()+","+
					event.getPlayer().getLocation().getBlockZ());
			
			event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
		} catch (CivException e) {
			CivMessage.sendError(event.getPlayer(), e.getMessage());
		}
	}

	@Override
	public void onPrepareCreate(AttributeUtil attrUtil) {
		attrUtil.addLore(ChatColor.RESET+CivColor.Gold+"Deploys War Cannon");
		attrUtil.addLore(ChatColor.RESET+CivColor.Rose+"<Right Click To Use>");	
	}
}
