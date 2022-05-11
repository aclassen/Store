package com.dropbox.kmp.external.fs3

import com.dropbox.kmp.external.fs3.filesystem.FileSystemFactory
import com.dropbox.kmp.external.fs3.filesystem.RealFileSystem
import okio.FileSystem
import okio.Path
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

internal actual object Platform {
    actual val currentTimeMillis get() = (NSDate().timeIntervalSince1970 * 1000).toLong()
}
@Throws(Exception::class)
actual fun FileSystemFactory.create(root: String) = FileSystemFactory.create(root, FileSystem.SYSTEM)
