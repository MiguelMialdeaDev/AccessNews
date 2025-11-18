package com.miguelmialdea.accessnews.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.miguelmialdea.accessnews.core.common.Constants
import com.miguelmialdea.accessnews.database.AccessNewsDatabase

/**
 * Factory for creating SQLDelight database driver
 */
class DatabaseDriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = AccessNewsDatabase.Schema,
            context = context,
            name = Constants.DATABASE_NAME
        )
    }
}
