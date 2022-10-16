package com.example.currency;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CurrencyAPII {
    @GET("2cbc15d00731b4edbe6b2710/latest/{curr}")
    Call<CurrencyAPI> getCurrencyName(@Path("curr") String name);
}
