package com.starim.android.apps.travelplanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.starim.android.apps.travelplanner.db.DatabaseManager;
import com.starim.android.apps.travelplanner.model.TravelItemTransport;
import com.starim.android.apps.travelplanner.model.TravelList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by starim on 14. 7. 15..
 */
public class ExportDatabaseCSVTask extends AsyncTask<Void, Void, Boolean> {

    private Activity mOwnerActivity;
    private int mTravelId;
    private ProgressDialog mProgressDialog;

    public ExportDatabaseCSVTask(Activity activity, int travelId) {
        mOwnerActivity = activity;
        mTravelId = travelId;
        mProgressDialog = new ProgressDialog(mOwnerActivity);
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setMessage("Exporting database...");
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "travelPlanner");
        if (!exportDir.exists()) {
           exportDir.mkdirs();
        }

        File file = new File(exportDir, "travelItem.csv");
        try {
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter((file)), ' ');

            List<TravelItemTransport> list = DatabaseManager.getInstance().getAllTravelItemTransport(mTravelId);

            String attrString[] = {"travelId", "type", "title", "startDate", "endDate", "name", "vehicle", "depCity", "arrCity", "duration" };
            csvWriter.writeNext(attrString);

            if (list != null && list.size() > 0) {
               for (int index = 0; index <list.size(); index++) {
                   TravelItemTransport travelItem = list.get(index);
                   String valueString[] = {String.valueOf(travelItem.getList().getId()), travelItem.getType(), travelItem.getTitle(),
                                           travelItem.getStartDate(), travelItem.getEndDate(), travelItem.getName(), travelItem.getVehicle(),
                                           travelItem.getDepartureCity(), travelItem.getArrivalCity(), String.valueOf(travelItem.getDuration())};
                   csvWriter.writeNext(valueString);
               }
            }
           csvWriter.close();
           return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
