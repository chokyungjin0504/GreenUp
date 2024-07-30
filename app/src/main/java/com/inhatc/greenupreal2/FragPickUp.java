//package com.inhatc.greenupreal2;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.bumptech.glide.Glide;
//
//public class FragPickUp extends Fragment {
//
//    private ImageView ivProfile;
//    private TextView tvId;
//    private TextView tvPw;
//    private TextView tvUserName;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        // 프래그먼트 레이아웃을 인플레이트합니다.
//        View view = inflater.inflate(R.layout.fragment_frag_pick_up, container, false);
//
//        // 뷰 초기화
//        ivProfile = view.findViewById(R.id.iv_profile);
//        tvId = view.findViewById(R.id.tv_id);
//        tvPw = view.findViewById(R.id.tv_pw);
//        tvUserName = view.findViewById(R.id.tv_userName);
//
//        // 전달된 번들 데이터를 가져옵니다.
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String profileUrl = bundle.getString("profile");
//            String id = bundle.getString("id");
//            String pw = bundle.getString("pw");
//            String userName = bundle.getString("userName");
//
//            // 데이터를 이용하여 UI 업데이트
//            Glide.with(this).load(profileUrl).into(ivProfile);
//            tvId.setText(id);
//            tvPw.setText(pw);
//            tvUserName.setText(userName);
//        }
//
//        return view;
//    }
//}
