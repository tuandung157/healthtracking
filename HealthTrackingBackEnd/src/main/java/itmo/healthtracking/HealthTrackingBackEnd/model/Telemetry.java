package itmo.healthtracking.HealthTrackingBackEnd.model;

import javax.persistence.*;

@Entity
@Table(name = "telemetry")
public class Telemetry extends AuditModel{
    @Id
    @GeneratedValue
    private Long telemetryId;

    @ManyToOne
    private Device device;

    private Long temperature;

    private Long oxygen;

    private Long heartRate;

    public Telemetry(){

    }
    public Telemetry(Long telemetryId, Device device, Long temperature, Long oxygen, Long heartRate) {
        this.telemetryId = telemetryId;
        this.device = device;
        this.temperature = temperature;
        this.oxygen = oxygen;
        this.heartRate = heartRate;
    }

    public Long getTelemetryId() {
        return telemetryId;
    }

    public void setTelemetryId(Long telemetryId) {
        this.telemetryId = telemetryId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Long getTemperature() {
        return temperature;
    }

    public void setTemperature(Long temperature) {
        this.temperature = temperature;
    }

    public Long getOxygen() {
        return oxygen;
    }

    public void setOxygen(Long oxygen) {
        this.oxygen = oxygen;
    }

    public Long getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Long heartRate) {
        this.heartRate = heartRate;
    }
}
