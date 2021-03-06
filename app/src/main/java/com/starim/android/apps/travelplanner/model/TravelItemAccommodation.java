package com.starim.android.apps.travelplanner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.starim.android.apps.travelplanner.TravelItemCategory;

/**
 * Created by starim on 14. 7. 2..
 */

@DatabaseTable
public class TravelItemAccommodation extends TravelItem {

    @DatabaseField
    private String name;

    @DatabaseField
    private String location;

    @DatabaseField
    private String howToFind;

    @DatabaseField
    private String notes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHowToFind() {
        return howToFind;
    }

    public void setHowToFind(String howToFind) {
        this.howToFind = howToFind;
    }

    public void setupItemDefault(TravelList list, String type, String title, String startDate, String endDate) {
        this.setList(list);
        this.setType(type);
        this.setTitle(title);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public void setupItemSpecific(String name, String location, String howToFind, String notes) {
        this.setName(name);
        this.setLocation(location);
        this.setHowToFind(howToFind);
        this.setNotes(notes);
    }

    public TravelItemAccommodation(TravelList list, String title, String startDate, String endDate, String name, String location, String howToFind, String notes) {
        setupItemDefault(list, TravelItemCategory.TYPE_STRING_ACCOMMODATIONS, title, startDate, endDate);
        setupItemSpecific(name, location, howToFind, notes);
    }

    public TravelItemAccommodation() {}

    @Override
    public void setList(TravelList travelList) {this.list = travelList; }

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

    @Override
    public int hashCode() {
        String buffer = super.hashCode() + this.name;
        return buffer.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other) == false)
            return false;

        if (name.equals(((TravelItemAccommodation) other).name) == false)
            return false;
        return true;
    }
}
