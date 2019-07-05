package com.destroystokyo.paper.event.entity

import com.google.common.base.Preconditions
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.CreatureSpawnEvent

/**
 * WARNING: This event only fires for a limited number of cases, and not for every case that CreatureSpawnEvent does.
 *
 * You should still listen to CreatureSpawnEvent as a backup, and only use this event as an "enhancement".
 * The intent of this event is to improve server performance, so limited use cases.
 *
 * Currently: NATURAL and SPAWNER based reasons. Please submit a Pull Request for future additions.
 * Also, Plugins that replace Entity Registrations with their own custom entities might not fire this event.
 */
open class PreCreatureSpawnEvent(location: Location, type: EntityType, reason: CreatureSpawnEvent.SpawnReason) : Event(), Cancellable {
    /**
     * @return The location this creature is being spawned at
     */
    val spawnLocation: Location = Preconditions.checkNotNull(location, "Location may not be null").clone()
    /**
     * @return The type of creature being spawned
     */
    val type: EntityType = Preconditions.checkNotNull(type, "Type may not be null")
    /**
     * @return Reason this creature is spawning (ie, NATURAL vs SPAWNER)
     */
    val reason: CreatureSpawnEvent.SpawnReason = Preconditions.checkNotNull(reason, "Reason may not be null")
    private var shouldAbortSpawn: Boolean = false

    private var cancelled = false

    /**
     * @return If the spawn process should be aborted vs trying more attempts
     */
    fun shouldAbortSpawn(): Boolean {
        return shouldAbortSpawn
    }

    /**
     * Set this if you are more blanket blocking all types of these spawns, and wish to abort the spawn process from
     * trying more attempts after this cancellation.
     *
     * @param shouldAbortSpawn Set if the spawn process should be aborted vs trying more attempts
     */
    fun setShouldAbortSpawn(shouldAbortSpawn: Boolean) {
        this.shouldAbortSpawn = shouldAbortSpawn
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    /**
     * @return If the spawn of this creature is cancelled or not
     */
    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * Cancelling this event is more effecient than cancelling CreatureSpawnEvent
     * @param cancel true if you wish to cancel this event, and abort the spawn of this creature
     */
    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
