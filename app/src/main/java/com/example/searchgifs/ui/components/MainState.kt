package com.example.searchgifs.ui.components

import com.example.searchgifs.network.responses.GifsItem


data class MainState(
    val isLoading:Boolean=false,
    val data: List<GifsItem> = emptyList(),
    val error:String=""
)
