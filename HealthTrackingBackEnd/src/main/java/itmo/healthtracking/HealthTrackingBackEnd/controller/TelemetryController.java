package itmo.healthtracking.HealthTrackingBackEnd.controller;

import itmo.healthtracking.HealthTrackingBackEnd.model.Telemetry;
import itmo.healthtracking.HealthTrackingBackEnd.repository.DeviceRepository;
import itmo.healthtracking.HealthTrackingBackEnd.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
        System.out.println(clientName);
        System.out.println(" get client name from telemetry "+telemetry.getDevice().getClientName());
        return telemetry;
    }

    @GetMapping("/telemetry/client")
    public List<Telemetry> loadAllTelemetry(@RequestParam String clientName){
        List<Telemetry> telemetry = telemetryRepository.findByDevice_ClientName(clientName);
        return telemetry;
    }

    @GetMapping("/telemetry/week/client")
    public List<Telemetry> loadTelemetryForWeek(@RequestParam String clientName){

        LocalDateTime ldt = LocalDateTime.now().minusDays(7);
        Date dateNow = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        List<Telemetry> telemetry = telemetryRepository.findByDevice_ClientNameAndCreatedAtAfterOrderByCreatedAtDesc(clientName, dateNow);
        return telemetry;
    }

    @GetMapping("/telemetry/today/client")
    public List<Telemetry> loadTelemetryForDay(@RequestParam String clientName){
//        Date dateAfter = new Date();
//        List<Telemetry> telemetry = telemetryRepository.findByDevice_ClientNameAndCreatedAtAfter(clientName, dateAfter);

        LocalDateTime ldt = LocalDateTime.now().minusDays(1);
        Date dateNow = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("check run "+dateNow);
        List<Telemetry> telemetry = telemetryRepository.findByDevice_ClientNameAndCreatedAtAfterOrderByCreatedAtDesc(clientName, dateNow);


        return telemetry;
    }
}
