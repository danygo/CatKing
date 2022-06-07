package space.catking.catking.viewmodel;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import space.catking.catking.model.Marketing;
import space.catking.catking.model.Resource;
import space.catking.catking.repository.CatKingRepository;

@HiltViewModel
public class MarketingViewModel extends ViewModel {
    private MutableLiveData<Boolean> trigger = new MutableLiveData<>();

    private LiveData<Resource<Marketing>> marketing;

    @Inject
    public MarketingViewModel(CatKingRepository catKingRepository) {
        marketing = Transformations.switchMap(trigger, trigger -> catKingRepository.getMarketing());
    }

    public LiveData<Resource<Marketing>> getMarketing() {
        return marketing;
    }

    public void fetchMarketing() {
        trigger.setValue(true);
    }
}
