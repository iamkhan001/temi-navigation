package sg.mirobotic.teminavigation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sg.mirobotic.teminavigation.R

class TextAdapter(private val list: ArrayList<String>, private val itemClickListener: ItemClickListener<Int>): RecyclerView.Adapter<TextAdapter.MyViewHolder>() {


    class  MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvName.text = list[position]

        holder.itemView.setOnClickListener { itemClickListener.onItemClick(position) }

    }

}