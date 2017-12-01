package com.avrgaming.civcraft.listener;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.avrgaming.civcraft.config.CivSettings;
import com.avrgaming.civcraft.main.CivCraft;
import com.avrgaming.civcraft.main.CivGlobal;
import com.avrgaming.civcraft.main.CivLog;
import com.avrgaming.civcraft.object.TradeGood;
import com.avrgaming.civcraft.util.BlockCoord;
import com.avrgaming.civcraft.util.CivColor;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

@SuppressWarnings("deprecation")
public class HolographicDisplaysListener {
	
	public static void generateTradeGoodHolograms() {
		if (CivSettings.hasHolographicDisplays == false) {
			CivLog.warning("A person tried generating Trade Good Holograms without HolographicDisplays plugin! This is fine, but no holograms can generate for items.");
		}
		
		Plugin p = CivCraft.getPlugin();
		CivLog.info(HolographicDisplaysAPI.getHolograms(p).length+" Trade Good Holograms deleted.");
		for (com.gmail.filoghost.holograms.api.Hologram hologram : HolographicDisplaysAPI.getHolograms(CivCraft.getPlugin())) {
			hologram.delete();
		}
		
		for (TradeGood good : CivGlobal.getTradeGoods()) {
			BlockCoord coord = good.getCoord();
			Location loc = new Location(coord.getBlock().getWorld(), coord.getBlock().getX()+0.5, coord.getBlock().getY()+5, coord.getBlock().getZ()+0.5);
			Hologram hologram = HologramsAPI.createHologram(p, loc);
			if (good.getInfo().water == true) {
				hologram.appendItemLine(new ItemStack(good.getInfo().material, 1, (short)good.getInfo().material_data));
				hologram.appendTextLine(CivColor.Gold+CivColor.BOLD+"Trade Resource: "+CivColor.LightBlue+CivColor.BOLD+CivColor.ITALIC+good.getInfo().name);
				hologram.appendTextLine(CivColor.Gold+CivColor.BOLD+"Value: "+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+good.getInfo().value+" Coins");
				hologram.appendTextLine(CivColor.Gold+CivColor.BOLD+"Culture: "+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+"Future Addition"
										+CivColor.Gold+CivColor.BOLD+" | Food: "+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+"Future Addition");
//				hologram.appendTextLine(CivColor.Gold+CivColor.BOLD+"Culture: "+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+good.getInfo().culture
//										+CivColor.Gold+CivColor.BOLD+" | Food:"+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+good.getInfo().food);
			} else {
				hologram.appendItemLine(new ItemStack(good.getInfo().material, 1, (short)good.getInfo().material_data));
				hologram.appendTextLine(CivColor.Gold+CivColor.BOLD+"Trade Resource: "+CivColor.LightGreen+CivColor.BOLD+CivColor.ITALIC+good.getInfo().name);
				hologram.appendTextLine(CivColor.Gold+CivColor.BOLD+"Value: "+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+good.getInfo().value+" Coins");
				hologram.appendTextLine(CivColor.Gold+CivColor.BOLD+"Culture: "+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+"Future Addition"
										+CivColor.Gold+CivColor.BOLD+" | Food: "+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+"Future Addition");
//				hologram.appendTextLine(CivColor.Gold+CivColor.BOLD+"Culture: "+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+good.getInfo().culture
//										+CivColor.Gold+CivColor.BOLD+" | Food:"+CivColor.Yellow+CivColor.BOLD+CivColor.ITALIC+good.getInfo().food);
			}
		}
		CivLog.info(CivGlobal.getTradeGoods().size()+" Trade Good Holograms created.");
	}
}
