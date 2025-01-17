package com.dropbox.kmp.external.fs3

import com.dropbox.kmp.external.fs3.filesystem.FileSystem
import com.dropbox.kmp.external.fs3.filesystem.FileSystemFactory
import okio.BufferedSource
import okio.IOException
import okio.Path.Companion.toPath
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Factory for [SourcePersister]
 */

object SourcePersisterFactory {

    /**
     * Returns a new [BufferedSource] persister with the provided file as the root of the
     * persistence [FileSystem].
     *
     * @throws IOException
     */
    @ExperimentalTime
    @Throws(IOException::class)
    fun create(
        root: String,
        expirationDuration: Duration
    ): Persister<BufferedSource, Pair<String, String>> {
        return RecordPersister.create(FileSystemFactory.create(root), expirationDuration)
    }

    /**
     * Returns a new [BufferedSource] persister with the provided fileSystem as the root of the
     * persistence [FileSystem].
     */
    @ExperimentalTime
    fun create(
        fileSystem: FileSystem,
        expirationDuration: Duration
    ): Persister<BufferedSource, Pair<String, String>> {
        return RecordPersister.create(fileSystem, expirationDuration)
    }

    /**
     * Returns a new [BufferedSource] persister with the provided file as the root of the
     * persistence [FileSystem].
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun create(root: String): Persister<BufferedSource, Pair<String, String>> {
        return SourcePersister.create(FileSystemFactory.create(root))
    }

    /**
     * Returns a new [BufferedSource] persister with the provided fileSystem as the root of the
     * persistence [FileSystem].
     */
    fun create(fileSystem: FileSystem): Persister<BufferedSource, Pair<String, String>> {
        return SourcePersister.create(fileSystem)
    }

    /**
     * Returns a new [BufferedSource] persister with the provided file as the root of the
     * persistence [FileSystem].
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun createAll(root: String): Persister<BufferedSource, Pair<String, String>> {
        return SourceAllPersister.create(FileSystemFactory.create(root))
    }

    /**
     * Returns a new [BufferedSource] persister with the provided fileSystem as the root of the
     * persistence [FileSystem].
     */
    fun createAll(fileSystem: FileSystem): Persister<BufferedSource, Pair<String, String>> {
        return SourceAllPersister.create(fileSystem)
    }
}
