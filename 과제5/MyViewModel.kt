package com.example.week10

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModel(private val repository: MyRepository) : ViewModel() {
    var longClickItem: Int = -1

    val myData = repository.myDao.getAll()
    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(name: String, address: String) {
        repository.addItem(name, address)
    }

    fun updateItem(pos: Int, name: String, address: String) {
        myData.value?.let {
            repository.updateItem(Item(it[pos].id, name, address))
        }
    }

    fun delteItem(pos: Int) {
        myData.value?.let {
            repository.deleteItem(it[pos].id)
        }
    }
}

class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            MyViewModel(MyRepository(context)) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}

