package com.example.rxu2.rxu2_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


public class TextViewActivity extends AppCompatActivity {
    private TextView countnum;
    private Button count,delete,edit;
    private TextView oldemotion;
    private static final String FILENAME = "file.sav";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        count = findViewById(R.id.countButton);
        countnum = findViewById(R.id.countEmotion);
        count.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) { //bu yun xing
                try {
                    FileReader fr = new FileReader(FILENAME);
                    LineNumberReader lnr = new LineNumberReader(fr);
                    int countOfLines = 0;
                    while (lnr.readLine() != null) {
                        countOfLines++;
                    }
                    lnr.close();
                    countnum.setText(Integer.toString(countOfLines));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        oldemotion = (TextView) findViewById(R.id.oldEmotions);
        delete = findViewById(R.id.deleteButton);
        edit = findViewById(R.id.editButton);
        delete.setOnClickListener(new View.OnClickListener() { //bu yun xing
            @Override
            public void onClick(View v) {
                try{
                    RandomAccessFile raf = new RandomAccessFile(FILENAME, "rw");
                    long length = raf.length();
                    System.out.println("File Length="+raf.length());
                    //supposing that last line is of 8
                    raf.setLength(length - 8);
                    System.out.println("File Length="+raf.length());
                    raf.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {  //Edit button
            @Override
            public void onClick(View v) {
                List<String> lines = new ArrayList<String>();
                BufferedReader r = null;
                try {
                    r = new BufferedReader(new FileReader(FILENAME));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String in;
                assert r != null;
                try {
                    while ((in = r.readLine()) != null) {
                        lines.add(in);
                    }
                    r.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String secondFromBottom = lines.get(lines.size() - 2);
                lines.remove(lines.size() - 1);
                editText = findViewById(R.id.editText);
                String str = editText.getText().toString();
                lines.add(str);

                PrintWriter w = null;
                try {
                    w = new PrintWriter(new FileWriter(FILENAME));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String line : lines)
                    w.println(line);
                w.close();
            }
        });

    }
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        String[] emos = loadFromFile();
        for(int i = 0; i < emos.length;i++){
            oldemotion.setText(emos[i]);
        }
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
}
