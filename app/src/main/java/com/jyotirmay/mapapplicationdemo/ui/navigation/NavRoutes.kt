package com.jyotirmay.mapapplicationdemo.ui.navigation

object NavRoutes {
    const val MAP_GRAPH = "map_graph"
    const val MAP = "map"
    const val DETAIL = "detail/{pointKey}"
    const val BOOK_SUMMARY = "book_summary"
    const val USAGE_HISTORY = "usage_history"

    fun detailRoute(pointKey: String): String = "detail/$pointKey"
}
