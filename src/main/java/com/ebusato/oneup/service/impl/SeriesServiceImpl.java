package com.ebusato.oneup.service.impl;

import com.ebusato.oneup.model.AuthenticationToken;
import com.ebusato.oneup.model.AuthenticationData;
import com.ebusato.oneup.model.Serie;
import com.ebusato.oneup.model.SerieResource;
import com.ebusato.oneup.model.SeriesSearch;
import com.ebusato.oneup.service.SeriesService;
import com.ebusato.oneup.tvdb.TvdbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private TvdbClient client;

    @Override
    public List<Serie> search(String name) {
        AuthenticationToken token = client.authenticate(new AuthenticationData());
        SeriesSearch search = client.search(name, "Bearer "+token.getToken());
        return search.getData();
    }

    @Override
    public Serie search(long id) {
        SerieResource resource = client.findById(id);
        return resource.getData();
    }
}