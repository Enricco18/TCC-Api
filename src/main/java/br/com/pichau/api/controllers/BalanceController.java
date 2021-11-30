package br.com.pichau.api.controllers;

import br.com.pichau.api.controllers.response.BalanceSummary;
import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;
import br.com.pichau.api.repositories.ChargesRepository;
import br.com.pichau.api.repositories.GenerationRepository;
import br.com.pichau.api.utils.enums.FilterBalance;
import br.com.pichau.api.utils.enums.Periodicity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("/balance")
public class BalanceController {
    private Logger logger = LoggerFactory.getLogger(BalanceController.class);

    @Autowired
    private ChargesRepository chargesRepository;
    @Autowired
    private GenerationRepository generationRepository;

    //Tirar depois do eureka
    private Double energyPrice = 2.6;

    @GetMapping
    public ResponseEntity<?> getBalance(@RequestParam(value = "start",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                        @RequestParam(value ="end",required = false)   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                        @RequestParam(value = "by",required = false, defaultValue = "none") Periodicity periodicity
                                        ){

        LocalDateTime startTime;
        LocalDateTime endTime;
        if(start==null){
             startTime = LocalDateTime.of(LocalDate.MIN,LocalTime.MAX);
        }else{
            startTime =  LocalDateTime.of(start, LocalTime.MIN);
        }

        if(end==null){
            endTime= LocalDateTime.of(LocalDate.now(),LocalTime.MAX);
        }else {
            endTime   = LocalDateTime.of(end, LocalTime.MAX);
        }

        if(     startTime.isAfter(endTime)||
                startTime.isAfter(LocalDateTime.now()) ||
                endTime.isAfter(LocalDateTime.of(LocalDate.now(),LocalTime.MAX))
        ){
            logger.error(startTime + "|" + endTime + "|" + LocalDateTime.now());
            return ResponseEntity.badRequest().body("Data not valid!");
        }

        List<GenerationLog> positiveList;
        List<ChargersLog> negativeList;
        negativeList = chargesRepository.findByTimestampBetween(startTime,endTime,Sort.by(Sort.Direction.DESC,"timestamp"));
        positiveList= generationRepository.findByTimestampBetween(startTime,endTime, Sort.by(Sort.Direction.DESC,"timestamp"));

        BalanceSummary summary = new BalanceSummary();
        for(GenerationLog generations:positiveList){
            String groupBy = periodicity.getGroupByName(generations.getTimestamp().toLocalDate());
            summary.addNewDetail(groupBy,generations);
        }
        for(ChargersLog chargersLog:negativeList){
            String groupBy = periodicity.getGroupByName(chargersLog.getTimestamp().toLocalDate());
            summary.addNewDetail(groupBy,chargersLog);
        }
        return ResponseEntity.ok(summary);
    }
}
