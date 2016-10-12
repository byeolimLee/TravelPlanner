package com.starim.android.apps.travelplanner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.starim.android.apps.travelplanner.TravelItemCategory;

/**
 * Created by starim on 14. 7. 2..
 */
@DatabaseTable
public class TravelItemTouristAttraction extends TravelItem {

    @DatabaseField
    private String name;

    @DatabaseField
    private String location;

    @DatabaseField
    private String admissionFee;

    @DatabaseField
    private String admissionHour;

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

    public String getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(String admissionFee) {
        this.admissionFee = admissionFee;
    }

    public String getAdmissionHour() {
        return admissionHour;
    }

    public void setAdmissionHour(String admissionHour) {
        this.admissionHour = admissionHour;
    }

    public String getHowToFind() {
        return howToFind;
    }

    public void setHowToFind(String howToFind) {
        this.howToFind = howToFind;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setupItemDefault(TravelList list, String type, String title, String startDate, String endDate) {
        this.setList(list);
        this.setType(type);
        this.setTitle(title);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public void setupItemSpecific(String name, String location, String admissionFee, String admissionHour, String howToFind, String notes) {
        this.setName(name);
        this.setLocation(location);
        this.setAdmissionFee(admissionFee);
        this.setAdmissionHour(admissionHour);
        this.setHowToFind(howToFind);
        this.setNotes(notes);
    }

    public TravelItemTouristAttraction(TravelList list, String title, String startDate, String endDate, String name, String location, String admissionFee, String admissionHour, String howToFind, String notes) {
        setupItemDefault(list, TravelItemCategory.TYPE_STRING_TOURISTATTRACTIONS, title, startDate, endDate);
        setupItemSpecific(name, location, admissionFee, admissionHour, howToFind, notes);
    }

    public TravelItemTouristAttraction() {}

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

        if (name.equals(((TravelItemTouristAttraction) other).name) == false)
            return false;
        return true;
    }
}
