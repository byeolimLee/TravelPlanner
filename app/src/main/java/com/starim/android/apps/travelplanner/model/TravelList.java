package com.starim.android.apps.travelplanner.model;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;

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
    private ForeignCollection<TravelItemTransport> transports;

    @ForeignCollectionField
    private ForeignCollection<TravelItemAccommodation> accommodations;

    @ForeignCollectionField
    private ForeignCollection<TravelItemTouristAttraction> touristAttractions;

    @ForeignCollectionField
    private ForeignCollection<TravelItemRestaurantNStore> restaurantNStores;

    private ArrayList<TravelItem> items;

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
        return this.description;
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

    public ForeignCollection<TravelItemTransport> getTransports() {
        return this.transports;
    }

    public ForeignCollection<TravelItemAccommodation> getAccommodations() {
        return this.accommodations;
    }

    public ForeignCollection<TravelItemTouristAttraction> getTouristAttractions() {
        return this.touristAttractions;
    }

    public ForeignCollection<TravelItemRestaurantNStore> getRestaurantNStores() {
        return this.restaurantNStores;
    }

    private int addTravelItemListFromForeignCollection(ForeignCollection<? extends TravelItem> collection, ArrayList<TravelItem> arrayList) {
        CloseableIterator<? extends TravelItem> iterator = null;
        try {
            iterator = collection.closeableIterator();
            while (iterator.hasNext()) {
                TravelItem travelItem = iterator.next();
                arrayList.add(travelItem);
            }
        } finally {
            try {
                iterator.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return -1;
            }
        }

        return 0;
    }

    public ArrayList<TravelItem> getAllTravelItems() {
        ArrayList<TravelItem> travelItemList = new ArrayList<TravelItem>();

        addTravelItemListFromForeignCollection(getTransports(), travelItemList);
        addTravelItemListFromForeignCollection(getAccommodations(), travelItemList);
        addTravelItemListFromForeignCollection(getTouristAttractions(), travelItemList);
        addTravelItemListFromForeignCollection(getRestaurantNStores(), travelItemList);

        return travelItemList;
    }
}
