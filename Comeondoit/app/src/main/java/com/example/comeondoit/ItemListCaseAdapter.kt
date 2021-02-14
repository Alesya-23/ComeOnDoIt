package com.example.comeondoit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ItemListCaseAdapter(year: String, date: String) :
        RecyclerView.Adapter<ItemListCaseAdapter.ItemViewHolder>() {
    private val itemsListCase: ArrayList<ItemListCase> = ArrayList()
    private var listener: onItemClickListener? = null
    private var currentItem: Int = 0;
    private var myRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    val rootRef = FirebaseDatabase.getInstance().reference.child(year).child(date)
            .addValueEventListener(object :
                    ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    itemsListCase.clear()
                    for (dataSnapshot: DataSnapshot in snapshot.children) {
                        itemsListCase.add(currentItem, ItemListCase())
                        itemsListCase[currentItem].title =
                                snapshot.child(dataSnapshot.key.toString()).child("title").value.toString()
                        itemsListCase[currentItem].description =
                                snapshot.child(dataSnapshot.key.toString())
                                        .child("description").value.toString()
                        itemsListCase[currentItem].date =
                                snapshot.child(dataSnapshot.key.toString()).child("date").value.toString()
                        itemsListCase[currentItem].timeStart =
                                snapshot.child(dataSnapshot.key.toString())
                                        .child("timeStart").value.toString()
                        itemsListCase[currentItem].timeEnd = snapshot.child(dataSnapshot.key.toString())
                                .child("timeEnd").value.toString()
                        currentItem++
                    }
                    notifyDataSetChanged()
                }
            })

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ItemViewHolder {
        val itemView: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_list_case, parent, false)
        return ItemViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        val currentItem = itemsListCase[position]
        holder.title.text = currentItem.title
        holder.time.text = currentItem.timeStart.toString() + "-" + currentItem.timeEnd.toString()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_case_short)
        val time: TextView = itemView.findViewById(R.id.time_case_short)
        fun bind(grid: ItemListCase) {
            title.text = "title"
            time.text = "16:00"
        }
        init {
            itemView.setOnClickListener(View.OnClickListener {
                listener?.let { listener ->
                    val position: Int = adapterPosition
                    if (position in 0..itemCount) {
                        listener.onСlick(itemsListCase[position])
                    }
                }
            })
        }
    }

    fun setOnCLickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    interface onItemClickListener {
        abstract fun onСlick(itemListCase: ItemListCase)
    }

    fun setItems(itemsListCase: ArrayList<ItemListCase>) {
        this.itemsListCase.clear()
        this.itemsListCase.addAll(itemsListCase)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): ItemListCase = itemsListCase[position]

    override fun getItemCount(): Int {
        return itemsListCase.size
    }
}
