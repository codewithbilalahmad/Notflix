package org.muhammad.notflix.domain.repository

import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieResponse
import org.muhammad.notflix.util.TimeWindow

interface MoviesRespository {
    suspend fun fetchNowPlayingMovies(page : Int):MovieResponse
    suspend fun fetchTreadingMovies(
        timeWindow: String = TimeWindow.DAY.name.lowercase(),page : Int
    ): MovieResponse
    suspend fun fetchPopularMovies(page : Int) : MovieResponse
    suspend fun fetchUpComingMovies(page : Int) : MovieResponse
}