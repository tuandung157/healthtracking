package itmo.frontend.app.healthtracking.android.graphicModule.today;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.androidnetworking.common.ANRequest;

import java.util.ArrayList;

import itmo.frontend.app.healthtracking.android.MainActivity;
import itmo.frontend.app.healthtracking.android.R;
import itmo.frontend.app.healthtracking.android.TelemetryFragment;
import itmo.frontend.app.healthtracking.android.graphicModule.FragmentType;
import itmo.frontend.app.healthtracking.rest.controller.TelemetryBaseController;

public class TodayFragment extends TelemetryFragment {
    private TelemetryBaseController telemetryController;

    private Button graphButton;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_today, container, false);

        graphButton = root.findViewById(R.id.btn_graph);
        graphButton.setBackgroundColor(Color.parseColor("#ffffff"));
        graphButton.setTextColor(Color.parseColor("#000000"));
        graphButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", FragmentType.TODAY.name());
            Navigation.findNavController(view)
                    .navigate(R.id.action_nav_today_to_graphFragmnet, bundle);
        });

        telemetryController = new TelemetryBaseController(getContext());
        telemetryGraphModel = ((MainActivity) requireActivity()).telemetryTableModel.TodayViewModel;

        //requests to get data from server
        loadTelemetryData();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onGetDataSuccess()
    {
        //this method is called when all data were received from sever and saved in telemetryDataSetViewModel
        if (telemetryGraphModel.isEmpty()) {
            graphButton.setEnabled(false);
            Toast.makeText(getContext(), "no data", Toast.LENGTH_LONG).show();
        } else {
            graphButton.setEnabled(true);
            Pair<ArrayList<String>, ArrayList<Integer>> temperatures = telemetryGraphModel
                    .getTemperatures("dd/MM HH:mm");

            TableLayout table = root.findViewById(R.id.table_today);
            table.setStretchAllColumns(true);
            table.setShrinkAllColumns(true);
//            for (int i = 0; i < telemetryDataSetViewModel.getTemperatures("dd/MM HH:mm").first.size(); i++) {

            int sizeLoop = telemetryGraphModel.getTemperatures("dd/MM HH:mm").first.size();
            for (int i = sizeLoop-1; i>=0; i--) {
                System.out.println("count" + sizeLoop);
                TextView temperatureValueTextView = new TextView(getContext());
                temperatureValueTextView.setText(telemetryGraphModel
                        .getTemperatures("dd/MM HH:mm").second.get(i).toString() + "°C");
                temperatureValueTextView.setGravity(Gravity.CENTER);

                TextView oxygenValueTextView = new TextView(getContext());
                oxygenValueTextView.setText(telemetryGraphModel
                        .getOxygen("dd/MM HH:mm").second.get(i).toString() + " %");
                oxygenValueTextView.setGravity(Gravity.CENTER);

                TextView heartrateValueTextView = new TextView(getContext());
                heartrateValueTextView.setText(telemetryGraphModel
                        .getHeartrate("dd/MM HH:mm").second.get(i).toString() + " bpm");
                heartrateValueTextView.setGravity(Gravity.CENTER);

                TextView resultValueTextView = new TextView(getContext());

                String result;

                if(telemetryGraphModel.getTemperatures("dd/MM HH:mm").second.get(i) > 37 ||
                   telemetryGraphModel.getOxygen("dd/MM HH:mm").second.get(i) < 95){
                    result = "WARNING";
                }else result ="GOOD";

//                resultValueTextView.setText(telemetryDataSetViewModel
//                        .getLuminosities("dd/MM HH:mm").second.get(i).toString() + " +");
//                resultValueTextView.setGravity(Gravity.CENTER);

                resultValueTextView.setText(result);
                resultValueTextView.setGravity(Gravity.CENTER);

                TextView timeValueTextView = new TextView(getContext());
                timeValueTextView.setText(telemetryGraphModel
                        .getTemperatures("dd/MM HH:mm:ss").first.get(i));
                timeValueTextView.setGravity(Gravity.CENTER);

                TableRow tableRow = new TableRow(getContext());
                TableRow.LayoutParams params = new TableRow.LayoutParams();

                if(i%2 ==0) tableRow.setBackgroundColor(Color.parseColor("#FEE1E8"));
                else tableRow.setBackgroundColor(Color.parseColor("#FFFFFF"));

                params.span = 6;
                params.topMargin = 20;

                tableRow.addView(timeValueTextView, params);
                tableRow.addView(temperatureValueTextView, params);
                tableRow.addView(oxygenValueTextView, params);
                tableRow.addView(heartrateValueTextView, params);
                tableRow.addView(resultValueTextView, params);

                table.addView(tableRow);

            }


        }

    }

    @Override
    public void onGetDataFailed(String message) {
        //this method is called when error response received from server
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void unauthorizedResponse() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_today_to_loginActivity);
    }

    @Override
    protected ANRequest getRequest(int size, int page) {
        return telemetryController.getTodayTelemetry(size, page);
    }
}
