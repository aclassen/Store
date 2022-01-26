package com.dropbox.kmp.external.fs3


import com.dropbox.kmp.external.fs3.filesystem.FileSystem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.runBlocking
import okio.BufferedSource
import okio.FileNotFoundException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class FilePersisterTest {
    private val fileSystem: FileSystem = mockk()
    private val bufferedSource: BufferedSource = mockk()
    private val simple = "type" to "key"
    private val resolvedPath = StringPairPathResolver(simple)
    private val fileSystemPersister = FileSystemPersister.create(fileSystem, StringPairPathResolver)

    @Test
    fun readExists() = runBlocking {

        every { fileSystem.exists(resolvedPath) }.returns(true)

        every { fileSystem.read(resolvedPath) }.returns(bufferedSource)

        val returnedValue = fileSystemPersister.read(simple)
        assertEquals(returnedValue, bufferedSource)

    }

    @Test
    fun readDoesNotExist() = runBlocking {
        every { fileSystem.exists(resolvedPath) }.returns(false)

        try {
            fileSystemPersister.read(simple)
            fail()
        } catch (e: FileNotFoundException) {
        }
    }

    @Test
    fun writeThenRead() = runBlocking {
        every { fileSystem.read(resolvedPath) }.returns(bufferedSource)
        every { fileSystem.exists(resolvedPath) }.returns(true)
        every { fileSystem.write(resolvedPath, bufferedSource) } returns Unit
        fileSystemPersister.write(simple, bufferedSource)
        val source = fileSystemPersister.read(simple)
        verifyOrder {
            fileSystem.write(resolvedPath, bufferedSource)
            fileSystem.exists(resolvedPath)
            fileSystem.read(resolvedPath)
        }
        assertEquals(source, bufferedSource)
    }
}
