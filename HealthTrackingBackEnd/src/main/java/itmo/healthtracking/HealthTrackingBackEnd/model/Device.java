package itmo.healthtracking.HealthTrackingBackEnd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device extends AuditModel {

    @Id @GeneratedValue
    private Long deviceId;

    private String clientName;
    private String ip;

    public Device(Long deviceId, String clientName, String ip) {
        this.deviceId = deviceId;
        this.clientName = clientName;
        this.ip = ip;
    }

    public Device() {

    }

//    public Device() {
//
//    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
