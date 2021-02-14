package com.example.comeondoit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shrikanthravi.collapsiblecalendarview.data.Day
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar.CalendarListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val collapsibleCalendar = findViewById<CollapsibleCalendar>(R.id.calendar)
        var year: String? = collapsibleCalendar.year.toString()
        var date: String = collapsibleCalendar.selectedDay.toString()
        collapsibleCalendar.setCalendarListener(object : CalendarListener {
            //select day
            override fun onDaySelect() {
                val list = findViewById<RecyclerView>(R.id.list_cases_grid)
                date = collapsibleCalendar.year.toString() + "-" + (collapsibleCalendar.month + 1) / 10 + (collapsibleCalendar.month + 1) + "-" + collapsibleCalendar.todayItemPosition
                val adapter = ItemListCaseAdapter(year.toString(), date)
                list.adapter = adapter
                val decoration =
                        DividerItemDecoration(this@MainActivity, DividerItemDecoration.HORIZONTAL)
                decoration.setDrawable(
                        ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.colorPrimaryDark
                        )!!
                )
                list.addItemDecoration(decoration)
                adapter.notifyDataSetChanged()
                adapter.setOnCLickListener(object : ItemListCaseAdapter.onItemClickListener {
                    override fun onСlick(itemListCase: ItemListCase) {
                        if (itemListCase != null) {
                            val intent = Intent(this@MainActivity, ItemListCaseFull::class.java)
                            intent.putExtra("title", itemListCase.title)
                            intent.putExtra("time", itemListCase.timeStart + "-" + itemListCase.timeEnd)
                            intent.putExtra("date", itemListCase.date)
                            intent.putExtra("description", itemListCase.description)
                            startActivity(intent)
                        }
                    }
                })
            }

            // triggered only when the views of day on calendar are clicked by user.
            override fun onItemClick(v: View) {
            }

            // triggered when the data of calendar are updated by changing month or adding events.
            override fun onDataUpdate() {}

            // triggered when the month are changed.
            override fun onMonthChange() {}

            // triggered when the week position are changed.
            override fun onWeekChange(position: Int) {}

            override fun onClickListener() {}

            override fun onDayChanged() {

            }
        })
        val buttonAddItem: FloatingActionButton = findViewById<FloatingActionButton>(R.id.add_case);
        buttonAddItem.setOnClickListener() {
            val intent = Intent(this, AddItemListCase::class.java)
            startActivity(intent)
        }
    }
}
