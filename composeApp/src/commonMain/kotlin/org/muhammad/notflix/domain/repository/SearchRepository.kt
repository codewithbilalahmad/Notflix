package org.muhammad.notflix.domain.repository

import org.muhammad.notflix.domain.model.MovieResponse

interface SearchRepository {
    suspend fun searchMovie(movieName: String, page : Int): MovieResponse
}