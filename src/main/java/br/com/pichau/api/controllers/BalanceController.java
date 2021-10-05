package br.com.pichau.api.controllers;

import br.com.pichau.api.controllers.response.BalanceSummary;
import br.com.pichau.api.controllers.response.ChargersResponse;
import br.com.pichau.api.controllers.response.DayBalance;
import br.com.pichau.api.controllers.response.GenerationResponse;
import br.com.pichau.api.repositories.ChargesRepository;
import br.com.pichau.api.repositories.GenerationRepository;
import br.com.pichau.api.utils.enums.FilterBalance;
import br.com.pichau.api.utils.enums.Periodicity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/balance")
public class BalanceController {
    private Logger logger = LoggerFactory.getLogger(BalanceController.class);

    @Autowired
    private ChargesRepository chargesRepository;
    @Autowired
    private GenerationRepository generationRepository;

    @GetMapping
    public ResponseEntity<?> getBalance(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                        @RequestParam("end")   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                        @RequestParam(value = "filter",required = false, defaultValue = "both") FilterBalance filter,
                                        @RequestParam(value = "by",required = false, defaultValue = "none") Periodicity periodicity
                                        ){

        LocalDateTime startTime = LocalDateTime.of(start, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end,LocalTime.MAX);

        List<DayBalance> positiveList = new ArrayList<>();
        List<DayBalance> negativeList = new ArrayList<>();
        List<DayBalance> sumList = new ArrayList<>();
        if(filter != FilterBalance.generation){
            negativeList = chargesRepository.findDayBalance(startTime,endTime).stream().map((dayBalance -> {
                dayBalance.setBalance(dayBalance.getBalance().multiply(BigDecimal.valueOf(-1)));
                return dayBalance;
            })).collect(Collectors.toList());
        }

        if(filter != FilterBalance.charge){
            positiveList= generationRepository.findDayBalance(startTime,endTime);
        }

        sumList.addAll(negativeList);
        sumList.addAll(positiveList);

        BalanceSummary summary = new BalanceSummary();
        for(DayBalance dayBalance:sumList){
            String groupBy = periodicity.getGroupByName(dayBalance.getDate());
            summary.addNewDetail(groupBy,dayBalance);
        }

        return ResponseEntity.ok(summary);
    }
}
