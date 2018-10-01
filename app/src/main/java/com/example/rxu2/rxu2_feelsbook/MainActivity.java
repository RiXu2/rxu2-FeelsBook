package com.example.rxu2.rxu2_feelsbook;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView title,now_date,temp;
    private Button checkDate,changedate,add,history;
    private EditText DateEdit,comment;
    TimePicker tpTime;
    private String changingDate,emotion,comm;
    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.titleMain);
        title.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        Date date = new Date();
        changingDate = date.toString();

        checkDate = findViewById(R.id.checkDate);
        checkDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) { //current time, the default time of emotion
                Date date = new Date();
                String curr_date = date.toString();
                now_date = findViewById(R.id.current_time);
                now_date.setText(curr_date);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.feels_spinner);
        // select emotions. category: Love = green, Joy = blue, surprise = black, anger = red, sadness = cyan, fear = magenta
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.feelingsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(item.toString().equals("Affection") || item.toString().equals("Lust") || item.toString().equals("Longing")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GREEN);
                }
                else if(item.toString().equals("Cheerfulness") || item.toString().equals("Zest") || item.toString().equals("Contentment") || item.toString().equals("Pride") || item.toString().equals("Optimism") || item.toString().equals("Enthrallment") || item.toString().equals("Relief")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                }
                else if(item.toString().equals("Surprise")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                }
                else if(item.toString().equals("Irritation") || item.toString().equals("Exasperation") || item.toString().equals("Rage") || item.toString().equals("Disgust") || item.toString().equals("Envy") || item.toString().equals("Torment")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.RED);
                }
                else if(item.toString().equals("Suffering") || item.toString().equals("Sadness") || item.toString().equals("Disappointment") || item.toString().equals("Shame") || item.toString().equals("Neglect") || item.toString().equals("Sympathy")){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.CYAN);
                }
                else{
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.MAGENTA);
                }
                emotion = item.toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Set Date
        tpTime = (TimePicker)findViewById(R.id.tpTime);
        tpTime.setIs24HourView(true);
        tpTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                String time = timePicker.getCurrentHour() + " : " + timePicker.getCurrentMinute();
                DateEdit = (EditText) findViewById(R.id.edit_time);
                String day = DateEdit.getText().toString();
                changingDate = day +"-" + time; //day is not shown

            }
        });
        temp = findViewById(R.id.tempmess);
        changedate = findViewById(R.id.changeDate);
        changedate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) { //current time, the default time of emotion
                temp.setText(changingDate);
            }
        });

        comment = findViewById(R.id.comment);

        add = findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                comm = comment.getText().toString();
                String text = emotion + " " + changingDate + " "+comm ;
                saveInFile(text);
                //finish();

            }
        });


        history = (Button) findViewById(R.id.reviewButton);
        history.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this,TextViewActivity.class);
                startActivity(intent);
            }
        });

    }
    private void saveInFile(String text) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_APPEND);
            byte[] strToBytes = text.getBytes();
            fos.write(strToBytes);
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
