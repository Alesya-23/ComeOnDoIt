package com.example.comeondoit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comeondoit.model.TimeGrid
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar

class TimeGridAdapter(Context: Context, private val android: List<TimeGrid>) :
        RecyclerView.Adapter<TimeGridAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeStGrid: TextView = itemView.findViewById(R.id.timeSt_grid)
        private val timeEnGrid: TextView = itemView.findViewById(R.id.timeEn_grid)
        private val caseView: View = itemView.findViewById(R.id.view_title)
        fun bind(grid: TimeGrid) {
            timeStGrid.text = grid.timeStart
            timeEnGrid.text = grid.timeEnd
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TimeGridAdapter.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.time_grid, parent, false))
    }

    private fun getItem(position: Int): TimeGrid = android[position]

    override fun getItemCount(): Int = android.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
