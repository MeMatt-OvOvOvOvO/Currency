package com.example.currency;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class ChooseCurrencyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_currency);
        getCurrencies();
    }

    public void getCurrencies(){

        createList();
    }

    public void createList(){
        ListView currencyLV = this.findViewById(R.id.currency_lv);

        CurrencyCustomAdapter adapter = new CurrencyCustomAdapter (
                this,
                this,
                R.layout.currency_layout,
                Helpers.getCurrencyArrayList()
        );
        currencyLV.setAdapter(adapter);
    }

}