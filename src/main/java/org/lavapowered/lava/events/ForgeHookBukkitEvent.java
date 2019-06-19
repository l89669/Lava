package org.lavapowered.lava.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Event used from the Mohist project
 * https://github.com/PFCraft/Mohist/blob/master/src/main/java/red/mohist/event/ForgeHookBukkitEvent.java
 *
 * @author Mgazul
 */
@Cancelable
public class ForgeHookBukkitEvent extends Event {

    private final org.bukkit.event.Event event;

    public ForgeHookBukkitEvent(org.bukkit.event.Event event){
        this.event = event;
    }

    public org.bukkit.event.Event getEvent() {
        return this.event;
    }

}
