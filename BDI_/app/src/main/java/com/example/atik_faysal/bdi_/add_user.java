package com.example.atik_faysal.bdi_;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Vector;

public class add_user extends AppCompatActivity
{
    Toolbar toolbar;
    public  String city,blood_group,gender=null;
    RadioGroup rg;
    RadioButton rb;
    public String name=null,email=null,phone=null,address=null,password=null,c_password=null,facebook=null;
    public String email_id="";
    public   EditText e_name,e_email,e_password,e_con_pass,e_address,e_phone,e_facebook;
    public ProgressDialog progressDialog;
    static private Vector<String>List = new Vector<>();
    static private Vector<String> array_list = new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);
        Firebase.setAndroidContext(this);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        set_blood_group();
        set_city();
        get_id();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id;
        res_id = item.getItemId();
        if(res_id==R.id.save)
        {
            save();
        }
        return true;
    }
    private static void get_id()
    {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("user").orderByChild("email").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String str;
                str = dataSnapshot.child("email").getValue(String.class);
                if(array_list.contains(str))
                {

                }else array_list.add(str);
                all_id(array_list);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        all_id(array_list);
    }
    private static void all_id(Vector<String>list)
    {
        List = list;
    }
    private void get_value()
    {
        e_name = (EditText)findViewById(R.id.e_name);
        e_email = (EditText)findViewById(R.id.e_email);
        e_password = (EditText)findViewById(R.id.E_password);
        e_con_pass = (EditText)findViewById(R.id.E_confirmPassword);
        e_address = (EditText)findViewById(R.id.e_address);
        e_phone = (EditText)findViewById(R.id.e_phone);
        e_facebook = (EditText)findViewById(R.id.facebook);

        name = e_name.getText().toString();
        email = e_email.getText().toString();
        phone = e_phone.getText().toString();
        address = e_address.getText().toString();
        password = e_password.getText().toString();
        c_password = e_con_pass.getText().toString();
        facebook = e_facebook.getText().toString();
    }
    private boolean check_data()
    {
        get_value();
        boolean flag = true;
        if(name.isEmpty())flag = false;
        if(email.isEmpty())flag = false;
        if(phone.isEmpty())flag = false;
        if(address.isEmpty())flag = false;
        if(password.isEmpty())flag = false;
        if(c_password.isEmpty())flag = false;
        if(facebook.isEmpty())flag = false;
        int i;
        if(password.equals(c_password))
        {

        }
        else
        {
            flag = false;
        }
        for(i=0;i<phone.length();i++)
        {
            if(phone.charAt(i)>='0'&&phone.charAt(i)<='9'||phone.charAt(i)=='-'||phone.charAt(i)=='+')
            {

            }
            else
            {
                flag = false;
                break;
            }
        }
        if(phone.length()!=11)flag = false;
        if(city.equals("Select City"))flag = false;
        if(blood_group.equals("Select blood group"))flag = false;

        for(i=0;i<name.length();i++)
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
        email_id = email.replace(".","*");
        if(flag==true)return true;
        else return false;
    }
    private void save()
    {
        boolean check;
        check = check_data();
        if(List.contains(email))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(add_user.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("This Email is already exist.Please try again another Email !!!");
            alertDialog.show();
        }
        else
        {
            if(check==true)
            {
                SetProgressDialog();
                Firebase firebase = new Firebase("https://blood-donner.firebaseio.com/user");
                firebase.child(email_id).child("name").setValue(name);
                firebase.child(email_id).child("email").setValue(email);
                firebase.child(email_id).child("phone").setValue(phone);
                firebase.child(email_id).child("facebook").setValue(facebook);
                firebase.child(email_id).child("address").setValue(address);
                firebase.child(email_id).child("city").setValue(city);
                firebase.child(email_id).child("blood group").setValue(blood_group);
                firebase.child(email_id).child("gender").setValue(gender);
                firebase.child(email_id).child("password").setValue(password);
                firebase.child(email_id).child("date").setValue("00-00-0000");
                Toast.makeText(add_user.this,"Data inserted ,Now you can log in !",Toast.LENGTH_LONG).show();

            }
            else
            {
                SetAlertDialog();
            }
        }
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
    public void select_gender(View v)
    {
        boolean checked = ((RadioButton)v).isChecked();
        if(v.getId()==R.id.R_male)
        {
            if(checked)gender="MALE";
        }
        else if(v.getId()==R.id.R_female)
        {
            if(checked)gender="FEMALE";
        }
    }

    private void SetProgressDialog()
    {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(add_user.this, "Please wait ...", "Data inserting...", true);
        ringProgressDialog.setCancelable(true);
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                }
                ringProgressDialog.dismiss();
            }
        }).start();
    }
    private void SetAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(add_user.this);
        builder.setTitle("Error");
        builder.setMessage("Please insert valid data !!\n\nFor more information Please Click on help Button ......");
        builder.setPositiveButton("Help", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent page = new Intent(add_user.this,rulls.class);
                startActivity(page);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
