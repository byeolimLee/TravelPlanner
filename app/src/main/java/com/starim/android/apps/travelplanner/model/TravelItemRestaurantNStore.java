package com.starim.android.apps.travelplanner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.starim.android.apps.travelplanner.TravelItemCategory;

/**
 * Created by starim on 14. 7. 2..
 */
@DatabaseTable
public class TravelItemRestaurantNStore extends TravelItem {

    @DatabaseField
    private String name;

    @DatabaseField
    private String stuff;

    @DatabaseField
    private String price;

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

    public String getStuff() {
        return stuff;
    }

    public void setStuff(String stuff) {
        this.stuff = stuff;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public void setupItemSpecific(String name, String stuff, String price, String howToFind, String notes) {
        this.setName(name);
        this.setStuff(stuff);
        this.setPrice(price);
        this.setHowToFind(howToFind);
        this.setNotes(notes);
    }

    public TravelItemRestaurantNStore(TravelList list, String title, String startDate, String endDate, String name, String stuff, String price, String howToFind, String notes) {
        setupItemDefault(list, TravelItemCategory.TYPE_STRING_RESTUARANTSNSTORES, title, startDate, endDate);
        setupItemSpecific(name, stuff, price, howToFind, notes);
    }

    public TravelItemRestaurantNStore() {}

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

        if (name.equals(((TravelItemRestaurantNStore) other).name) == false)
            return false;
        return true;
    }
}
