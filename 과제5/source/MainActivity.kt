package com.example.week10

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val factory = MyViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)

        floatingActionButton.setOnClickListener {
            ItemDialog().show(supportFragmentManager, "ItemDialog")
        }

        val adapter = CustomAdapter(viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        viewModel.myData.observe(this) {
            recyclerView.adapter?.notifyDataSetChanged()
        }
        viewModel.itemClickEvent.observe(this) {
            ItemDialog(it).show(supportFragmentManager, "ItemDialog")
        }

        registerForContextMenu(recyclerView)
    }
    
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.ctx_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> viewModel.delteItem(viewModel.longClickItem)
            R.id.edit -> //viewModel.itemClickEvent.value = viewModel.longClickItem
            {
                // Edit을 눌렀을 때 ItemDialog에 수정할 항목의 위치를 전달
                ItemDialog(viewModel.longClickItem).show(supportFragmentManager, "ItemDialog")
            }
            else -> return false
        }
        return true
    }

}

