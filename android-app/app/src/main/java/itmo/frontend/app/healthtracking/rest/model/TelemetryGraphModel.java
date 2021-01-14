package itmo.frontend.app.healthtracking.rest.model;

import android.util.Pair;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import itmo.frontend.app.healthtracking.android.graphicModule.FragmentType;

public class TelemetryGraphModel {
    private Map<Date, Integer> temperatures;
    private Map<Date, Integer> oxygen;
    private Map<Date, Integer> heartrate;
    private Map<Date, Integer> result;
    private FragmentType fragmentType;

    public TelemetryGraphModel(FragmentType type) {
        this.fragmentType = type;
        this.temperatures = new HashMap<>();
        this.oxygen = new HashMap<>();
        this.heartrate = new HashMap<>();
        this.result = new HashMap<>();
    }

    public boolean isEmpty(){
        return this.temperatures.size() == 0
                && this.oxygen.size() == 0
                && this.heartrate.size() == 0
                && this.result.size() == 0;
    }

    public void addTelemetries(TelemetryModel[] telemetries) {
        if (telemetries.length == 0) return;
        Date currentDate = new Date(System.currentTimeMillis());
        currentDate.setHours(0); currentDate.setMinutes(0); currentDate.setSeconds(0);

        for (TelemetryModel telemetry : telemetries) {

                if (telemetry.Temperature != null)
                    this.temperatures.put(telemetry.Timestamp, telemetry.Temperature);
                if (telemetry.Oxygen != null)
                    this.oxygen.put(telemetry.Timestamp, telemetry.Oxygen);
                if (telemetry.HeartRate != null)
                    this.heartrate.put(telemetry.Timestamp, telemetry.HeartRate);
                if (telemetry.Result != null)
                    this.result.put(telemetry.Timestamp, telemetry.Result);
        }
    }

    public Pair<ArrayList<String>, ArrayList<Integer>> getTemperatures(String dateFormat) {
        return mapToArrayListPair(this.temperatures, dateFormat);
    }

    public Pair<ArrayList<String>, ArrayList<Integer>> getOxygen(String dateFormat) {
        return mapToArrayListPair(this.oxygen, dateFormat);
    }

    public Pair<ArrayList<String>, ArrayList<Integer>> getHeartrate(String dateFormat) {
        return mapToArrayListPair(this.heartrate, dateFormat);
    }

    public Pair<ArrayList<String>, ArrayList<Integer>> getResult(String dateFormat) {
        return mapToArrayListPair(this.result, dateFormat);
    }


    private Pair<ArrayList<String>, ArrayList<Integer>> mapToArrayListPair(Map<Date, Integer> map, String dateFormat) {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        DateFormat format = new SimpleDateFormat(dateFormat);
        ArrayList<Date> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        for (Date key: keys) {
            if (map.get(key) != null) {
                labels.add(format.format(key));
                values.add(map.get(key));
            }
        }

        return new Pair<>(labels, values);
    }
}
