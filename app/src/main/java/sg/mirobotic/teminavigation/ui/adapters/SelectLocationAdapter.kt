package sg.mirobotic.teminavigation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sg.mirobotic.teminavigation.databinding.ItemLocationSelectBinding

class SelectLocationAdapter(private val itemClickListener: OnLocationUpdateListener): RecyclerView.Adapter<SelectLocationAdapter.MyViewHolder>() {

    private var list = ArrayList<String>()

    fun setData(list: ArrayList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addLocation(location: String) {
        this.list.add(location)
        notifyItemInserted(list.size)
        itemClickListener.onAdd(list)
    }

    private fun removeLocation(position: Int) {
        this.list.removeAt(position)
        itemClickListener.onRemoved(list)
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<String> = list

    class MyViewHolder(itemView: ItemLocationSelectBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemLocationSelectBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemLocationSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = list[position]
        holder.binding.tvName.text = location

        holder.binding.imgRemove.setOnClickListener {
            removeLocation(position)
        }

    }

    fun clearData() {
        list = ArrayList()
        notifyDataSetChanged()
        itemClickListener.onRemoved(list)
    }

    interface OnLocationUpdateListener {

        fun onAdd(list: ArrayList<String>)

        fun onRemoved(list: ArrayList<String>)

    }


}