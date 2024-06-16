package com.inhatc.greenupreal2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    private Button btnReserve;
    private ImageView ivProfile;
    private TextView tvId;
    private TextView tvPw;
    private TextView tvUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivProfile = findViewById(R.id.iv_profile);
        tvId = findViewById(R.id.tv_id);
        tvPw = findViewById(R.id.tv_pw);
        tvUserName = findViewById(R.id.tv_userName);
        btnReserve = findViewById(R.id.btnReserve);

        // MainActivity에서 전달된 데이터를 가져옵니다.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String profileUrl = extras.getString("profile");
            String id = extras.getString("id");
            String pw = extras.getString("pw");
            String userName = extras.getString("userName");

            // 데이터를 뷰에 설정합니다.
            Glide.with(this).load(profileUrl).into(ivProfile);
            tvId.setText(id);
            tvPw.setText(pw);
            tvUserName.setText(userName);
        }

    }
}
