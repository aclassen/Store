package com.dropbox.kmp.external.fs3

import com.dropbox.kmp.external.fs3.filesystem.FileSystem
import com.dropbox.kmp.external.fs3.filesystem.FileSystemFactory
import com.dropbox.kmp.external.fs3.filesystem.RealFileSystem
import okio.IOException
import okio.Path

internal expect object Platform {
    val currentTimeMillis: Long

}

@Throws(IOException::class)
expect fun FileSystemFactory.create(root: String): FileSystem
internal expect fun RealFileSystem.rename(source: Path, target: Path)


