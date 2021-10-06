package br.com.pichau.api.controllers.response;

import br.com.pichau.api.models.ChargerDetails;
import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class BalanceSummary {
    private BigDecimal total= BigDecimal.ZERO;
    private BigDecimal generationTotal = BigDecimal.ZERO;
    private BigDecimal chargeTotal = BigDecimal.ZERO;
    private Map<String,DetailedSummary> details = new TreeMap<>(
            Comparator.comparing(LocalDate::parse)
    );

    public class DetailedSummary{
        private BigDecimal total;
        private BigDecimal generationTotal = BigDecimal.ZERO;
        private BigDecimal chargeTotal = BigDecimal.ZERO;
        private List<ChargerDetails> details = new ArrayList<>();

        public DetailedSummary(BigDecimal total, GenerationLog log) {
            this.total = total;
            this.generationTotal = log.getEnergy();
            this.details.add(log);
        }
        public DetailedSummary(BigDecimal total, ChargersLog log) {
            this.total = total;
            this.chargeTotal = log.getEnergy();
            this.details.add(log);
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        public BigDecimal getTotal() {
            return total;
        }
        public BigDecimal getGenerationTotal(){
            return generationTotal;
        }
    
        public BigDecimal getChargeTotal(){
            return chargeTotal;
        }

        public List<ChargerDetails> getDetails() {
            return details;
        }

        public void addDetail(GenerationLog log) {
            this.details.add(log);
            this.total = this.total.add(log.getEnergy());
            this.generationTotal =  this.generationTotal.add(log.getEnergy());
        }

        public void addDetail(ChargersLog log) {
            this.details.add(log);
            this.total = this.total.add(log.getEnergy());
            this.chargeTotal =  this.chargeTotal.add(log.getEnergy());
        }
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getGenerationTotal(){
        return generationTotal;
    }

    public BigDecimal getChargeTotal(){
        return chargeTotal;
    }

    public Map<String, DetailedSummary> getDetails() {
        return details;
    }

    public void addNewDetail(String date, GenerationLog log) {
        DetailedSummary detailedSummary = this.details.get(date);
        this.total = this.total.add(log.getEnergy());
        this.generationTotal = this.total.add(log.getEnergy());

        if(detailedSummary == null){
            detailedSummary = new DetailedSummary(log.getEnergy(),log);
            this.details.put(date, detailedSummary);
            return;
        }
        detailedSummary.addDetail(log);
    }

    public void addNewDetail(String date, ChargersLog log) {
        DetailedSummary detailedSummary = this.details.get(date);
        this.total = this.total.add(log.getEnergy());
        this.chargeTotal = this.chargeTotal.add(log.getEnergy());

        if(detailedSummary == null){
            detailedSummary = new DetailedSummary(log.getEnergy(),log);
            this.details.put(date, detailedSummary);
            return;
        }
        detailedSummary.addDetail(log);
    }
}
