package com.example.android.magshop.Sellers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.magshop.R;

public class SellerProductCategoryActivity extends AppCompatActivity {

    private ImageView telephone, bathroom, armchair, cupboard;
    private ImageView cot, flowers, heater, fan;
    private ImageView books, cleaning, clock, cookingstove;
    private ImageView coffeemaker, fireplace, freezer, fridge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_category);


        //first line
        telephone = (ImageView) findViewById(R.id.telephone);
        bathroom = (ImageView) findViewById(R.id.bathroom);
        armchair = (ImageView) findViewById(R.id.armchair);
        cupboard = (ImageView) findViewById(R.id.cupboard);

        //second line
        cot = (ImageView) findViewById(R.id.cot);
        flowers = (ImageView) findViewById(R.id.flowers);
        heater = (ImageView) findViewById(R.id.heater);
        fan = (ImageView) findViewById(R.id.fan);

        //third line
        books = (ImageView) findViewById(R.id.books);
        cleaning = (ImageView) findViewById(R.id.cleaning);
        clock = (ImageView) findViewById(R.id.clock);
        cookingstove = (ImageView) findViewById(R.id.cookingstove);

        //four line
        coffeemaker = (ImageView) findViewById(R.id.coffeemaker);
        fireplace = (ImageView) findViewById(R.id.fireplace);
        freezer = (ImageView) findViewById(R.id.freezer);
        fridge = (ImageView) findViewById(R.id.fridge);

        //first line
        telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Телефоны");
                startActivity(intent);
            }
        });

        bathroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Ванные");
                startActivity(intent);
            }
        });

        armchair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Кресла");
                startActivity(intent);
            }
        });

        cupboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Шкафы");
                startActivity(intent);
            }
        });

        //second line
        cot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Детские кровати");
                startActivity(intent);
            }
        });

        flowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Цветы");
                startActivity(intent);
            }
        });

        heater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Обогреватели");
                startActivity(intent);
            }
        });

        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Вентиляторы");
                startActivity(intent);
            }
        });

        //third line

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Книжные полки");
                startActivity(intent);
            }
        });

        cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Чистящие средства");
                startActivity(intent);
            }
        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Часы");
                startActivity(intent);
            }
        });

        cookingstove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Кухонные плиты");
                startActivity(intent);
            }
        });

        //four line

        coffeemaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Кофемашины");
                startActivity(intent);
            }
        });

        fireplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Камины");
                startActivity(intent);
            }
        });

        freezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Морозилки");
                startActivity(intent);
            }
        });

        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Холодильники");
                startActivity(intent);
            }
        });



    }
}