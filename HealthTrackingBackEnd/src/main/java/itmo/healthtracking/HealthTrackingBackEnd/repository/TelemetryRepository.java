package itmo.healthtracking.HealthTrackingBackEnd.repository;

import itmo.healthtracking.HealthTrackingBackEnd.model.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TelemetryRepository  extends JpaRepository<Telemetry,Long> {
    List<Telemetry> findByDevice_ClientName(String clientName);
    Telemetry findTopByDevice_ClientNameOrderByUpdatedAtDesc(String clientName);
}
