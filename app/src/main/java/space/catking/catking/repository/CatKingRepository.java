package space.catking.catking.repository;


import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.catking.catking.model.CatKing;
import space.catking.catking.model.Marketing;
import space.catking.catking.model.Resource;
import space.catking.catking.service.Service;

public class CatKingRepository {
    private Service service;

    @Inject
    public CatKingRepository(Service service) {
        this.service = service;
    }

    public LiveData<Resource<CatKing>> getCatKing(Integer days) {
        MutableLiveData<Resource<CatKing>> data = new MutableLiveData<>();
        data.setValue(Resource.loading(null));

        service.getCatKing(days).enqueue(new Callback<CatKing>() {
            @Override
            public void onResponse(Call<CatKing> call, Response<CatKing> response) {
                if (response.isSuccessful()) {
                    data.setValue(Resource.ok(response.body()));
                } else {
                    data.setValue(Resource.error(new Throwable(), null));
                }
            }

            @Override
            public void onFailure(Call<CatKing> call, Throwable t) {
                data.setValue(Resource.error(t, null));
            }
        });

        return data;
    }

    public LiveData<Resource<Marketing>> getMarketing() {
        MutableLiveData<Resource<Marketing>> data = new MutableLiveData<>();
        data.setValue(Resource.loading(null));

        service.getCatKingMarketing().enqueue(new Callback<Marketing>() {
            @Override
            public void onResponse(Call<Marketing> call, Response<Marketing> response) {
                if (response.isSuccessful()) {
                    data.setValue(Resource.ok(response.body()));
                } else {
                    data.setValue(Resource.error(new Throwable(), null));
                }
            }

            @Override
            public void onFailure(Call<Marketing> call, Throwable t) {
                data.setValue(Resource.error(t, null));
            }
        });

        return data;
    }
}
