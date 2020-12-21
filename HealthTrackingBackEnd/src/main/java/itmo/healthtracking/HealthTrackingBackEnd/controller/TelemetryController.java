package itmo.healthtracking.HealthTrackingBackEnd.controller;

import itmo.healthtracking.HealthTrackingBackEnd.model.Telemetry;
import itmo.healthtracking.HealthTrackingBackEnd.repository.DeviceRepository;
import itmo.healthtracking.HealthTrackingBackEnd.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class TelemetryController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TelemetryRepository telemetryRepository;

    @GetMapping("/telemetry/load")
    public Telemetry loadCurrentDevice(@RequestParam String clientName){
//find user used device by client name
//        Device device = deviceRepository.findByClientName(clientName);
//        List<Telemetry> listTelemetryUser = telemetryRepository.findByDevice_ClientName(clientName);
        Telemetry telemetry = telemetryRepository.findTopByDevice_ClientNameOrderByUpdatedAtDesc(clientName);
        return telemetry;
    }

    @GetMapping("/telemetry/client")
    public List<Telemetry> loadAllTelemetry(@RequestParam String clientName){
        List<Telemetry> telemetry = telemetryRepository.findByDevice_ClientName(clientName);
        return telemetry;
    }
}
