package com.example.lab06;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerArticles;
    private ArticleAdapter adapter;
    private ArrayList<Article> articleList;

    // khai bao launcher bo phong intent cho ket qua tra ve
    private ActivityResultLauncher<Intent> detailLauncher;

    private void initMockData() {
        articleList = new ArrayList<>();
        articleList.add(new Article("Exploring Hidden Gems in Hanoi", "A detailed guide to the less-traveled streets and local culinary spots...", R.drawable.ic_image_placeholder, 1));
        articleList.add(new Article("Saigon Nightlife", "Discover the vibrant coffee culture and street food of Ho Chi Minh City.", R.drawable.ic_image_placeholder, 2));
        articleList.add(new Article("Hoi An Lantern Festival", "Experience the magical atmosphere of the ancient town glowing with lanterns.", R.drawable.ic_image_placeholder, 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ham tao du lieu mau
        initMockData();

        // anh xa the recyclerView tu activity_main.xml
        recyclerArticles = findViewById(R.id.recycler_articles);

        // khai bao layout manager de hien thi cuon doc tu tren xuong
        recyclerArticles.setLayoutManager(new LinearLayoutManager(this));

        // 2. khoi tao launcher
        detailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {

                }
        );



        // 3. khoi tao adapter voi mang du lieu va gan vao recylerView
        adapter = new ArticleAdapter(articleList, (article, positon) -> {
            // 1. tao inten de dieu huong tu mainactivity sang detailactivity
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            //2. dong goi article va vi tri vao intent
            intent.putExtra("EXTRA_ARTICLE", article);
            intent.putExtra("EXTRA_POSITION", positon);
            // dung launcher de phong
            detailLauncher.launch(intent);
        });
        recyclerArticles.setAdapter(adapter);
    }
}