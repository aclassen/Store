package com.dropbox.kmp.external.fs3

import com.dropbox.kmp.external.fs3.filesystem.FileSystem
import okio.BufferedSource

/**
 * FileSystemPersister is used when persisting to/from file system
 * PathResolver will be used in creating file system paths based on cache keys.
 * Make sure to have keys containing same data resolve to same "path"
 * @param <T> key type
</T> */
class FileSystemPersister<Key> private constructor(
    fileSystem: FileSystem,
    pathResolver: (Key) -> String
) : Persister<BufferedSource, Key> {
    private val fileReader: FSReader<Key> = FSReader(fileSystem, pathResolver)
    private val fileWriter: FSWriter<Key> = FSWriter(fileSystem, pathResolver)

    override suspend fun read(key: Key): BufferedSource? = fileReader.read(key)

    override suspend fun write(key: Key, raw: BufferedSource): Boolean = fileWriter.write(key, raw)

    companion object {

        fun <Key> create(
            fileSystem: FileSystem,
            pathResolver: (Key) -> String
        ): Persister<BufferedSource, Key> = FileSystemPersister(
            fileSystem = fileSystem,
            pathResolver = pathResolver
        )
    }
}
