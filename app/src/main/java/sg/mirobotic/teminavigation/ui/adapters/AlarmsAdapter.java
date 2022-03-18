package sg.mirobotic.teminavigation.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tomergoldst.timekeeper.model.Alarm;

import java.util.ArrayList;
import java.util.List;

import sg.mirobotic.teminavigation.R;

public class AlarmsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Data set
    private List<Alarm> mDataSet = new ArrayList<>();
    private OnAdapterInteractionListener mListener;

    public interface OnAdapterInteractionListener {
        void onItemClicked(Alarm alarm);
    }

    AlarmsAdapter(OnAdapterInteractionListener mListener) {
        this.mListener = mListener;
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder{
        TextView uidTxv;
        TextView timeTxv;
        TextView persistTxv;

        AlarmViewHolder(View v) {
            super(v);
            uidTxv = v.findViewById(R.id.alarmUidTxv);
            timeTxv = v.findViewById(R.id.alarmTimeTxv);
            persistTxv = v.findViewById(R.id.alarmPersistTxv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_alarm, parent, false);
        return new AlarmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Alarm alarm = mDataSet.get(position);
        AlarmViewHolder alarmViewHolder = (AlarmViewHolder) holder;
        alarmViewHolder.uidTxv.setText(String.valueOf(alarm.getUid()));
        alarmViewHolder.timeTxv.setText(alarm.getReadableDate());
        alarmViewHolder.persistTxv.setText(String.valueOf(alarm.isPersist()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onItemClicked(alarm);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    void addAll(@NonNull List<Alarm> alarm){
        mDataSet.addAll(alarm);
        notifyDataSetChanged();
    }

    void clear(){
        mDataSet.clear();
        notifyDataSetChanged();
    }

}
