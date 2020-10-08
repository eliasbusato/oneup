package com.ebusato.oneup.controller;

import com.ebusato.oneup.model.Serie;
import com.ebusato.oneup.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SeriesService service;

    @GetMapping("/search")
    public List<Serie> search(@RequestParam String name) {
        return service.search(name);
    }

    @GetMapping("/{id}")
    public Serie get(@PathVariable("id") long id) {
        return service.search(id);
    }
}
