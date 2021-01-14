package itmo.frontend.app.healthtracking.android.graphicModule.now;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NowViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public LiveData<String> getText() {
        return mText;
    }
}