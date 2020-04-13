package com.txtled.gpa220.unknown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.txtled.gpa220.R;
import com.txtled.gpa220.widget.CustomTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private Context context;
    private List<Float> data;
    private HashMap<Integer, Boolean> checked;
    private OnDataClickListener listener;

    public DataAdapter(Context context, OnDataClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Float> data) {
        if (data != null) {
            this.data = data;
            checked = new HashMap<>();
            notifyDataSetChanged();
        }
    }

    public void insertData(float singleData) {
        if (data == null){
            data = new ArrayList<>();
            data.add(singleData);
        }
        notifyItemChanged(data.size() - 1);

    }

    public HashMap<Integer, Boolean> getChecked() {
        return checked;
    }

    @NonNull
    @Override
    public DataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_unbind_data, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataViewHolder holder, int position) {

        if (data != null) {
            holder.ctvUnbindData.setText(data.get(position) + "℃");
            if (data.get(position) > 37.2f && data.get(position) < 38.0f){
                holder.ctvUnbindData.setTextColor(context.getResources().getColor(R.color.orange));
            }else if (data.get(position) >= 38.0f){
                holder.ctvUnbindData.setTextColor(context.getResources().getColor(R.color.red));
            }else {
                holder.ctvUnbindData.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
            holder.ckUnbindCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onDataClick();
                    if (isChecked) {
                        checked.put(position, true);
                        holder.ckUnbindCheck.setBackground(context.getResources()
                                .getDrawable(R.drawable.circle_selected));
                    } else {
                        checked.put(position, false);
                        holder.ckUnbindCheck.setBackground(context.getResources()
                                .getDrawable(R.drawable.circle_unselected));
                    }
                }
            });
            holder.itemView.setOnClickListener(v -> {
                listener.onDataClick();
                try {
                    if (checked.get(position)) {
                        holder.ckUnbindCheck.setChecked(false);
                    } else {
                        holder.ckUnbindCheck.setChecked(true);
                    }
                } catch (Exception e) {
                    holder.ckUnbindCheck.setChecked(true);
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ck_unbind_check)
        CheckBox ckUnbindCheck;
        @BindView(R.id.ctv_unbind_data)
        CustomTextView ctvUnbindData;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnDataClickListener {
        void onDataClick();
    }
}
