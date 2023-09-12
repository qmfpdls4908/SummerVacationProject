/*
일정추가하기 클래스
 */
package com.example.earringdiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class adjust_day extends AppCompatActivity
{
    Button addDayBtn;
    EditText title_Text, loc_Text, cmt_Text;
    TextView StartDay_Text, EndDay_Text;
    TextView StartTime_Text, EndTime_Text;
    Calendar calendar = Calendar.getInstance();
    DBHelper myDb;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_day);

        // intent 받는 부분
        Intent intent = getIntent();
        String str = intent.getStringExtra("str");

        //초기화
        String Email = "qmfpdls4908@naver.com";
        title_Text = findViewById(R.id.title_Text);
        loc_Text = findViewById(R.id.loc_Text);
        cmt_Text = findViewById(R.id.cmt_Text);
        StartDay_Text = findViewById(R.id.StartDay_Text);
        EndDay_Text = findViewById(R.id.EndDay_Text);
        StartTime_Text = findViewById(R.id.StartTime_Text);
        EndTime_Text = findViewById(R.id.EndTime_Text);
        addDayBtn = findViewById(R.id.addDayBtn);

        // 금일 일자관련 출력
        long now = System.currentTimeMillis();  //현재시간 가져오기
        Date date = new Date(now);  //Date 형색으로 Convert
        String getTime = dateFormat.format(date);
        StartDay_Text.setText(getTime);
        EndDay_Text.setText(getTime);

        //시작 날짜 클릭시
        StartDay_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(StartDay_Text);
            }
        });

        //끝 날짜 클릭시
        EndDay_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(EndDay_Text);
            }
        });

        // 시작 시간 클릭 시
        StartTime_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(StartTime_Text);
            }
        });

        // 끝 시간 클릭 시
        EndTime_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(EndTime_Text);
            }
        });

        //저장 버튼 클릭 시
        addDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_title = title_Text.getText().toString().trim();
                String str_loc = loc_Text.getText().toString().trim();
                String str_cmt = cmt_Text.getText().toString().trim();
                String str_startDay = StartDay_Text.getText().toString().trim();
                String str_endDay = EndDay_Text.getText().toString().trim();
                String str_startTime = StartTime_Text.getText().toString().trim();
                String str_endTime = EndTime_Text .getText().toString().trim();
                AddUserData();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "일정 등록에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "일정 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                setCalenderRequest setCalender = new setCalenderRequest(Email, str_title, str_startDay, str_startTime, str_endDay, str_endTime, str_loc, str_cmt,responseListener);
                RequestQueue queue = Volley.newRequestQueue(adjust_day.this);
                queue.add(setCalender);
                finish();
            }
        });
    }

    //달력처리
    private void showDatePicker(final TextView this_Text){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // 날짜 선택 시 호출되는 콜백 함수
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);

                        this_Text.setText(String.format("%d-%d-%d", year, month+1, day));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    //시간처리
    private void showTimePicker(final TextView this_Text) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hour, int min) {
                        // 시간 선택 시 호출되는 콜백 함수
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, min);

                        this_Text.setText(String.format("%d:%d", hour, min));
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true // 24시간 형식 사용
        );

        timePickerDialog.show();
    }

    //DB 처리
    public void AddUserData() {
        myDb = new DBHelper(adjust_day.this);
        myDb.addDay(title_Text.getText().toString().trim(),
                loc_Text.getText().toString().trim(),
                cmt_Text.getText().toString().trim(),
                StartDay_Text.getText().toString().trim(),
                EndDay_Text.getText().toString().trim(),
                StartTime_Text.getText().toString().trim(),
                EndTime_Text .getText().toString().trim());
    }

}

