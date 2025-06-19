package org.muhammad.notflix.util

import kotlinx.coroutines.CoroutineDispatcher

interface  Dispatcher{
     val  io : CoroutineDispatcher
     val  main : CoroutineDispatcher
     val  default : CoroutineDispatcher
}

expect object DispatcherProvider : Dispatcher