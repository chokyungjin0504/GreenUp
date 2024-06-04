package com.inhatc.greenupreal2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

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

        // Get the data passed from the MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String profileUrl = extras.getString("profile");
            String id = extras.getString("id");
            String pw = extras.getString("pw");
            String userName = extras.getString("userName");

            // Set the data to the views
            Glide.with(this).load(profileUrl).into(ivProfile);
            tvId.setText(id);
            tvPw.setText(pw);
            tvUserName.setText(userName);
        }
    }
}
