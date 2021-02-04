package com.surajdev.grocerymanagementsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Product_Items_Adapter extends RecyclerView.Adapter<MyCartHolder> {


    private Context context;
    private List<ProductModel> cartList;

    RequestQueue requestQueue;
    ProductModel model = new ProductModel();

    public Product_Items_Adapter(Context context, List<ProductModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // step 11
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.list_products_items,parent,false);

        // step 12
        return new MyCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCartHolder holder, final int position) {

        Glide.with(context).load(cartList.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.pro_img);
        holder.pro_name.setText(cartList.get(position).getProduct_name());
        holder.pro_price.setText(cartList.get(position).getPrice());
        holder.pro_sp.setText(cartList.get(position).getDiscount_price());

        Log.w("ppp",""+cartList.get(position).getQuantity());





        holder.pro_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SimpleArcDialog mDialog = new SimpleArcDialog(context);
                mDialog.setConfiguration(new ArcConfiguration(context));
                ArcConfiguration configuration = new ArcConfiguration(context);
                configuration.setText("Removing...");
                mDialog.setConfiguration(configuration);
                mDialog.setCancelable(false);
                mDialog.show();

                requestQueue = Volley.newRequestQueue(context);
                StringRequest request = new StringRequest(Request.Method.POST, Util.DeleteFromCart, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = ((Activity) context).getIntent();
                        ((Activity) context).finish();
                        context.startActivity(intent);
                        Log.w("resp",response);
                        cartList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,cartList.size());
                        notifyDataSetChanged();
                        mDialog.hide();



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDialog.hide();

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();

                        map.put("product_id",""+cartList.get(position).product_id);


                        return map;
                    }

                };

                requestQueue.add(request);


            }
        });


        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


context.startActivity(new Intent(context, UpdateActivity.class)
.putExtra("position",position));



            }
        });

    }

    @Override
    public int getItemCount() {
        // step 10 return the size of the list
        return cartList.size();
    }
}



// any class with your own name that extends recyclerviewViewholder
// step 1
class MyCartHolder extends RecyclerView.ViewHolder
{
    TextView pro_id;
    TextView pro_name;
    TextView pro_price;
    TextView pro_sp;
    TextView pro_qty;
    ImageView pro_del;
    ImageView pro_img;
    ImageView add, remove;
    ImageView update;
    // step 2 (constructor)
    public MyCartHolder(@NonNull View itemView) {
        super(itemView);
        // step 4
        pro_id = itemView.findViewById(R.id.product_id);
        pro_name = itemView.findViewById(R.id.product_name);
        pro_img = itemView.findViewById(R.id.product_img);
        pro_price = itemView.findViewById(R.id.selling_price);
        pro_sp = itemView.findViewById(R.id.product_price);
        pro_del = itemView.findViewById(R.id.product_del);
        update = itemView.findViewById(R.id.update);

    }
}
