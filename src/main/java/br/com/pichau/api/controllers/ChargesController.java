package br.com.pichau.api.controllers;

import br.com.pichau.api.controllers.request.ChargesRequest;
import br.com.pichau.api.controllers.response.ChargersResponse;
import br.com.pichau.api.controllers.response.GenerationResponse;
import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.repositories.ChargesRepository;
import br.com.pichau.api.utils.enums.Periodicity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/charges")
public class ChargesController {
    private Logger logger = LoggerFactory.getLogger(ChargesController.class);

    @Autowired
    private ChargesRepository chargesRepository;

    @GetMapping
    public ResponseEntity<?> getChargers(@RequestParam(value ="start",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                         @RequestParam(value ="end",required = false)   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                         @PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ){
        logger.info("Get charges executing... body:"+ start+ " | "+ end," | "+ pageable);
        if(start==null && end==null){
            Page<ChargersResponse> logs = chargesRepository.findAll(pageable).map(ChargersResponse::new);
            logger.info(logs.getContent().toString());
            return ResponseEntity.ok(logs);
        }

        LocalDateTime startTime= LocalDateTime.MIN ;
        LocalDateTime endTime = LocalDateTime.now();

        if(start!=null){
            startTime =  LocalDateTime.of(start, LocalTime.MIN);
        }

        if(end!=null){
            endTime   = LocalDateTime.of(end, LocalTime.MAX);
        }

        if(     startTime.isAfter(endTime)||
                startTime.isAfter(LocalDateTime.now()) ||
                endTime.isAfter(LocalDateTime.now())
        ){
            logger.error(startTime + "|" + endTime + "|" + LocalDateTime.now());
            return ResponseEntity.badRequest().body("Data not valid!");
        }

        Page<ChargersResponse> logs = chargesRepository.findByTimestampBetween(startTime,endTime,pageable).map(ChargersResponse::new);

        logger.info(logs.getContent().toString());
        return ResponseEntity.ok(logs);
    }

    @PostMapping
    public ResponseEntity<?> postChargers(@RequestBody @Valid ChargesRequest request){
        ChargersLog newCharge = request.toModel();

        chargesRepository.save(newCharge);

        return ResponseEntity.ok(newCharge);
    }
}
