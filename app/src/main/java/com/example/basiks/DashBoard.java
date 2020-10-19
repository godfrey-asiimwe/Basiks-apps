package com.example.basiks;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class DashBoard extends AppCompatActivity implements View.OnClickListener{

    private CardView productCard,paymentCard,accountsCard,networkCard;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //defining cards
        productCard=(CardView) findViewById(R.id.productCard);
        paymentCard=(CardView) findViewById(R.id.paymentsCard);
        accountsCard=(CardView) findViewById(R.id.accountsCard);
        networkCard=(CardView) findViewById(R.id.networkCard);

        //adding a listener to cards
        productCard.setOnClickListener(this);
        paymentCard.setOnClickListener(this);
        accountsCard.setOnClickListener(this);
        networkCard.setOnClickListener(this);

        productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(DashBoard.this, ProductActivity.class));
            }
        });

        paymentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(DashBoard.this, Payments.class));
            }
        });

        accountsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(DashBoard.this, accounts.class));
            }
        });

        networkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(DashBoard.this, NetWork.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    public void logout(){
        finish();
        startActivity(new Intent(DashBoard.this, MainActivity.class));
    }

    public void backToDashBoard(){
        finish();
        startActivity(new Intent(DashBoard.this, DashBoard.class));
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


    @Override
    public void onClick(View view) {

    }
}