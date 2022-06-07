package space.catking.catking.viewmodel;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import space.catking.catking.model.CatKing;
import space.catking.catking.model.Resource;
import space.catking.catking.repository.CatKingRepository;

@HiltViewModel
public class CatKingViewModel extends ViewModel {
    private MutableLiveData<Integer> days = new MutableLiveData<>();

    private LiveData<Resource<CatKing>> catKing;

    @Inject
    public CatKingViewModel(CatKingRepository catKingRepository) {
        catKing = Transformations.switchMap(days, catKingRepository::getCatKing);
    }

    public LiveData<Resource<CatKing>> getCatKing() {
        return catKing;
    }

    public void setDays(Integer days) {
        this.days.setValue(days);
    }
}

