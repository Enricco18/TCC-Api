package br.com.pichau.api.utils.enums;

import br.com.pichau.api.controllers.response.BalanceSummary;
import br.com.pichau.api.controllers.response.DayBalance;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public enum Periodicity {
    day{
        @Override
        public String getGroupByName(LocalDate date) {
            return date.toString();
        }
    },
    week {
        @Override
        public String getGroupByName(LocalDate date) {
            return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).toString();
        }
    },
    month {
        @Override
        public String getGroupByName(LocalDate date) {
            return date.getYear()+"-"+String.format("%02d" , date.getMonthValue())+"-01";
        }
    },
    year {
        @Override
        public String getGroupByName(LocalDate date) {
            return  date.getYear()+"-"+"01"+"-01";
        }
    },
    none {
        @Override
        public String getGroupByName(LocalDate date) {
            return date.toString();
        }
    };

    public abstract String getGroupByName(LocalDate date);

}
