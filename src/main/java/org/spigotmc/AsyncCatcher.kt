package org.spigotmc

import net.minecraft.server.MinecraftServer

object AsyncCatcher {

    var enabled = true

    @JvmStatic
    fun catchOp(reason: String) {
        if (enabled && Thread.currentThread() !== MinecraftServer.getServerInstance().primaryThread) {
            throw IllegalStateException("Asynchronous $reason!")
        }
    }

    @JvmStatic
    fun catchInv(): Boolean {
        return enabled && Thread.currentThread() !== MinecraftServer.getServerInstance().primaryThread
    }

}
