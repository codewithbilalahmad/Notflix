package org.muhammad.notflix.data.local

import org.muhammad.notflix.domain.model.MovieDetails
import orgmuhammadnotflix.FavoriteMovies

interface DataStore {
    fun saveFavoriteMovie(movieDetails: MovieDetails)
    fun getAllFavouriteMovies() : List<FavoriteMovies>
    fun getFavoriteMovie(id : Long) : FavoriteMovies
    fun deleteFavoriteMovie(id : Long)
    fun deleteAllFavoriteMovies()
    fun isMovieFavourite(id : Long) : Boolean
}