package com.inhatc.greenupreal2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);

        // 기본 선택 항목 설정
        bottomNavigationView.setSelectedItemId(R.id.mypage_btn);

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
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // 현재 액티비티에 있는 경우는 재실행하지 않도록 체크
        if (itemId == R.id.mypage_btn) {
            return true;
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
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // 애니메이션 없이 전환
            startActivity(intent);
            overridePendingTransition(0, 0); // 전환 애니메이션 없앰
            return true;
        }

        return false; // 매칭되는 메뉴 항목이 없는 경우 false 반환
    }
}
