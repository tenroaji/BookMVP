package com.lagecong.books.data.datasource.entity.daos

import androidx.room.*
import com.lagecong.books.data.datasource.entity.BookEntity

/**
 * Created by Andi Tenroaji Ahmad on 9/25/2019.
 */
@Dao
interface BookDAO {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAll(listSKP: MutableList<BookEntity>)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(listSKP: BookEntity)

        @Query("SELECT * FROM book")
        fun seleactAll() : MutableList<BookEntity>

        @Query("SELECT * FROM book WHERE search=:search ")
        fun select(search: String) : MutableList<BookEntity>?

        @Update
        fun update(dataEnt: BookEntity)

        @Query("DELETE FROM book WHERE search=:search")
        fun deleteBySearch(search: String)

        @Query("DELETE FROM book")
        fun deleteAll()
    }
