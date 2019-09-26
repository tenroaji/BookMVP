package com.lagecong.books.data.datasource.books

import com.lagecong.books.data.models.Book
import com.lagecong.books.data.models.BookResponse

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

interface BooksDataSource {

    interface LoadListCallback<T>{

        fun onSuccess(data : T, load: String)

        fun onError(code : Int, message : String?)
    }

    fun loadDataBooks(callback: LoadListCallback<MutableList<BookResponse>?>)

    fun loadBookBySearch(search : String, callback: LoadListCallback<MutableList<BookResponse>?>)

    interface Local : BooksDataSource {
        fun saveData(search : String, data : MutableList<BookResponse>)
        fun deleteDataBySearch(search : String)

    }
}