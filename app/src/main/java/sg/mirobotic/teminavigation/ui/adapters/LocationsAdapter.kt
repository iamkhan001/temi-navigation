package sg.mirobotic.teminavigation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sg.mirobotic.teminavigation.databinding.ItemLocationBinding

class LocationsAdapter(private val itemClickListener: ItemClickListener<String>): RecyclerView.Adapter<LocationsAdapter.MyViewHolder>() {

    private var list = ArrayList<String>()

    fun setData(list: ArrayList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: ItemLocationBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemLocationBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = list[position]
        holder.binding.tvName.text = location

        holder.itemView.setOnClickListener { itemClickListener.onItemClick(location) }
    }

}