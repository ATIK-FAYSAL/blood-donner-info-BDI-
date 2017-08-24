package com.example.atik_faysal.bdi_;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ThreadPoolExecutor;

public class PersonInformation extends AppCompatActivity {
    public static String Email;
    Toolbar toolbar;
    TextView t_name, t_email, t_phone, t_blood, t_city,t_date,t_facebook;
    public static int total_day=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        get_information();
        get_date();
    }

    private void get_date()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String current_date = df.format(c.getTime());
        String[] value;
        try
        {
            value = current_date.split("-");
            total_day = Integer.parseInt(value[0]);
            total_day+=Integer.parseInt(value[1])*30;
            total_day+=Integer.parseInt(value[2])*365;
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        catch (StringIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
    }
    public static void get_email(String email) {
        Email = email;
    }

    public void get_information() {
        t_name = (TextView) findViewById(R.id.t_name);
        t_email = (TextView) findViewById(R.id.t_email);
        t_phone = (TextView) findViewById(R.id.t_phone);
        t_blood = (TextView) findViewById(R.id.t_blood);
        t_city = (TextView) findViewById(R.id.t_city);
        t_date = (TextView)findViewById(R.id.t_date);
        t_facebook = (TextView)findViewById(R.id.facebook);
        Email = Email.replace(".", "*");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(Email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name, email, phone, city, blood_group,date,facebook;
                name = dataSnapshot.child("name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                phone = dataSnapshot.child("phone").getValue(String.class);
                blood_group = dataSnapshot.child("blood group").getValue(String.class);
                city = dataSnapshot.child("city").getValue(String.class);
                date = dataSnapshot.child("date").getValue(String.class);
                facebook = dataSnapshot.child("facebook").getValue(String.class);
                String[] string;
                string = date.split("-");
                int day=0,reminder;
                try
                {
                    day = Integer.parseInt(string[0]);
                    if(string[1].equals("Janu"))day+=1*30;
                    else if (string[1].equals("Feb"))day+=2*30;
                    else if (string[1].equals("March"))day+=3*30;
                    else if (string[1].equals("April"))day+=4*30;
                    else if (string[1].equals("May"))day+=5*30;
                    else if (string[1].equals("Jun"))day+=6*30;
                    else if (string[1].equals("July"))day+=7*30;
                    else if (string[1].equals("Aug"))day+=8*30;
                    else if (string[1].equals("Sep"))day+=9*30;
                    else if (string[1].equals("Oct"))day+=10*30;
                    else if (string[1].equals("Nov"))day+=11*30;
                    else if (string[1].equals("Dec"))day+=12*30;
                    day+=Integer.parseInt(string[2])*365;
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
                String str;
                reminder = total_day-day;
                str = reminder+"";
                t_name.setText(name);
                t_email.setText(email);
                t_phone.setText(phone);
                t_blood.setText("   Blood Group : " + blood_group);
                t_city.setText("   City : " + city);
                if(reminder==total_day||reminder<=0)t_date.setText("   Last Donate 0 day ago  ");
                else t_date.setText("   Last Donate "+str+" days ago");
                t_facebook.setText(facebook);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void makeAcall(View view) {
        t_phone = (TextView) findViewById(R.id.t_phone);
        String phone_number = t_phone.getText().toString();
        Intent call = new Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:" + phone_number));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(call);
    }
    public void sendEmail(View view)
    {
        t_email = (TextView)findViewById(R.id.t_email);
        String  Email;
        Email = t_email.getText().toString();
        Intent send_email = new Intent(Intent.ACTION_SEND);
        send_email.putExtra(send_email.EXTRA_EMAIL,new String[]{Email});
        send_email.putExtra(send_email.EXTRA_SUBJECT,"Help");
        send_email.setType("message/rfc822");
        startActivity(Intent.createChooser(send_email,"Select app to send Email"));
    }
}
