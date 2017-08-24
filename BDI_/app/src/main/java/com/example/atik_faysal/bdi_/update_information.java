package com.example.atik_faysal.bdi_;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class update_information extends AppCompatActivity
{
    EditText e_name,e_phone,e_address;
    TextView e_gender,e_blood_group,e_email,e_facebook,e_city;
    String city;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateinfo);
        e_name = (EditText)findViewById(R.id.e_name);
        e_address = (EditText)findViewById(R.id.e_address);
        e_phone = (EditText)findViewById(R.id.e_phone);
        e_gender = (TextView)findViewById(R.id.e_gender);
        e_blood_group = (TextView)findViewById(R.id.e_blood_group);
        e_email = (TextView)findViewById(R.id.e_email);
        e_facebook = (EditText)findViewById(R.id.facebook);
        e_city = (TextView)findViewById(R.id.e_city) ;
        get_data();
        set_city();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Menu = getMenuInflater();
        Menu.inflate(R.menu.update_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id;
        res_id = item.getItemId();
        if(res_id==R.id.update)
        {
            update_info();
        }
        return true;
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
    private void get_data()
    {
        String id;
        id = MainActivity.return_id();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(id).addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String email,name,address,phone,gender,bg,facebook,City;
                name = dataSnapshot.child("name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                phone = dataSnapshot.child("phone").getValue(String.class);
                address = dataSnapshot.child("address").getValue(String.class);
                gender = dataSnapshot.child("gender").getValue(String.class);
                bg = dataSnapshot.child("blood group").getValue(String.class);
                facebook = dataSnapshot.child("facebook").getValue(String.class);
                City = dataSnapshot.child("city").getValue(String.class);
                e_name.setText(name);
                e_email.setText(email);
                e_phone.setText(phone);
                e_gender.setText("  Gender : "+gender);
                e_blood_group.setText("  Blood Group : "+bg);
                e_address.setText(address);
                e_facebook.setText(facebook);
                e_city.setText(City);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void update_info()
    {
        String name,phone,address,facebook;
        name = e_name.getText().toString();
        phone = e_phone.getText().toString();
        address = e_address.getText().toString();
        facebook = e_facebook.getText().toString();
        String id = MainActivity.return_id();
        boolean flag = true;
        if(name.isEmpty())flag = false;
        if(phone.isEmpty())flag = false;
        if(address.isEmpty())flag = false;
        for(int i=0;i<phone.length();i++)
        {
            if(phone.charAt(i)>='0'&&phone.charAt(i)<='9'||phone.charAt(i)=='-')
            {

            }
            else
            {
                flag = false;
                break;
            }
        }
        for(int i=0;i<name.length();i++)
        {
            if((name.charAt(i)>='A'&&name.charAt(i)<='Z')||(name.charAt(i)>='a'&&name.charAt(i)<='z')||name.charAt(i)==' '||name.charAt(i)=='.')
            {

            }
            else
            {
                flag = false;
                break;
            }
        }
        if(flag==true)
        {
            SetProgressDialog();
            Firebase firebase = new Firebase("https://blood-donner.firebaseio.com/user");
            firebase.child(id).child("name").setValue(name);
            firebase.child(id).child("phone").setValue(phone);
            firebase.child(id).child("address").setValue(address);
            firebase.child(id).child("facebook").setValue(facebook);
            if(city.equals("Select City"))
            {
                String str;
                str = e_city.getText().toString();
                firebase.child(id).child("city").setValue(str);
            }
            else firebase.child(id).child("city").setValue(city);
            Toast.makeText(this,"Update successful",Toast.LENGTH_LONG).show();
        }
        else
        {
            SetAlertDialog();
        }
    }
    protected void SetProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(update_information.this,"Please Wait","Updating...",true);
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
        progressDialog.show();
    }
    protected void SetAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(update_information.this);
        builder.setTitle("Error");
        builder.setMessage("Please insert valid data !!\n\nFor more information Please Click on help Button ......");
        builder.setPositiveButton("Help", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent page = new Intent(update_information.this,rulls.class);
                startActivity(page);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
