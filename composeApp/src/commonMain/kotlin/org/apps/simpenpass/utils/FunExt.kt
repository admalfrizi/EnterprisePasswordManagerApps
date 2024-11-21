package org.apps.simpenpass.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem
import org.apps.simpenpass.presentation.components.DialogLoading

fun isValidEmail(email: String): Boolean{
    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}\$")
    return emailPattern.matches(email)
}

fun profileNameInitials(name: String): String {
    val truncateStr = name
        .split(' ')
        .mapNotNull { it.firstOrNull()?.toString() }
        .reduce { acc, s -> acc + s }

    if(truncateStr.length <= 2){
        return truncateStr
    } else {
        return truncateStr.substring(0,2)
    }
}

fun detectRoute(navController: NavController): String? {
    val mainRoute = navController.previousBackStackEntry?.destination?.route

    return mainRoute
}

@Composable
fun popUpLoading(isDismiss : MutableState<Boolean>){
    DialogLoading(onDismissRequest = {isDismiss.value})
}

fun maskString(data: String): String {
    return "*".repeat(data.length)
}

expect fun setToast(message: String)
expect fun copyText(text: String)

@Composable
expect fun getScreenHeight(): Dp

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024) // 512MB
        .build()
}

expect fun disableScreenshots(enable: Boolean)
