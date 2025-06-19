package org.muhammad.notflix.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.muhammad.notflix.data.local.DataSourceImpl
import org.muhammad.notflix.data.repository.MovieServiceImpl
import org.muhammad.notflix.data.repository.SettingsImpl
import org.muhammad.notflix.domain.model.Actor
import org.muhammad.notflix.domain.model.Cast
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails
import org.muhammad.notflix.domain.model.MovieResponse
import org.muhammad.notflix.domain.util.Result
import org.muhammad.notflix.ui.screens.home.HomeState
import orgmuhammadnotflix.FavoriteMovies

class MovieViewModel(
    private val repository: MovieServiceImpl,
    private val database: DataSourceImpl,
    private val settings: SettingsImpl,
) : ViewModel() {
    private val _homeStateData = MutableStateFlow<Result<HomeState>>(Result.Loading)
    val homeStateData = _homeStateData.asStateFlow()
    private val _movieDetail = MutableStateFlow<Result<MovieDetails>>(Result.Loading)
    val movieDetail = _movieDetail.asStateFlow()
    private val _movieCast = MutableStateFlow<Result<List<Actor>>>(Result.Loading)
    val movieCast = _movieCast.asStateFlow()
    private val _similarMovies = MutableStateFlow<Result<MovieResponse>>(Result.Loading)
    val similarMovies = _similarMovies.asStateFlow()
    private val _searchMovies = MutableStateFlow<Result<MovieResponse>>(Result.Loading)
    val searchMovies = _searchMovies.asStateFlow()
    private val _favoriteMovies = MutableStateFlow<Result<List<FavoriteMovies>>>(Result.Loading)
    val favoriteMovies = _favoriteMovies.asStateFlow()
    private val _favoriteMovie = MutableStateFlow<Result<FavoriteMovies>>(Result.Loading)
    val favoriteMovie = _favoriteMovie.asStateFlow()
    private val _themePrefernces = MutableStateFlow(0)
    val themePrefernces = _themePrefernces.asStateFlow()
    private val _qualityPrefernces = MutableStateFlow(0)
    val qualityPrefernces = _qualityPrefernces.asStateFlow()
    private val _languagePrefernces = MutableStateFlow("")
    val languagePrefernces = _languagePrefernces.asStateFlow()
    private var popularCurrentPage = 1
    private var isPopularLastPage = false
    private var upComingCurrentPage = 1
    private var upComingLastPage = false
    private var similarCurrentPage = 1
    private var similarLastPage = false
    private var nowPlayingCurrentPage = 1
    private var nowPlayingLastPage = false
    private var searchCurrentPage = 1
    private var searchLastPage = false
    private var trendingCurrentPage = 1
    private var trendingLastPage = false

    fun getHomeState(){
        viewModelScope.launch {
            _homeStateData.value = Result.Loading
            try {

            } catch (e : Exception){
                
            }
        }
    }

    fun getDetailMovie(movieId : Int){
        viewModelScope.launch {
            _movieDetail.value = Result.Loading
            try {
                val response =repository.fetchMovieDetails(movieId)
                _movieDetail.value =Result.Success(response)
            } catch (e : Exception){
                _movieDetail.value = Result.Error(e.message.toString())
            }
        }
    }
    fun getMovieCast(movieId : Int){
        viewModelScope.launch {
            _movieCast.value = Result.Loading
            try {
                val response = repository.fetchMovieCast(movieId)
                _movieCast.value = Result.Success(response)
            } catch (e : Exception){
                _movieCast.value = Result.Error(e.message.toString())
            }
        }
    }


    suspend fun loadMorePopularMovies() {
        if (_popularMovies.value == Result.Loading || isPopularLastPage) return
        popularCurrentPage++
        viewModelScope.launch {
            try {
                val response = repository.fetchPopularMovies(popularCurrentPage)
                if (response.results.isNotEmpty()) {
                    val currentMovies =
                        (_popularMovies.value as? Result.Success)?.response?.results ?: emptyList()
                    val updatedMovies = currentMovies + response.results
                    _popularMovies.value =
                        Result.Success(response = response.copy(results = updatedMovies))
                    isPopularLastPage = response.total_pages == popularCurrentPage
                } else {
                    isPopularLastPage = true
                }
            } catch (e: Exception) {
                _popularMovies.value = Result.Error(e.message.toString())
                popularCurrentPage--
            }
        }
    }


    suspend fun loadMoreTreadingMovies() {
        if (_trendingMovies.value == Result.Loading || trendingLastPage) return
        trendingCurrentPage++
        try {
            val response = repository.fetchTreadingMovies(page = trendingCurrentPage)
            if (response.results.isNotEmpty()) {
                val currentPage =
                    (_trendingMovies.value as? Result.Success)?.response?.results ?: emptyList()
                val updatedPages = currentPage + response.results
                _trendingMovies.value =
                    Result.Success(response = response.copy(results = updatedPages))
                trendingLastPage = response.total_pages == popularCurrentPage
            } else {
                trendingLastPage = true
            }
        } catch (e: Exception) {
            _trendingMovies.value = Result.Error(e.message.toString())
            trendingCurrentPage--
        }
    }


    suspend fun loadMoreUpcomingMovies() {
        if (_upComingMovies.value == Result.Loading || upComingLastPage) return
        upComingCurrentPage++
        try {
            val response = repository.fetchUpComingMovies(upComingCurrentPage)
            if (response.results.isNotEmpty()) {
                val currentMovies =
                    (_upComingMovies.value as? Result.Success)?.response?.results ?: emptyList()
                val updatedMovies = currentMovies + response.results
                _upComingMovies.value = Result.Success(response.copy(results = updatedMovies))
                upComingLastPage = response.total_pages == upComingCurrentPage
            } else {
                upComingLastPage = true
            }
        } catch (e: Exception) {
            _upComingMovies.value = Result.Error(e.message.toString())
            popularCurrentPage--
        }
    }

    fun getSimilarMovies(movieId : Int) {
        viewModelScope.launch {
            _similarMovies.value = Result.Loading
            try {
                val response = repository.fetchSimilarMovies(movieId)
                _similarMovies.value = Result.Success(response)
            } catch (e: Exception) {
                val error = e.message.toString()
                _similarMovies.value = Result.Error(error)
            }
        }
    }

    suspend fun loadMoreSimilarMovies() {
        if (_similarMovies.value == Result.Loading || similarLastPage) return
        similarCurrentPage++
        try {
            val response = repository.fetchUpComingMovies(similarCurrentPage)
            if (response.results.isNotEmpty()) {
                val currentMovies =
                    (_similarMovies.value as? Result.Success)?.response?.results ?: emptyList()
                val updatedMovies = currentMovies + response.results
                _upComingMovies.value =
                    Result.Success(response = response.copy(results = updatedMovies))
                similarLastPage = response.total_pages == similarCurrentPage
            } else {
                similarLastPage = true
            }
        } catch (e: Exception) {
            _upComingMovies.value = Result.Error(e.message.toString())
            similarCurrentPage--
        }
    }


    suspend fun loadMoreNowPlayingMovies() {
        if (_similarMovies.value == Result.Loading || nowPlayingLastPage) return
        nowPlayingCurrentPage++
        try {
            val response = repository.fetchNowPlayingMovies(nowPlayingCurrentPage)
            if (response.results.isNotEmpty()) {
                val currentMovies =
                    (_nowPlayingMovies.value as? Result.Success)?.response?.results ?: emptyList()
                val updatedMovies = currentMovies + response.results
                _nowPlayingMovies.value =
                    Result.Success(response = response.copy(results = updatedMovies))
                nowPlayingLastPage = response.total_pages == nowPlayingCurrentPage
            } else {
                nowPlayingLastPage = true
            }
        } catch (e: Exception) {
            _upComingMovies.value = Result.Error(e.message.toString())
            nowPlayingCurrentPage--
        }
    }

    fun getSearchMovies(query: String) {
        viewModelScope.launch {
            _searchMovies.value = Result.Loading
            try {
                val response = repository.searchMovie(movieName = query, page = 1)
                _searchMovies.value = Result.Success(response)
            } catch (e: Exception) {
                val error = e.message.toString()
                _searchMovies.value = Result.Error(error)
            }
        }
    }

    suspend fun loadMoreSearchMovies(query: String) {
        if (_searchMovies.value == Result.Loading || searchLastPage) return
        searchCurrentPage++
        try {
            val response = repository.searchMovie(query, searchCurrentPage)
            if (response.results.isNotEmpty()) {
                val currentMovies =
                    (_searchMovies.value as? Result.Success)?.response?.results ?: emptyList()
                val updatedMovies = currentMovies + response.results
                _searchMovies.value = Result.Success(response.copy(results = updatedMovies))
                searchLastPage = response.total_pages == searchCurrentPage
            } else searchLastPage = true
        } catch (e: Exception) {
            _searchMovies.value = Result.Error(e.message.toString())
            searchCurrentPage--
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            _favoriteMovies.value = Result.Loading
            try {
                val response = database.getAllFavouriteMovies()
                _favoriteMovies.value = Result.Success(response)
            } catch (e: Exception) {
                _favoriteMovies.value = Result.Error(e.message.toString())
            }
        }
    }

    fun saveFavoriteMovie(movieDetails: MovieDetails) {
        database.saveFavoriteMovie(movieDetails)
    }

    fun getFavoriteMovie(id: Long) {
        _favoriteMovie.value = Result.Loading
        try {
            val response = database.getFavoriteMovie(id)
            _favoriteMovie.value = Result.Success(response)
        } catch (e: Exception) {
            _favoriteMovie.value = Result.Error(e.message.toString())
        }
    }

    fun deleteFavoriteMovie(id: Long) {
        database.deleteFavoriteMovie(id)
    }

    fun deleteAllFavouriteMovie() {
        database.deleteAllFavoriteMovies()
    }

    fun isMovieFavourite(id: Long) : Boolean {
      return  database.isMovieFavourite(id)
    }

    fun getThemePreferences() {
        viewModelScope.launch {
            try {
                val response = settings.getThemePreferences()
                _themePrefernces.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

     fun setThemePreferences(key: String, value: String) {
         viewModelScope.launch {
             settings.setThemePreferences(key = key, value = value.toInt())
         }
    }
    fun getImageQualityPreferences() {
        viewModelScope.launch {
            try {
                val response = settings.getImageQualityPreferences()
                _qualityPrefernces.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setImageQualityPreferences(key: String, value: String) {
        viewModelScope.launch {
            settings.setImageQualityPreferences(key = key, value = value.toInt())
        }
    }
    fun getLanguagePreferences() {
        viewModelScope.launch {
            try {
                val response = settings.getLanguagePreferences()
                _languagePrefernces.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun setLanguagePreferences(key: String, value: String) {
        viewModelScope.launch {
            settings.setLanguagePreferences(key = key, value = value)
        }
    }
}