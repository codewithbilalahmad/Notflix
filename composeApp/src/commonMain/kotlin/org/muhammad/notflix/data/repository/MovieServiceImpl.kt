package org.muhammad.notflix.data.repository

import org.muhammad.notflix.data.remote.MovieClientApi
import org.muhammad.notflix.domain.model.Actor
import org.muhammad.notflix.domain.model.Cast
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails
import org.muhammad.notflix.domain.model.MovieResponse
import org.muhammad.notflix.domain.repository.MovieDetailRepository
import org.muhammad.notflix.domain.repository.MoviesRespository
import org.muhammad.notflix.domain.repository.SearchRepository

class MovieServiceImpl(private val movieClientApi: MovieClientApi) : MovieDetailRepository, MoviesRespository, SearchRepository{
    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        return movieClientApi.getMovieDetail(movieId)
    }

    override suspend fun fetchMovieCast(movieId: Int): List<Actor> {
        return movieClientApi.getMovieCast(movieId)
    }

    override suspend fun fetchSimilarMovies(movieId: Int): MovieResponse {
        return movieClientApi.getSimilarMovies(movieId)
    }

    override suspend fun fetchNowPlayingMovies(page : Int): MovieResponse {
        return movieClientApi.getNowPlayingMovies(page)
    }

    override suspend fun fetchTreadingMovies(timeWindow: String, page : Int): MovieResponse {
        return movieClientApi.getTreadingMovies(timeWindow, page)
    }

    override suspend fun fetchPopularMovies(page : Int): MovieResponse {
        return movieClientApi.getPopularMovies(page)
    }

    override suspend fun fetchUpComingMovies(page : Int): MovieResponse {
        return movieClientApi.getUpcomingMovies(page)
    }

    override suspend fun searchMovie(movieName: String, page : Int): MovieResponse {
        return movieClientApi.getSearchMovies(query = movieName, page = page)
    }

}