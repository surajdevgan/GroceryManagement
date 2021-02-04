package com.surajdev.grocerymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    RecyclerView recyclerView;
    Product_Items_Adapter cartAdapter;
    public static ArrayList<ProductModel> cartList = cartList = new ArrayList<>();
    ProductModel model2;
    RequestQueue requestQueue;
    LinearLayoutManager linearLayoutManager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Products");
        requestQueue = Volley.newRequestQueue(this);



        mProgressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerview_item_products);
        linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager2);

        cartAdapter = new Product_Items_Adapter(this, cartList);
        recyclerView.setAdapter(cartAdapter);

        fetchCartProducts();

        //   mProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //and this to handle action
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.cart) {

            startActivity(new Intent(this, InsertIntoDBActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchCartProducts()
    {

        StringRequest request = new StringRequest(Request.Method.POST, Util.GetCartProducts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w("response",response);

                try {
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    JSONArray jsonArray = object.getJSONArray("data");
                    if(message.equals("success"))
                    {
                        cartAdapter.notifyDataSetChanged();
                        for(int i=0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int Product_id = jsonObject.getInt("PRODUCT_ID");
                            Log.d("pi", String.valueOf(Product_id));
                            String Name = jsonObject.getString("PRODUCT_NAME");
                            String ImageUrl = jsonObject.getString("PRODUCT_IMAGE");
                            String Category =jsonObject.getString("CATEGORY");
                            String Price =jsonObject.getString("DISCOUNT_PRICE");
                            String DiscountPrice = jsonObject.getString("PRICE");
                            String quant = jsonObject.getString("ATTRIBUTES");

                            String UrlImg = Util.ProductImages+Category+"/"+ImageUrl;




                            model2 = new ProductModel(Product_id, Name, UrlImg, "Rs"+Price,  DiscountPrice, quant);
                            cartList.add(model2);
                            // productsAdapter.notifyDataSetChanged();
                        }

                        cartAdapter.notifyDataSetChanged();


                    }
                } catch (JSONException e) {
                    Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(HomeActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();



            }
        });
        requestQueue.add(request);
    }



}