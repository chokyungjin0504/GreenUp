package com.inhatc.greenupreal2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference = null;
    EditText mEtEmail, mEtPwd;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference("Customer Information");
        mAuth = FirebaseAuth.getInstance();

        mEtEmail = (EditText) findViewById(R.id.editTextID);
        mEtPwd = (EditText) findViewById(R.id.editTextPassword);

        Button btn_login = findViewById(R.id.login_button);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPwd)) {
                    Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                    Toast.makeText(LoginActivity.this, "유효한 이메일 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(strEmail, strPwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 로그인 성공
                                    checkUserExists(strEmail);
                                } else {
                                    // 로그인 실패
                                    Toast.makeText(LoginActivity.this, "로그인 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        Button btn_register = findViewById(R.id.join_button);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkUserExists(String email) {
        String userID = mAuth.getCurrentUser().getUid();
        myDB_Reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 사용자가 데이터베이스에 존재
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    // 메인 화면으로 이동
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 사용자가 데이터베이스에 존재하지 않음
                    Toast.makeText(LoginActivity.this, "사용자 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터베이스 오류 처리
                Toast.makeText(LoginActivity.this, "데이터베이스 오류: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
