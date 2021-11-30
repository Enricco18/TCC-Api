package br.com.pichau.api.utils.services;

import br.com.pichau.api.utils.httpClient.Way2API;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class GetKhwPriceService {
    public static BigDecimal execute(Way2API api, LocalDateTime date) throws Exception{
        BigDecimal kwhPrice = BigDecimal.ZERO;
        var energyPrices = api.getEnergyTransaction("489e8ed7a6b5461e9d98fd973afd54b5","ELETROPAULO", String.valueOf(date.getYear()));
        LocalDate monthBegin = date.withDayOfMonth(1).toLocalDate();
        LocalDate monthEnd = date.plusMonths(1).minusDays(1).toLocalDate();
        var flags = api.getFlag("489e8ed7a6b5461e9d98fd973afd54b5",monthBegin, monthEnd);

        var pricePlan = energyPrices.stream().filter((Map<String,String> x)->{
            return x.get("subgrupo").compareTo("A4")==0 && x.get("modalidade").compareTo("Verde")==0 && x.get("posto").compareTo("P")==0;
        }).collect(Collectors.toList());

        if(pricePlan.size()==0){
            throw new Exception("Grupo n achado");
        }
        BigDecimal consumote = BigDecimal.valueOf(Double.parseDouble(pricePlan.get(0).get("tarifaconsumote")));
        consumote = consumote.divide(BigDecimal.valueOf(1000));
        kwhPrice = kwhPrice.add(consumote);
        if(flags.body.items.size()>0){
            kwhPrice = kwhPrice.add(BigDecimal.valueOf(Double.parseDouble(flags.body.items.get(0).get("value"))));
        }
        return kwhPrice;
    }
}
