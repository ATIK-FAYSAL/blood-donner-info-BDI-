package com.example.atik_faysal.bdi_;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements TextWatcher,CompoundButton.OnCheckedChangeListener{
    public static String check_username,check_password;
    EditText E_email,E_password;
    public String get_email;
    public static String get_password;
    public static  String email;
    Firebase firebase;
    Toolbar toolbar;
    CheckBox checkBox ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static String u_name="atik",u_password="123456",u_remember = "remember",Pref ="pref";
    network_class network ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://blood-donner.firebaseio.com/user");
        initialize();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure??You want to Exit!!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void add_user(View view)
    {
        Intent page = new Intent(MainActivity.this,add_user.class);
        startActivity(page);
    }
    public void about_me(View view)
    {
        Intent page = new Intent(MainActivity.this,About_me.class);
        startActivity(page);
    }
    private void get_login_data()
    {
        EditText e_email = (EditText)findViewById(R.id.e_email);
        EditText e_password = (EditText)findViewById(R.id.e_password);
        get_email = e_email.getText().toString();
        get_password = e_password.getText().toString();
        email = get_email.replace(".","*");
    }

    public static  String return_id()
    {
        return email;
    }
    public static String return_password()
    {
        return get_password;
    }
    public void login(View v)
    {
        get_login_data();
        network = new network_class(this);
        if(network.check_internet())
        {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
            databaseReference.child(email).addValueEventListener(new com.google.firebase.database.ValueEventListener(){
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    String save_email = dataSnapshot.child("email").getValue(String.class);
                    String save_password = dataSnapshot.child("password").getValue(String.class);
                    if(get_email.isEmpty()||get_password.isEmpty())
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Please enter Email and Password ! ");
                        alertDialog.show();
                    }
                    else if(get_email.equals(save_email)&&get_password.equals(save_password))
                    {
                        SetProgressDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    Thread.sleep(3000);
                                }
                                catch (Exception e )
                                {
                                    e.printStackTrace();
                                }
                                Intent page = new Intent(MainActivity.this,home_page.class);
                                startActivity(page);
                            }
                        }).start();
                    }
                    else
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Log in Failed !!");
                        alertDialog.setMessage("\nPlease try again with correct email and password !!!");
                        alertDialog.show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please check your Internet connection .\n");
            alertDialog.show();
        }

    }
    private void SetProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this,"Pleas wait","Loging....",true);
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
    private void initialize()
    {
        E_email = (EditText)findViewById(R.id.e_email);
        E_password = (EditText)findViewById(R.id.e_password);
        checkBox = (CheckBox)findViewById(R.id.remember);
        sharedPreferences = getSharedPreferences(Pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean(u_remember,false))
            checkBox.setChecked(true);
        else checkBox.setChecked(false);

        E_email.setText(sharedPreferences.getString(u_name,""));
        E_password.setText(sharedPreferences.getString(u_password,""));

        E_email.addTextChangedListener(this);
        E_password.addTextChangedListener(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        set_data();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        set_data();
    }
    private void set_data()
    {
        if(checkBox.isChecked())
        {
            editor.putString(u_name,E_email.getText().toString().trim());
            editor.putString(u_password,E_password.getText().toString().trim());
            editor.putBoolean(u_remember,true);
            editor.apply();
        }
        else
        {
            editor.putBoolean(u_remember,false);
            editor.remove(u_name);
            editor.remove(u_password);
            editor.apply();
        }
    }
}
