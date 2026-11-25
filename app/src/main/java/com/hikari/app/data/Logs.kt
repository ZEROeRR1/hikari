package com.hikari.app.data

import android.content.Context
import java.io.File

object Logs {

    data class LogFile(
        val key: String,
        val title: String,
        val description: String,
        val file: File,
        val isCrash: Boolean,
    )

    fun flush() {}

    fun init(context: Context) {
        // Delete any leftover logs from previous versions on disk
        runCatching {
            val d = File(context.filesDir, "logs")
            if (d.exists()) {
                d.listFiles()?.forEach { runCatching { it.delete() } }
            }
        }
    }

    fun isReady(): Boolean = false

    fun logDir(context: Context): File = File(context.filesDir, "logs")

    fun heapDumpFile(context: Context): File =
        File(logDir(context), "hikari-heap-disabled.hprof")

    fun heapFiles(context: Context): List<File> = emptyList()

    fun log(tag: String, message: String) {}

    fun logError(tag: String, message: String, t: Throwable? = null) {}

    fun trace(tag: String, message: String) {}

    fun traceSampledEvery(tag: String, key: String, every: Long, message: () -> String) {}

    fun safeUrl(url: String?): String = ""

    fun safeHost(url: String?): String = ""

    suspend fun <T> timed(tag: String, label: String, block: suspend () -> T): T = block()

    fun recordCrash(threadName: String, t: Throwable): String = ""

    fun logFiles(context: Context): List<LogFile> = emptyList()

    fun existingFiles(context: Context): List<LogFile> = emptyList()

    fun hasCrash(context: Context): Boolean = false

    fun crashText(context: Context): String? = null

    fun clear(context: Context) {
        runCatching {
            val d = File(context.filesDir, "logs")
            if (d.exists()) {
                d.listFiles()?.forEach { runCatching { it.delete() } }
            }
        }
    }

    fun clearCrash(context: Context) {}

    fun clearHeap(context: Context) {}

    fun snapshot(): String = ""
}
