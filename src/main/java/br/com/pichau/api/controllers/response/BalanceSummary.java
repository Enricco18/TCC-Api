package br.com.pichau.api.controllers.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class BalanceSummary {
    private BigDecimal total= BigDecimal.ZERO;
    private Map<String,DetailedSummary> details = new TreeMap<>(
            Comparator.comparing(LocalDate::parse)
    );

    public class DetailedSummary{
        private BigDecimal total;
        private List<DayBalance> balances = new ArrayList<>();

        public DetailedSummary(BigDecimal total, DayBalance balance) {
            this.total = total;
            this.balances.add(balance);
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public List<DayBalance> getBalances() {
            return balances;
        }
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Map<String, DetailedSummary> getDetails() {
        return details;
    }

    public void addNewDetail(String date, DayBalance dayBalance) {
        DetailedSummary detailedSummary = this.details.get(date);
        this.total = this.total.add(dayBalance.getBalance());
        if(this.details.get(date)==null){
            detailedSummary = new DetailedSummary(dayBalance.getBalance(),dayBalance);
            this.details.put(date, detailedSummary);
            return;
        }
        detailedSummary.getBalances().add(dayBalance);
        detailedSummary.setTotal(detailedSummary.getTotal().add(dayBalance.getBalance()));
    }
}
