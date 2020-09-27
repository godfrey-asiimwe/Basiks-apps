package com.example.basiks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.basiks.adapter.MessagesAdapter;
import com.example.basiks.adapter.ProductAdapter;
import com.example.basiks.helper.DividerItemDecoration;
import com.example.basiks.model.BProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterListener {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView kesProductPrice;
    private ProductAdapter pdapter;
    public Button buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        //Make call to AsyncTask
        new AsyncFetch().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    public void logout(){
        finish();
        startActivity(new Intent(ProductActivity.this, MainActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                //newGame();
                return true;
            case R.id.help:
               /// showHelp();
                return true;

            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getApplicationContext(), "Unable to fetch json: " + position, Toast.LENGTH_LONG).show();
    }


    private class AsyncFetch extends AsyncTask<String, String, String> implements ProductAdapter.ProductAdapterListener {
        ProgressDialog pdLoading = new ProgressDialog(ProductActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://demo.basiksservices.com/api/products.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();
            List<BProduct> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    BProduct productData;
                    productData = new BProduct();
                    productData.setImage(json_data.getString("image"));
                    productData.setName(json_data.getString("name"));
                    productData.setInfo( json_data.getString("info"));
                    productData.setCategory( json_data.getString("category"));
                    productData.setPrice(String.format(json_data.getString("price"),3));

                    data.add(productData);
                }

                // Setup and Handover data to recyclerview
                kesProductPrice = (RecyclerView)findViewById(R.id.recycler_view);
                pdapter = new ProductAdapter(ProductActivity.this, data,this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                kesProductPrice.setLayoutManager(mLayoutManager);
                kesProductPrice.setItemAnimator(new DefaultItemAnimator());
                kesProductPrice.addItemDecoration(new DividerItemDecoration(ProductActivity.this, LinearLayoutManager.VERTICAL));
                kesProductPrice.setAdapter(pdapter);
                kesProductPrice.setLayoutManager(new LinearLayoutManager(ProductActivity.this));

                //setOnClickListener()


            } catch (JSONException e) {
                Toast.makeText(ProductActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }


        @Override
        public void onItemClicked(int position) {
            Uri uri = Uri.parse("http://demo.basiksservices.com/User/product.php?purchase=18"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}