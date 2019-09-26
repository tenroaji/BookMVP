package com.lagecong.books.data.datasource.books

import android.content.Context
import android.util.Log
import com.lagecong.books.data.models.BookResponse
import com.lagecong.books.utils.TypeRepo

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

class BooksRepository (context: Context) : BooksDataSource.Local {


    private var mRemoteDataSource : BooksDataSource? = null
    private var mLocalDataSource : BooksDataSource.Local? = null
    private var mCacheBook : MutableMap<String, MutableList<BookResponse>>? = null
    private var mSearch = ""
    private var mForceUpdate = true

    init {
        mLocalDataSource = BooksLocalDataSource.getInstance(context)
        mRemoteDataSource = BooksRemoteDataSource.getInstance(context)
    }


    override fun loadDataBooks(callback: BooksDataSource.LoadListCallback<MutableList<BookResponse>?>) {

    }

    override fun loadBookBySearch(
        search: String,
        callback: BooksDataSource.LoadListCallback<MutableList<BookResponse>?>
    ) {
        if (mCacheBook != null && mCacheBook!!.isNotEmpty() && mSearch == search){
            Log.e("lapar","$search ${mCacheBook?.values}")
            callback.onSuccess(mCacheBook?.get(search), TypeRepo.CACHE.name)
            return

        }else {
            mLocalDataSource?.loadBookBySearch(
                search,object :BooksDataSource.LoadListCallback<MutableList<BookResponse>?>{
                    override fun onSuccess(data: MutableList<BookResponse>?, load: String) {
                        cacheBookSearch(search, data)
                        callback.onSuccess(mCacheBook?.get(search),load)
                        Log.e("lapar","$search ${mCacheBook?.get(search)}")
                    }

                    override fun onError(code: Int, message: String?) {
                        shouldLoadFromRemote(search,callback)
                    }

                })
            mSearch = search
        }

    }

    override fun saveData(search: String, data: MutableList<BookResponse>) {
        mLocalDataSource?.saveData(search,data)
    }

    override fun deleteDataBySearch(search: String) {
        mLocalDataSource?.deleteDataBySearch(search)
    }

    private fun shouldLoadFromRemote(search: String, callback: BooksDataSource.LoadListCallback<MutableList<BookResponse>?>){
        mRemoteDataSource?.loadBookBySearch(
            search,object :BooksDataSource.LoadListCallback<MutableList<BookResponse>?>{
                override fun onSuccess(data: MutableList<BookResponse>?, load: String) {
                    cacheBookSearch(search, data)
                    changeLocal(search,data)
                    callback.onSuccess(mCacheBook?.get(search),load)
                    Log.e("lapar","$search ${mCacheBook?.get(search)}")

                }

                override fun onError(code: Int, message: String?) {
                    callback.onError(code, message)
                }

            })
    }

    fun changeLocal(search:String,data: MutableList<BookResponse>?) {
        deleteDataBySearch(search)
        data ?: return
        saveData(search, data)
    }


    private fun cacheBookSearch(search : String , data : MutableList<BookResponse>?){
        if(mCacheBook == null){
            mCacheBook = LinkedHashMap()
            Log.e("lapar","link null $search ${mCacheBook?.get(search)}")
        }
        data?: return
        mCacheBook!!.put(search,data)
        Log.e("lapar","link tersimpan $search ${mCacheBook?.get(search)}")
    }


    companion object{

        @Volatile
        private var INSTANCE : BooksRepository? = null

        @JvmStatic
        fun getInstance(context : Context) : BooksRepository {
            if(INSTANCE == null){
                synchronized(BooksRepository::class){
                    if(INSTANCE == null){
                        INSTANCE = BooksRepository(context)
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