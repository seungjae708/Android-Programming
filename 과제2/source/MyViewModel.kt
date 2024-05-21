package com.example.week7

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val countLiveData = MutableLiveData<Int>()
    init {
        countLiveData.value=0
    }

    fun increaseCount() {
        countLiveData.value = (countLiveData.value ?: 0) +1
    }
}