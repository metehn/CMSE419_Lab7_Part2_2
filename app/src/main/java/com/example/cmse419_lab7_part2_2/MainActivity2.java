package com.example.cmse419_lab7_part2_2;

import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity {

    ListView lv;
    Button buttonUpdate;
    ArrayAdapter<String> adapter;
    ArrayList<String> people=new ArrayList<String>();
    ArrayList<String> temp=new ArrayList<String>();
    ArrayList<String> delete=new ArrayList<String>();
    DB_SQLite DB;

     boolean activated= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        lv = findViewById(R.id.lv);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        DB=new DB_SQLite(this);

        ReadDB(people);

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,people);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                people.remove(position);
                adapter.notifyDataSetChanged();

                activated =true;


            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReadDB(temp);
                if (activated){

                    for (String str : temp) {

                        if (!people.contains(str)) {
                            delete.add(str);
                        }
                    }

                for (String str : delete) {

                    String[] strList = str.split(" ");
                    SQLiteDatabase db1 = DB.getWritableDatabase();

                    String[] aa = {strList[2]};
                    db1.delete("people", "phone=?", aa);

                    db1.close();


                }
                Toast.makeText(MainActivity2.this, "Successfully removed", Toast.LENGTH_SHORT).show();
            }
                else{
                    Toast.makeText(MainActivity2.this, "First remove person to update", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    String[] tableinfo={"name","surname","phone"};
    public  void ReadDB(ArrayList<String> people){

        SQLiteDatabase db = DB.getReadableDatabase();

        Cursor c=db.query("people",tableinfo,null,null,null,null,tableinfo[2]);

        while (c.moveToNext()){

            people.add(c.getString(0)+" "+c.getString(1)+" "+c.getString(2));
        }

        db.close();


    }


}