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
        private BigDecimal totalMoney;
        private BigDecimal generationTotal = BigDecimal.ZERO;
        private BigDecimal chargeTotal = BigDecimal.ZERO;
        private BigDecimal generationTotalMoney = BigDecimal.ZERO;
        private BigDecimal chargeTotalMoney = BigDecimal.ZERO;
        private List<ChargerDetails> details = new ArrayList<>();

        public DetailedSummary(BigDecimal total, GenerationLog log) {
            this.total = total;
            this.totalMoney = log.getEnergyCost();
            this.generationTotal = log.getEnergy();
            this.generationTotalMoney = log.getEnergyCost();
            this.details.add(log);
        }
        public DetailedSummary(BigDecimal total, ChargersLog log) {
            this.total = total;
            this.totalMoney = log.getEnergyCost().multiply(BigDecimal.valueOf(-1));
            this.chargeTotal = log.getEnergy().multiply(BigDecimal.valueOf(-1));
            this.chargeTotalMoney = log.getEnergyCost().multiply(BigDecimal.valueOf(-1));
            this.details.add(log);
        }


        public BigDecimal getTotalMoney() {
            return totalMoney;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        public BigDecimal getGenerationTotalMoney() {
            return generationTotalMoney;
        }

        public BigDecimal getChargeTotalMoney() {
            return chargeTotalMoney;
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
            this.totalMoney = this.totalMoney.add(log.getEnergyCost());
            this.generationTotal =  this.generationTotal.add(log.getEnergy());
            this.generationTotalMoney = log.getEnergyCost();
        }

        public void addDetail(ChargersLog log) {
            this.details.add(log);
            this.total = this.total.add(log.getEnergy().multiply(BigDecimal.valueOf(-1)));
            this.totalMoney = this.totalMoney.add(log.getEnergyCost().multiply(BigDecimal.valueOf(-1)));
            this.chargeTotal =  this.chargeTotal.add(log.getEnergy().multiply(BigDecimal.valueOf(-1)));
            this.chargeTotalMoney = log.getEnergyCost().multiply(BigDecimal.valueOf(-1));
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
        this.total = this.total.add(log.getEnergy().multiply(BigDecimal.valueOf(-1)));
        this.chargeTotal = this.chargeTotal.add(log.getEnergy().multiply(BigDecimal.valueOf(-1)));

        if(detailedSummary == null){
            detailedSummary = new DetailedSummary(log.getEnergy().multiply(BigDecimal.valueOf(-1)),log);
            this.details.put(date, detailedSummary);
            return;
        }
        detailedSummary.addDetail(log);
    }
}
