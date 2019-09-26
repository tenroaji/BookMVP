package com.lagecong.books.data.datasource.entity.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lagecong.books.data.datasource.entity.BookEntity
import com.lagecong.books.data.datasource.entity.daos.BookDAO

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class BookDB : RoomDatabase() {

    abstract fun bookDAO(): BookDAO

    companion object {
        private var INSTANCE: BookDB? = null
        private val sLock = Any()
        private const val DATABASE_NAME = "buku"

        fun getInstance(context: Context) : BookDB {
            synchronized(sLock) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(
                        context,
                        BookDB::class.java,
                        DATABASE_NAME
                    ).build()

                return INSTANCE as BookDB
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}