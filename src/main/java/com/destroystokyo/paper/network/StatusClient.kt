package com.destroystokyo.paper.network

interface StatusClient : NetworkClient {
    val isLegacy: Boolean
        get() = false
}
