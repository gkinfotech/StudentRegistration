package com.gokul.tut.studentregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewStudentActivity extends AppCompatActivity {

    private static final String TAG = "AddNewStudentActivity";
    private EditText mName;
    private EditText mEmail;
    private EditText mCountry;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        mName = (EditText)findViewById(R.id.et_sr_name);
        mEmail = (EditText)findViewById(R.id.et_sr_email);
        mCountry = (EditText)findViewById(R.id.et_sr_country);
        mSubmit = (Button)findViewById(R.id.btn_sr_submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mName.getText())){
                    Toast.makeText(AddNewStudentActivity.this, "Please Enter the All the Fields", Toast.LENGTH_SHORT).show();
                }else {
                    String name = mName.getText().toString();
                    Log.d(TAG,"Name:"+name);
                    String email = mEmail.getText().toString();
                    Log.d(TAG,"Email:"+email);
                    String country = mCountry.getText().toString();
                    Log.d(TAG,"Country:"+country);

                    Intent intent = new Intent();

                    intent.putExtra("NAME",name);
                    intent.putExtra("EMAIL",email);
                    intent.putExtra("COUNTRY",country);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }
}
