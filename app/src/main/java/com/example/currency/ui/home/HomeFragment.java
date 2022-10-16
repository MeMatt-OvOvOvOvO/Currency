package com.example.currency.ui.home;

import static com.example.currency.SomeCode.getCurrencyBitmapFromCode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.currency.CurrencyAPI;
import com.example.currency.CurrencyAPII;
import com.example.currency.CustomBaseAdapter;
import com.example.currency.R;
import com.example.currency.RetrofitCurrency;
import com.example.currency.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button butekAdd;
    ListView listView;
    String currency;
    ImageButton butekCurr;
    TextView currText;
    String[] arrWithCurrs;
    CustomBaseAdapter customBaseAdapter;
    ArrayList<String> items = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        butekAdd = binding.butekAdd;
        listView = binding.listView;
        butekCurr = binding.butekCurr;
        currText = binding.currText;

        currency = "USD";

        CurrencyAPII currencyAPII = RetrofitCurrency.getClient().create(CurrencyAPII.class);
        Call<CurrencyAPI> call = currencyAPII.getCurrencyName(currency);
        call.enqueue(new Callback<CurrencyAPI>() {
            @Override
            public void onResponse(Call<CurrencyAPI> call, Response<CurrencyAPI> response) {
                CurrencyAPI res = response.body();
                Log.d("TAG", "onResponse: "+ res.getBaseCode());
                currText.setText(res.getBaseCode());
                butekCurr.setImageBitmap(getCurrencyBitmapFromCode(getContext(),res.getBaseCode()));
                for(int i = 0; i < res.getConversionRates().keySet().size();i++){
                    arrWithCurrs = res.getConversionRates().keySet().toArray(new String[i]);
                }

                Log.d("TAG", "onResponse: " + arrWithCurrs);


            }

            @Override
            public void onFailure(Call<CurrencyAPI> call, Throwable t) {

            }
        });

        butekAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Currencies");
                alert.setItems(arrWithCurrs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        items.add(arrWithCurrs[which]);
                        customBaseAdapter.notifyDataSetChanged();
                    }
                });
                alert.show();
            }
        });

        butekCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.removeAll(items);
                customBaseAdapter.notifyDataSetChanged();
                AlertDialog.Builder alert1 = new AlertDialog.Builder(getContext());
                alert1.setTitle("Currencies");
                alert1.setItems(arrWithCurrs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        currText.setText(arrWithCurrs[which]);
                        butekCurr.setImageBitmap(getCurrencyBitmapFromCode(getContext(),arrWithCurrs[which]));
                        currency = arrWithCurrs[which];

                    }

                });
                alert1.show();
            }
        });
        customBaseAdapter = new CustomBaseAdapter(getContext(), items, currency);
        listView.setAdapter(customBaseAdapter);




        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}