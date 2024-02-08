package com.dnd.gooding.record.command.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Coordinate {

    @Column(name = "place_title")
    private String placeTitle;

    @Column(name = "place_latitude")
    private Double placeLatitude;

    @Column(name = "place_longitude")
    private Double placeLongitude;

    protected Coordinate() {}

    public Coordinate(String placeTitle, Double placeLatitude, Double placeLongitude) {
        this.placeTitle = placeTitle;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public Double getPlaceLatitude() {
        return placeLatitude;
    }

    public Double getPlaceLongitude() {
        return placeLongitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(placeTitle, that.placeTitle)
                && Objects.equals(placeLatitude, that.placeLatitude)
                && Objects.equals(placeLongitude, that.placeLongitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeTitle, placeLatitude, placeLongitude);
    }
}
