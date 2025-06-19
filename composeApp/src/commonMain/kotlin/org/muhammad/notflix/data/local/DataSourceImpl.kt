package org.muhammad.notflix.data.local

import org.muhammad.notflix.NotflixDatabase
import org.muhammad.notflix.domain.model.MovieDetails
import orgmuhammadnotflix.FavoriteMovies

class DataSourceImpl(db: NotflixDatabase) : DataStore {
    private val queries = db.movieDetailEntityQueries
    override fun saveFavoriteMovie(movieDetails: MovieDetails) {
        queries.saveFavoriteMovie(
            id = movieDetails.id.toLong(),
            imdbId = movieDetails.imdbId,
            posterPath = movieDetails.posterPath.toString(),
            originalLanguage = movieDetails.originalLanguage,
            originalTitle = movieDetails.originalTitle.toString(),
            overview = movieDetails.overview,
            status = movieDetails.status,
            tagLine = movieDetails.tagline,
            title = movieDetails.title,
            createdAt = movieDetails.createdAt.toString(),
            voteCount = movieDetails.voteCount?.toLong(),
            voteAverage = movieDetails.voteAverage?.toLong(),
            backdropPath = movieDetails.backdropPath.toString(),
            popularity = movieDetails.popularity,
            runtime = movieDetails.runtime?.toLong(), releaseDate = movieDetails.releaseDate
        )
    }

    override fun getAllFavouriteMovies(): List<FavoriteMovies> {
        return queries.getAllFavouriteMovies().executeAsList()
    }

    override fun getFavoriteMovie(id: Long): FavoriteMovies {
        return queries.getFavoriteMovie(id = id).executeAsOne()
    }

    override fun deleteFavoriteMovie(id: Long) {
        return queries.deleteFavoriteMovie(id)
    }

    override fun deleteAllFavoriteMovies() {
        queries.deleteAllFavoruiteMovies()
    }

    override fun isMovieFavourite(id: Long): Boolean {
        return queries.isMovieFavourite(id).executeAsOne()
    }

}