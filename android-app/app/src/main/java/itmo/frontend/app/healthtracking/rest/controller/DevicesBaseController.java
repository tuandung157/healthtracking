package itmo.frontend.app.healthtracking.rest.controller;

import android.content.Context;

import com.androidnetworking.common.ANRequest;

public class DevicesBaseController extends BaseController {
    public DevicesBaseController(Context ctx) {
        super(ctx);
    }

    public ANRequest getDevices() {
        String path = "devices";
        return getGetRequestWithAuth(path)
                .build();
    }
}
