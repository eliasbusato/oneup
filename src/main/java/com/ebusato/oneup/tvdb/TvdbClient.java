package com.ebusato.oneup.tvdb;

import com.ebusato.oneup.model.AuthenticationToken;
import com.ebusato.oneup.model.AuthenticationData;
import com.ebusato.oneup.model.SerieResource;
import com.ebusato.oneup.model.SeriesSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(value = "tvdb", url = "https://api.thetvdb.com/")
public interface TvdbClient {

    @RequestMapping(method = POST, value = "/login", produces = "application/json")
    AuthenticationToken authenticate(@RequestBody AuthenticationData authenticationData);

    @RequestMapping(method = GET, value = "/search/series", produces = "application/json")
    SeriesSearch search(@RequestParam String name, @RequestHeader("Authorization") String authorization);

    @RequestMapping(method = GET, value = "/series/{id}", produces = "application/json")
    SerieResource findById(@PathVariable("id") Long id);
}