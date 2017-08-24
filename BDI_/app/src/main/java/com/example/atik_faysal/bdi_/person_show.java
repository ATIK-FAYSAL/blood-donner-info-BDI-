package com.example.atik_faysal.bdi_;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Vector;

public class person_show extends Activity
{
   static Vector<String>email = new Vector<>();
    static Vector<String>name = new Vector<>();
    static Vector<String>city = new Vector<>();
    static String[] Name,Email,City;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personlistview);
        show_person();
    }
   public static void get_email(Vector<String>vector)
    {
        email = vector;
    }
    public static void get_name(Vector<String>vector)
    {
        name = vector;
    }
    private void show_person()
    {
        String str="",get_city;
        String string="";
        String[] value;
        for(int i=0;i<email.size();i++)string+="\n"+name.get(i)+"            "+email.get(i)+"\n"+",";
        if(string.equals(""))value = new String[]{"Sorry no data found"};
        else value = string.split(",");
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,R.layout.persontextview,value);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String)list.getItemAtPosition(position);
                if(str.equals("Sorry no data found"))
                {

                }
                else
                {
                    PersonInformation.get_email(email.get(position));
                    Intent page = new Intent(person_show.this,PersonInformation.class);
                    startActivity(page);
                }
            }
        });
    }
}
