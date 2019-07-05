package org.bukkit.entity;

import com.destroystokyo.paper.entity.SentientNPC;

import java.util.Set;

/**
 * Represents a complex living entity - one that is made up of various smaller
 * parts
 */
public interface ComplexLivingEntity extends LivingEntity, SentientNPC { // Paper
    /**
     * Gets a list of parts that belong to this complex entity
     *
     * @return List of parts
     */
    public Set<ComplexEntityPart> getParts();
}
