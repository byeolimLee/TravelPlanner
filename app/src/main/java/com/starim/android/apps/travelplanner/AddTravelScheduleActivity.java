package com.starim.android.apps.travelplanner;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.starim.android.apps.travelplanner.common.CustomContextMenu;
import com.starim.android.apps.travelplanner.common.CustomMenuItem;
import com.starim.android.apps.travelplanner.common.DatePickerFragment;
import com.starim.android.apps.travelplanner.common.DateTimeUtils;
import com.starim.android.apps.travelplanner.common.TimePickerFragment;
import com.starim.android.apps.travelplanner.db.DatabaseManager;
import com.starim.android.apps.travelplanner.model.TravelList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddTravelScheduleActivity extends FragmentActivity {
    private int mTravelListId;
    private int mTravelItemType = TravelItemCategory.TYPE_ID_NONE;
    private Calendar mStartDate;
    private Calendar mEndDate;

    @InjectView(R.id.schedule_title_edit) EditText mScheduleTitleEdit;
    @InjectView(R.id.startdate_yyyymmdd_edit) TextView mStartDateYYYYMMDDEdit;
    @InjectView(R.id.startdate_hhmm_edit) TextView mStartDateHHMMEdit;
    @InjectView(R.id.enddate_yyyymmdd_edit) TextView mEndDateYYYYMMDDEdit;
    @InjectView(R.id.enddate_hhmm_edit) TextView mEndDateHHMMEdit;

    @OnClick(R.id.select_travel_item)
    void setupCustomMenuItems() {
        List<CustomMenuItem> menus = new ArrayList<CustomMenuItem>();

        menus.add(TRAVEL_ITEM_TRANSPORTS);
        menus.add(TRAVEL_ITEM_ACCOMMODATIONS);
        menus.add(TRAVEL_ITEM_RESTUARANTSNSTORES);
        menus.add(TRAVEL_ITEM_TOURISTATTRACTIONS);

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
                setDateText(mStartDateYYYYMMDDEdit, DateTimeUtils.createGlobalFormatDate(year, monthOfYear, dayOfMonth));
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
                setTimeText(mStartDateHHMMEdit, DateTimeUtils.createGlobalFormatTime(hourOfDay, minute, calender.get(Calendar.AM_PM)));
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
                setDateText(mEndDateYYYYMMDDEdit, DateTimeUtils.createGlobalFormatDate(year, monthOfYear, dayOfMonth));
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
                setTimeText(mEndDateHHMMEdit, DateTimeUtils.createGlobalFormatTime(hourOfDay, minute, calender.get(Calendar.AM_PM)));
            }
        });
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_travel_schedule);

        ButterKnife.inject(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TravelItemTransitionFragment fragment = new TravelItemTransitionFragment();
        transaction.replace(R.id.travel_fragment_container, fragment);
        transaction.commit();
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
        setDateText(mStartDateYYYYMMDDEdit, DateTimeUtils.createGlobalFormatDate(currentYear, currentMonth, currentDay));
        setTimeText(mStartDateHHMMEdit, DateTimeUtils.createGlobalFormatTime(currentHour, currentMinute, currentAmPm));
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
        setDateText(mEndDateYYYYMMDDEdit, DateTimeUtils.createGlobalFormatDate(currentYear, currentMonth, currentDay));
        setTimeText(mEndDateHHMMEdit, DateTimeUtils.createGlobalFormatTime(currentHour, currentMinute, currentAmPm));
    }


    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        if (null!=bundle && bundle.containsKey(Constants.keyTravelListId)) {
            mTravelListId = bundle.getInt(Constants.keyTravelListId);

            TravelList travelList = DatabaseManager.getInstance().getTravelistWithId(mTravelListId);
            setTitle("Travel list '" + travelList.getTitle() + "'");

        }
        setStartDate(Calendar.getInstance());
        setEndDate(Calendar.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.travel_options_menu, menu);

        MenuItem addItem = menu.add(0, R.id.menu_add, 0, R.string.menu_add);
//        addItem.setIcon(R.drawable.ic_action_location);

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
            case R.id.menu_add :
                if (mScheduleTitleEdit.getText().toString().isEmpty() || mTravelItemType == TravelItemCategory.TYPE_ID_NONE) {
                    String message = "should enter schedule title";
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    return false;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public String getScheduleTitle() { return this.mScheduleTitleEdit.getText().toString(); }

    public long getTimeStartDate() {
        return this.mStartDate.getTimeInMillis();
    }

    public long getTimeEndDate() {
        return this.mEndDate.getTimeInMillis();
    }

    private final CustomMenuItem TRAVEL_ITEM_TRANSPORTS = new CustomMenuItem(TravelItemCategory.TYPE_STRING_TRANSPORTS,
            new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_TRANSPORTS;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container, TravelItemTransportFragment.newInstance(mTravelListId))
                    .addToBackStack("transport")
                    .commit();
        }
    });

    private final CustomMenuItem TRAVEL_ITEM_ACCOMMODATIONS = new CustomMenuItem(TravelItemCategory.TYPE_STRING_ACCOMMODATIONS,
            new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_ACCOMMODATIONS;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container, TravelItemAccommodationFragment.newInstance(mTravelListId))
                    .addToBackStack("accommodation")
                    .commit();
        }
    });

    private final CustomMenuItem TRAVEL_ITEM_RESTUARANTSNSTORES  = new CustomMenuItem(TravelItemCategory.TYPE_STRING_RESTUARANTSNSTORES,
            new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_RESTUARANTSNSTORES;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container, TravelItemRestaurantNStoreFragment.newInstance(mTravelListId))
                    .addToBackStack("restuarantNstore")
                    .commit();
        }
    });

    private final CustomMenuItem TRAVEL_ITEM_TOURISTATTRACTIONS = new CustomMenuItem(TravelItemCategory.TYPE_STRING_TOURISTATTRACTIONS,
            new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTravelItemType = TravelItemCategory.TYPE_ID_TOURISTATTRACTIONS;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.travel_fragment_container,
                            TravelItemTouristAttractionFragment.newInstance(mTravelListId))
                    .addToBackStack("touristAttractions")
                    .commit();
        }
    });

}
