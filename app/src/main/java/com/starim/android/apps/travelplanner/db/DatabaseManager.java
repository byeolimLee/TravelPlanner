package com.starim.android.apps.travelplanner.db;

import android.content.Context;

import com.j256.ormlite.stmt.QueryBuilder;
import com.starim.android.apps.travelplanner.TravelItemCategory;
import com.starim.android.apps.travelplanner.model.TravelItem;
import com.starim.android.apps.travelplanner.model.TravelItemAccommodation;
import com.starim.android.apps.travelplanner.model.TravelItemRestaurantNStore;
import com.starim.android.apps.travelplanner.model.TravelItemTouristAttraction;
import com.starim.android.apps.travelplanner.model.TravelItemTransport;
import com.starim.android.apps.travelplanner.model.TravelList;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by starim on 14. 7. 2..
 */
public class DatabaseManager {
    static private DatabaseManager instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseHelper helper;
    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }

    public List<TravelList> getAllTravelList() {
        List<TravelList> travelList = null;
        try {
            travelList = getHelper().getTravelListDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelList;
    }

    public void addTravelList(TravelList travelList) {
        try {
            getHelper().getTravelListDao().create(travelList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TravelList getTravelistWithId(int travelListId) {
        TravelList travelList = null;
        try {
            travelList = getHelper().getTravelListDao().queryForId(travelListId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelList;
    }

    public void refreshTravelList(TravelList travelList) {
        try {
            getHelper().getTravelListDao().refresh(travelList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTravelList(TravelList travelList) {
        try {
            getHelper().getTravelListDao().update(travelList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTravelList(TravelList travelList) {
        try {
            getHelper().getTravelListDao().delete(travelList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TravelItemTransport newTravelItemTransport(int travelId, String title, String startDate, String endDate,
                                                    String name, String vehicle, String depCity, String arrCity) {
        TravelItemTransport travelItem = new TravelItemTransport();
        try {
            TravelList travelList = getTravelistWithId(travelId);
            travelItem.setupItemDefault(travelList, TravelItemCategory.TYPE_STRING_TRANSPORTS, title, startDate, endDate);
            travelItem.setupItemSpecific(name, vehicle, depCity, arrCity);
            getHelper().getTravelItemTransportDao().create(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public TravelItemTransport getTravelItemTransportWithId(int travelItemId) {
        TravelItemTransport travelItem = null;
        try {
            travelItem = getHelper().getTravelItemTransportDao().queryForId(travelItemId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public void updateTravelItemTransport(TravelItemTransport travelItem) {
        try {
            getHelper().getTravelItemTransportDao().update(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTravelItemTransport(TravelItemTransport travelItem) {
        try {
            getHelper().getTravelItemTransportDao().delete(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TravelItemTransport> getAllTravelItemTransport(int travelId) {
        List<TravelItemTransport> TravelItems = null;
        try {
            QueryBuilder<TravelList, Integer> travelListQB  = getHelper().getTravelListDao().queryBuilder();
            travelListQB.where().eq("id", travelId);

            QueryBuilder<TravelItemTransport, Integer> travelItemQB = getHelper().getTravelItemTransportDao().queryBuilder();
            TravelItems = travelItemQB.join(travelListQB).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TravelItems;
    }

    public TravelItemAccommodation newTravelItemAccommdation(int travelId, String title, String startDate, String endDate,
                                                            String name, String location, String address, String howTofind) {
        TravelItemAccommodation travelItem = new TravelItemAccommodation();
        try {
            TravelList travelList = getTravelistWithId(travelId);
            travelItem.setupItemDefault(travelList, TravelItemCategory.TYPE_STRING_ACCOMMODATIONS, title, startDate, endDate);
            travelItem.setupItemSpecific(name, location, address, howTofind);
            getHelper().getTravelItemAccommodationDao().create(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public TravelItemAccommodation getTravelItemAccommodationWithId(int travelItemId) {
        TravelItemAccommodation travelItem = null;
        try {
            travelItem = getHelper().getTravelItemAccommodationDao().queryForId(travelItemId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public void updateTravelItemAccommodation(TravelItemAccommodation travelItem) {
        try {
            getHelper().getTravelItemAccommodationDao().update(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTravelItemAccommodation(TravelItemAccommodation travelItem) {
        try {
            getHelper().getTravelItemAccommodationDao().delete(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TravelItemAccommodation> getAllTravelItemAccomodation(int travelId) {
        List<TravelItemAccommodation> TravelItems = null;
        try {
            QueryBuilder<TravelList, Integer> travelListQB = getHelper().getTravelListDao().queryBuilder();
            travelListQB.where().eq("id", travelId);

            QueryBuilder<TravelItemAccommodation, Integer> travelItemQB = getHelper().getTravelItemAccommodationDao().queryBuilder();
            TravelItems = travelItemQB.join(travelListQB).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TravelItems;
    }

    public TravelItemRestaurantNStore newTravelItemRestaurantNStore() {
        TravelItemRestaurantNStore travelItem = new TravelItemRestaurantNStore();
        try {
            getHelper().getTravelItemRestaurantNStoreDao().create(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }
    public TravelItemRestaurantNStore newTravelItemRestaurantNStore(int travelId, String title, String startDate, String endDate,
                                                                    String name, String stuff, String price, String address, String howTofind) {
        TravelItemRestaurantNStore travelItem = new TravelItemRestaurantNStore();
        try {
            TravelList travelList = getTravelistWithId(travelId);
            travelItem.setupItemDefault(travelList, TravelItemCategory.TYPE_STRING_RESTUARANTSNSTORES, title, startDate, endDate);
            travelItem.setupItemSpecific(name, stuff, price, address, howTofind);
            getHelper().getTravelItemRestaurantNStoreDao().create(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public TravelItemRestaurantNStore getTravelItemRestaurantNStoreWithId(int travelItemId) {
        TravelItemRestaurantNStore travelItem = null;
        try {
            travelItem = getHelper().getTravelItemRestaurantNStoreDao().queryForId(travelItemId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public void updateTravelItemRestaurantNStore(TravelItemRestaurantNStore travelItem) {
        try {
            getHelper().getTravelItemRestaurantNStoreDao().update(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTravelItemRestaurantNStore(TravelItemRestaurantNStore travelItem) {
        try {
            getHelper().getTravelItemRestaurantNStoreDao().delete(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TravelItemTouristAttraction newTravelItemTouristAttraction() {
        TravelItemTouristAttraction travelItem = new TravelItemTouristAttraction();
        try {
            getHelper().getTravelItemTouristAttractionDao().create(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public TravelItemTouristAttraction newTravelItemTouristAttraction(int travelId, String title, String startDate, String endDate,
                                                                      String name, String location, String admissionFee, String admissionHour, String address, String howToFind) {
        TravelItemTouristAttraction travelItem = new TravelItemTouristAttraction();
        try {
            TravelList travelList = getTravelistWithId(travelId);
            travelItem.setupItemDefault(travelList, TravelItemCategory.TYPE_STRING_TOURISTATTRACTIONS, title, startDate, endDate);
            travelItem.setupItemSpecific(name, location, admissionFee, admissionHour, address, howToFind);
            getHelper().getTravelItemTouristAttractionDao().create(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public TravelItemTouristAttraction getTravelItemTouristAttractionWithId(int travelItemId) {
        TravelItemTouristAttraction travelItem = null;
        try {
            travelItem = getHelper().getTravelItemTouristAttractionDao().queryForId(travelItemId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travelItem;
    }

    public void updateTravelItemTouristAttraction(TravelItemTouristAttraction travelItem) {
        try {
            getHelper().getTravelItemTouristAttractionDao().update(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTravelItemTouristAttraction(TravelItemTouristAttraction travelItem) {
        try {
            getHelper().getTravelItemTouristAttractionDao().delete(travelItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
