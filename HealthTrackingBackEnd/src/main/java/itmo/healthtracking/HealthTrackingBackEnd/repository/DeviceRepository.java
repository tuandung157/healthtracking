package itmo.healthtracking.HealthTrackingBackEnd.repository;

import itmo.healthtracking.HealthTrackingBackEnd.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    Device findByClientName(String clientName);

    List<Device> findByClientNameStartingWith(String clientName);
}
