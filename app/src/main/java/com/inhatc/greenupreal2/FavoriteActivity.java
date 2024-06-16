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
import java.util.List;

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
