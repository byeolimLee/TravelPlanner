package com.starim.android.apps.travelplanner.model;

import com.j256.ormlite.field.DatabaseField;
import com.starim.android.apps.travelplanner.TravelItemCategory;

import java.io.Serializable;

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

    public String getTitle() {
        return title;
    }

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

    @Override
    public int hashCode() {
        String buffer = this.id + this.type + this.title;
        return buffer.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        if (id != ((TravelItem) other).id)
            return false;
        if (type.equals(((TravelItem) other).type) == false)
            return false;
        if (title.equals(((TravelItem) other).title) == false)
            return false;
        if (startDate.equals(((TravelItem) other).startDate) == false)
            return false;
        if (endDate.equals(((TravelItem) other).endDate) == false)
            return false;
        return true;
    }

}
