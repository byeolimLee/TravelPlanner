package com.starim.android.apps.travelplanner;

/**
 * Created by starim on 14. 7. 9..
 */
public class TravelItemCategory {
    public int resourceId;
    public String typeString;
    public int typeId;

    public static final int TYPE_ID_TRANSPORT = 0x01;
    public static final int TYPE_ID_ACCOMMODATION = 0x02;
    public static final int TYPE_ID_SHOPPINGPLACES = 0x03;
    public static final int TYPE_ID_RESTUARANT = 0x04;
    public static final int TYPE_ID_TOURISTPLACES = 0x05;

    public static final String TYPE_STRING_TRANSPORT = "Transport";
    public static final String TYPE_STRING_ACCOMMODATION = "Accommodation";
    public static final String TYPE_STRING_SHOPPINGPLACES = "ShoppingPlaces";
    public static final String TYPE_STRING_RESTUARANT = "Restaurant";
    public static final String TYPE_STRING_TOURISTPLACES = "TouristPlaces";

    public TravelItemCategory(int resourceId, String typeString, int typeId) {
        this.resourceId = resourceId;
        this.typeString = typeString;
        this.typeId = typeId;
    }

    public static final TravelItemCategory[] CATEGORIES = {
            new TravelItemCategory(R.drawable.p1, TYPE_STRING_TRANSPORT, TYPE_ID_TRANSPORT),
            new TravelItemCategory(R.drawable.p2, TYPE_STRING_ACCOMMODATION, TYPE_ID_ACCOMMODATION),
            new TravelItemCategory(R.drawable.p3, TYPE_STRING_SHOPPINGPLACES, TYPE_ID_SHOPPINGPLACES),
            new TravelItemCategory(R.drawable.p4, TYPE_STRING_RESTUARANT, TYPE_ID_RESTUARANT),
            new TravelItemCategory(R.drawable.p5, TYPE_STRING_TOURISTPLACES, TYPE_ID_TOURISTPLACES),
    };
}
