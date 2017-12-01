package com.avrgaming.civcraft.threading.timers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.avrgaming.civcraft.listener.ActionBar;
import com.avrgaming.civcraft.main.CivGlobal;
import com.avrgaming.civcraft.object.Resident;
import com.avrgaming.civcraft.util.CivColor;

public class ActionBarUpdateTimer implements Runnable {
	
	@Override
	public void run() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			Resident res = CivGlobal.getResident(p);
			
			String civ;
			if (res.getCiv() == null) {
				civ = "-----";
			} else {
				civ = res.getCiv().getName();
			}
			
			String town;
			if (res.getTown() == null) {
				town = "-----";
			} else {
				town = res.getTown().getName();
			}
			
			int money = (int) res.getTreasury().getBalance();
			int exposure = (int) res.getSpyExposure();
			
			ActionBar abl = new ActionBar(
					CivColor.LightGreen+money+CivColor.Gold+CivColor.BOLD+" Coins"
					+CivColor.Gray+CivColor.BOLD+" |"
					+CivColor.Gold+CivColor.BOLD+" Citizen of: "+CivColor.LightGreen+town+" [T], "+CivColor.Green+CivColor.BOLD+civ+" [C]"
					+CivColor.Gray+CivColor.BOLD+" |"
					+CivColor.Gold+CivColor.BOLD+" Spy Exposure: "+CivColor.LightGreen+CivColor.ITALIC+exposure);
			abl.sendToAll();
		}
	}
}
