package com.example.atik_faysal.bdi_;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class feedback extends AppCompatActivity
{
    EditText e_text;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void send_feedback(View v)
    {
        e_text = (EditText)findViewById(R.id.edittext);
        String text = e_text.getText().toString();
        if(text.length()>200)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("It is too long,please write your suggestion within 200 character !");
            alertDialog.show();
        }
        else
        {
            Firebase firebase = new Firebase("https://blood-donner.firebaseio.com/feedback");
            String get_id = firebase.push().getKey();
            firebase.child(get_id).setValue(text);
            Toast.makeText(this,"Thank you for your cooperation ",Toast.LENGTH_LONG).show();
        }
    }
}
