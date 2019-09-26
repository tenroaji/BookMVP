package com.lagecong.books.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lagecong.books.R
import com.lagecong.books.data.models.Book
import com.lagecong.books.data.models.BookResponse
import com.lagecong.books.data.models.VolumeInfo
import com.lagecong.books.utils.visibility
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_books.view.*

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

class BooksAdapter(private val mWidth: Int) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {
    private var data: MutableList<BookResponse>? = mutableListOf()
    private var show = true

    fun updateAdapter(data: MutableList<BookResponse>?) {
        data?.also {
            this.data?.clear()
            this.data?.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clearList() {
        data?.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_list_books, parent, false)
            .also {
                return ViewHolder(it)
            }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.run {
            val layoutParams = layoutParams as RecyclerView.LayoutParams
            layoutParams.apply {
                width = mWidth
                height = RecyclerView.LayoutParams.WRAP_CONTENT
                bottomMargin = context.resources.getDimensionPixelSize(R.dimen.margin_16dp)

                if (position%3 == 0) {
                    marginStart = context.resources.getDimensionPixelSize(R.dimen.margin_16dp)
                    marginEnd =  (context.resources.displayMetrics.density * 5.5F).toInt()
                }else if (position%3 == 2){
                    marginStart = (context.resources.displayMetrics.density * 5.5F).toInt()
                    marginEnd =  context.resources.getDimensionPixelSize(R.dimen.margin_16dp)
                }
                else{
                    marginStart =(context.resources.displayMetrics.density * 10.75).toInt()
                }
            }
            holder.itemView.layoutParams = layoutParams



            val book = data?.get(position)?.info
            val authors = book?.authors

            holder.itemView.setOnClickListener {
                viewAuthors visibility show
                labelAuthors visibility show
                tvAuthors visibility show
                if(!authors.isNullOrEmpty()){
                    var auth = ""

                    for (i in authors ){
                        auth += "$i \n"
                    }
                    tvAuthors.text = auth
                }else tvAuthors.text = ""

                show = !show
            }
            tvTitle.text = book?.title

            rating_book.rating = book?.averageRating?.toFloat() ?: 0F

            val image = book?.image
            Picasso.get()
                .load(image?.thumbnail)
                .into(item_image)
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}