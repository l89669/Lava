package com.destroystokyo.paper.event.player

import org.bukkit.Location
import org.bukkit.block.EndGateway
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

/**
 * Fired when a teleport is triggered for an End Gateway
 */
open class PlayerTeleportEndGatewayEvent(player: Player, from: Location, to: Location,
                                    /**
                                     * The gateway triggering the teleport
                                     *
                                     * @return EndGateway used
                                     */
                                    val gateway: EndGateway) : PlayerTeleportEvent(player, from, to, TeleportCause.END_GATEWAY)
