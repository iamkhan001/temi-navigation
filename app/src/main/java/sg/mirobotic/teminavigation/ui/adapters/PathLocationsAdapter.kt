package sg.mirobotic.teminavigation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import sg.mirobotic.teminavigation.R
import sg.mirobotic.teminavigation.databinding.ItemLocationBinding

class PathLocationsAdapter(private val context: Context, private val itemClickListener: ItemClickListener<String>): RecyclerView.Adapter<PathLocationsAdapter.MyViewHolder>() {

    private var list = ArrayList<String>()
    private var point = 0

    fun setData(list: ArrayList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setCurrentPoint(point: Int) {
        this.point = point
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

        when {
            point == position -> {
                holder.binding.cvMain.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue_dark))
            }
            position < point -> {
                holder.binding.cvMain.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green_dark))
            }
            else -> {
                holder.binding.cvMain.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card_bg_dark))
            }
        }

        holder.binding.tvName.text = location

        holder.itemView.setOnClickListener { itemClickListener.onItemClick(location) }
    }

}