package com.lagecong.books.books

import com.lagecong.books.BasePresenter
import com.lagecong.books.BaseView
import com.lagecong.books.data.models.Book
import com.lagecong.books.data.models.BookResponse

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

interface BookContracts {
    interface Presenter : BasePresenter {

        fun searchBooks(search: String)

        fun loadBooks()

        fun forceUpdate()

    }

    interface View : BaseView<Presenter> {

        fun showLoadingBooks(show : Boolean)

        fun showDataBuku(data : MutableList<BookResponse>?)

        fun showNoData(message : String)

        fun showError(code : Int, message : String?)

        fun showToastLocal()

    }
}