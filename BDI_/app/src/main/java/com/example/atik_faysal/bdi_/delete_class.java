package com.example.atik_faysal.bdi_;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class delete_class extends Activity
{
    CheckBox check1,check2,check3,check4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_reson);
        check1 = (CheckBox)findViewById(R.id.check1);
        check2 = (CheckBox)findViewById(R.id.check2);
        check3 = (CheckBox)findViewById(R.id.check3);
        check4 = (CheckBox)findViewById(R.id.check4);
    }

    public void delete_account(View v)
    {
        boolean flag1,flag2,flag3,flag4,flag=true;
        flag1 = check1.isChecked();
        flag2 = check2.isChecked();
        flag3 = check3.isChecked();
        flag4 = check4.isChecked();
        String str1=null,str2=null,str3=null,str4=null;
        boolean temp1=true,temp2=true,temp3=true,temp4=true;
        if(flag1==true)str1="এই এপস টি আমার কোন উপকারে আসছে না ।";
        else temp1=false;
        if (flag2==true)str2 = "এপস টি ঠিকমতো কাজ করছে না । ";
        else temp2=false;
        if(flag3==true)str3 = "আমি আগ্রোহী না । ";
        else temp3=false;
        if (flag4==true)str4="এই এপস টি আমার পছন্দ না ।";
        else temp4=false;
        if(temp1==true||temp2==true||temp3==true||temp4==true)flag=true;
        else flag=false;
        Firebase firebase = new Firebase("https://blood-donner.firebaseio.com/delete_causes");
        String get_id = firebase.push().getKey();
        firebase.child(get_id).child("cause1").setValue(str1);
        firebase.child(get_id).child("cause2").setValue(str2);
        firebase.child(get_id).child("cause3").setValue(str3);
        firebase.child(get_id).child("cause4").setValue(str4);
        if(flag==true)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(delete_class.this);
            builder.setTitle("Delete");
            builder.setMessage("Do you want to delete your account ??");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String id = MainActivity.return_id();
                    Firebase firebase = new Firebase("https://blood-donner.firebaseio.com/user");
                    firebase.child(id).removeValue();
                    Toast.makeText(delete_class.this,"Account deleted successfully",Toast.LENGTH_LONG).show();
                    Close_activity(delete_class.this, MainActivity.class);

                }
            });
            builder.setNegativeButton("No",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(delete_class.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please select at least one reason ");
            alertDialog.show();
        }
    }
    public void close_activity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public static void Close_activity(Activity context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
    }
}
