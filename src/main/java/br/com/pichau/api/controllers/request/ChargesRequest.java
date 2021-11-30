package br.com.pichau.api.controllers.request;

import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;
import br.com.pichau.api.utils.httpClient.Way2API;
import br.com.pichau.api.utils.services.GetKhwPriceService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ChargesRequest {
    private LocalDateTime timestamp;
    private BigDecimal energy;

    public ChargersLog toModel(Way2API api){
        try {
            BigDecimal kwhPrice  = GetKhwPriceService.execute(api,this.timestamp);
            BigDecimal energyCost = kwhPrice.multiply(this.energy);
            return new ChargersLog(this.timestamp,this.energy, energyCost);
        }catch (Exception e){
            return new ChargersLog(this.timestamp,this.energy, null);
        }
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getEnergy() {
        return energy;
    }
}
