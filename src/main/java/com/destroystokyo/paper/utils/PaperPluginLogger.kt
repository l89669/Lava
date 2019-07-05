package com.destroystokyo.paper.utils

import org.bukkit.plugin.PluginDescriptionFile

import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

/**
 * Prevents plugins (e.g. Essentials) from changing the parent of the plugin logger.
 */
class PaperPluginLogger private constructor(description: PluginDescriptionFile) : Logger(if (description.prefix != null) description.prefix else description.name, null) {

    override fun setParent(parent: Logger) {
        if (getParent() != null) {
            warning("Ignoring attempt to change parent of plugin logger")
        } else {
            this.log(Level.FINE, "Setting plugin logger parent to {0}", parent)
            super.setParent(parent)
        }
    }

    companion object {
        @JvmStatic
        fun getLogger(description: PluginDescriptionFile): Logger {
            var logger: Logger = PaperPluginLogger(description)
            if (!LogManager.getLogManager().addLogger(logger)) {
                // Disable this if it's going to happen across reloads anyways...
                //logger.log(Level.WARNING, "Could not insert plugin logger - one was already found: {}", LogManager.getLogManager().getLogger(this.getName()));
                logger = LogManager.getLogManager().getLogger(if (description.prefix != null) description.prefix else description.name)
            }
            return logger
        }
    }
}
