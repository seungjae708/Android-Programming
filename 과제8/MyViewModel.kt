package com.example.a14

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Repo(val name: String, val owner: Owner, val url: String)

data class Owner(val login: String)

class MyViewModel : ViewModel() {

    private val _repos = MutableLiveData<List<Repo>>()
    val repos: LiveData<List<Repo>> get() = _repos

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(GitHubApi::class.java)

    fun fetchRepos(username: String) {
        viewModelScope.launch {
            try {
                val repos = api.getRepos(username)
                _repos.postValue(repos)
            } catch (e: Exception) {
                //_repos.postValue(emptyList())
            }
        }
    }
    interface GitHubApi {
        @GET("users/{username}/repos")
        suspend fun getRepos(@Path("username") username: String): List<Repo>
    }
}

