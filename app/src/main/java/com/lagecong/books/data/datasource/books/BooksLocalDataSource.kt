package com.lagecong.books.data.datasource.books

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.lagecong.books.data.datasource.entity.BookEntity
import com.lagecong.books.data.datasource.entity.database.BookDB
import com.lagecong.books.data.models.BookResponse
import com.lagecong.books.utils.TypeRepo
import com.lagecong.books.utils.executors.AppExecutors

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

class BooksLocalDataSource (context: Context) : BooksDataSource.Local {


    private val mDao = BookDB.getInstance(context).bookDAO()
    private val mAppExecutors = AppExecutors()
    private val mLoad = TypeRepo.LOCAL.name
    override fun loadDataBooks(callback: BooksDataSource.LoadListCallback<MutableList<BookResponse>?>) {
    }

    override fun loadBookBySearch(
        search: String,
        callback: BooksDataSource.LoadListCallback<MutableList<BookResponse>?>
    ) {
        Log.e("LOCAL","load")
        val runnable = Runnable {
            val mEntity = mDao.select(search)
            val mList: MutableList<BookResponse> = mutableListOf()
            if (mEntity!!.isNotEmpty()) {
                for (dataEntity in mEntity) {
                    val mJson = Gson().fromJson(dataEntity.data, BookResponse::class.java)
                    mList.add(mJson)
                }
            }
            mAppExecutors.mainThread().execute {
                if (mList.isEmpty()) {
                    callback.onError(0, "")
                } else {
                    callback.onSuccess(mList,mLoad)
                }
            }
        }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun saveData(search: String, data: MutableList<BookResponse>) {
        Log.e("LOCAL","save")
        val runnable = Runnable {
            if (data.isNotEmpty()) {
                val mListBook = ArrayList<BookEntity>()
                for ((count, menu) in data.withIndex()) {
                    val dataJson = Gson().toJson(menu)
                    val dataEntity = BookEntity(count, dataJson, search )
                    mListBook.add(dataEntity)
                }
                mDao.insertAll(mListBook)
            }
        }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun deleteDataBySearch(search: String) {
        Log.e("LOCAL","delete")
        mAppExecutors.diskIO().execute { mDao.deleteBySearch(search) }
    }


    companion object {
        private var INSTANCE: BooksDataSource.Local? = null

        fun getInstance(context: Context) : BooksDataSource.Local {
            if (INSTANCE == null) {
                synchronized(BooksDataSource::class.java) {
                    INSTANCE = BooksLocalDataSource(context)
                }
            }

            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}