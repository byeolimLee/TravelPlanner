package com.starim.android.apps.travelplanner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.starim.android.apps.travelplanner.TravelItemCategory;

/**
 * Created by starim on 14. 7. 2..
 */
@DatabaseTable
public class TravelItemTransport extends TravelItem {

    @DatabaseField
    private String name;

    @DatabaseField
    private String vehicle;

    @DatabaseField
    private String departureCity;

    @DatabaseField
    private String arrivalCity;

    @DatabaseField
    private int duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setupItemDefault(TravelList list, String type, String title, String startDate, String endDate) {
        this.setList(list);
        this.setType(type);
        this.setTitle(title);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public void setupItemSpecific(String name, String vehicle, String depCity, String arrCity) {
        this.setName(name);
        this.setVehicle(vehicle);
        this.setDepartureCity(depCity);
        this.setArrivalCity(arrCity);
        int duration = (int)(Long.parseLong(this.getEndDate()) - Long.parseLong(this.getStartDate()));
        this.setDuration(duration);
    }

    public TravelItemTransport(TravelList list, String title, String startDate, String endDate, String name, String vehicle, String depCity, String arrCity) {
        setupItemDefault(list, TravelItemCategory.TYPE_STRING_TRANSPORT, title, startDate, endDate);
        setupItemSpecific(name, vehicle, depCity, arrCity);
    }

    public TravelItemTransport() {}

    @Override
    public void setList(TravelList list) {
        this.list = list;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setTitle(String title) { this.title = title; }

    @Override
    public void setStartDate(String startDate) { this.startDate = startDate; }

    @Override
    public void setEndDate(String endDate) { this.endDate = endDate; }

}
