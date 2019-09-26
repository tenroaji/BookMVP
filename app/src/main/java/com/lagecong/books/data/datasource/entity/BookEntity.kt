package com.lagecong.books.data.datasource.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "book")
class BookEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id : Int,
    @ColumnInfo(name = "data") var data : String?,
    @ColumnInfo(name = "search") var search : String?
)