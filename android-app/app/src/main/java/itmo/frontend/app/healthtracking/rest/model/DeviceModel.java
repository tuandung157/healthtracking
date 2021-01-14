package itmo.frontend.app.healthtracking.rest.model;

import org.json.JSONObject;

public class DeviceModel {

    public String Name;

    public DeviceModel() {}
    public DeviceModel(JSONObject jsonObject)
    {
        if (jsonObject.has("clientName")) {
            try {
                this.Name = jsonObject.getString("clientName");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
