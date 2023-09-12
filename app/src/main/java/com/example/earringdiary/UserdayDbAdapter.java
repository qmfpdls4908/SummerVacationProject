package com.example.earringdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserdayDbAdapter extends RecyclerView.Adapter<UserdayDbAdapter.MyViewHolder> {
    Context context;
    ArrayList<UserDays> userDaysList = new ArrayList<>();
    UserdayDbAdapter(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public MyViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@Nullable MyViewHolder holder, int position){
        UserDays userdays = userDaysList.get(position);

        holder.titleText.setText(String.valueOf(userdays.getUserDay_title()));  //title
        holder.dateText.setText(String.valueOf(userdays.getUserDay_sDay()));    //시작 날짜

        /*사진
        byte[] imageIcon = userdays.getUser_image();    //사진
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageIcon, 0, userdays.length);
        holder.imageView.setImageBitmap(bitmap);
         */
    }
    @Override
    public int getItemCount(){
        return userDaysList.size();
    }

    /**
     * 아이템 삭제
     * @param position 위치
     */
    public void removeItem(int position){
        userDaysList.remove(position);
    }

    public void addItem(UserDays item){
        userDaysList.add(item);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, dateText;
        ImageView imageIcon;
        LinearLayout main_layout;

        public MyViewHolder(@Nullable View itemView){
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            dateText = itemView.findViewById(R.id.dateText);
            imageIcon = itemView.findViewById(R.id.imageIcon);
            main_layout = itemView.findViewById(R.id.main_layout);
        }
    }
}
