package com.txtled.gpa220.unknown;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<UserData> data;
    private int oldPosition;
    private OnUserClickListener listener;

    public UserAdapter(Context context, OnUserClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public void setData(List<UserData> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_member, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {

        if (data != null) {
            holder.ctvMemberTemp.setVisibility(View.GONE);
            Bitmap bitmap;
            RoundedBitmapDrawable roundedDrawable;
            if (oldPosition == position){
                holder.ctvMemberName.setTextColor(context.getResources().getColor(R.color.text_black));
            }
            switch (data.get(position + 1).getSex()){
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
                default:
                    bitmap = Utils.drawableToBitmap(context.getResources()
                            .getDrawable(R.mipmap.home_girlxhdpi));
                    roundedDrawable = RoundedBitmapDrawableFactory
                            .create(context.getResources(),bitmap);
                    roundedDrawable.setCircular(true);
                    holder.imgMember.setImageDrawable(roundedDrawable);
                    holder.imgMember.setBackground(context.getResources()
                            .getDrawable(R.drawable.woman_selector));
                    break;
            }
            holder.ctvMemberName.setText(data.get(position + 1).getUserName());
            holder.imgMember.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        holder.ctvMemberName.setTextColor(context.getResources().getColor(R.color.blue));
                        listener.onUserClick(position);
                        if (oldPosition != position)
                            notifyItemChanged(oldPosition);
                        oldPosition = position;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : (data.size() - 1);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_member)
        ImageView imgMember;
        @BindView(R.id.ctv_member_name)
        CustomTextView ctvMemberName;
        @BindView(R.id.ctv_member_temp)
        CustomTextView ctvMemberTemp;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnUserClickListener{
        void onUserClick(int position);
    }
}
