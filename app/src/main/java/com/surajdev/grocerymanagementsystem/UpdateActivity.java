package com.surajdev.grocerymanagementsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    EditText ProductNameET, ProductPriceET, DiscountPriceET,StockAvailableET;
    ImageView ProductImage;
    Spinner ProductCategory, ProductUnit, StockUnit;
    ArrayList<String> CategoryList, ProductUnitList, StockUnitList;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    static final int GALLERY_REQUEST =2;
    Bitmap bitmap;
    private int position;
    String encodeImage, SelectedProCategory,SelectedProUnit, SelectedStockUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ProductNameET = findViewById(R.id.up_product_name_et);
        ProductImage = findViewById(R.id.up_product_imageView);
        ProductCategory = findViewById(R.id.up_productCategory);
        ProductPriceET = findViewById(R.id.up_product_price_et);
        ProductUnit = findViewById(R.id.up_productunit);
        StockUnit = findViewById(R.id.up_stockunit);
        StockAvailableET = findViewById(R.id.up_stockAvailable_et);
        DiscountPriceET = findViewById(R.id.up_discount_price);

        Intent i = getIntent();
        position = i.getExtras().getInt("position");


        ProductNameET.setText(HomeActivity.cartList.get(position).getProduct_name());



        CategoryList =new ArrayList<>();
        CategoryList.add("--Select Category--");
        CategoryList.add("Fruits");
        CategoryList.add("Vegetables");
        CategoryList.add("Dairy Product");

        ProductCategory.setAdapter(new ArrayAdapter<String>(UpdateActivity.this,R.layout.support_simple_spinner_dropdown_item,
                CategoryList));
        ProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedProCategory =ProductCategory.getItemAtPosition(ProductCategory.getSelectedItemPosition()).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ProductPriceET.setText(HomeActivity.cartList.get(position).getDiscount_price().trim());
        DiscountPriceET.setText(HomeActivity.cartList.get(position).getPrice().trim().substring(2));


        requestQueue = Volley.newRequestQueue(this);
    }







    public void Update(View view) {

    }
}