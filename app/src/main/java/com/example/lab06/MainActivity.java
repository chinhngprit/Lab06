package com.example.lab06;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
        articleList.add(new Article("Exploring Hidden Gems in Hanoi", "Vietnam’s two largest cities are polar opposites—Ho Chi Minh City is a sleek, modern city of skyscrapers while the charming capital of Hanoi is filled with ancient history. Hanoi is the best for watching motorbikes defy gravity with their loads ranging from refrigerators to a family of five with a dog. It’s home to the country’s largest airport and the gateway to Ha Long and Lan Ha Bay and Ninh Binh. \n" +
                "\n" +
                "These recommendations are based on my experience living and working for travel companies in Southeast Asia for the past decade. (I visit Vietnam at least once a year for work.)\n" +
                "\n" +
                "Here are the best things to do in Hanoi along with the best time to visit and where to stay!\n" +
                "\n" +
                "You can easily do all these things on your own, but if you’re short on time consider a city tour.", R.drawable.image1, 1));
        articleList.add(new Article("Saigon Nightlife", "If you ask travelers what they remember most about Ho Chi Minh City, many will mention the food, the scooters, or the incredible energy that fills every street. But ask those who have spent an evening exploring beyond their hotel, and you’ll often hear the same answer: Saigon truly comes alive after sunset.\n" +
                "\n" +
                "So, is Saigon beautiful at night?\n" +
                "\n" +
                "Absolutely—but not because of grand monuments or dazzling light shows alone. Saigon’s beauty comes from its unique atmosphere. Modern skyscrapers glow above historic French buildings, tiny alleyways buzz with families enjoying dinner, rooftop bars overlook the sparkling Saigon River, and the aroma of sizzling street food drifts through neighborhoods that rarely appear in guidebooks.\n" +
                "\n" +
                "Unlike cities where nightlife revolves around clubs or entertainment districts, Ho Chi Minh City offers something far more authentic. It’s a place where you can sip Vietnamese coffee at 10 PM, enjoy freshly grilled seafood on a tiny plastic stool, watch locals exercising in parks, and discover hidden corners that feel untouched by mass tourism.\n" +
                "\n" +
                "Whether you’re visiting for a weekend or planning a longer Vietnam itinerary, exploring Saigon after dark is one of the most rewarding experiences you can have. Here’s why the city looks even more captivating once the sun goes down.\n" +
                "\n" +
                "If you’re looking for curated Ho Chi Minh City tours, exploring with a knowledgeable local guide can help you experience the city’s hidden side beyond the typical attractions.\n" +
                "\n", R.drawable.saigon, 2));
        articleList.add(new Article("Hoi An Lantern Festival", "Hoi An is a picturesque city located in central Vietnam. Once a bustling trading port, today Hoi An is a charming destination that attracts visitors from all over the world with its well-preserved architecture, stunning natural scenery, and vibrant culture.\n" +
                "\n" +
                "The city’s Old Town, a UNESCO World Heritage Site, is a fascinating mix of Japanese, Chinese, and Vietnamese influences, with beautiful temples, merchant houses, and canals winding through the narrow streets. Visitors can explore the local markets, sample delicious Vietnamese cuisine, and shop for traditional handicrafts.\n" +
                "\n" +
                "Beyond the city limits, there are plenty of opportunities for adventure and relaxation, from beautiful beaches to lush countryside and historic sites. Hoi An truly has something for everyone, making it a must-visit destination in Vietnam.\n" +
                "\n" +
                "In this article, we’ll take a closer look at Hoi An, exploring its history, culture, attractions, and more. Whether you’re planning a trip to Hoi An or simply curious about this beautiful city, read on to discover all that it has to offer.", R.drawable.hoian, 0));
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
                    // ktra du lieu tra ve có dan tem RESULT_OK vaf co chua inten khong
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Article updateArticle = (Article) data.getSerializableExtra("UPDATED_ARTICLE");
                        int position = data.getIntExtra("EXTRA_POSITION", -1);

                        if (updateArticle != null && position != -1) {
                            articleList.set(position, updateArticle);
                            // lenhj cap nhan giao dien
                            adapter.notifyItemChanged(position);
                        }
                    }

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
    // ham bom file menu len thanh action bar

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Danh sách bài viết");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_add) {
            // xu ly khi bam nut them bai
            android.widget.Toast.makeText(this, "Add post opening...", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}