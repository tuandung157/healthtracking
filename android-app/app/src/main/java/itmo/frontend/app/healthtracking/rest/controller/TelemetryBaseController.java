package itmo.frontend.app.healthtracking.rest.controller;

import android.content.Context;

import com.androidnetworking.common.ANRequest;

import java.text.SimpleDateFormat;

public class TelemetryBaseController extends BaseController {
    private final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
    private final long dayInMillis = 24*60*60*1000;

    public TelemetryBaseController(Context ctx) { super(ctx); }

    public ANRequest getTodayTelemetry(int size, int page) {
        String device = propertiesManager.getDevice();
        String format = "telemetry/today/client?clientName=%s";
        String path = String.format(format,device);
        return getGetRequestWithAuth(path)
                .build();

    }

    public ANRequest getWeeklyTelemetry(int size, int page) {
        String device = propertiesManager.getDevice();
        String format = "telemetry/week/client?clientName=%s";
        String path = String.format(format,device);

        return getGetRequestWithAuth(path)
                .build();
    }

    public ANRequest getCurrentTelemetry() {
        String device = propertiesManager.getDevice();
        String format = "telemetry/load?clientName=%s";
        String path = String.format(format, device);
        return getGetRequestWithAuth(path)
                .build();
    }
}
