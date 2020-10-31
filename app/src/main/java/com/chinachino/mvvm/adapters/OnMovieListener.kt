package com.chinachino.mvvm.adapters

interface OnMovieListener {
    fun onMovieClick(position: Int)
    fun onCategoryClick(category: String?)
}