package com.example.earringdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<Post> posts;
    private  final  SimpleDateFormat   dateFormat; // 날짜 형식 지정을 위한 포맷터

    public GridAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    public void updateData(List<Post> updatedPosts) {
        this.posts = updatedPosts;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // grid_item_layout.xml 레이아웃을 사용하여 새로운 View 생성
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
        }

        // 레이아웃 내부의 뷰 요소들을 찾아옴
        ImageView itemImage = convertView.findViewById(R.id.gridItemImage);
        TextView itemText = convertView.findViewById(R.id.gridItemText);
        TextView itemDate = convertView.findViewById(R.id.gridItemDate); // 날짜와 시간을 표시할 TextView
        TextView itemTime = convertView.findViewById(R.id.gridItemTime); // 날짜와 시간을 표시할 TextView

        // 현재 위치의 Post 객체 가져오기
        Post post = getItem(position);
        if (post != null) {
            // 이미지, 내용 등을 레이아웃에 설정
            itemText.setText(post.getPostContent()); // 게시물 내용 설정

            // Post 객체의 getPostDate 및 getPostTime 메서드를 통해 날짜와 시간 가져와서 설정
            itemDate.setText(post.getPostDate()); // 게시물 날짜 설정
            itemTime.setText(post.getPostTime()); // 게시물 시간 설정
        }

        return convertView;
    }

}
