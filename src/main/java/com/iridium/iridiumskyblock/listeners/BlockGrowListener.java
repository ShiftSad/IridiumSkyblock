package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import org.bukkit.Bukkit;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

public class BlockGrowListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorBlockGrow(BlockGrowEvent event) {
        if (!IridiumSkyblockAPI.getInstance().isIslandWorld(event.getBlock().getWorld())) return;
        Bukkit.getScheduler().runTask(IridiumSkyblock.getInstance(), () ->
                IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(event.getBlock().getLocation()).ifPresent(island -> {
                    XMaterial material = XMaterial.matchXMaterial(event.getBlock().getType());

                    if (event.getNewState().getBlock().getBlockData() instanceof Ageable) {
                        Ageable ageable = (Ageable) event.getNewState().getBlock().getBlockData();
                        ageable.setAge(Math.min(ageable.getAge() + 1, ageable.getMaximumAge()));
                        event.getNewState().getBlock().setBlockData(ageable);
                        if (ageable.getAge() == ageable.getMaximumAge()) {
                            IridiumSkyblock.getInstance().getMissionManager().handleMissionUpdates(island, "GROW", material.name(), 1);
                        }
                    } else {
                        IridiumSkyblock.getInstance().getMissionManager().handleMissionUpdates(island, "GROW", material.name(), 1);
                    }
                })
        );
    }

}
