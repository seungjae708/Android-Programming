package org.techtown.homework1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val btnOK = findViewById<Button>(R.id.button)
        val txtViewTitle = findViewById<TextView>(R.id.textView)
        val editText = findViewById<EditText>(R.id.editText)
        btnOK.setOnClickListener {
            var str = editText.text.toString()
            txtViewTitle.text = str
        }
    }
}