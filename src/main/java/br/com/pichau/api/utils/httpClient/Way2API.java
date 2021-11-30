package br.com.pichau.api.utils.httpClient;

import br.com.pichau.api.utils.httpClient.response.FlagsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@FeignClient(name = "transaction" ,url = "${way2.url}")
public interface Way2API {
    @RequestMapping(method = RequestMethod.GET, path = "v1/tarifas")
    public List<Map<String,String>> getEnergyTransaction(@RequestParam String apikey,
                                                         @RequestParam String agente,
                                                         @RequestParam String ano
                                                         );
    @RequestMapping(method = RequestMethod.GET, path = "v1/bandeiras")
    public FlagsResponse getFlag(@RequestParam String apikey,
                                 @RequestParam LocalDate datainicial,
                                 @RequestParam LocalDate datafinal
                                            );
}
