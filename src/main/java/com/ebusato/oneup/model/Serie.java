package com.ebusato.oneup.model;

import lombok.Data;

import java.util.List;

@Data
public class Serie {
    private long id;
    private String seriesName;
    private String season;
    private String poster;
    private String fanart;
    private String status;
    private String firstAired;
    private String network;
    private List<String> genre;
    private String overview;
    private String banner;
    private double siteRating;
    private long siteRatingCount;
}
