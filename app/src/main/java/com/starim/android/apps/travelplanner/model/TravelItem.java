package com.starim.android.apps.travelplanner.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by starim on 14. 7. 3..
 */
public abstract class TravelItem {
    @DatabaseField(generatedId = true)
    protected int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    protected TravelList list;

    @DatabaseField
    protected String type;

    @DatabaseField
    protected String title;

    @DatabaseField
    protected String startDate;

    @DatabaseField
    protected String endDate;

    public TravelList getList() {
        return list;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {return title; }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public abstract void setList(TravelList list);
    public abstract void setType(String type);
    public abstract void setTitle(String title);
    public abstract void setStartDate(String startDate);
    public abstract void setEndDate(String endDate);

    public abstract void setupItemDefault(TravelList list, String type, String title, String startDate, String endDate);
}
