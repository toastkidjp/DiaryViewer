package jp.toastkid.diary.search

import jp.toastkid.diary.converter.NameDecoder
import jp.toastkid.diary.search.result.DictionaryFile
import okio.Okio
import timber.log.Timber
import java.io.InputStream
import java.nio.charset.Charset
import java.util.zip.ZipInputStream

/**
 * @author toastkidjp
 */
object ZipSearcher {

    operator fun invoke(inputStream: InputStream, keyword: String): List<DictionaryFile> {
        val results: MutableList<DictionaryFile> = mutableListOf()

        ZipInputStream(inputStream, Charset.forName("UTF-8"))
            .also { zipInputStream ->
                var nextEntry = zipInputStream.nextEntry
                while (nextEntry != null) {
                    Okio.buffer(Okio.source(zipInputStream)).also {
                        val content = it.readUtf8()
                        if (content.contains(keyword)) {
                            val name = nextEntry.name
                            val title =
                                NameDecoder(name.substring(name.indexOf("/") + 1, name.lastIndexOf(".")))
                            results.add(DictionaryFile(title, content))
                        }
                    }
                    nextEntry = try {
                        zipInputStream.nextEntry
                    } catch (e: IllegalArgumentException) {
                        Timber.e("illegal: ${nextEntry.name}")
                        Timber.e(e)
                        return results
                    }
                }
                zipInputStream.closeEntry()
            }
        return results
    }

}