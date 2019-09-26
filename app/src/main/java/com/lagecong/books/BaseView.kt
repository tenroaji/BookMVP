package com.lagecong.books


interface BaseView<T> {
    fun setPresenter(presenter : T)
}