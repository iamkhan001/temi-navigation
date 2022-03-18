package sg.mirobotic.teminavigation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomergoldst.timekeeper.model.Alarm
import sg.mirobotic.teminavigation.databinding.ItemAlarmBinding

class AlarmsListAdapter(private val onAlarmOptionSelectListener: OnAlarmOptionSelectListener): RecyclerView.Adapter<AlarmsListAdapter.MyViewHolder>() {

    private var list = ArrayList<Alarm>()

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    fun setData(list: List<Alarm>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: ItemAlarmBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemAlarmBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val alarm = list[position]

        holder.binding.tvName.text = alarm.payload
        holder.binding.tvTime.text = alarm.readableDate

        holder.binding.imgDelete.setOnClickListener { onAlarmOptionSelectListener.onDelete(alarm) }
    }

    interface OnAlarmOptionSelectListener {
        fun onDelete(alarm: Alarm)
    }
}