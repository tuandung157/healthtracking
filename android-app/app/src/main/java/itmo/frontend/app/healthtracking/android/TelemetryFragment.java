package itmo.frontend.app.healthtracking.android;

import android.app.ProgressDialog;
import androidx.fragment.app.Fragment;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;

import itmo.frontend.app.healthtracking.rest.model.TelemetryGraphModel;
import itmo.frontend.app.healthtracking.rest.model.TelemetryModel;

public abstract class TelemetryFragment extends Fragment {
    protected TelemetryGraphModel telemetryGraphModel;
    private int load;
    private int pageCount;

    protected void loadTelemetryData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        load = 0;
        sendRequest(progressDialog, -1, 1);
    }

    private void sendRequest(ProgressDialog progressDialog, int size, int page) {


        ANRequest request = getRequest(Math.min(size, 1000), page-1);

        request.getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {

                        JSONArray jsonArray = response;

                        if (response.length() == 0) {
                            onGetDataFailed("No data available");
                        }
                        TelemetryModel[] array = TelemetryModel.getTelemetryByArray(jsonArray);
                        telemetryGraphModel.addTelemetries(array);

                        load++;
            }
            @Override
            public void onError(ANError anError) {
                System.out.println(anError.toString());
                if (anError.getErrorCode() == 403) {
                    unauthorizedResponse();
                } else {
                    onGetDataFailed("Error occurred");
                }
                progressDialog.dismiss();
            }
        });



    }

    protected abstract ANRequest getRequest(int size, int page);

    protected abstract void unauthorizedResponse();

    protected abstract void onGetDataSuccess();

    protected abstract void onGetDataFailed(String message);
}
