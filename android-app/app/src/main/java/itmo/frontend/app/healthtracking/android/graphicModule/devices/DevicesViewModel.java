package itmo.frontend.app.healthtracking.android.graphicModule.devices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DevicesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DevicesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Devices");
    }

    public LiveData<String> getText() {
        return mText;
    }
}