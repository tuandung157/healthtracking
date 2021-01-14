package itmo.frontend.app.healthtracking.android.graphicModule.today;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodayViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TodayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("health today");
    }

    public LiveData<String> getText() {
        return mText;
    }
}