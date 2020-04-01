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

import com.inuker.bluetooth.library.search.SearchResult;
import com.txtled.gpa220.R;
import com.txtled.gpa220.widget.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BleAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SearchResult> data;
    private OnBleItemClickListener listener;
    private int mPosition;

    public BleAdapter(Context context, OnBleItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<SearchResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void notifyItem() {
        notifyItemChanged(mPosition);
    }

    public void removeAll() {
        data = null;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_ble,
                    parent, false);
            return new BleViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_header,
                    parent, false);
            return new HeaderViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (data != null && position > 0) {
            ((BleViewHolder)holder).ctvBleName.setText(data.get(position - 1).getName());
            ((BleViewHolder)holder).ctvBleAddress.setText(data.get(position - 1).getAddress());
            //((BleViewHolder)holder).ctvConnect.setText(data.get(position - 1). ? R.string.connected : R.string.dis_conn);
            ((BleViewHolder)holder).itemView.setOnClickListener(v -> {
                listener.onBleClick(data.get(position - 1));
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 1 : data.size() + 1;
    }

    public class BleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_ble)
        ImageView imgItemBle;
        @BindView(R.id.ctv_ble_name)
        CustomTextView ctvBleName;
        @BindView(R.id.ctv_ble_address)
        CustomTextView ctvBleAddress;
        @BindView(R.id.ctv_connect)
        CheckedTextView ctvConnect;
        public BleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnBleItemClickListener {
        void onBleClick(SearchResult info);
    }
}
