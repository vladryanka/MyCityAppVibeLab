package com.example.mycityapp.data

enum class RoutOnCity(val route: String) {
        Categories("categorys"),
        Recommendations("recommendation/{category}"),
        RecommendationDetail("recdetail/{recId}")

}