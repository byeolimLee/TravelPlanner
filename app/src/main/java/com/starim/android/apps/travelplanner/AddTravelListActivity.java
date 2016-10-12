package com.starim.android.apps.travelplanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.starim.android.apps.travelplanner.db.DatabaseHelper;
import com.starim.android.apps.travelplanner.db.DatabaseManager;
import com.starim.android.apps.travelplanner.model.TravelList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddTravelListActivity extends Activity {
    @InjectView(R.id.title_edit) EditText mTitleEdit;
    @InjectView(R.id.desc_edit) EditText mDescEdit;
    @InjectView(R.id.date_edit) EditText mDateEdit;

    private TravelList mTravelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_travel_list);

        ButterKnife.inject(this);

        setupTravelList();
    }

    private void setupTravelList() {
        Bundle bundle = getIntent().getExtras();
        if (null!=bundle && bundle.containsKey(Constants.keyTravelListId)) {
            int travelListId = bundle.getInt(Constants.keyTravelListId);
            mTravelList = DatabaseManager.getInstance().getTravelistWithId(travelListId);
            mTitleEdit.setText(mTravelList.getTitle());
        }
    }

    @OnClick(R.id.button_save)
    public void setupButton(Button button) {
        String title = mTitleEdit.getText().toString();
        String desc = mDescEdit.getText().toString();
        String date = mDateEdit.getText().toString();

        final Activity activity = this;

        if (null != title && title.length() > 0) {
            if (null != mTravelList) {
                updateTravelList(title, desc, date);
            } else {
                createTravelList(title, desc, date);
            }
            finish();
        } else {
            new AlertDialog.Builder(activity)
                    .setTitle("Error")
                    .setMessage("Invalid travel title!")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void updateTravelList(String title, String desc, String date) {
        if (null!=mTravelList) {
            if (!title.isEmpty())
                mTravelList.setTitle(title);
            if (!desc.isEmpty())
                mTravelList.setDescription(desc);
            if (!date.isEmpty())
                mTravelList.setDate(date);
            DatabaseManager.getInstance().updateTravelList(mTravelList);
        }
    }

    private void createTravelList(String title, String desc, String date) {
        TravelList travelList  = new TravelList();
        travelList.setTitle(title);
        if (!desc.isEmpty())
            travelList.setDescription(desc);
        if (!date.isEmpty())
            travelList.setDate(date);
        DatabaseManager.getInstance().addTravelList(travelList);
    }
}
