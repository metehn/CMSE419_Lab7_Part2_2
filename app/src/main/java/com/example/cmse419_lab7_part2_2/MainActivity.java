package com.example.cmse419_lab7_part2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextSurname, editTextNumber;

    Button buttonAdd, buttonList;


    String filename = "people.txt";
    FileOutputStream fos = null;

    DB_SQLite MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextNumber = findViewById(R.id.editTextNumber);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonList = findViewById(R.id.buttonList);


        MyDB = new DB_SQLite(this);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextName.getText().toString().isEmpty() && !editTextSurname.getText().toString().isEmpty() && !editTextNumber.getText().toString().isEmpty()) {

                    String person = editTextName.getText().toString() + " " + editTextSurname.getText().toString() + " " + editTextNumber.getText().toString();


                    try {

                        SaveDB("people", editTextName.getText().toString(), editTextSurname.getText().toString(), editTextNumber.getText().toString());

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "DB Error", Toast.LENGTH_SHORT).show();
                    } finally {
                        MyDB.close();
                    }


                } else {

                    Toast.makeText(MainActivity.this, "All areas must be filled", Toast.LENGTH_SHORT).show();
                }


            }
        });

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent mainintent = new Intent(MainActivity.this, MainActivity2.class);

                startActivity(mainintent);


            }
        });


    }


    protected void SaveDB(String Table, String name, String surname, String phone) {

        SQLiteDatabase db = MyDB.getWritableDatabase();

        ContentValues cv1 = new ContentValues();

        cv1.put("name", name);
        cv1.put("surname", surname);
        cv1.put("phone", phone);

        db.insertOrThrow(Table, null, cv1);
        db.close();

        editTextName.setText("");
        editTextSurname.setText("");
        editTextNumber.setText("");
        Toast.makeText(this, "Save Ok", Toast.LENGTH_SHORT).show();

    }

}