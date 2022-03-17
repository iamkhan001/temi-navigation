package sg.mirobotic.teminavigation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sg.mirobotic.teminavigation.data.TextIcon
import sg.mirobotic.teminavigation.databinding.ItemHomeBinding

class HomeAdapter(private val list: ArrayList<TextIcon>, private val itemClickListener: ItemClickListener<TextIcon>): RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: ItemHomeBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemHomeBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvTitle.text = item.name
        holder.binding.tvTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, item.icon, 0, 0)

        holder.itemView.setOnClickListener { itemClickListener.onItemClick(item) }
    }

}