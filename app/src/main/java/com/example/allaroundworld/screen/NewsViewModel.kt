package com.example.allaroundworld.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allaroundworld.network.NewsModel
import com.example.allaroundworld.repo.Repo

import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    var res = mutableStateOf<NewsModel?>(null)
    private var currentCountry = "us"

    init {
        viewModelScope.launch {
            res.value = getNews(Repo())
        }
    }

    private suspend fun getNews(repo: Repo): NewsModel? {
        return repo.newProvider(currentCountry).body()
    }

    fun updateCountry(newCountry: String) {
        currentCountry = newCountry
        viewModelScope.launch {
            res.value = getNews(Repo())
        }
    }
}
