package br.com.pichau.api.controllers.request;

import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GenerationRequest {
    private LocalDateTime timestamp;
    private BigDecimal energy;

    public GenerationLog toModel(){
        return new GenerationLog(this.timestamp,this.energy);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getEnergyUsed() {
        return energy;
    }
}
