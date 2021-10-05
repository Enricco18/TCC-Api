package br.com.pichau.api.controllers.response;

import br.com.pichau.api.models.GenerationLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class GenerationResponse {
    private UUID id;
    private BigDecimal energy;
    private LocalDateTime timestamp;

    public GenerationResponse(GenerationLog log) {
        this.id = log.getId();
        this.energy = log.getEnergyGenerated();
        this.timestamp = log.getTimestamp();
    }

    @Override
    public String toString() {
        return "GenerationResponse{" +
                "id=" + id +
                ", energy=" + energy +
                ", timestamp=" + timestamp +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getEnergyGenerated() {
        return energy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
