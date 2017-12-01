package com.avrgaming.civcraft.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import org.bukkit.entity.Player;

import com.avrgaming.civcraft.exception.CivException;
import com.avrgaming.civcraft.main.CivGlobal;

import net.minecraft.server.v1_10_R1.PacketPlayOutMultiBlockChange;

public class PlayerBlockChangeUtil {
	/* This class sends fake block updates to residents.
	 * It attempts to package all pending block updates. It allows the user to add blocks that
	 * need to change to a list, then calculates which chunks need to be updated and sent to the player. */
	// This hashmap contains the blocks in each chunk to update. The Simple block's x, y, and z value are now chunk offsets.
	HashMap<String, HashMap<ChunkCoord, LinkedList<SimpleBlock>>> blocksInChunkToUpdate = new HashMap<String, HashMap<ChunkCoord, LinkedList<SimpleBlock>>>();
	TreeMap<String, PacketPlayOutMultiBlockChange> preparedPackets = new TreeMap<String, PacketPlayOutMultiBlockChange>();
	
	public void addUpdateBlock(String playerName, BlockCoord bcoord, int type_id, int data) {
		HashMap<ChunkCoord, LinkedList<SimpleBlock>> blocksInChunk = blocksInChunkToUpdate.get(playerName);
		if (blocksInChunk == null) {
			blocksInChunk = new HashMap<ChunkCoord, LinkedList<SimpleBlock>>();
		}
		
		/* Add to chunk table. */
		ChunkCoord coord = new ChunkCoord(bcoord);
		SimpleBlock sb2 = new SimpleBlock(type_id, data);
		sb2.worldname = bcoord.getWorldname();
		sb2.x = bcoord.getX();
		sb2.y = bcoord.getY();
		sb2.z = bcoord.getZ();
		
		LinkedList<SimpleBlock> blocks = blocksInChunk.get(coord);
		if (blocks == null) {
			blocks = new LinkedList<SimpleBlock>();
		}
		blocks.add(sb2);
		blocksInChunk.put(coord, blocks);
		blocksInChunkToUpdate.put(playerName, blocksInChunk);
	}
	
	public void sendUpdate(String playerName) {
		HashMap<ChunkCoord, LinkedList<SimpleBlock>> blocksInChunk = blocksInChunkToUpdate.get(playerName);
		if (blocksInChunk == null) {
			return;
		}	
		
		for (ChunkCoord chunk : blocksInChunk.keySet()) {
			LinkedList<SimpleBlock> blocks = blocksInChunk.get(chunk);
			Player player;
			try {
				player = CivGlobal.getPlayer(playerName);
			} catch (CivException e) {
				e.printStackTrace();
				return;
			}
			
			for (SimpleBlock sb : blocks) {
				ItemManager.sendBlockChange(player, sb.getLocation(), sb.getType(), sb.getData());
			}
		}		
	}
}
