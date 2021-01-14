package itmo.frontend.app.healthtracking.android;

import android.app.ProgressDialog;
import androidx.fragment.app.Fragment;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class DataFragment extends Fragment {
    protected void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        sendRequest(progressDialog);
    }
    protected void getCurrentData(){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        sendRequestForNow(progressDialog);
    }

    private void sendRequestForNow(ProgressDialog progressDialog) {
        ANRequest request = getRequest();
        request.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                parseResponseObject(response);
                onGetDataSuccess();
                progressDialog.dismiss();
            }

            @Override
            public void onError(ANError anError) {
                System.out.println(anError);
            }
        });
    }

    private void sendRequest(ProgressDialog progressDialog) {
        ANRequest request = getRequest();
//        request.getAsJSONObject(new JSONObjectRequestListener() {
//            @Override
//            public void onResponse(JSONObject response) {
//                parseResponse(response);
//                onGetDataSuccess();
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                System.out.println(anError.toString());
//                if (anError.getErrorCode() == 403) {
//                    unauthorizedResponse();
//                } else {
//                    onGetDataFailed("Error occurred");
//                }
//                progressDialog.dismiss();
//            }
//        });

        request.getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                parseResponse(response);
                onGetDataSuccess();
                progressDialog.dismiss();
            }

            @Override
            public void onError(ANError anError) {
                System.out.println(anError);
            }
        });
    }
    protected abstract void parseResponseObject(JSONObject response);

    protected abstract void parseResponse(JSONArray response);

    protected abstract ANRequest getRequest();

    protected abstract void unauthorizedResponse();

    protected abstract void onGetDataSuccess();

    protected abstract void onGetDataFailed(String message);
}
