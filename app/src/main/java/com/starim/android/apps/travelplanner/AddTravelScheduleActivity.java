package com.starim.android.apps.travelplanner;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.starim.android.apps.travelplanner.common.CustomContextMenu;
import com.starim.android.apps.travelplanner.common.CustomMenuItem;
import com.starim.android.apps.travelplanner.common.DatePickerFragment;
import com.starim.android.apps.travelplanner.common.DateTimeUtils;
import com.starim.android.apps.travelplanner.common.TimePickerFragment;
import com.starim.android.apps.travelplanner.db.DatabaseManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddTravelScheduleActivity extends FragmentActivity {
    int mTravelListId;
    int mTravelItemType;
    Calendar mStartDate;
    Calendar mEndDate;

    @InjectView(R.id.startdate_yyyymmdd_edit) TextView startDateYYYYMMDDEdit;
    @InjectView(R.id.startdate_hhmm_edit) TextView startDateHHMMEdit;
    @InjectView(R.id.enddate_yyyymmdd_edit) TextView endDateYYYYMMDDEdit;
    @InjectView(R.id.enddate_hhmm_edit) TextView endDateHHMMEdit;

    @OnClick(R.id.select_travel_item)
    void setupCustomMenuItems() {
        List<CustomMenuItem> menus = new ArrayList<CustomMenuItem>();

        menus.add(TRAVEL_ITEM_TRANSPORT);
        menus.add(TRAVEL_ITEM_ACCOMMODATION);
        menus.add(TRAVEL_ITEM_SHOPPINGPLACES);
        menus.add(TRAVEL_ITEM_RESTUARANT);
        menus.add(TRAVEL_ITEM_TOURISTPLACES);

        CustomContextMenu.show(AddTravelScheduleActivity.this, getResources().getString(R.string.title_travel_category_select), menus);
    }

    @OnClick(R.id.startdate_yyyymmdd_edit)
    void setupStartDateOnClick() {
        DatePickerFragment dateFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dateValue", mStartDate);
        dateFragment.setArguments(bundle);
        dateFragment.setOnValueChangedListener(new DatePickerFragment.OnDateSetListener() {
            @Override
            public void onDateSetCallBack(int year, int monthOfYear, int dayOfMonth) {
                Calendar calender = Calendar.getInstance();
                calender.set(year, monthOfYear, dayOfMonth);

                mStartDate = calender;
                setDateText(startDateYYYYMMDDEdit, DateTimeUtils.createGlobalFormatDate(year, monthOfYear, dayOfMonth));
            }
        });
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @OnClick(R.id.startdate_hhmm_edit)
    void setupStartTimeOnClick() {
        TimePickerFragment timeFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("timeValue", mStartDate);
        timeFragment.setArguments(bundle);
        timeFragment.setOnValueChangedListener(new TimePickerFragment.OnTimeSetListener() {
            @Override
            public void onTimeSetCallBack(int hourOfDay, int minute) {
                Calendar calender = Calendar.getInstance();
                calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calender.set(Calendar.MINUTE, minute);

                mStartDate = calender;
                setTimeText(startDateHHMMEdit, DateTimeUtils.createGlobalFormatTime(hourOfDay, minute, calender.get(Calendar.AM_PM)));
            }
        });
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @OnClick(R.id.enddate_yyyymmdd_edit)
    void setupEndDateOnClick() {
        DatePickerFragment dateFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dateValue", mEndDate);
        dateFragment.setArguments(bundle);
        dateFragment.setOnValueChangedListener(new DatePickerFragment.OnDateSetListener() {
            @Override
            public void onDateSetCallBack(int year, int monthOfYear, int dayOfMonth) {
                Calendar calender = Calendar.getInstance();
                calender.set(year, monthOfYear, dayOfMonth);

                mEndDate = calender;
                setDateText(endDateYYYYMMDDEdit, DateTimeUtils.createGlobalFormatDate(year, monthOfYear, dayOfMonth));
            }
        });
        dateFragment.show(getSupportFragmentManager(), "datePicker");

    }

    @OnClick(R.id.enddate_hhmm_edit)
    void setupEndTimeOnClick() {
        TimePickerFragment timeFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("timeValue", mEndDate);
        timeFragment.setArguments(bundle);
        timeFragment.setOnValueChangedListener(new TimePickerFragment.OnTimeSetListener() {
            @Override
            public void onTimeSetCallBack(int hourOfDay, int minute) {
                Calendar calender = Calendar.getInstance();
                calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calender.set(Calendar.MINUTE, minute);

                mEndDate = calender;
                setTimeText(endDateHHMMEdit, DateTimeUtils.createGlobalFormatTime(hourOfDay, minute, calender.get(Calendar.AM_PM)));
            }
        });
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_travel_schedule);

        Bundle bundle = getIntent().getExtras();
        if (null!=bundle && bundle.containsKey(Constants.keyTravelListId)) {
            mTravelListId = bundle.getInt(Constants.keyTravelListId);
        }

        ButterKnife.inject(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TravelItemTransitionFragment fragment = new TravelItemTransitionFragment();
        transaction.replace(R.id.travel_fragment_container, fragment);
        transaction.commit();

        setStartDate(Calendar.getInstance());
        setEndDate(Calendar.getInstance());
    }

    private void setDateText(TextView textView, String date) {
        String param[] = date.split("\\.");
        if (param.length == 3) {
            if (param[0] != null && param[1] != null && param[2] != null) {
                String formatedDay = getResources().getString(R.string.travel_item_date_format, param[0], param[1], param[2]);
                textView.setText(formatedDay);
            }
        } else {
            textView.setText(date);
        }
    }

    private void setTimeText(TextView textView, String time) {
        String param[] = time.split("\\.");
        if (param.length == 3) {
            if (param[0] != null && param[1] != null && param[2] != null) {
//              String formatedTime = getResources().getString(R.string.travel_item_time_format, param[0], param[1]);
                String formatedTime = String.format("%s:%s %s", param[0], param[1], param[2]);
                textView.setText(formatedTime);
            }
        } else {
            textView.setText(time);
        }
    }

    private void setStartDate(Calendar calendar) {
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentAmPm = calendar.get(Calendar.AM_PM);

        mStartDate = calendar;
        setDateText(startDateYYYYMMDDEdit, DateTimeUtils.createGlobalFormatDate(currentYear, currentMonth, currentDay));
        setTimeText(startDateHHMMEdit, DateTimeUtils.createGlobalFormatTime(currentHour, currentMinute, currentAmPm));
    }

    private void setEndDate(Calendar calendar) {
        long miliSec = calendar.getTimeInMillis() + (3600 * 1000L); // add 1 hour
        calendar.setTimeInMillis(miliSec);

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentAmPm = calendar.get(Calendar.AM_PM);

        mEndDate = calendar;
        setDateText(endDateYYYYMMDDEdit, DateTimeUtils.createGlobalFormatDate(currentYear, currentMonth, currentDay));
        setTimeText(endDateHHMMEdit, DateTimeUtils.createGlobalFormatTime(currentHour, currentMinute, currentAmPm));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.travel_options_menu, menu);
        MenuItem addItem = menu.add(0, R.id.menu_add, 0, R.string.menu_add);
        addItem.setIcon(R.drawable.ic_action_location);

        // Need to use MenuItemCompat methods to call any action item related methods
        MenuItemCompat.setShowAsAction(addItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.menu_refresh:
                // Here we might start a background refresh task
                return true;

            case R.id.menu_settings:
                // Here we would open up our settings activity
                return true;

            case R.id.menu_add:
                switch(mTravelItemType) {
                    case TravelItemCategory.TYPE_ID_TRANSPORT:
                        DatabaseManager.getInstance().newTravelItemTransport(mTravelListId, "test",
                                                    String.valueOf(mStartDate.getTimeInMillis()), String.valueOf(mEndDate.getTimeInMillis()),
                                                    "OZ551", "flight", "Seoul", "Istanbul");
                        break;
                    case TravelItemCategory.TYPE_ID_ACCOMMODATION:
                        break;
                    case TravelItemCategory.TYPE_ID_SHOPPINGPLACES:
                        break;
                    case TravelItemCategory.TYPE_ID_RESTUARANT:
                        break;
                    case TravelItemCategory.TYPE_ID_TOURISTPLACES:
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final CustomMenuItem TRAVEL_ITEM_TRANSPORT = new CustomMenuItem(TravelItemCategory.TYPE_STRING_TRANSPORT, R.drawable.p1, new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_TRANSPORT;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container,
                            TravelItemTransportFragment.newInstance((int) view.getX(), (int) view.getY(),
                                                                   view.getWidth(), view.getHeight())
                    )
                    // We push the fragment transaction to back stack. User can go back to the
                    // previous fragment by pressing back button.
                    .addToBackStack("detail")
                    .commit();
        }
    });

    private final CustomMenuItem TRAVEL_ITEM_ACCOMMODATION = new CustomMenuItem(TravelItemCategory.TYPE_STRING_ACCOMMODATION, new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_ACCOMMODATION;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container,
                            TravelItemAccommodationFragment.newInstance((int) view.getX(), (int) view.getY(),
                                    view.getWidth(), view.getHeight())
                    )
                    // We push the fragment transaction to back stack. User can go back to the
                    // previous fragment by pressing back button.
                    .addToBackStack("detail")
                    .commit();
        }
    });

    private final CustomMenuItem TRAVEL_ITEM_SHOPPINGPLACES = new CustomMenuItem(TravelItemCategory.TYPE_STRING_SHOPPINGPLACES, new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_SHOPPINGPLACES;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container,
                            TravelItemShoppingPlacesFragment.newInstance((int) view.getX(), (int) view.getY(),
                                    view.getWidth(), view.getHeight())
                    )
                    // We push the fragment transaction to back stack. User can go back to the
                    // previous fragment by pressing back button.
                    .addToBackStack("detail")
                    .commit();
        }
    });

    private final CustomMenuItem TRAVEL_ITEM_RESTUARANT  = new CustomMenuItem(TravelItemCategory.TYPE_STRING_RESTUARANT, new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_RESTUARANT;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container,
                            TravelItemRestaurantFragment.newInstance((int) view.getX(), (int) view.getY(),
                                    view.getWidth(), view.getHeight())
                    )
                    // We push the fragment transaction to back stack. User can go back to the
                    // previous fragment by pressing back button.
                    .addToBackStack("detail")
                    .commit();
        }
    });

    private final CustomMenuItem TRAVEL_ITEM_TOURISTPLACES = new CustomMenuItem(TravelItemCategory.TYPE_STRING_TOURISTPLACES, new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_TOURISTPLACES;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container,
                            TravelItemTourlistPlacesFragment.newInstance((int) view.getX(), (int) view.getY(),
                                    view.getWidth(), view.getHeight())
                    )
                    // We push the fragment transaction to back stack. User can go back to the
                    // previous fragment by pressing back button.
                    .addToBackStack("detail")
                    .commit();
        }
    });

}
