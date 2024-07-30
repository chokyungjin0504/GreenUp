package com.inhatc.greenupreal2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference = null;

    HashMap<String, Object> Customer_Value = null;
    UserAccount objCustomerInfo = null;

    EditText mEtEmail, mEtPwd, mEtName, mEtPhone; // 회원가입 입력필드
    Button mBtnRegister; // 회원가입 버튼
    String strHeader = "Customer Information"; //Firebase Key
    String strCName = null;
    String strCEmail = null;
    String strCPhone = null;
    String strCPassword = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_join);

        mEtName = (EditText) findViewById(R.id.id_nametext);
        mEtEmail = (EditText) findViewById(R.id.loginID_text);
        mEtPwd = (EditText) findViewById(R.id.editTextPassword);
        mEtPhone = (EditText) findViewById(R.id.id_Phonetext);

        mBtnRegister = (Button) findViewById(R.id.join_button);
        mBtnRegister.setOnClickListener(this);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();

        Customer_Value = new HashMap<>();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.join_button) {
            strCName = mEtName.getText().toString();
            strCPassword = mEtPwd.getText().toString();
            strCEmail = mEtEmail.getText().toString();
            strCPhone = mEtPhone.getText().toString();
            if (!strCName.equals("")) {

                Customer_Value.put("Name", strCName);
                Customer_Value.put("Email", strCEmail);
                Customer_Value.put("Phone", strCPhone);
                Customer_Value.put("Password", strCPassword);

                mSet_FirebaseDatabase(true);
                mGet_FirebaseDatabase();
            }
            mEtEmail.setText("");
            mEtPwd.setText("");
            mEtName.setText("");
            mEtPhone.setText("");
        } else {
            mEtEmail.setText("");
            mEtPwd.setText("");
            mEtName.setText("");
            mEtPhone.setText("");
        }

        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();


    }

    private void mGet_FirebaseDatabase() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError dbError) {
                Log.w("Tag: ", "Failed to read value", dbError.toException());
            }
        };

        //이름순으로 분류
        Query sortbyName = FirebaseDatabase.getInstance().getReference().child(strHeader).orderByChild(strCName);
        sortbyName.addListenerForSingleValueEvent(postListener);
    }

    private void mSet_FirebaseDatabase(boolean bFlag) {
        if (bFlag) {
            myDB_Reference.child(strHeader).child(strCPhone).setValue(Customer_Value);
        }
    }
}