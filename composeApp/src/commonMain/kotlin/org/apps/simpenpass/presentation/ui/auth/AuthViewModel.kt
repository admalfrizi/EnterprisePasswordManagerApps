package org.apps.simpenpass.presentation.ui.auth

import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import org.apps.simpenpass.data.remoteData.AuthApi

class AuthViewModel(
    private val client: HttpClient,
    private val repo : AuthApi = AuthApi(client)
) : ViewModel() {

    suspend fun login(email: String, password: String) = repo.login(email, password).data

}