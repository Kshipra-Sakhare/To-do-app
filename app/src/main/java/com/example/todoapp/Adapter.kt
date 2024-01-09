package com.example.todoapp

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ViewBinding
import java.util.Locale

class Adapter(private var data: List<CardInfo>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(private val viewBinding: ViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        val title = viewBinding.title
        val priority = viewBinding.priority
        val layout = viewBinding.mylayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ViewBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (data[position].priority.toLowerCase(Locale.ROOT)) {
            "high" -> holder.layout.setBackgroundColor(Color.parseColor("#F05454"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("#E4D00A"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("#AFE1AF"))
        }
        holder.title.text = data[position].title
        holder.priority.text = data[position].priority
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, update_card::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // Add a function to update the data in the adapter
    fun updateData(newData: List<CardInfo>) {
        data = newData
        notifyDataSetChanged()
    }
}
