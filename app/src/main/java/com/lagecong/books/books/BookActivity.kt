package com.lagecong.books.books

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.lagecong.books.data.models.BookResponse
import com.lagecong.books.utils.visibility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_error.*
import android.app.Activity
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.lagecong.books.R
import android.text.Layout





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
                    if (actionSearch.text.isNullOrEmpty()){
                        Toast.makeText(this@BookActivity, "Isian search tidak boleh kosong",
                            Toast.LENGTH_LONG).show()
                    }else {
                        val imm = getSystemService(
                            Activity.INPUT_METHOD_SERVICE
                        ) as InputMethodManager
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                        mPresenter.forceUpdate()
                        mPresenter.searchBooks(actionSearch.text.toString())
                    }
                    return true
                }
                return false
            }
        })

        actionRefresh.setOnClickListener {
            mPresenter.forceUpdate()
            mPresenter.searchBooks(actionSearch.text.toString()) }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val inputMethodManager =  getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    override fun onResume() {
        super.onResume()
        if (actionSearch.text.isNullOrEmpty()){
            mPresenter.forceUpdate()
        mPresenter.searchBooks("game")}
        else{
            mPresenter.searchBooks(actionSearch.text.toString())
        }
    }

    override fun showLoadingBooks(show: Boolean) {
        mBooksAdapter.clearList()
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
        actionRefresh visibility true

        tvError.text = "$message"
    }
    override fun showError(code: Int, message: String?) {
        mBooksAdapter.clearList()
        tvError visibility true
        actionRefresh visibility true
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
