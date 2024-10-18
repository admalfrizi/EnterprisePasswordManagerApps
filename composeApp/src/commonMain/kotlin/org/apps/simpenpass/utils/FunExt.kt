package org.apps.simpenpass.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import org.apps.simpenpass.presentation.components.DialogLoading

fun isValidEmail(email: String): Boolean{
    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}\$")
    return emailPattern.matches(email)
}

fun profileNameInitials(name: String): String {
    return name
        .split(' ')
        .mapNotNull { it.firstOrNull()?.toString() }
        .reduce { acc, s -> acc + s }
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