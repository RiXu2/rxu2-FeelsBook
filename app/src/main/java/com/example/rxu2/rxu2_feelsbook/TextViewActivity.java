package com.example.rxu2.rxu2_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TextViewActivity extends AppCompatActivity {
    private TextView countnum;
    private Button count,delete,edit;
    private static final String FILENAME = "file.sav";
    private EditText editText;

    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        list = (ListView) findViewById(R.id.oldEmotions); //show all history emotion in listview
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        list.setAdapter(adapter);
        //list.setOnItemClickListener(MshowforItem);
        final String[] emos = loadFromFile();
        for(int i = 0; i < emos.length;i++){
            arrayList.add(emos[i]);
            //adapter.notifyDataSetChanged();
        }
        Collections.sort(arrayList);
        adapter.notifyDataSetChanged();


        final int itemInList = adapter.getCount(); //count of the history emotions
        final String itemInListStr = Integer.toString(itemInList);
        count = findViewById(R.id.countButton);
        countnum = findViewById(R.id.countEmotion);
        count.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                countnum.setText(itemInListStr);
            }
        });


        delete = findViewById(R.id.deleteButton); //delete
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setOnItemClickListener(deleteItem);
            }
        });


        edit = findViewById(R.id.editButton);//edit
        edit.setOnClickListener(new View.OnClickListener() {  //Edit button
            @Override
            public void onClick(View v) {
                list.setOnItemClickListener(MshowforItem);
            }
        });

    }
    private String[] loadFromFile() {
        ArrayList<String> emos = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null) {
                emos.add(line);
                line = in.readLine();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return emos.toArray(new String[emos.size()]);
    }
    private AdapterView.OnItemClickListener MshowforItem = new AdapterView.OnItemClickListener() { //edit by click listView
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //enter new feel first,then click related
            TextView tvTemp = ((TextView) view);                                           //position in listView, and click edit button
            editText = findViewById(R.id.editText);
            String str = editText.getText().toString();
            arrayList.set(position,str);
            tvTemp.setText(arrayList.get(position));
            adapter.notifyDataSetChanged();
            saveInFile(str);
        }
    };
    private AdapterView.OnItemClickListener deleteItem = new AdapterView.OnItemClickListener() { //edit by click listView
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //enter new feel first,then click related
            arrayList.remove(position);
            adapter.notifyDataSetChanged();

        }
    };
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
