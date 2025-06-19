package org.muhammad.notflix.ui.screens.home

import org.muhammad.notflix.domain.model.Movie

data class HomeState(
    val trendingMovies : List<Movie>,
    val upComingMovies : List<Movie>,
    val popularMovies : List<Movie>,
    val nowPlayingMovies : List<Movie>,
)
