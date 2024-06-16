package com.inhatc.greenupreal2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_info);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);

        // 메뉴 항목 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigationItemSelected(item);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.home_btn) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (itemId == R.id.fav_btn) {
            startActivity(new Intent(this, FavoriteActivity.class));
        } else if (itemId == R.id.pickup_btn) {
            startActivity(new Intent(this, PickUpActivity.class));
        } else if (itemId == R.id.mypage_btn) {
            startActivity(new Intent(this, MyInfoActivity.class));
        } else {
            return false; // 매칭되는 메뉴 항목이 없는 경우 false 반환
        }

        return true; // 매칭되는 메뉴 항목이 있는 경우 true 반환
    }
}