package itmo.healthtracking.HealthTrackingBackEnd.listener;

import itmo.healthtracking.HealthTrackingBackEnd.model.AuditModel;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;

@Configurable
public class CreatedUpdatedListener {
    @javax.persistence.PrePersist
    public void touchForCreate(AuditModel target) {
        Date now = new Date();
        target.setCreatedAt(now);
        target.setUpdatedAt(now);
    }

    @javax.persistence.PreUpdate
    public void touchForUpdate(AuditModel target) {
        target.setUpdatedAt(new Date());
    }
}
