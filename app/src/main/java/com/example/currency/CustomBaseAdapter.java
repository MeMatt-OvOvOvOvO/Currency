package com.example.currency;

import static com.example.currency.SomeCode.getCurrencyBitmapFromCode;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> currencyCode;
    LayoutInflater inflater;
    TextView textView;
    TextView textViewCode;
    TextView firstNumbers;
    ImageView imageView;
    String chosenCurr;
    public CustomBaseAdapter(Context ctx, ArrayList<String> currencyCode, String chosenCurr){
        this.context = ctx;
        this.currencyCode = currencyCode;
        this.chosenCurr = chosenCurr;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return currencyCode.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_row_layout, null);
        textView = view.findViewById(R.id.tv1);
        imageView = view.findViewById(R.id.image);
        textViewCode = view.findViewById(R.id.textViewCode);
        firstNumbers = view.findViewById(R.id.firstNumbers);


        CurrencyAPII currencyAPII = RetrofitCurrency.getClient().create(CurrencyAPII.class);
        Call<CurrencyAPI> call = currencyAPII.getCurrencyName(chosenCurr);
        call.enqueue(new Callback<CurrencyAPI>() {
            @Override
            public void onResponse(Call<CurrencyAPI> call, Response<CurrencyAPI> response) {
                CurrencyAPI res = response.body();
                Log.d("TAG", "onResponse: "+ res.getBaseCode());
                textViewCode.setText(String.valueOf(res.getConversionRates().get(currencyCode.get(i))));


            }

            @Override
            public void onFailure(Call<CurrencyAPI> call, Throwable t) {

            }
        });



        textView.setText(currencyCode.get(i));
        imageView.setImageBitmap(getCurrencyBitmapFromCode(view.getContext(), currencyCode.get(i)));
        return view;
    }
}
