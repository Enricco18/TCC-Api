package br.com.pichau.api.controllers;

import br.com.pichau.api.controllers.request.ChargesRequest;
import br.com.pichau.api.controllers.request.GenerationRequest;
import br.com.pichau.api.controllers.response.ChargersResponse;
import br.com.pichau.api.controllers.response.GenerationResponse;
import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;
import br.com.pichau.api.repositories.GenerationRepository;
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
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/generations")
public class GenerationController {
    private Logger logger = LoggerFactory.getLogger(GenerationController.class);

    @Autowired
    private GenerationRepository generationRepository;

    @GetMapping
    public ResponseEntity<?> getGenerations(@RequestParam(value ="start",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                            @RequestParam(value ="end",required = false)   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                            @PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC, size = 20) Pageable pageable)
    {

        if(start==null && end==null){
            Page<GenerationResponse> logs = generationRepository.findAll(pageable).map(GenerationResponse::new);
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
            logger.error(startTime.toString() + "|" + endTime.toString() + "|" + LocalDateTime.now());
            return ResponseEntity.badRequest().body("Date are not valid");
        }

        Page<GenerationResponse> logs = generationRepository.findByTimestampBetween(startTime,endTime,pageable).map(GenerationResponse::new);

        return ResponseEntity.ok(logs);
    }

    @PostMapping
    public ResponseEntity<?> postGeneration(@RequestBody @Valid GenerationRequest request){
        GenerationLog newCharge = request.toModel();

        generationRepository.save(newCharge);

        return ResponseEntity.ok(newCharge);
    }

}
