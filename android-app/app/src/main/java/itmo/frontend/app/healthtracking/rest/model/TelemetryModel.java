package itmo.frontend.app.healthtracking.rest.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelemetryModel {
    private final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public Date Timestamp;
    public Integer Temperature;
    public Integer Oxygen;
    public Integer HeartRate;
    public Integer Result;

    public TelemetryModel() {}
    public TelemetryModel(JSONObject jsonObject)
    {
        System.out.println(jsonObject);
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        try {
            this.Timestamp = format.parse(jsonObject.getString("createdAt"));
            this.Temperature = getIntValue(jsonObject,"temperature");
            this.Oxygen = getIntValue(jsonObject,"oxygen");
            this.HeartRate = getIntValue(jsonObject,"heartRate");
            this.Result = getIntValue(jsonObject,"temperature");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static TelemetryModel[] getTelemetryByArray(JSONArray jsonArray){
        int length = jsonArray.length();
        TelemetryModel[] array = new TelemetryModel[length];
        for (int i=0; i<length; i++) {
            try {
                array[i] = new TelemetryModel(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return array;
    }

    private Integer getIntValue(JSONObject jsonObject, String fieldName){
        Integer value = null;

        if (jsonObject.has(fieldName)) {
            try {
                value = jsonObject.getInt(fieldName);
            } catch (JSONException ignored) {}
        }

        return value;
    }

    public TelemetryModel(Date timestamp, Integer temperature, Integer oxygen, Integer heartRate, Integer result) {
        Timestamp = timestamp;
        Temperature = temperature;
        Oxygen = oxygen;
        HeartRate = heartRate;
        Result = result;
    }
}
