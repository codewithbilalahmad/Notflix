package org.muhammad.notflix.domain.repository

import org.muhammad.notflix.domain.model.Actor
import org.muhammad.notflix.domain.model.Cast
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails
import org.muhammad.notflix.domain.model.MovieResponse

interface MovieDetailRepository {
    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
    suspend fun fetchMovieCast(movieId: Int): List<Actor>
    suspend fun fetchSimilarMovies(movieId: Int): MovieResponse
}