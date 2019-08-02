package org.lavapowered.lava.events;

import net.minecraftforge.fml.common.eventhandler.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

/**
 * Event used from the Mohist project
 * https://github.com/PFCraft/Mohist/blob/master/src/main/java/red/mohist/event/BukkitHookForgeEvent.java
 *
 * @author Mgazul
 */
public class BukkitHookForgeEvent extends org.bukkit.event.Event {

    private static final HandlerList handlers = new HandlerList();
    private final Event event;

    public BukkitHookForgeEvent(Event event) {
        super(!Bukkit.getServer().isPrimaryThread());
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Get the MinecraftForge event
     *
     * @return the forge event
     */
    public Event getEvent() {
        return this.event;
    }

    public String getEventName() {
        return event.getClass().getSimpleName();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
