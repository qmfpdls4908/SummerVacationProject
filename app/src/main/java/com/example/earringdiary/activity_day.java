/*
activity_day 처리
 */
package com.example.earringdiary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class activity_day extends AppCompatActivity {
    private ImageButton btn_AddDay;
    DBHelper db;
    ArrayList<UserDays> userDaysList = new ArrayList<>();
    RecyclerView recyclerView;
    UserdayDbAdapter adapter;
    TextView noDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        //데이터 유무
        noDataText = findViewById(R.id.noDataText);

        // 리스트 보여줄 화면
        recyclerView = findViewById(R.id.recyclerView);

        //adapter
        adapter = new UserdayDbAdapter(activity_day.this);

        //어댑터 등록
        recyclerView.setAdapter(adapter);

        //레이아웃 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(activity_day.this));

        //db 생성
        db = new DBHelper(activity_day.this);

        //데이터 가져오기
        storeDataInArrays();

        btn_AddDay = findViewById(R.id.btn_AddDay);
        btn_AddDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_day = new Intent(getApplicationContext(), adjust_day.class);
                startActivity(add_day);
            }
        });

    }
    /**
     * 데이터 가져오기
     */
    void storeDataInArrays(){
        Cursor cursor = db.readAllData();

        if(cursor.getCount() == 0){
            noDataText.setVisibility(noDataText.VISIBLE);
        } else {
            noDataText.setVisibility(noDataText.GONE);
            while (cursor.moveToNext()){
                UserDays userdays = new UserDays(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
                //데이터 등록
                userDaysList.add(userdays);
                adapter.addItem(userdays);

                //적용
                adapter.notifyDataSetChanged();
            }
        }
    }
}
