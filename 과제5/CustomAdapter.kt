package com.example.week10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val viewModel: MyViewModel) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun setContents(pos: Int) {
            val textView = itemView.findViewById<TextView>(R.id.textView)
            val textView2 = itemView.findViewById<TextView>(R.id.textView2)
            textView.text=viewModel.myData.value?.get(pos)?.name ?: ""
            textView2.text=viewModel.myData.value?.get(pos)?.address ?: ""
        }
    }

    // ViewHolder 생성, ViewHolder 는 View 를 담는 상자
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(R.layout.item_layout, viewGroup, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
           viewModel.itemClickEvent.value = viewHolder.adapterPosition
        }
        view.setOnLongClickListener {
            viewModel.longClickItem = viewHolder.adapterPosition
            false // for context menu
        }
        return viewHolder
    }

    // ViewHolder 에 데이터 쓰기
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)
    }

    override fun getItemCount() = viewModel.myData.value?.size ?: 0
}