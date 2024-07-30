package com.inhatc.greenupreal2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_MAP = 1;
    private Button btnLocation;
    private EditText edtLocation;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Contents> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);

        // 기본 선택 항목 설정
        bottomNavigationView.setSelectedItemId(R.id.home_btn);

        // 메뉴 항목 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigationItemSelected(item);
            }
        });

        btnLocation = findViewById(R.id.btnLocation);
        edtLocation = findViewById(R.id.edtLocation);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MAP);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // Contents 객체를 담을 어레이 리스트 (어댑터쪽으로)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("Contents"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear(); // 기존 배열 리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contents contents = snapshot.getValue(Contents.class); // 만들어뒀던 Contents 객체에 데이터를 담는다
                    arrayList.add(contents); // 담은 데이터들을 배열 리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", String.valueOf(error.toException())); // 에러문 출력
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // 현재 액티비티에 있는 경우는 재실행하지 않도록 체크
        if (itemId == R.id.home_btn) {
            return true;
        }

        Intent intent = null;
        if (itemId == R.id.fav_btn) {
            intent = new Intent(this, FavoriteActivity.class);
        } else if (itemId == R.id.pickup_btn) {
            intent = new Intent(this, PickUpActivity.class);
        } else if (itemId == R.id.mypage_btn) {
            intent = new Intent(this, MyInfoActivity.class);
            intent.putExtra("userId", userId); // 사용자 ID 전달
        }

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // 애니메이션 없이 전환
            startActivity(intent);
            overridePendingTransition(0, 0); // 전환 애니메이션 없앰
            return true;
        }

        return false; // 매칭되는 메뉴 항목이 없는 경우 false 반환
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK && data != null) {
            String location = data.getStringExtra("location");
            edtLocation.setText(location);
        }
    }
}
