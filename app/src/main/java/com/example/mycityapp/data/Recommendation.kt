package com.example.mycityapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recommendation(
    val id: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    @StringRes val category:Int
)
