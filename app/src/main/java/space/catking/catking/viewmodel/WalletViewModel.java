package space.catking.catking.viewmodel;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import space.catking.catking.model.Resource;
import space.catking.catking.model.Wallet;
import space.catking.catking.repository.WalletRepository;

@HiltViewModel
public class WalletViewModel extends ViewModel {
    private LiveData<Resource<Wallet>> wallet;

    private MutableLiveData<String> address = new MutableLiveData<>();

    @Inject
    public WalletViewModel(WalletRepository repository) {
        wallet = Transformations.switchMap(address, repository::getWallet);
    }

    public LiveData<Resource<Wallet>> getWallet() {
        return wallet;
    }

    public LiveData<String> getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address.setValue(address);
    }
}
