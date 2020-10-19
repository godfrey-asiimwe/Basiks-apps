package com.example.basiks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.basiks.adapter.AccountAdapter;
import com.example.basiks.helper.DividerItemDecoration;
import com.example.basiks.model.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Printer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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

public class accounts extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView paymentRecycler;
    private AccountAdapter pdapter;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       id=SharedPrefManager.getInstance(this).getUser().getId();



        //Make call to AsyncTask
        new accounts.AsyncFetch().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    public void logout(){
        finish();
        startActivity(new Intent(accounts.this, MainActivity.class));
    }

    public void backToDashBoard(){
        finish();
        startActivity(new Intent(accounts.this, DashBoard.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                //newGame();
                return true;
            case R.id.backToDash:
                backToDashBoard();
                return true;

            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(accounts.this);
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
                url = new URL("http://demo.basiksservices.com/api/purchases.php?id="+id);

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
            List<Account> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Account accountData;
                    accountData = new Account();

                    accountData.setAmount(json_data.getString("amount"));
                    accountData.setBill_no(json_data.getString("bill_no"));
                    accountData.setDate(json_data.getString("entry_date"));
                    accountData.setName(json_data.getString("name"));
                    accountData.setStatus(json_data.getString("status"));
                    accountData.setUser_no(json_data.getString("user_no"));
                    accountData.setStockist(json_data.getString("stockist"));

                    data.add(accountData);
                }

                // Setup and Handover data to recyclerview
                paymentRecycler = (RecyclerView)findViewById(R.id.recycler_view3);
                pdapter = new AccountAdapter(accounts.this, data);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                paymentRecycler.setLayoutManager(mLayoutManager);
                paymentRecycler.setItemAnimator(new DefaultItemAnimator());
                paymentRecycler.addItemDecoration(new DividerItemDecoration(accounts.this, LinearLayoutManager.VERTICAL));
                paymentRecycler.setAdapter(pdapter);
                paymentRecycler.setLayoutManager(new LinearLayoutManager(accounts.this));

                //setOnClickListener()


            } catch (JSONException e) {
                Toast.makeText(accounts.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }
}