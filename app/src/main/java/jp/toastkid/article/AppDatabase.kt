/*
 * Copyright (c) 2019 toastkidjp.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html.
 */
package jp.toastkid.article

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.toastkid.article.article.Article
import jp.toastkid.article.article.ArticleRepository

/**
 * @author toastkidjp
 */
@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryRepository(): ArticleRepository
}