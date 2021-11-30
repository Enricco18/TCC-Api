package br.com.pichau.api.models;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ChargersLog implements ChargerDetails{
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;
    private LocalDateTime timestamp;
    private BigDecimal energyUsed;
    private BigDecimal energyCost;

    private ChargersLog() {
    }

    public ChargersLog(LocalDateTime timestamp, BigDecimal energyUsed,BigDecimal energyCost) {
        this.id = UUID.randomUUID();
        this.timestamp = timestamp;
        this.energyUsed = energyUsed;
        this.energyCost = energyCost;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getEnergy() {
        return energyUsed;
    }

    @Override
    public BigDecimal getEnergyCost() {
        return energyCost;
    }

    public void setEnergyUsed(BigDecimal energyUsed) {
        this.energyUsed = energyUsed;
    }
}
