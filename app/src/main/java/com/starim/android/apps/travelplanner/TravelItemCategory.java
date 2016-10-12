package com.starim.android.apps.travelplanner;

/**
 * Created by starim on 14. 7. 9..
 */
public class TravelItemCategory {
    public int resourceId;
    public String typeString;
    public int typeId;

    public static final int TYPE_ID_NONE = 0x00;
    public static final int TYPE_ID_TRANSPORTS = 0x01;
    public static final int TYPE_ID_ACCOMMODATIONS = 0x02;
    public static final int TYPE_ID_RESTUARANTSNSTORES = 0x03;
    public static final int TYPE_ID_TOURISTATTRACTIONS = 0x04;

    public static final String TYPE_STRING_TRANSPORTS = "Transports";
    public static final String TYPE_STRING_ACCOMMODATIONS = "Accommodations";
    public static final String TYPE_STRING_RESTUARANTSNSTORES = "Restaurants & Stores";
    public static final String TYPE_STRING_TOURISTATTRACTIONS = "TouristAttractions";

    public TravelItemCategory(int resourceId, String typeString, int typeId) {
        this.resourceId = resourceId;
        this.typeString = typeString;
        this.typeId = typeId;
    }

    public static final TravelItemCategory[] CATEGORIES = {
            new TravelItemCategory(R.drawable.p1, TYPE_STRING_TRANSPORTS, TYPE_ID_TRANSPORTS),
            new TravelItemCategory(R.drawable.p2, TYPE_STRING_ACCOMMODATIONS, TYPE_ID_ACCOMMODATIONS),
            new TravelItemCategory(R.drawable.p3, TYPE_STRING_RESTUARANTSNSTORES, TYPE_ID_RESTUARANTSNSTORES),
            new TravelItemCategory(R.drawable.p4, TYPE_STRING_TOURISTATTRACTIONS, TYPE_ID_TOURISTATTRACTIONS)
    };
}
