package itmo.frontend.app.healthtracking.android.graphicModule.devices;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import com.androidnetworking.common.ANRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import itmo.frontend.app.healthtracking.android.DataFragment;
import itmo.frontend.app.healthtracking.android.R;
import itmo.frontend.app.healthtracking.rest.controller.DevicesBaseController;
import itmo.frontend.app.healthtracking.rest.model.DeviceModel;
import itmo.frontend.app.healthtracking.util.PropertiesManager;

public class DevicesFragment extends DataFragment {
    private DevicesBaseController devicesController;
    private PropertiesManager propertiesManager;
    private ArrayList<DeviceModel> deviceModels;
    private View root;

    @SuppressLint("NonConstantResourceId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_devices, container, false);

        propertiesManager = new PropertiesManager(getContext());
        devicesController = new DevicesBaseController(getContext());
        deviceModels = new ArrayList<>();

        //requests to get data from server
        loadData();

        return root;
    }

    //example of saving device value
    private void SaveChosenDevice(String deviceName){
        propertiesManager.saveDevice(deviceName);
    }

    @Override
    protected void parseResponseObject(JSONObject response) {

    }

    @Override
    protected void parseResponse(JSONArray response) {
        System.out.println(" check response "+response);

        for(int i=0;i<response.length();i++){
            try {
                deviceModels.add(new DeviceModel(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected ANRequest getRequest() {
        return devicesController.getDevices();
    }

    @Override
    protected void unauthorizedResponse() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_devices_to_loginActivity);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onGetDataSuccess() {

        System.out.println("run function onGetDataSuccess");
        //this method is called when all data were received from sever and saved in deviceModels
        RadioGroup radioGroup = root.findViewById(R.id.radio_group);

        Map<Integer, String> devicesNames = new HashMap<>();
        Map<Integer, RadioButton> devices = new HashMap<>();

        PropertiesManager propertiesManager = new PropertiesManager(getContext());

        String chosenName = propertiesManager.getDevice();

        for (int i = 0; i < deviceModels.size(); i++) {

            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(i);
            radioButton.setText(deviceModels.get(i).Name);

            if (chosenName != null && chosenName.equals(deviceModels.get(i).Name)){
                radioButton.setChecked(true);
            }

            int id = radioButton.getId();
            devicesNames.put(id, deviceModels.get(i).Name);
            devices.put(id, radioButton);

            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            SaveChosenDevice(devicesNames.get(checkedId));
            Objects.requireNonNull(devices.get(checkedId)).setChecked(true);

        });

    }

    @Override
    protected void onGetDataFailed(String message) {
        //this method is called when error response received from server
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}