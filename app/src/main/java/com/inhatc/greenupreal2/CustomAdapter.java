package com.inhatc.greenupreal2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Contents> arrayList; //상품 정보 리스트
    private Context context;


    public CustomAdapter(ArrayList<Contents> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile); //서버로부터 이미지 받아와서 삽입
        holder.tv_id.setText(arrayList.get(position).getId());
        holder.tv_pw.setText(arrayList.get(position).getPw()); //패스워트가 숫자여도 문자 형태로 가져옴!
        holder.tv_userName.setText(arrayList.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return (arrayList!=null? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_id;
        TextView tv_pw;
        TextView tv_userName;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_id = itemView.findViewById(R.id.tv_id);
            this.tv_pw = itemView.findViewById(R.id.tv_pw);
            this.tv_userName = itemView.findViewById(R.id.tv_userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(itemView.getContext(),"ok", Toast.LENGTH_LONG).show();

                    // DetailsActivity로 이동하는 Intent 생성 및 시작
                    Intent intent = new Intent(itemView.getContext(), DetailsActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}