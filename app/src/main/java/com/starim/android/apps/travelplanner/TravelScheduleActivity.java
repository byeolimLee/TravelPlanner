package com.starim.android.apps.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starim.android.apps.travelplanner.common.DateTimeUtils;
import com.starim.android.apps.travelplanner.db.DatabaseManager;
import com.starim.android.apps.travelplanner.model.TravelItem;
import com.starim.android.apps.travelplanner.model.TravelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TravelScheduleActivity extends Activity {
    TravelList mTravelList;

    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<TravelItem>> mChildList = null;

    private ExpandableListView mListView;

    @OnClick(R.id.add_new_schedule)
    public void setupButtonAdd() {
        Intent intent = new Intent(this, AddTravelScheduleActivity.class);
        intent.putExtra(Constants.keyTravelListId, mTravelList.getId());
        startActivity(intent);
    }

//    @OnClick(R.id.button_add)
//    public void setupButtonAdd() {
//        Intent intent = new Intent(this, AddTravelScheduleActivity.class);
//        intent.putExtra(Constants.keyTravelListId, mTravelList.getId());
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.button_export)
//    public void setupButtonExport() {
//        new ExportDatabaseCSVTask(this, mTravelList.getId()).execute();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_schedule);
        mListView = (ExpandableListView) findViewById(R.id.travel_schedule_list);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.travel_options_menu, menu);
        MenuItem addItem = menu.add(0, R.id.menu_export, 0, R.string.menu_export);
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
            case R.id.menu_export:
                new ExportDatabaseCSVTask(this, mTravelList.getId()).execute();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int travelListId = getIntent().getExtras().getInt(Constants.keyTravelListId);
        mTravelList = DatabaseManager.getInstance().getTravelistWithId(travelListId);
        setTitle("Travel list '"+ mTravelList.getTitle()+"'");
        setupTravelScheduleListView();
    }

    private void setupTravelScheduleListView() {
        if (null != mTravelList) {
            List<TravelItem> travelItemList = mTravelList.getAllTravelItems();
            mGroupList = new ArrayList<String>();
            mChildList = new ArrayList<ArrayList<TravelItem>>();
            ArrayList<TravelItem> childListContent = null;

            TravelItem lastTravelItem = (travelItemList.size() == 0) ? null : travelItemList.get(travelItemList.size() - 1);

            for (TravelItem travelItem : travelItemList) {
                String pattern = getApplicationContext().getResources().getString(R.string.travel_schedule_group_date_pattern);
                String yyyymmdd = DateTimeUtils.convertFormattedTimeFromSystemTime(pattern, Long.parseLong(travelItem.getStartDate()));
//                Log.i("TravelSchedule:: ", "type:" + travelItem.getType() + " title:" +  travelItem.getTitle() + " startDate:" + travelItem.getStartDate() + " endDate:" + travelItem.getEndDate());
//                if (travelItem instanceof TravelItemTransport) {
//                    TravelItemTransport transportItem = (TravelItemTransport)travelItem;
//                    Log.i("TravelSchedule:: ", "name:" + transportItem.getName() + " vehicle:" + transportItem.getVehicle() +
//                            " depCity:" + transportItem.getDepartureCity() + " arrCity:" + transportItem.getArrivalCity() + " duration:" + transportItem.getDuration());
//                }

                if (!mGroupList.contains(yyyymmdd)) {
                    mGroupList.add(yyyymmdd);
                    if (childListContent != null && !childListContent.isEmpty()) {
                        mChildList.add(childListContent);
                    } else {
                        childListContent = new ArrayList<TravelItem>();
                    }
                }
                childListContent.add(travelItem);
                if (travelItem.equals(lastTravelItem)) {
                    // if check last object or not
                    mChildList.add(childListContent);
                }
            }

            mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));

            for (int i=0; i < mListView.getExpandableListAdapter().getGroupCount(); i++) {
                mListView.expandGroup(i);
            }

            // 그룹 클릭 했을 경우 이벤트
            mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
//                    Toast.makeText(getApplicationContext(), "g click = " + groupPosition,
//                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            // 차일드 클릭 했을 경우 이벤트
            mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
//                    Toast.makeText(getApplicationContext(), "c click = " + childPosition,
//                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            // 그룹이 닫힐 경우 이벤트
            mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
//                    Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition,
//                            Toast.LENGTH_SHORT).show();
                }
            });

            // 그룹이 열릴 경우 이벤트
            mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
//                    Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition,
//                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class BaseExpandableAdapter extends BaseExpandableListAdapter {
        private ArrayList<String> groupList = null;
        private ArrayList<ArrayList<TravelItem>> childList = null;
        private LayoutInflater inflater = null;
        private GroupViewHolder groupViewHolder = null;
        private ChildViewHolder childViewHolder = null;

        public BaseExpandableAdapter(Context context, ArrayList<String> groupList,
                                     ArrayList<ArrayList<TravelItem>> childList) {
            super();
            this.inflater = LayoutInflater.from(context);
            this.groupList = groupList;
            this.childList = childList;
        }

        // 그룹 포지션을 반환한다.
        @Override
        public String getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        // 그룹 사이즈를 반환한다.
        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        // 그룹 ID를 반환한다.
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        // 그룹뷰 각각의 ROW
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                groupViewHolder = new GroupViewHolder();
              v = inflater.inflate(R.layout.travel_schedule_list_row_group, parent, false);
                groupViewHolder.groupName = (TextView) v.findViewById(R.id.group_name);
                groupViewHolder.groupExpandIcon = (ImageView) v.findViewById(R.id.group_indicator_expand_icon);
                v.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) v.getTag();
            }

            groupViewHolder.groupExpandIcon.setEnabled(isExpanded);
            groupViewHolder.groupName.setText(getGroup(groupPosition));

            return v;
        }

        // 차일드뷰를 반환한다.
        @Override
        public TravelItem getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        // 차일드뷰 사이즈를 반환한다.
        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        // 차일드뷰 ID를 반환한다.
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        // 차일드뷰 각각의 ROW
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                childViewHolder = new ChildViewHolder();
                v = inflater.inflate(R.layout.travel_schedule_list_row_child, null);
//                childViewHolder.layout_child = (RelativeLayout) v.findViewById(R.id.layout_child);
                childViewHolder.child_category_icon = (ImageView) v.findViewById(R.id.child_category_icon);
                childViewHolder.child_title = (TextView) v.findViewById(R.id.child_title);
                childViewHolder.child_date = (TextView) v.findViewById(R.id.child_date);
                v.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) v.getTag();
            }

            TravelItem travelItem = getChild(groupPosition, childPosition);

            childViewHolder.child_title.setText(travelItem.getTitle());
            String pattern = getApplicationContext().getResources().getString(R.string.travel_schedule_child_time_pattern);
            String startHhmm = DateTimeUtils.convertFormattedTimeFromSystemTime(pattern, Long.parseLong(travelItem.getStartDate()));
            String endHhmm = DateTimeUtils.convertFormattedTimeFromSystemTime(pattern, Long.parseLong(travelItem.getEndDate()));
            childViewHolder.child_date.setText(startHhmm + "~" + endHhmm);

            return v;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class GroupViewHolder {
            public TextView groupName;
            public ImageView groupExpandIcon;
        }

        class ChildViewHolder {
//            public RelativeLayout layout_child;
            public ImageView child_category_icon;
            public TextView child_title;
            public TextView child_date;
        }
    }
}

