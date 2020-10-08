package com.ebusato.oneup.service;

import com.ebusato.oneup.model.Serie;

import java.util.List;

public interface SeriesService {

    List<Serie> search(String name);

    Serie search(long id);
}
