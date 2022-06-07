package space.catking.catking.repository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.catking.catking.model.Resource;
import space.catking.catking.model.Wallet;
import space.catking.catking.service.Service;

public class WalletRepository {
    private Service service;

    @Inject
    public WalletRepository(Service service) {
        this.service = service;
    }

    public LiveData<Resource<Wallet>> getWallet(String address) {
        MutableLiveData<Resource<Wallet>> data = new MutableLiveData<>();
        data.setValue(Resource.loading(null));

        service.getWallet(address).enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(Call<Wallet> call, Response<Wallet> response) {
                if (response.isSuccessful()) {
                    data.setValue(Resource.ok(response.body()));
                } else {
                    data.setValue(Resource.error(new Throwable(), null));
                }
            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {
                data.setValue(Resource.error(t, null));
            }
        });

        return data;
    }
}
