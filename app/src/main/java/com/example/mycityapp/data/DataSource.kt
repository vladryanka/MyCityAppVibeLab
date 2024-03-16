package com.example.mycityapp.data

import com.example.mycityapp.R

object DataSource {
    val recommendations = listOf(
        Recommendation(
            id = 1,
            R.string.sky_caffe_title,
            R.string.sky_caffe_description,
            R.drawable.sky_caffe_image,
            R.string.caffe
        ),
        Recommendation(
            2,
            R.string.minerals_caffe_title,
            R.string.minerals_caffe_description,
            R.drawable.minerals_caffe_image,
            R.string.caffe
        ),

        Recommendation(
            3,
            R.string.amusement_park_title,
            R.string.amusement_park_description,
            R.drawable.amusement_park_image,
            R.string.park
        ),
        Recommendation(
            4,
            R.string.summer_garden_park_title,
            R.string.summer_garden_park_description,
            R.drawable.summer_garden_park_image,
            R.string.park
        ),

        Recommendation(
            5,
            R.string.cinema_food_title,
            R.string.cinema_food_description,
            R.drawable.cinema_food_image,
            R.string.cinema
        ),
        Recommendation(
            6,
            R.string.film_cinema_title,
            R.string.film_cinema_description,
            R.drawable.film_cinema_image,
            R.string.cinema
        )
    )
}
object SelectedScr{
    var scr = 0
    var selectedRec = 0
}