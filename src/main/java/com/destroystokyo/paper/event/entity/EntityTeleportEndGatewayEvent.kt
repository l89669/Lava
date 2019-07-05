package com.destroystokyo.paper.event.entity

import org.bukkit.Location
import org.bukkit.block.EndGateway
import org.bukkit.entity.Entity
import org.bukkit.event.entity.EntityTeleportEvent

/**
 * Fired any time an entity attempts to teleport in an end gateway
 */
open class EntityTeleportEndGatewayEvent(what: Entity, from: Location, to: Location,
                                    /**
                                     * The gateway triggering the teleport
                                     *
                                     * @return EndGateway used
                                     */
                                    val gateway: EndGateway) : EntityTeleportEvent(what, from, to)
