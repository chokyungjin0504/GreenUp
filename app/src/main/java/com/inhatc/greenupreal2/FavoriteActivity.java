package com.inhatc.greenupreal2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Contents> arrayList; // 좋아요 리스트
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);

        // 기본 선택 항목 설정
        bottomNavigationView.setSelectedItemId(R.id.fav_btn);

        // 메뉴 항목 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigationItemSelected(item);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Contents");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contents contents = snapshot.getValue(Contents.class);
                    if (contents != null && sharedPreferences.getBoolean(contents.getId(), false)) {
                        contents.setFavorite(true); // 좋아요 상태로 설정
                        arrayList.add(contents);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FavoriteActivity", String.valueOf(error.toException()));
            }
        });

        adapter = new CustomAdapter(arrayList, this); // 좋아요 리스트를 어댑터에 연결
        recyclerView.setAdapter(adapter);
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // 현재 액티비티에 있는 경우는 재실행하지 않도록 체크
        if (itemId == R.id.fav_btn) {
            return true;
        }

        Intent intent = null;
        if (itemId == R.id.home_btn) {
            intent = new Intent(this, MainActivity.class);
        } else if (itemId == R.id.pickup_btn) {
            intent = new Intent(this, PickUpActivity.class);
        } else if (itemId == R.id.mypage_btn) {
            intent = new Intent(this, MyInfoActivity.class);
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
