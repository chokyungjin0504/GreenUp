package com.inhatc.greenupreal2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PickUpActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private TextView tvId;
    private TextView tvPw;
    private TextView tvUserName;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);

        ivProfile = findViewById(R.id.iv_profile_pickup);
        tvId = findViewById(R.id.tv_id_pickup);
        tvPw = findViewById(R.id.tv_pw_pickup);
        tvUserName = findViewById(R.id.tv_userName_pickup);
        linearLayout = findViewById(R.id.linearLayout);

        // UserDataRepository로부터 데이터를 가져옵니다.
        UserDataRepository repository = UserDataRepository.getInstance();

        // 데이터를 뷰에 설정합니다.
        Glide.with(this).load(repository.getProfileUrl()).into(ivProfile);
        tvId.setText(repository.getId());
        tvPw.setText(repository.getPw());
        tvUserName.setText(repository.getUserName());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);
        // 기본 선택 항목 설정
        bottomNavigationView.setSelectedItemId(R.id.pickup_btn);

        // 메뉴 항목 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigationItemSelected(item);
            }
        });

        // 시스템 창 인셋 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // btn_submit 클릭 리스너 설정
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });
    }

    private void showImage() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.img_1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 16, 0, 0); // 마진 설정
        imageView.setLayoutParams(layoutParams);

        // 이미지 뷰를 레이아웃에 추가
        linearLayout.addView(imageView);
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.pickup_btn) {
            return true; // 현재 액티비티이므로 아무것도 하지 않음
        }

        Intent intent = null;
        if (itemId == R.id.home_btn) {
            intent = new Intent(this, MainActivity.class);
        } else if (itemId == R.id.fav_btn) {
            intent = new Intent(this, FavoriteActivity.class);
        } else if (itemId == R.id.mypage_btn) {
            intent = new Intent(this, MyInfoActivity.class);
        }

        if (intent != null) {
            // UserDataRepository로부터 데이터를 가져옵니다.
            UserDataRepository repository = UserDataRepository.getInstance();

            // 데이터를 인텐트에 추가
            intent.putExtra("profile", repository.getProfileUrl());
            intent.putExtra("id", repository.getId());
            intent.putExtra("pw", repository.getPw());
            intent.putExtra("userName", repository.getUserName());

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // 애니메이션 없이 전환
            startActivity(intent);
            overridePendingTransition(0, 0); // 전환 애니메이션 없앰
            return true;
        }

        return false; // 매칭되는 메뉴 항목이 없는 경우 false 반환
    }
}
