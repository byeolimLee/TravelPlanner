package com.starim.android.apps.travelplanner.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by starim on 14. 7. 2..
 */

@DatabaseTable
public class TravelList {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String date;

    @ForeignCollectionField
    private ForeignCollection<TravelItem> items;

    public void setId(int id) {this.id = id;}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TravelItem> getItems() {
        ArrayList<TravelItem> itemList = new ArrayList<TravelItem>();
//        if (items != null) {
//            for (TravelItem item : items) {
//                itemList.add(item);
//            }
//        }
        return itemList;
    }
}
