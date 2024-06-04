package com.inhatc.greenupreal2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    private ImageView iv_profile;
    private TextView tv_id;
    private TextView tv_pw;
    private TextView tv_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        iv_profile = findViewById(R.id.iv_profile);
        tv_id = findViewById(R.id.tv_id);
        tv_pw = findViewById(R.id.tv_pw);
        tv_userName = findViewById(R.id.tv_userName);

        Contents contents = (Contents) getIntent().getSerializableExtra("Contents");

        if (contents != null) {
            Glide.with(this).load(contents.getProfile()).into(iv_profile);
            tv_id.setText(contents.getId());
            tv_pw.setText(contents.getPw());
            tv_userName.setText(contents.getUserName());
        }
    }
}
