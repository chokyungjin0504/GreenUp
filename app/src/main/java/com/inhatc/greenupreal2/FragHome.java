//package com.inhatc.greenupreal2;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class FragHome extends Fragment {
//
//    private static final int REQUEST_CODE_MAP = 1;
//    private Button btnLocation;
//    private EditText edtLocation;
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<Contents> arrayList;
//    private FirebaseDatabase database;
//    private DatabaseReference databaseReference;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_frag_home, container, false);
//
//
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        arrayList = new ArrayList<>(); // Content 객체를 담을 어레이 리스트 (어댑터쪽으로)
//
//        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
//        databaseReference = database.getReference("Contents"); // DB 테이블 연결
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
//                arrayList.clear(); // 기존 배열 리스트가 존재하지 않게 초기화
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해 냄
//                    Contents contents = snapshot.getValue(Contents.class); // 만들어뒀던 Contents 객체에 데이터를 담는다
//                    arrayList.add(contents); // 담은 데이터들을 배열 리스트에 넣고 리사이클러뷰로 보낼 준비
//                }
//                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // 디비를 가져오던 중 에러 발생
//                Log.e("FragHome", String.valueOf(error.toException())); // 에러문 출력
//            }
//        });
//
//        adapter = new CustomAdapter(arrayList, getActivity());
//        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
//
//        return view;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_MAP && resultCode == getActivity().RESULT_OK && data != null) {
//            String location = data.getStringExtra("location");
//            edtLocation.setText(location);
//        }
//    }
//}