package br.com.pichau.api.controllers.request;

import br.com.pichau.api.models.ChargersLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ChargesRequest {
    private LocalDateTime timestamp;
    private BigDecimal energy;

    public ChargersLog toModel(){
        return new ChargersLog(this.timestamp,this.energy);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getEnergyUsed() {
        return energy;
    }
}
