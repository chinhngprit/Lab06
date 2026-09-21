package com.example.lab06;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    // lop ViewHolder anh xa ID trong item_article.xml
    public static class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvContent, tvViews;
        ImageView imgCover;

        public ArticleViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvViews = itemView.findViewById(R.id.tv_views);
            imgCover = itemView.findViewById(R.id.img_cover);
        }
    }
    private ArrayList<Article> articleList;

    // khai bao interface de MainACtivity lang nghe
    public interface OnItemClickListener {
        void onItemClick(Article article, int position);
    }
    // Khai bao bien listener luu tru nguoi lang nghe laf mainactivity
    private OnItemClickListener listener;



    // constructor nhan du lieu tu mainactivity truyen vao
    public ArticleAdapter(ArrayList<Article> articleList, OnItemClickListener listener){
        this.articleList = articleList;
        this.listener = listener;
    }

    // lay file XML item_article.xml vaf inflate thanh view that, sau do nhet vao ViewHolder
    @NonNull
    @Override

    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent,false);

        return new ArticleViewHolder(view);
    }

    // bom du lieu tu object article tai vi tri tuong ung vao cac thanh phan cua UI ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);

        holder.tvTitle.setText(article.getTitle());
        holder.tvContent.setText(article.getContent());
        holder.tvViews.setText("Views: " + article.getViews());
        holder.imgCover.setImageResource(article.getImgCover());

        // 4. bat su kien click trn khung itemview
        // khi click adapter ko tu mo intent -> goi ham onItemClick de gui doi tuong article va position
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(article, position);
            }
        });

    }

    // Bao cho recyclerView biet co bao nhieu bai viet can ve
    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
