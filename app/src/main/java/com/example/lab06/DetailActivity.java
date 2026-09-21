package com.example.lab06;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgDetailCover;
    private TextView tvDetailTitle, tvDetialContent, tvDetailViews;

    // bien luu du lieu nhan tu MainActivity
    private Article currentArticle;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgDetailCover = findViewById(R.id.img_detail_cover);
        tvDetailTitle = findViewById(R.id.tv_detail_title);
        tvDetialContent = findViewById(R.id.tv_detail_content);
        tvDetailViews = findViewById(R.id.tv_detail_views);

        // hien thi mui ten back tren thanh Action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // nahn inten tu activitymain
        Intent intent = getIntent();
        if (intent != null) {
            // lay doi duong article va ep kieu va article
            currentArticle = (Article) intent.getSerializableExtra("EXTRA_ARTICLE");
            // lay position, loi tra ve -1
            position = intent.getIntExtra("EXTRA_POSITION", -1);

            if (currentArticle != null) {
                int oldViews = currentArticle.getViews();
                currentArticle.setViews(oldViews + 1);
                // hien thi man hinh tri tiet
                imgDetailCover.setImageResource(currentArticle.getImgCover());
                tvDetailTitle.setText(currentArticle.getTitle());
                tvDetialContent.setText(currentArticle.getContent());
                tvDetailViews.setText("Views: " + currentArticle.getViews());
            }
        }

        // bat su kien nhan cut back
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                sendDataBackToMain(); //goi ham gui su lieu ve
            }
        });
    }

    // bat su kien bam nut back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // mui ten back co id mac dinh la android.R.id.home
            sendDataBackToMain();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendDataBackToMain() {
        Intent returnIntent = new Intent();

        // dong goi artivle voi views moi va vi tri
        returnIntent.putExtra("UPDATED_ARTICLE", currentArticle);
        returnIntent.putExtra("EXTRA_POSITION", position);

        setResult(RESULT_OK, returnIntent);
        finish();
    }

}