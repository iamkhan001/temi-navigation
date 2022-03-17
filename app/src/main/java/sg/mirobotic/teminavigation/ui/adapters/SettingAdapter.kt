package sg.mirobotic.teminavigation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sg.mirobotic.teminavigation.data.TextIcon
import sg.mirobotic.teminavigation.databinding.ItemSettingOptionBinding

class SettingAdapter(private val list: ArrayList<TextIcon>, private val itemClickListener: ItemClickListener<TextIcon>): RecyclerView.Adapter<SettingAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: ItemSettingOptionBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemSettingOptionBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSettingOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvName.text = item.name
        holder.binding.imgIcon.setImageResource(item.icon)

        holder.itemView.setOnClickListener { itemClickListener.onItemClick(item) }

    }

}