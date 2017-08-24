package com.example.atik_faysal.bdi_;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class change_password extends AppCompatActivity
{
    EditText old_pass,new_pass,con_pass;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        old_pass = (EditText)findViewById(R.id.old_pass);
        new_pass = (EditText)findViewById(R.id.new_pass);
        con_pass = (EditText)findViewById(R.id.con_pass);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void change_pass(View v)
    {
        String id = MainActivity.return_id();
        String password = MainActivity.return_password();
        boolean flag = true;
        String old_password,new_password,con_password;
        old_password = old_pass.getText().toString();
        new_password = new_pass.getText().toString();
        con_password = con_pass.getText().toString();
        if(old_password.isEmpty())flag = false;
        if(new_password.isEmpty())flag = false;
        if(con_password.isEmpty())flag = false;
        if(flag==true)
        {
            if(password.equals(old_password)&&new_password.equals(con_password)&&(new_password.length()>=6&&new_password.length()<=25))
            {
                Firebase firebase = new Firebase("https://blood-donner.firebaseio.com/user");
                firebase.child(id).child("password").setValue(new_password);
                Toast.makeText(change_password.this,"Password change successful",Toast.LENGTH_LONG).show();
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(change_password.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Please insert valid Password !!");
                alertDialog.show();
            }
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please insert Password !!");
            alertDialog.show();
        }
    }
}
