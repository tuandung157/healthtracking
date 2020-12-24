package itmo.healthtracking.HealthTrackingBackEnd.controller;

import itmo.healthtracking.HealthTrackingBackEnd.model.Device;
import itmo.healthtracking.HealthTrackingBackEnd.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("devices/{id}")
    public Optional<Device> getDevice(@PathVariable Long id){
        return deviceRepository.findById(id);
    }

    @GetMapping("devices")
    public List<Device> getDevice(){
        System.out.println("find all devices");
//        System.out.println(deviceRepository.findAll().size());
        return deviceRepository.findAll();
    }

}
