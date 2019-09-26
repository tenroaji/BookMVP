package com.lagecong.books.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

data class Book (
    @SerializedName("kind")
    var kind : String? = "",
    @SerializedName("items")
    var items : MutableList<BookResponse>
)

data class BookResponse (
    @SerializedName("volumeInfo")
    var info : VolumeInfo? = null
)

data class VolumeInfo(
    @SerializedName("title")
    var title : String? = "",
    @SerializedName("authors")
    var authors : MutableList<String>? = null,
    @SerializedName("imageLinks")
    var image : imageLinks? = null,
    @SerializedName ("averageRating")
    var averageRating : Double? = 0.0
)

data class imageLinks (
    @SerializedName("thumbnail")
    var thumbnail : String? = null
)