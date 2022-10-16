package com.example.currency.ui.chart;


import static com.example.currency.SomeCode.getCurrencyBitmapFromCode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.currency.CurrencyAPI;
import com.example.currency.CurrencyAPII;
import com.example.currency.RetrofitCurrency;
import com.example.currency.databinding.FragmentChartBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChartFragment extends Fragment {
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barentries;
    TextView tvUSD;
    ImageView ivUSD;
    TextView tvCurr;
    ImageView ibCurr;
    String[] arrWithCurrs;
    String currency;
    private FragmentChartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChartViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ChartViewModel.class);

        binding = FragmentChartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        barChart = binding.barChart;
        tvUSD = binding.tvUSD;
        ivUSD = binding.ivUSD;
        tvCurr = binding.tvCurr;
        ibCurr = binding.ibCurr;

        tvUSD.setText("USD");
        ivUSD.setImageBitmap(getCurrencyBitmapFromCode(getContext(),"USD"));

        getData();

        currency = "USD";

        CurrencyAPII currencyAPII = RetrofitCurrency.getClient().create(CurrencyAPII.class);
        Call<CurrencyAPI> call = currencyAPII.getCurrencyName(currency);
        call.enqueue(new Callback<CurrencyAPI>() {
            @Override
            public void onResponse(Call<CurrencyAPI> call, Response<CurrencyAPI> response) {
                CurrencyAPI res = response.body();
                Log.d("TAG", "onResponse: "+ res.getBaseCode());
                tvCurr.setText(res.getBaseCode());
                ibCurr.setImageBitmap(getCurrencyBitmapFromCode(getContext(),res.getBaseCode()));
                for(int i = 0; i < res.getConversionRates().keySet().size();i++){
                    arrWithCurrs = res.getConversionRates().keySet().toArray(new String[i]);
                }
            }

            @Override
            public void onFailure(Call<CurrencyAPI> call, Throwable t) {

            }
        });

        ibCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert1 = new AlertDialog.Builder(getContext());
                alert1.setTitle("Currencies");
                alert1.setItems(arrWithCurrs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tvCurr.setText(arrWithCurrs[which]);
                        ibCurr.setImageBitmap(getCurrencyBitmapFromCode(getContext(),arrWithCurrs[which]));
                        currency = arrWithCurrs[which];

                    }

                });
                alert1.show();
            }
        });



        barDataSet = new BarDataSet(barentries, "data set");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);


        return root;
    }

    private void getData(){
        barentries = new ArrayList<>();
        barentries.add(new BarEntry(1f,2));
        barentries.add(new BarEntry(3f,4));
        barentries.add(new BarEntry(5f,7));
        barentries.add(new BarEntry(6f,10));
        barentries.add(new BarEntry(7f,13));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}