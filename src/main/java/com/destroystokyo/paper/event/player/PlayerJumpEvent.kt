package com.destroystokyo.paper.event.player

import com.google.common.base.Preconditions
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * Called when the server detects the player is jumping.
 *
 *
 * Added to avoid the overhead and special case logic that many plugins use
 * when checking for jumps via PlayerMoveEvent, this event is fired whenever
 * the server detects that the player is jumping.
 */
open class PlayerJumpEvent(player: Player, private var from: Location?,
                      /**
                       * Gets the location this player jumped to
                       *
                       * This information is based on what the client sends, it typically
                       * has little relation to the arc of the jump at any given point.
                       *
                       * @return Location the player jumped to
                       */
                      val to: Location) : PlayerEvent(player), Cancellable {
    private var cancel = false

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     *
     * If a jump event is cancelled, the player will be moved or
     * teleported back to the Location as defined by getFrom(). This will not
     * fire an event
     *
     * @return true if this event is cancelled
     */
    override fun isCancelled(): Boolean {
        return cancel
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     *
     * If a jump event is cancelled, the player will be moved or
     * teleported back to the Location as defined by getFrom(). This will not
     * fire an event
     *
     * @param cancel true if you wish to cancel this event
     */
    override fun setCancelled(cancel: Boolean) {
        this.cancel = cancel
    }

    /**
     * Gets the location this player jumped from
     *
     * @return Location the player jumped from
     */
    fun getFrom(): Location? {
        return from
    }

    /**
     * Sets the location to mark as where the player jumped from
     *
     * @param from New location to mark as the players previous location
     */
    fun setFrom(from: Location) {
        validateLocation(from)
        this.from = from
    }

    private fun validateLocation(loc: Location) {
        Preconditions.checkArgument(loc != null, "Cannot use null location!")
        Preconditions.checkArgument(loc.world != null, "Cannot use location with null world!")
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
