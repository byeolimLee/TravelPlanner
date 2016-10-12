package com.starim.android.apps.travelplanner.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.starim.android.apps.travelplanner.model.TravelItemAccommodation;
import com.starim.android.apps.travelplanner.model.TravelItemRestaurantNStore;
import com.starim.android.apps.travelplanner.model.TravelItemTouristAttraction;
import com.starim.android.apps.travelplanner.model.TravelItemTransport;
import com.starim.android.apps.travelplanner.model.TravelList;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "travelplannerDB.sqlite";

	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;
//    private static final int DATABASE_VERSION_2 = 2;
//    private static final int DATABASE_VERSION_3 = 3;
    private static final int CURRENT_DATEBASE_VERSION = DATABASE_VERSION;

	// the DAO object we use to access the SimpleData table
	private Dao<TravelList, Integer> TravelListDao = null;
	private Dao<TravelItemAccommodation, Integer> TravelItemAccommodationDao = null;
    private Dao<TravelItemTransport, Integer> TravelItemTransportDao = null;
    private Dao<TravelItemRestaurantNStore, Integer> TravelItemRestaurantNStoreDao = null;
    private Dao<TravelItemTouristAttraction, Integer> TravelItemTouristAttractionDao = null;

    public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, CURRENT_DATEBASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, TravelList.class);
			TableUtils.createTable(connectionSource, TravelItemAccommodation.class);
            TableUtils.createTable(connectionSource, TravelItemTransport.class);
            TableUtils.createTable(connectionSource, TravelItemRestaurantNStore.class);
            TableUtils.createTable(connectionSource, TravelItemTouristAttraction.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			List<String> allSql = new ArrayList<String>(); 
			switch(oldVersion) 
			{
			  case 1: 
//                  allSql.add("alter table TravelItemTransport add column `startDate` VARCHAR");
//                  allSql.add("alter table TravelItemTransport add column `endDate` VARCHAR");
//                  allSql.add("alter table TravelItemTransport add column `arrivalCity` VARCHAR");
//                  allSql.add("alter table TravelItemTransport add column `departureCity` VARCHAR");
                  break;

                case 2:
//                    allSql.add("alter table TravelItemTransport add column `title` VARCHAR");
                    break;
            }
			for (String sql : allSql) {
				db.execSQL(sql);
			}
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
			throw new RuntimeException(e);
		}
		
	}

	public Dao<TravelList, Integer> getTravelListDao() {
		if (null == TravelListDao) {
			try {
				TravelListDao = getDao(TravelList.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return TravelListDao;
	}

	public Dao<TravelItemTransport, Integer> getTravelItemTransportDao() {
		if (null == TravelItemTransportDao) {
			try {
				TravelItemTransportDao = getDao(TravelItemTransport.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return TravelItemTransportDao;
	}

    public Dao<TravelItemAccommodation, Integer> getTravelItemAccommodationDao() {
        if (null == TravelItemAccommodationDao) {
            try {
                TravelItemAccommodationDao = getDao(TravelItemAccommodation.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return TravelItemAccommodationDao;
    }

    public Dao<TravelItemRestaurantNStore, Integer> getTravelItemRestaurantNStoreDao() {
        if (null == TravelItemRestaurantNStoreDao) {
            try {
                TravelItemRestaurantNStoreDao = getDao(TravelItemRestaurantNStore.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return TravelItemRestaurantNStoreDao;
    }

    public Dao<TravelItemTouristAttraction, Integer> getTravelItemTouristAttractionDao() {
        if (null == TravelItemTouristAttractionDao) {
            try {
                TravelItemTouristAttractionDao = getDao(TravelItemTouristAttraction.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return TravelItemTouristAttractionDao;
    }
}
