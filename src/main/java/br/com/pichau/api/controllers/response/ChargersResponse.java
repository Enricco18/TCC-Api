package br.com.pichau.api.controllers.response;

import br.com.pichau.api.models.ChargersLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ChargersResponse {
    private UUID id;
    private BigDecimal energy;
    private LocalDateTime timestamp;

    public ChargersResponse(ChargersLog log) {
        this.id = log.getId();
        this.energy = log.getEnergyUsed().multiply(BigDecimal.valueOf(-1));
        this.timestamp = log.getTimestamp();
    }

    @Override
    public String toString() {
        return "ChargersResponse{" +
                "id=" + id +
                ", energyUsed=" + energyUsed +
                ", timestamp=" + timestamp +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getEnergyUsed() {
        return energy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
