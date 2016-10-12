package com.starim.android.apps.travelplanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.starim.android.apps.travelplanner.db.DatabaseManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TravelItemTransportFragment extends Fragment implements Animation.AnimationListener  {

    private static final String TAG = "TravelItemTransportFragment";
    private static final String ARG_TRAVEL_LIST_ID = "travelListId";

    @InjectView(R.id.travelitem_name_edit) EditText mTravelItemNameEdit;
    @InjectView(R.id.travelitem_from_edit) EditText mTravelItemFromEdit;
    @InjectView(R.id.travelitem_to_edit) EditText mTravelItemToEdit;
    @InjectView(R.id.travelitem_notes_edit) TextView mTravelItemNoteEdit;

    public static TravelItemTransportFragment newInstance(int travelListId) {
        TravelItemTransportFragment fragment = new TravelItemTransportFragment();
        Bundle args=new Bundle();

        args.putInt(ARG_TRAVEL_LIST_ID, travelListId);
        fragment.setArguments(args);
        return fragment;
    }

    public TravelItemTransportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // if this is set true,
        // Activity.onCreateOptionsMenu will call Fragment.onCreateOptionsMenu
        // Activity.onOptionsItemSelected will call Fragment.onOptionsItemSelected
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_travelitem_detail_transport, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {}

    @Override
    public void onResume() {
        super.onResume();

        // destroy all menu and re-call onCreateOptionsMenu
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_add:
                String travelItemTitle = ((AddTravelScheduleActivity)getActivity()).getScheduleTitle();
                if (travelItemTitle == null || travelItemTitle.isEmpty()) {
                    String message = "should enter schedule title";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    return false;
                }

                long travelItemStartDate = ((AddTravelScheduleActivity)getActivity()).getTimeStartDate();
                long travelItemEndDate = ((AddTravelScheduleActivity)getActivity()).getTimeEndDate();

                int travelListId = getArguments().getInt(ARG_TRAVEL_LIST_ID);
                Log.i("TravelItemTransportFragment", "travelListId: " + travelListId + " travelItemTitle:" + travelItemTitle +
                       " Name:" + mTravelItemNameEdit.getText().toString() + " DepCity:" + mTravelItemFromEdit.getText().toString() + " ArrCity:" + mTravelItemToEdit.getText().toString());

                if (mTravelItemNameEdit.getText().toString().isEmpty()) {
                    String message = "should enter vehicle name";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    return false;
                }

                DatabaseManager.getInstance().newTravelItemTransport(travelListId, travelItemTitle,
                        String.valueOf(travelItemStartDate), String.valueOf(travelItemEndDate), mTravelItemNameEdit.getText().toString(),
                        mTravelItemFromEdit.getText().toString(), mTravelItemToEdit.getText().toString(), mTravelItemNoteEdit.getText().toString());

                // 사용자가 백버튼을 누른 것와 같은 효과를 위해서 backstack에 있는 fragment를 pop함
                // getSupportFragmentManager().popBackStack();

                getActivity().finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Bind the views inside of parent with the fragment arguments.
     *
     * @param parent The parent of views to bind.
     */
    private void bind(View parent) {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        String travelItemTitle = ((AddTravelScheduleActivity)getActivity()).getScheduleTitle();
        if (!travelItemTitle.isEmpty())
            mTravelItemNameEdit.requestFocus();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(),
                enter ? android.R.anim.fade_in : android.R.anim.fade_out);
        // We bind a listener for the fragment transaction. We only bind it when
        // this fragment is entering.
        if (animation != null && enter) {
            animation.setAnimationListener(this);
        }
        return animation;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // This method is called at the end of the animation for the fragment transaction.
        // There is nothing we need to do in this sample.
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // This method is called at the end of the animation for the fragment transaction,
        // which is perfect time to start our Transition.
        Log.i(TAG, "Fragment animation ended. Starting a Transition.");

        bind(getView());
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // This method is never called in this sample because the animation doesn't repeat.
    }
}
