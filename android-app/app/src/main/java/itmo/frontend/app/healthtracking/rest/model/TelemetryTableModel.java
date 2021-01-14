package itmo.frontend.app.healthtracking.rest.model;

import itmo.frontend.app.healthtracking.android.graphicModule.FragmentType;

public class TelemetryTableModel {
    public TelemetryGraphModel TodayViewModel;
    public TelemetryGraphModel WeekViewModel;
    public TelemetryGraphModel MonthViewModel;

    public TelemetryTableModel()
    {
        TodayViewModel = new TelemetryGraphModel(FragmentType.TODAY);
        WeekViewModel = new TelemetryGraphModel(FragmentType.WEEK);
        MonthViewModel = new TelemetryGraphModel(FragmentType.MONTH);
    }
}
