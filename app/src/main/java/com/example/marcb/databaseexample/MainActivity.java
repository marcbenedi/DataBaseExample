package com.example.marcb.databaseexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcb.databaseexample.database.MyDataBaseHelper;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button buttonCreate;
    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonQuery;

    private final String TAG = "MainActivity";

    private MyDataBaseHelper myDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //Initialize objects
        textView = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editText1);
        buttonCreate = (Button) findViewById(R.id.button1);
        buttonUpdate = (Button) findViewById(R.id.button2);
        buttonDelete = (Button) findViewById(R.id.button3);
        buttonQuery = (Button) findViewById(R.id.button4);

        myDataBaseHelper = MyDataBaseHelper.getInstance(this);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                int afected = myDataBaseHelper.updateRow(s);
                textView.setText("The new name of the row is "+s.charAt(0)+s);
                Toast.makeText(view.getContext(),afected+" rows were afected!", Toast.LENGTH_SHORT).show();

            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                long id = myDataBaseHelper.createRow(s);
                Toast.makeText(view.getContext(),"We created a row with "+id+"!", Toast.LENGTH_SHORT).show();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                textView.setText("");
                myDataBaseHelper.deleteRow(s);
                Toast.makeText(view.getContext(),"We deleted a row!", Toast.LENGTH_SHORT).show();

            }
        });

        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                String id = myDataBaseHelper.queryRow(s);
                textView.setText(id);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Here we close the db instance because we are not gonna need it more in this activity
        myDataBaseHelper.close();
        Log.v(TAG,"onDestroy()");
    }
}
