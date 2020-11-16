package com.boyko.savestate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter(var items: List<ContactModel>, val callback: Callback) : RecyclerView.Adapter<Adapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val firstName = itemView.findViewById<TextView>(R.id.contact_name)
        private val lastName = itemView.findViewById<TextView>(R.id.contact_number)

        fun bind(item: ContactModel) {
            firstName.text = item.username
            lastName.text = item.phone
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: ContactModel)
    }
}