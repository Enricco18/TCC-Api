package br.com.pichau.api.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ChargerDetails {
    public LocalDateTime getTimestamp();

    public BigDecimal getEnergy();
}
