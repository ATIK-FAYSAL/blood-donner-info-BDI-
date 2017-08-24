package com.example.atik_faysal.bdi_;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class rulls extends Activity
{
    private String string = "1.Name can not contain digit or special character !\n\n2.Phone number can not contain alphabet or special character !\n\n3.You have to select one city and one blood group !\n\n4.You have to also select gender !\n\n5.Password and confirm password must be same !\n\n6.No field can not be empty !\n\n";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rulls);
        textView = (TextView)findViewById(R.id.view);
        textView.setText(string);
    }
}
