package br.com.pichau.api.controllers.request;

import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;
import br.com.pichau.api.utils.httpClient.Way2API;
import br.com.pichau.api.utils.services.GetKhwPriceService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerationRequest {
    private LocalDateTime timestamp;
    private BigDecimal energy;

    public GenerationLog toModel(Way2API api){
        try {
            BigDecimal kwhPrice  = GetKhwPriceService.execute(api,this.timestamp);
            BigDecimal energyCost = kwhPrice.multiply(this.energy);
            return new GenerationLog(this.timestamp,this.energy, energyCost);
        }catch (Exception e){
            return new GenerationLog(this.timestamp,this.energy, null);
        }
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getEnergy() {
         return energy;
    }
}
