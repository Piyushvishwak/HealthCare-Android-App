package com.example.traveldiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorAdapter(
    private val names: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(android.R.id.text1)

        init {
            view.setOnClickListener {
                onItemClick(names[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = names[position]
    }

    override fun getItemCount(): Int = names.size
}
