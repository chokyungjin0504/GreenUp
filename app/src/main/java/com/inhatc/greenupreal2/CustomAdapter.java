package com.inhatc.greenupreal2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Contents> arrayList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferences2;

    public CustomAdapter(ArrayList<Contents> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
        this.sharedPreferences2 = context.getSharedPreferences("pickup", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Contents contents = arrayList.get(position);

        // 기존 바인딩 코드
        Glide.with(holder.itemView)
                .load(contents.getProfile())
                .into(holder.iv_profile);
        holder.tv_id.setText(contents.getId());
        holder.tv_pw.setText(contents.getPw());
        holder.tv_userName.setText(contents.getUserName());

        // Load favorite state from SharedPreferences
        boolean isFavorite = sharedPreferences.getBoolean(contents.getId(), false);
        contents.setFavorite(isFavorite);

        // 하트 아이콘 상태 설정
        holder.iv_favorite.setImageResource(contents.isFavorite() ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);

        // 하트 아이콘 클릭 리스너 추가
        holder.iv_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newState = !contents.isFavorite();
                contents.setFavorite(newState);
                holder.iv_favorite.setImageResource(newState ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);

                // Save favorite state to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (newState) {
                    editor.putBoolean(contents.getId(), true);
                } else {
                    editor.remove(contents.getId());
                }
                editor.apply();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("profile", contents.getProfile());
                intent.putExtra("id", contents.getId());
                intent.putExtra("pw", contents.getPw());
                intent.putExtra("userName", contents.getUserName());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_id;
        TextView tv_pw;
        TextView tv_userName;
        ImageView iv_favorite;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_id = itemView.findViewById(R.id.tv_id);
            this.tv_pw = itemView.findViewById(R.id.tv_pw);
            this.tv_userName = itemView.findViewById(R.id.tv_userName);
            this.iv_favorite = itemView.findViewById(R.id.iv_favorite);
        }
    }
}
