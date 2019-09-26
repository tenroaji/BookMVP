package com.lagecong.books.books

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.lagecong.books.R
import com.lagecong.books.data.models.Book
import com.lagecong.books.data.models.BookResponse
import com.lagecong.books.utils.visibility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_error.*

class BookActivity : AppCompatActivity(), BookContracts.View {


    private lateinit var mPresenter : BookContracts.Presenter
    private lateinit var mBooksAdapter : BooksAdapter
    private var mWidth = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calculateSize()
        mBooksAdapter = BooksAdapter(mWidth)
        mRecyclerBook.run{
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
            layoutManager = GridLayoutManager(this@BookActivity, 3)
            adapter = mBooksAdapter
        }

        BookPresenter(this,this)

        actionSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mPresenter.searchBooks(actionSearch.text.toString())
                    return true
                }
                return false
            }
        })

    }

    override fun onResume() {
        super.onResume()
        mPresenter.searchBooks("game")
    }

    override fun showLoadingBooks(show: Boolean) {
        if (tvError.isShown) tvError visibility false
        mProgressBar visibility show
    }

    override fun showDataBuku(data: MutableList<BookResponse>?) {
        if (tvError.isShown ) tvError visibility false
        if(mProgressBar.isShown) mProgressBar visibility false
        mBooksAdapter.updateAdapter(data)

    }

    override fun showNoData(message: String) {
        mBooksAdapter.clearList()
        tvError visibility true
        tvError.text = "$message"
    }
    override fun showError(code: Int, message: String?) {
        tvError visibility true
        tvError.text = "$message"
    }

    override fun setPresenter(presenter: BookContracts.Presenter) {
        mPresenter = presenter
    }
    override fun showToastLocal() {
        Toast.makeText(this@BookActivity, resources.getString(R.string.dataNotUpdate),
            Toast.LENGTH_LONG).show()
    }

    private fun calculateSize() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val margins = resources.getDimension(R.dimen.margin_16dp).toInt() * 4
        mWidth = (width - (margins)) / 3
    }
}
