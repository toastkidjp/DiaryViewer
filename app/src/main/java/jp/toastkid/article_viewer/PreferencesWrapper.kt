/*
 * Copyright (c) 2019 toastkidjp.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html.
 */
package jp.toastkid.article_viewer

import android.content.Context
import android.content.SharedPreferences

/**
 * @author toastkidjp
 */
class PreferencesWrapper(context: Context) {

    /**
     * TODO Divide and move package.
     */
    private enum class Key {
        FILE_PATH, LAST_UPDATED, USE_TITLE_FILTER, BOOKMARK
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(javaClass.canonicalName, Context.MODE_PRIVATE)

    fun setTarget(targetPath: String?) {
        if (targetPath == null) {
            return
        }
        preferences.edit()
            .putString(Key.FILE_PATH.name, targetPath)
            .apply()
    }

    fun getTarget(): String? {
        return preferences.getString(Key.FILE_PATH.name, null)
    }

    fun setLastUpdated(ms: Long) {
        preferences.edit().putLong(Key.LAST_UPDATED.name, ms).apply()
    }

    fun getLastUpdated(): Long {
        return preferences.getLong(Key.LAST_UPDATED.name, 0L)
    }

    fun switchUseTitleFilter(checked: Boolean) {
        preferences.edit().putBoolean(Key.USE_TITLE_FILTER.name, checked).apply()
    }

    fun useTitleFilter(): Boolean {
        return preferences.getBoolean(Key.USE_TITLE_FILTER.name, true)
    }

    private fun readBookmark(): MutableList<String> {
        return preferences.getString(Key.BOOKMARK.name, "")
            ?.split(System.lineSeparator())
            ?.filter { it.isNotBlank() }
            ?.toMutableList()
            ?: mutableListOf()
    }

    fun addBookmark(title: String) {
        val bookmarks = readBookmark()
        bookmarks.add(title)
        val lineSeparator = System.lineSeparator()
        preferences.edit().putString(
            Key.BOOKMARK.name,
            bookmarks.reduce { base, item -> "$base$lineSeparator$item" }
            )
            .apply()
    }

    fun bookmark(): Collection<String> {
        return readBookmark()
    }

    fun containsBookmark(title: String): Boolean {
        return bookmark().contains(title)
    }
}