package com.example.comeondoit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.full_discription_item_list_case.view.*
import java.util.*

class ItemListCaseFull : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_discription_item_list_case)
        val title: TextView = findViewById(R.id.title)
        val description: TextView = findViewById(R.id.description)
        val textDate: TextView = findViewById(R.id.date)
        val time: TextView = findViewById(R.id.time)
        title.text = intent.getStringExtra("title")
        time.text = intent.getStringExtra("time")
        textDate.text = intent.getStringExtra("date")
        description.text = intent.getStringExtra("description")
    }
}
