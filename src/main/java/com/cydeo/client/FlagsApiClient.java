package com.cydeo.client;


import com.cydeo.dto.country_flag.ResponseCountry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "https://restcountries.com",name = "FLAGS-CLIENT")
public interface FlagsApiClient {

    @GetMapping("/v3.1/name/{country}")
    List<ResponseCountry> getFlagByCountry(@PathVariable("country") String country);
}
