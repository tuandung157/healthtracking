package itmo.frontend.app.healthtracking.android.graphicModule.graph;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

import itmo.frontend.app.healthtracking.android.MainActivity;
import itmo.frontend.app.healthtracking.android.R;
import itmo.frontend.app.healthtracking.android.graphicModule.FragmentType;
import itmo.frontend.app.healthtracking.rest.controller.TelemetryBaseController;
import itmo.frontend.app.healthtracking.rest.model.TelemetryGraphModel;

public class GraphFragment extends Fragment {
    private TelemetryBaseController telemetryController;
    private GraphBuilder graphBuilder;
    private FragmentType fragmentType;
    private String dateFormat;
    private TelemetryGraphModel viewModel;

    BarChart temperatureChart;
    BarChart oxygenChart;
    BarChart heartrateChart;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_graph, container, false);
        telemetryController =  new TelemetryBaseController(getContext());
        graphBuilder = new GraphBuilder();
        fragmentType = FragmentType.valueOf(getArguments().getString("type", "TODAY"));

        temperatureChart = (BarChart) root.findViewById(R.id.temp_chart);
//        temperatureChart = (LineChart) root.findViewById(R.id.temp_chart);
        oxygenChart = (BarChart) root.findViewById(R.id.oxy_chart);
        heartrateChart = (BarChart) root.findViewById(R.id.heart_chart);

        switch (fragmentType){
            case WEEK:
                dateFormat = "dd/MM";
                viewModel = ((MainActivity)getActivity()).telemetryTableModel.WeekViewModel;
                break;
            case MONTH:
                dateFormat = "dd/MM";
                viewModel = ((MainActivity)getActivity()).telemetryTableModel.MonthViewModel;
                break;
            case TODAY:
            default:
                dateFormat = "HH:mm";
                viewModel = ((MainActivity)getActivity()).telemetryTableModel.TodayViewModel;
        }

        DrawGraphs(viewModel);

        return root;
    }

    public void DrawGraphs(TelemetryGraphModel viewModel) {
        Pair<ArrayList<String>, ArrayList<Integer>> temp = viewModel.getTemperatures(dateFormat);
        graphBuilder.fillBarChart(temperatureChart, "Temperature", temp.first, temp.second, "#9DBBE3");

        Pair<ArrayList<String>, ArrayList<Integer>> oxygen = viewModel.getOxygen(dateFormat);
        graphBuilder.fillBarChart(oxygenChart, "Oxygen", oxygen.first, oxygen.second, "#A8E3C9");

        Pair<ArrayList<String>, ArrayList<Integer>> heartrate = viewModel.getHeartrate(dateFormat);
        graphBuilder.fillBarChart(heartrateChart, "Heart rate", heartrate.first, heartrate.second, "#EE9D94");

    }
}