package org.muhammad.notflix.domain.util


sealed class Result<out T>{
    data object Loading : Result<Nothing>()
    data class Success<T>(val response : T) : Result<T>()
    data class Error(val error : String) : Result<Nothing>()
}