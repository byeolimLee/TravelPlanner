package com.starim.android.apps.travelplanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.starim.android.apps.travelplanner.db.DatabaseManager;
import com.starim.android.apps.travelplanner.model.TravelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;


public class TravelListActivity extends Activity {
    final Activity activity = this;
    List<TravelList> travelLists;

    @InjectView(R.id.list_view)
    ListView listView;

    @OnClick(R.id.button_add)
    public void setupButton() {
        Intent intent = new Intent(this, AddTravelListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager.init(this);
        setContentView(R.layout.travel_list);

        ButterKnife.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupListView(listView);
    }

    @OnItemClick(R.id.list_view)
    void TravelListItemClick (int position) {
        TravelList travelList = travelLists.get(position);
        Intent intent = new Intent(this, TravelScheduleActivity.class);
        intent.putExtra(Constants.keyTravelListId, travelList.getId());
        startActivity(intent);
    }

    @OnItemLongClick(R.id.list_view)
    boolean TravelListItemLongClick (int position) {
        final TravelList TravelList = travelLists.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TravelList.getTitle())
                .setItems(R.array.travel_item_menu_list, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:
                                Intent intent = new Intent (activity, AddTravelListActivity.class);
                                intent.putExtra(Constants.keyTravelListId, TravelList.getId());
                                startActivity(intent);
                                break;
                            case 1:
                                new AlertDialog.Builder(activity)
                                        .setMessage("Are you sure you would like to delete list '"+TravelList.getTitle()+"'?")
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                DatabaseManager.getInstance().deleteTravelList(TravelList);
                                            }
                                        })
                                        .create()
                                        .show();
                                break;
                        }
                    }
                });
        builder.create().show();
        return true;

    }

    private void setupListView(ListView listView) {
        travelLists = DatabaseManager.getInstance().getAllTravelList();

        List<String> titles = new ArrayList<String>();
        for (TravelList travelList : travelLists) {
            titles.add(travelList.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);
    }

//    private void setupListView(ListView listView) {
//        final List<TravelList> TravelLists = DatabaseManager.getInstance().getAllTravelList();
//
//        List<String> titles = new ArrayList<String>();
//        for (TravelList travelList : TravelLists) {
//            titles.add(travelList.getTitle());
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
//        listView.setAdapter(adapter);
//
//        final Activity activity = this;
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TravelList TravelList = TravelLists.get(position);
//                Intent intent = new Intent(activity, TravelScheduleActivity.class);
//                intent.putExtra(Constants.keyTravelListId, TravelList.getId());
//                startActivity(intent);
//            }
//        });
//
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final TravelList TravelList = TravelLists.get(position);
//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                builder.setTitle(TravelList.getTitle())
//                        .setItems(R.array.travel_item_menu_list, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                switch(which) {
//                                    case 0:
//                                        Intent intent = new Intent (activity, AddTravelListActivity.class);
//                                        intent.putExtra(Constants.keyTravelListId, TravelList.getId());
//                                        startActivity(intent);
//                                        break;
//                                    case 1:
//                                        new AlertDialog.Builder(activity)
//                                                .setMessage("Are you sure you would like to delete list '"+TravelList.getTitle()+"'?")
//                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        dialog.dismiss();
//                                                    }
//                                                })
//                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        dialog.dismiss();
//                                                        DatabaseManager.getInstance().deleteTravelList(TravelList);
//                                                    }
//                                                })
//                                                .create()
//                                                .show();
//                                        break;
//                                }
//                            }
//                        });
//                builder.create().show();
//                return true;
//            }
//        });
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
