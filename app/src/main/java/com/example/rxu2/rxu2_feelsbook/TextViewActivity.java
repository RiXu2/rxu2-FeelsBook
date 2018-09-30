package com.example.rxu2.rxu2_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class TextViewActivity extends AppCompatActivity {
    private TextView countnum;
    private Button count;
    private TextView oldemotion;
    private static final String FILENAME = "file.sav";
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        count = findViewById(R.id.countButton);
        countnum = findViewById(R.id.countEmotion);
        count.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //try {
                  //  num = countLines(FILENAME);
                //} catch (IOException e) {
                  //  e.printStackTrace();
                //}
                //countnum.setText(Integer.toString(num));

            }
        });
        oldemotion = (TextView) findViewById(R.id.oldEmotions);

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
