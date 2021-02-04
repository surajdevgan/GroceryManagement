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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InsertIntoDBActivity extends AppCompatActivity {
    EditText ProductNameET, ProductPriceET, DiscountPriceET,StockAvailableET;
    ImageView ProductImage;
    Spinner ProductCategory, ProductUnit, StockUnit;
    ArrayList<String> CategoryList, ProductUnitList, StockUnitList;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    static final int GALLERY_REQUEST =2;
    Bitmap bitmap;
    String encodeImage, SelectedProCategory,SelectedProUnit, SelectedStockUnit;
    public static final String urlinst="http://gulatimart.000webhostapp.com/GroceryAdmin/Scripts/productinsert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_into_d_b);
        ProductNameET = findViewById(R.id.product_name_et);
        ProductImage = findViewById(R.id.product_imageView);
        ProductCategory = findViewById(R.id.productCategory);
        ProductPriceET = findViewById(R.id.product_price_et);
        ProductUnit = findViewById(R.id.productunit);

        StockAvailableET = findViewById(R.id.stockAvailable_et);
        ProductUnitList = new ArrayList<>();
        ProductUnitList.add("--Select Unit--");
        ProductUnitList.add("/-kg");
        ProductUnitList.add("/-g");
        ProductUnitList.add("/-pack");
        ProductUnitList.add("/-piece");
        ProductUnit.setAdapter(new ArrayAdapter<String>(InsertIntoDBActivity.this,R.layout.support_simple_spinner_dropdown_item,
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

        DiscountPriceET = findViewById(R.id.discount_price);
        CategoryList =new ArrayList<>();
        CategoryList.add("--Select Category--");
        CategoryList.add("Fruits");
        CategoryList.add("Vegetables");
        CategoryList.add("Dairy Product");
        ProductCategory.setAdapter(new ArrayAdapter<String>(InsertIntoDBActivity.this,R.layout.support_simple_spinner_dropdown_item,
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

        requestQueue = Volley.newRequestQueue(this);

    }
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream =  new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        encodeImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            bitmap = (BitmapFactory.decodeFile(picturePath));
            bitmap=getResizedBitmap(bitmap, 400);
            Log.w("path ", picturePath+"");
            ProductImage.setImageBitmap(bitmap);
            imageToString(      bitmap);
        }

    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    void insertProducts(){
        stringRequest = new StringRequest(Request.Method.POST, urlinst, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w("rre",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("Status");
                    if (message.contains("OK")) {
                        Toast.makeText(InsertIntoDBActivity.this, "Inserted" + message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(InsertIntoDBActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(InsertIntoDBActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InsertIntoDBActivity.this, "server error "+error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ProductName",ProductNameET.getText().toString().trim());
                map.put("ProductCategory", SelectedProCategory);
                map.put("ProductPrice",ProductPriceET.getText().toString().trim());
                map.put("ProductUnit", SelectedProUnit);
                map.put("DiscountPrice",DiscountPriceET.getText().toString().trim());
                map.put("StockAvailable",StockAvailableET.getText().toString().trim());
                String imageData = imageToString(bitmap);
                map.put("image", imageData);
                return map;

            }
        };

        requestQueue.add(stringRequest);
    }

    public void SelectGallery(View view) {

        Intent galleryIntent =new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2);

    }

    public void Submit(View view) {

        insertProducts();
    }
}