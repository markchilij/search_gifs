package com.example.searchgifs.network.responses

data class GifsItem(

    val id: String,
    val images: Images,
    val title: String,
    val type: String,
    val url: String,

)