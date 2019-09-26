package com.lagecong.books.data

import com.lagecong.books.data.models.Book
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

interface BooksApiRoute{

    @GET("volumes")
    fun getBookSearch(@Query("q") search : String) : Call<Book>
}
