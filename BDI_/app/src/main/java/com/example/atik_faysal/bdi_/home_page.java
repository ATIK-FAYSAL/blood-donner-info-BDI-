package com.example.atik_faysal.bdi_;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;

public class home_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String city,blood_group;
    final static Vector<String>v_name = new Vector<>();
    final static Vector<String>v_email = new Vector<>();
    final static Vector<String>v_city = new Vector<>();
    static String[] value1,value2,value3;
    static String name1="",email1="",city1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        set_city();
        set_blood_group();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.update)
        {
            try
            {
                Intent page = new Intent(home_page.this,update_information.class);
                startActivity(page);
            }
            catch (Exception e)
            {
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        else if (id==R.id.setDate)
        {
            Intent page = new Intent(home_page.this,set_date.class);
            startActivity(page);
        }
        else if (id == R.id.Log_out)
        {
            SetProgressDialog();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        Thread.sleep(3000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        Close_activity(home_page.this,MainActivity.class);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }).start();
        }
        else if (id == R.id.change_password)
        {
            try
            {
                Intent page = new Intent(home_page.this,change_password.class);
                startActivity(page);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (id == R.id.delete_account)
        {
            try
            {
                Intent page = new Intent(home_page.this,delete_class.class);
                startActivity(page);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (id == R.id.feedback)
        {
            Intent page = new Intent(home_page.this,feedback.class);
            startActivity(page);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void SetProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(home_page.this,"Please Wait","Log out...",true);
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }
    protected void set_city()
    {
        ArrayAdapter<CharSequence> adapter;
        Spinner spin = (Spinner)findViewById(R.id.s_city);
        adapter = ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    protected void set_blood_group()
    {
        ArrayAdapter<CharSequence>adapter;
        Spinner spin = (Spinner)findViewById(R.id.s_blood_group);
        adapter = ArrayAdapter.createFromResource(this,R.array.blood_group,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                blood_group = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public static String return_group()
    {
        return blood_group;
    }
    public static String return_city()
    {
        return city;
    }
    public void search(View view)
    {
        v_email.clear();
        v_name.clear();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query query = databaseReference.child("user").orderByChild("blood group").equalTo(blood_group);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                String name,email,City;
                for(com.google.firebase.database.DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    email = dataSnapshot.child("email").getValue(String.class);
                    City = dataSnapshot.child("city").getValue(String.class);
                    if(v_email.contains(email))
                    {

                    }
                    else
                    {
                        if(City.equals(city))
                        {
                            v_email.add(email);
                            name = dataSnapshot.child("name").getValue(String.class);
                            v_name.add(name);
                        }

                    }
                }
                person_show.get_email(v_email);
                person_show.get_name(v_name);
            }
            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(2000);
                    person_show.get_email(v_email);
                    person_show.get_name(v_name);
                    Intent page = new Intent(home_page.this,person_show.class);
                    startActivity(page);
                }
                catch (Exception e )
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void setProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(home_page.this,"Please Wait","Data Searching...",true);
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }
    public static void Close_activity(Activity context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
    }
}
