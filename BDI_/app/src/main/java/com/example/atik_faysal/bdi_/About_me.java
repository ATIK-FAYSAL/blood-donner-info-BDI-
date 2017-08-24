package com.example.atik_faysal.bdi_;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class About_me extends AppCompatActivity
{
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_me);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        about_me();
    }
    protected void about_me()
    {
        TextView e_text = (TextView)findViewById(R.id.e_text);
        String string = "Hi I am ATIK,Real name MD.ATIK FAYSAL.Stundet of Software Engineering department at Daffodil International University.\n\n"+
                "Contact : \n\nMobile : +8801835897172\nEmail : atikfaysal1404@gmail.com\nFacebook: https://www.facebook.com/atikfaysal.niloy";
        e_text.setText(string);
    }
}
