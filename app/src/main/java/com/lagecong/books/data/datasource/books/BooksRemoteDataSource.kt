package com.lagecong.books.data.datasource.books

import android.content.Context
import com.lagecong.books.R
import com.lagecong.books.data.BooksApiRoute
import com.lagecong.books.data.models.Book
import com.lagecong.books.data.models.BookResponse
import com.lagecong.books.utils.RetrofitUtils
import com.lagecong.books.utils.TypeRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

class BooksRemoteDataSource (context: Context) : BooksDataSource {

    private val mService : BooksApiRoute = RetrofitUtils.createService(
        context.resources.getString(R.string.base_url),
        BooksApiRoute::class.java,
        30000L)
    val mLocal = TypeRepo.REMOTE.name

    override fun loadDataBooks(callback: BooksDataSource.LoadListCallback<MutableList<BookResponse>?>) {
    }

    override fun loadBookBySearch(
        search: String,
        callback: BooksDataSource.LoadListCallback<MutableList<BookResponse>?>
    ) {
        mService.getBookSearch(search).also {
            it.enqueue(object : Callback<Book> {

                override fun onResponse(call: Call<Book>, response: Response<Book>) {
                    if(response.code() == 200){
                        response.body()?.run {
                            callback.onSuccess(this.items,mLocal)
                        } ?: callback.onError(404,"Buku Tidak Ditemukan")
                    }else{
                        callback.onError(response.code(),null)
                    }

                }

                override fun onFailure(call: Call<Book>, t: Throwable) {
                    callback.onError(0,"$t")
                }
            })
        }
    }

    companion object{

        @Volatile
        private var INSTANCE : BooksRemoteDataSource? = null

        @JvmStatic
        fun getInstance(context : Context) : BooksRemoteDataSource {
            if(INSTANCE == null){
                synchronized(BooksRemoteDataSource::class){
                    if(INSTANCE == null){
                        INSTANCE = BooksRemoteDataSource(context)
                    }
                }
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}