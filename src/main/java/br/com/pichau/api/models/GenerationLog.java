package br.com.pichau.api.models;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class GenerationLog implements ChargerDetails{
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;
    private LocalDateTime timestamp;
    private BigDecimal energyGenerated;
    private BigDecimal energyCost;

    private GenerationLog() {
    }

    public GenerationLog(LocalDateTime timestamp, BigDecimal energyGenerated, BigDecimal energyCost) {
        this.id = UUID.randomUUID();
        this.timestamp = timestamp;
        this.energyGenerated = energyGenerated;
        this.energyCost = energyCost;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getEnergy() {
        return energyGenerated;
    }

    @Override
    public BigDecimal getEnergyCost() {
        return energyCost;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setEnergyGenerated(BigDecimal energyGenerated) {
        this.energyGenerated = energyGenerated;
    }
}
