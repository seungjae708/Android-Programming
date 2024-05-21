package com.example.week10

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val firstName:String, val lastName:String)
enum class ItemEvent { ADD, UPDATE, DELETE }

class MyViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<Item>>()
    val items = ArrayList<Item>()
    var itemsEvent = ItemEvent.ADD
    var itemsEventPos = -1

    val itemClickEvent = MutableLiveData<Int>()
    var itemLongClick = -1

    fun getItem(pos: Int) =  items[pos]
    val itemsSize
        get() = items.size

    fun addItem(item: Item) {
        itemsEvent = ItemEvent.ADD
        itemsEventPos = items.size
        items.add(item)
        itemsListData.value = items
    }
    fun updateItem(pos: Int, item: Item) {
        itemsEvent = ItemEvent.UPDATE
        itemsEventPos = pos
        items[pos] = item
        itemsListData.value = items
    }
    fun deleteItem(pos: Int) {
        itemsEvent = ItemEvent.DELETE // 데이터가 삭제됨
        itemsEventPos = pos // 삭제 직전의 위치
        items.removeAt(pos)
        itemsListData.value = items
    }

}