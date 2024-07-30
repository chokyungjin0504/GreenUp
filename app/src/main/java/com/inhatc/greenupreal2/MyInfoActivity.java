package com.inhatc.greenupreal2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyInfoActivity extends AppCompatActivity {

    private TextView tvFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        tvFullName = findViewById(R.id.fullname); // fullname 필드

        // LoginDataRepository로부터 사용자 ID를 가져옴
        LoginDataRepository repository = LoginDataRepository.getInstance();
        String userId = repository.getUserId();
        tvFullName.setText(userId); // fullname에 사용자 ID 설정

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);

        // 기본 선택 항목 설정
        bottomNavigationView.setSelectedItemId(R.id.mypage_btn);

        // 메뉴 항목 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> handleNavigationItemSelected(item));

        // 시스템 창 인셋 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.mypage_btn) {
            return true; // 현재 액티비티이므로 아무것도 하지 않음
        }

        Intent intent = null;
        if (itemId == R.id.home_btn) {
            intent = new Intent(this, MainActivity.class);
        } else if (itemId == R.id.fav_btn) {
            intent = new Intent(this, FavoriteActivity.class);
        } else if (itemId == R.id.pickup_btn) {
            intent = new Intent(this, PickUpActivity.class);
        }

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
            return true;
        }

        return false;
    }
}
