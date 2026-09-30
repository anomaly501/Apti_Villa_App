package app.apti.villa.data.backup

import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object ZipBackupManager {

    /**
     * Compresses a map of fileName -> content into a ZIP stream.
     */
    fun writeZip(entries: Map<String, String>, outputStream: OutputStream) {
        ZipOutputStream(outputStream.buffered()).use { zipOut ->
            for ((name, content) in entries) {
                val entry = ZipEntry(name)
                zipOut.putNextEntry(entry)
                val bytes = content.toByteArray(Charsets.UTF_8)
                zipOut.write(bytes)
                zipOut.closeEntry()
            }
            zipOut.finish()
        }
    }

    /**
     * Reads all text entries from a ZIP input stream.
     */
    fun readZip(inputStream: InputStream): Map<String, String> {
        val entries = mutableMapOf<String, String>()
        ZipInputStream(inputStream.buffered()).use { zipIn ->
            var entry: ZipEntry? = zipIn.nextEntry
            while (entry != null) {
                if (!entry.isDirectory) {
                    val content = zipIn.bufferedReader(Charsets.UTF_8).readText()
                    entries[entry.name] = content
                }
                zipIn.closeEntry()
                entry = zipIn.nextEntry
            }
        }
        return entries
    }
}
