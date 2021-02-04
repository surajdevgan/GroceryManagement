package com.surajdev.grocerymanagementsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    EditText ProductNameET, ProductPriceET, DiscountPriceET,StockAvailableET;
    ImageView ProductImage;
    Spinner ProductCategory, ProductUnit;
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
        ProductCategory = findViewById(R.id.up_productCategory);
        ProductPriceET = findViewById(R.id.up_product_price_et);
        ProductUnit = findViewById(R.id.up_productunit);
        StockAvailableET = findViewById(R.id.up_stockAvailable_et);
        DiscountPriceET = findViewById(R.id.up_discount_price);

        Intent i = getIntent();
        position = i.getExtras().getInt("position");


        ProductNameET.setText(HomeActivity.cartList.get(position).getProduct_name());


        ProductUnitList = new ArrayList<>();
        ProductUnitList.add("--Select Unit--");
        ProductUnitList.add("/-kg");
        ProductUnitList.add("/-g");
        ProductUnitList.add("/-pack");
        ProductUnitList.add("/-piece");
        ProductUnit.setAdapter(new ArrayAdapter<String>(UpdateActivity.this,R.layout.support_simple_spinner_dropdown_item,
                ProductUnitList));
        ProductUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedProUnit =ProductUnit.getItemAtPosition(ProductUnit.getSelectedItemPosition()).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        final String ProductName = ProductNameET.getText().toString();
        final String Category = SelectedProCategory;
        final String Price = ProductPriceET.getText().toString();
        final String Discount = DiscountPriceET.getText().toString();
        final int id = HomeActivity.cartList.get(position).getProduct_id();





        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://gulatimart.000webhostapp.com/GroceryAdmin/Scripts/UpdateProduct.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(UpdateActivity.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UpdateActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();

                params.put("id",""+id);
                params.put("name",ProductName);
                params.put("email",Category);
                params.put("contact",Price);
                params.put("address",Discount);

                return params;
            }
        };


        requestQueue.add(request);


    }
}