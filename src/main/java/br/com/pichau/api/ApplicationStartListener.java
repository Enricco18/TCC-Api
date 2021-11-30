package br.com.pichau.api;

import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;
import br.com.pichau.api.repositories.ChargesRepository;
import br.com.pichau.api.repositories.GenerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ApplicationStartListener {
    @Autowired
    private ChargesRepository chargesRepository;

    @Autowired
    private GenerationRepository generationRepository;

    @EventListener
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxStartEvt) {
        List<ChargersLog> listCharges = new ArrayList<>();
        List<GenerationLog> listGeneration = new ArrayList<>();

        for(int i=0; i<100; i++){
            long minDay = LocalDateTime.of(2019,1,1,00,00).toEpochSecond(ZoneOffset.ofHours(3));
            long maxDay = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(3));
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDateTime randomDate = LocalDateTime.ofEpochSecond(randomDay,0,ZoneOffset.ofHours(3));

            listCharges.add(new ChargersLog(randomDate, BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 100)),BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 100))));
            listGeneration.add(new GenerationLog(randomDate,BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 100)),BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 100))));

            randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            randomDate = LocalDateTime.ofEpochSecond(randomDay,0,ZoneOffset.ofHours(3));

            listCharges.add(new ChargersLog(randomDate, BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 100)),BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 100))));

        }

        chargesRepository.saveAll(listCharges);
        generationRepository.saveAll(listGeneration);

    }
}