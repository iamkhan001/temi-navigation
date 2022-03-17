package sg.mirobotic.teminavigation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sg.mirobotic.teminavigation.data.Path
import sg.mirobotic.teminavigation.databinding.ItemLocationBinding
import sg.mirobotic.teminavigation.databinding.ItemPathBinding

class PathListAdapter(private val itemClickListener: ItemClickListener<Path>): RecyclerView.Adapter<PathListAdapter.MyViewHolder>() {

    private var list = ArrayList<Path>()

    fun setData(list: ArrayList<Path>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: ItemPathBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemPathBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPathBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val path = list[position]
        holder.binding.tvName.text = path.name

        holder.itemView.setOnClickListener { itemClickListener.onItemClick(path) }
    }

}