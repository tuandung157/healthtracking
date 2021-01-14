package itmo.frontend.app.healthtracking.android.graphicModule.week;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeekViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WeekViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("The health week");
    }

    public LiveData<String> getText() {
        return mText;
    }
}