package org.tavioribeiro.adb_manager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform