package space.catking.catking.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import space.catking.catking.service.Service;
import space.catking.catking.util.AppConstants;


@InstallIn(SingletonComponent.class)
@Module
public class NetworkModule {
    @Singleton
    @Provides
    Service provideService() {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service.class);
    }
}
