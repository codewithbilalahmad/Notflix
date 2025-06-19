package org.muhammad.notflix.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.muhammad.notflix.domain.model.Actor
import org.muhammad.notflix.domain.model.Cast
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails
import org.muhammad.notflix.domain.model.MovieResponse
import org.muhammad.notflix.domain.util.Constants.API_KEY
import org.muhammad.notflix.domain.util.Constants.BASE_URL
import org.muhammad.notflix.util.TimeWindow

class MovieClientApi(private val httpClient: HttpClient) {
    suspend fun getPopularMovies(page: Int): MovieResponse {
        val url = "${BASE_URL}/movie/popular"
        return httpClient.get(urlString = url) {
            parameter("api_key", API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getTreadingMovies(timeWindow: String = TimeWindow.DAY.name.lowercase(), page : Int): MovieResponse {
        val url = "${BASE_URL}/trending/movie/$timeWindow"
        return httpClient.get(urlString = url){
            parameter("api_key", API_KEY)
            parameter("page", page)
        }.body()
    }
    suspend fun getUpcomingMovies(page : Int) : MovieResponse{
        val url = "${BASE_URL}/movie/upcoming"
        return httpClient.get(urlString = url){
            parameter("api_key", API_KEY)
            parameter("page", page)
        }.body()
    }
    suspend fun getSearchMovies(query : String, page : Int) : MovieResponse{
        val url = "${BASE_URL}/search/movie"
        return httpClient.get(urlString = url){
            parameter("api_key", API_KEY)
            parameter("page", page)
            parameter("query", query)
        }.body()
    }
    suspend fun getMovieDetail(movieId : Int) : MovieDetails{
        val url = "${BASE_URL}/movie/$movieId"
        return httpClient.get(url){
            parameter("api_key", API_KEY)
        }.body()
    }
    suspend fun getSimilarMovies(movieId: Int, page: Int = 1): MovieResponse {
        return httpClient.get("${BASE_URL}/$movieId/similar") {
            parameter("api_key", API_KEY)
            parameter("page", page)
        }.body()
    }
    suspend fun getMovieCast(movieId : Int) : List<Actor>{
        return httpClient.get("${BASE_URL}/$movieId/credits"){
            parameter("api_key", API_KEY)
        }.body()
    }
    suspend fun getNowPlayingMovies(page : Int) : MovieResponse{
        val url = "${BASE_URL}/movie/now_playing"
        return httpClient.get(url){
            parameter("api_key", API_KEY)
            parameter("page", page)
            parameter("language", "en-US")
        }.body()
    }
}