package com.txtled.gpa220.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.txtled.gpa220.R;
import com.txtled.gpa220.bean.BleConnectInfo;
import com.txtled.gpa220.widget.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BleAdapter extends RecyclerView.Adapter<BleAdapter.BleViewHolder> {
    private Context context;
    private List<BluetoothDevice> data;
    private OnBleItemClickListener listener;
    private int mPosition;

    public BleAdapter(Context context, OnBleItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<BluetoothDevice> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void notifyItem(){
        notifyItemChanged(mPosition);
    }

    public void removeAll(){
        data = null;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ble, parent, false);
        return new BleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BleViewHolder holder, int position) {
        if (data != null){
            holder.ctvBleName.setText(data.get(position).getName());
            holder.ctvConnect.setText(data.get(position).getBondState() == 12 ? R.string.connected : R.string.dis_conn);
            holder.itemView.setOnClickListener(v -> {
                mPosition = position;
                listener.onBleClick(data.get(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class BleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ctv_ble_name)
        CustomTextView ctvBleName;
        @BindView(R.id.ctv_connect)
        CheckedTextView ctvConnect;
        @BindView(R.id.img_ble_i)
        ImageView imgBleI;
        public BleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnBleItemClickListener{
        void onBleClick(BluetoothDevice info);
    }
}
