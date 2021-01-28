package ru.geekbrains.noteapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.model.data.Color
import ru.geekbrains.noteapp.model.data.Note


class NoteAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

     var values: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.note_item_title)
        private val contentView: TextView = view.findViewById(R.id.note_item_content)

        fun bind(item: Note) {
            with(item) {
                titleTextView.text = title
                contentView.text = content

                val color = when(item.color) {
                    Color.WHITE -> R.color.color_white
                    Color.PURPLE -> R.color.color_purple
                    Color.YELLOW -> R.color.color_yellow
                }
                view.setBackgroundResource(color)
                view.setOnClickListener{listener.onItemClick(item)}
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}


interface OnItemClickListener {
    fun onItemClick(note: Note)
}