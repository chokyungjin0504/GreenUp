package com.inhatc.greenupreal2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

        // Set the click listener for the reserve button
        btnReserve.setOnClickListener(view -> {
            FragPickUp fragPickUp = new FragPickUp();
            Bundle bundle = new Bundle();
            bundle.putString("profile", extras.getString("profile"));
            bundle.putString("id", extras.getString("id"));
            bundle.putString("pw", extras.getString("pw"));
            bundle.putString("userName", extras.getString("userName"));
            fragPickUp.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragPickUp);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
}
