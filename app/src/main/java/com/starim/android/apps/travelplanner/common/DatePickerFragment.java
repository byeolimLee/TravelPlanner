package com.starim.android.apps.travelplanner.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by starim on 14. 7. 14..
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    OnDateSetListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        Bundle bundle = getArguments();
        Calendar cal = (Calendar)bundle.getSerializable("dateValue");
        if (cal == null) {
            cal = Calendar.getInstance();
        }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        /**
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param monthOfYear The month that was set (0-11) for compatibility
         *  with {@link java.util.Calendar}.
         * @param dayOfMonth The day of the month that was set.
         */
        if (mListener == null) {
            throw new NullPointerException(" must implement setOnValueChangedListener");
        }
        mListener.onDateSetCallBack(year, monthOfYear, dayOfMonth);
    }

    public void setOnValueChangedListener(OnDateSetListener listener) {
        mListener = listener;
    }

    public interface OnDateSetListener {
        public void onDateSetCallBack(int year, int monthOfYear, int dayOfMonth);
    }
}
