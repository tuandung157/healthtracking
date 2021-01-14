package itmo.frontend.app.healthtracking.android.graphicModule.now;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import com.androidnetworking.common.ANRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import itmo.frontend.app.healthtracking.android.DataFragment;
import itmo.frontend.app.healthtracking.android.R;
import itmo.frontend.app.healthtracking.rest.controller.TelemetryBaseController;
import itmo.frontend.app.healthtracking.rest.model.TelemetryModel;

public class NowFragment extends DataFragment {
    private TelemetryBaseController telemetryController;
    private TelemetryModel telemetryModel;
    private View root;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_now, container, false);

        telemetryController = new TelemetryBaseController(getContext());

        //requests to get data from server
        getCurrentData();

        return root;
    }

    @Override
    protected void parseResponseObject(JSONObject response) {
            telemetryModel = new TelemetryModel(response);
    }
    @Override
    protected void parseResponse(JSONArray response) {
    }
    @Override
    protected ANRequest getRequest() {
        return telemetryController.getCurrentTelemetry();
    }

    @Override
    protected void unauthorizedResponse() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_now_to_loginActivity);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onGetDataSuccess() {

        TextView temperatureValueTextView = root.findViewById(R.id.current_temp);
        temperatureValueTextView.setText(telemetryModel.Temperature.toString() + "°C");

        TextView oxygenValueTextView = root.findViewById(R.id.current_oxygen);
        oxygenValueTextView.setText(telemetryModel.Oxygen.toString() + " %");

        TextView heartRateValueTextView = root.findViewById(R.id.current_heartrate);
        heartRateValueTextView.setText(telemetryModel.HeartRate.toString() + " bpm ");

        TextView resultValueTextView = root.findViewById(R.id.current_result);
        String result = "";
        if(telemetryModel.Result > 38 || telemetryModel.Oxygen < 95) {
            result = "WARNING";
        }else result ="GOOD";
        resultValueTextView.setText(result + "");
    }

    @Override
    protected void onGetDataFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}