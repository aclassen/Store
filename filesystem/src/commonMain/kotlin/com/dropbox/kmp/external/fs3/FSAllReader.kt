package com.dropbox.kmp.external.fs3

import com.dropbox.kmp.external.fs3.filesystem.FileSystem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import okio.BufferedSource
import okio.FileNotFoundException

/**
 * FSReader is used when persisting from file system
 * PathResolver will be used in creating file system paths based on cache keys.
 * Make sure to have keys containing same data resolve to same "path"
 *
 */
class FSAllReader(internal val fileSystem: FileSystem) : DiskAllRead<BufferedSource> {

    @Throws(FileNotFoundException::class)
    override fun CoroutineScope.readAll(path: String): ReceiveChannel<BufferedSource> {
        return produce {
            fileSystem.list(path).forEach {
                send(fileSystem.read(it))
            }
        }
    }
}