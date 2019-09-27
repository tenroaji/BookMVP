package com.lagecong.books.books

import android.content.Context
import android.util.Log
import com.lagecong.books.R
import com.lagecong.books.data.datasource.books.BooksDataSource
import com.lagecong.books.data.datasource.books.BooksRepository
import com.lagecong.books.data.models.BookResponse
import com.lagecong.books.utils.TypeRepo

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

class BookPresenter (val context: Context, val view : BookContracts.View) : BookContracts.Presenter {

    private val mRepository = BooksRepository.getInstance(context)

    init {
        view.setPresenter(this)
    }

    override fun searchBooks(search: String) {
        view.showLoadingBooks(true)
        mRepository.loadBookBySearch(search, object : BooksDataSource.LoadListCallback<MutableList<BookResponse>?>{
            override fun onSuccess(data: MutableList<BookResponse>?, load: String) {
                view.showLoadingBooks(false)
                if (data.isNullOrEmpty()){
                    view.showNoData(context.resources.getString(R.string.noData))
                }else view.showDataBuku(data)
                if (load == TypeRepo.LOCAL.name){view.showToastLocal()}
            }

            override fun onError(code: Int, message: String?) {
                view.showLoadingBooks(false)
                view.showError(code,message)
            }

        })
    }

    override fun forceUpdate() {
        mRepository.forceUpdate()
    }


    override fun loadBooks() {
    }

    override fun start() {
    }

}