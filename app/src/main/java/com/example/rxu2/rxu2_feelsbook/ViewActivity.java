package com.example.rxu2.rxu2_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private TextView list;
    private static final String FILENAME = "file.sav";
    @Override
    protected void onCreate(Bundle savedInstanceState) { //view all histories
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        list = (TextView) findViewById(R.id.allHistories); //show all history emotion in listview
        final String[] emos = loadFromFile();
        for(int i = 0; i < emos.length;i++){
            list.setText(emos[i]);
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
