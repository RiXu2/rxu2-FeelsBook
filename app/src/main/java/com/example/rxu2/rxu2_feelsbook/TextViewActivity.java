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
import java.io.BufferedWriter;
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
    private static final String FILENAME1 = "emotionList.sav";
    private EditText editText,num;

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


         //count of the history emotions
        final String[] feelList = loadEmotion();
        num = findViewById(R.id.num);
        count = findViewById(R.id.countButton);
        countnum = findViewById(R.id.countEmotion);
        count.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(feelList.length == 0){
                    countnum.setText("zero");
                }
                else{
                    final String checkedFeel = num.getText().toString();
                    int a = 0;
                    for(int i = 0; i < feelList.length;i++){
                        if(feelList[i].toString().equals(checkedFeel.toString())){
                            a++;
                        }
                        countnum.setText(Integer.toString(a));
                    }
                }
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
    private String[] loadEmotion() {
        ArrayList<String> emos = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME1);
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
            String new_str = editText.getText().toString();
            String ori_str = arrayList.get(position).toString();
            try {
                deleteFromFile(ori_str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            arrayList.set(position,new_str);
            tvTemp.setText(arrayList.get(position));
            adapter.notifyDataSetChanged();
            saveInFile(new_str);
        }
    };
    private AdapterView.OnItemClickListener deleteItem = new AdapterView.OnItemClickListener() { //edit by click listView
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //enter new feel first,then click related
            String deleteText;
            deleteText = arrayList.get(position).toString();
            try {
                deleteFromFile(deleteText);
            } catch (IOException e) {
                e.printStackTrace();
            }
            arrayList.remove(position);
            adapter.notifyDataSetChanged();

        }
    };
    private void deleteFromFile(String text) throws IOException {
        File inputFile = new File(FILENAME);
        File tempFile = new File("myTempFile.sav");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = text;
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(inputFile);
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
