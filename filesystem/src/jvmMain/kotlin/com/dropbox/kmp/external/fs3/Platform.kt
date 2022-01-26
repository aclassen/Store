package com.dropbox.kmp.external.fs3

import com.dropbox.kmp.external.fs3.filesystem.FileSystemFactory
import com.dropbox.kmp.external.fs3.filesystem.RealFileSystem
import okio.FileSystem
import okio.Path

internal actual object Platform {
    actual val currentTimeMillis get() = System.currentTimeMillis()
}

actual fun FileSystemFactory.create(root: String) = FileSystemFactory.create(root, FileSystem.SYSTEM)


internal actual fun RealFileSystem.rename(source: Path, target: Path) {
    if (isWindows && exists(target)) {
        delete(target)
    }
    atomicMove(source, target)
}

private val isWindows = System.getProperty("os.name")?.startsWith("Windows") == true