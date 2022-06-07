package space.catking.catking.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.catking.catking.model.CatKing;
import space.catking.catking.model.Marketing;
import space.catking.catking.model.Wallet;

public interface Service {
    @GET("/getCatKing")
    Call<CatKing> getCatKing(@Query("days") Integer days);

    @GET("/getCatKingMarketing")
    Call<Marketing> getCatKingMarketing();

    @GET("/getWallet")
    Call<Wallet> getWallet(@Query("address") String address);
}
