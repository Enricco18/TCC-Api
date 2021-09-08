package br.com.pichau.api.controllers.response;

import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DayBalance implements Comparable<DayBalance>{
    private LocalDate date;
    private BigDecimal balance;

    public DayBalance(LocalDate dateTi, BigDecimal decimal) {
        this.date=dateTi;
        this.balance=decimal;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "DayBalance{" +
                "date=" + date +
                ", balance=" + balance +
                '}';
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public int compareTo(DayBalance o) {
        return date.compareTo(o.date);
    }
}
