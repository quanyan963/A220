package com.txtled.gpa220.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.txtled.gpa220.R;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.utils.Utils;
import com.txtled.gpa220.widget.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Quan on 2020/3/30.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private Context context;
    private List<UserData> data;
    private long exitTime;
    private OnItemClick listener;

    public MemberAdapter(Context context, OnItemClick listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<UserData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void insertData(UserData user){
        data.add(user);
        notifyItemInserted(0);
    }

    public void deleteData(int position) {
        data.remove(position);
        notifyItemRemoved(data.size() - position - 1);
        if (position != 0)
            notifyItemRangeChanged(data.size() - position, position);
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        if (data != null) {
            holder.ctvMemberName.setText(data.get(data.size() - position - 1).getUserName());
            if (data.get(data.size() - position - 1).getData() != null){
                List<Float> temp = data.get(data.size() - position - 1).getData();
                holder.ctvMemberTemp.setText(temp.get(temp.size() - 1) + "℃");
                if (temp.get(temp.size() - 1) > 37.2f && temp.get(temp.size() - 1) < 38.0f){
                    holder.ctvMemberTemp.setTextColor(context.getResources().getColor(R.color.orange));
                }else if (temp.get(temp.size() - 1) >= 38.0f){
                    holder.ctvMemberTemp.setTextColor(context.getResources().getColor(R.color.red));
                }else {
                    holder.ctvMemberTemp.setTextColor(context.getResources().getColor(R.color.text_black));
                }
            }else {
                holder.ctvMemberTemp.setText("");
            }
            Bitmap bitmap;
            RoundedBitmapDrawable roundedDrawable;
            switch (data.get(data.size() - position - 1).getSex()){
                case 0:
                    bitmap = Utils.drawableToBitmap(context.getResources()
                            .getDrawable(R.mipmap.home_boyxhdpi));
                    roundedDrawable = RoundedBitmapDrawableFactory
                            .create(context.getResources(),bitmap);
                    roundedDrawable.setCircular(true);
                    holder.imgMember.setImageDrawable(roundedDrawable);
                    holder.imgMember.setBackground(context.getResources()
                            .getDrawable(R.drawable.man_selector));
                    break;
                case 1:
                    bitmap = Utils.drawableToBitmap(context.getResources()
                            .getDrawable(R.mipmap.home_girlxhdpi));
                    roundedDrawable = RoundedBitmapDrawableFactory
                            .create(context.getResources(),bitmap);
                    roundedDrawable.setCircular(true);
                    holder.imgMember.setImageDrawable(roundedDrawable);
                    holder.imgMember.setBackground(context.getResources()
                            .getDrawable(R.drawable.woman_selector));
                    break;
                default:
                    bitmap = Utils.drawableToBitmap(context.getResources()
                            .getDrawable(R.mipmap.home_unkownxhdpi));
                    roundedDrawable = RoundedBitmapDrawableFactory
                            .create(context.getResources(),bitmap);
                    roundedDrawable.setCircular(true);
                    holder.imgMember.setImageDrawable(roundedDrawable);
                    holder.imgMember.setBackground(context.getResources()
                            .getDrawable(R.drawable.man_selector));
                    break;
            }

            holder.imgMember.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (System.currentTimeMillis() - exitTime > 600){
                        listener.onOnceClick(data.size() - position - 1);
                        exitTime = System.currentTimeMillis();
                    }else {
                        listener.onTwiceClick(data.size() - position - 1,
                                data.get(data.size() - position - 1).getSex());
                    }
                }
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void update(int mPosition, UserData str) {
        data.add(mPosition,str);
    }

    public void notifyTrueItem(int mPosition) {
        notifyItemChanged(data.size() - 1 - mPosition);
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_member)
        ImageView imgMember;
        @BindView(R.id.ctv_member_name)
        CustomTextView ctvMemberName;
        @BindView(R.id.ctv_member_temp)
        CustomTextView ctvMemberTemp;
        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClick{
        void onOnceClick(int position);
        void onTwiceClick(int position,int userType);
    }
}
