package org.muhammad.notflix.util

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger

@Composable
fun String?.loadImage() : String{
    return if(this.isNullOrEmpty()) "https://image.tmdb.org/t/p/w500/$this" else ""
}


fun PlatformContext.getAsyncImageLoader() =
    ImageLoader.Builder(this).crossfade(true).memoryCachePolicy(CachePolicy.ENABLED).memoryCache{
        MemoryCache.Builder().maxSizePercent(this, 0.3).strongReferencesEnabled(true).build()
    }.logger(DebugLogger()).build()