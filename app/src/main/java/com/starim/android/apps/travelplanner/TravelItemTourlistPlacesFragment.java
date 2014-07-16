package com.starim.android.apps.travelplanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelItemTourlistPlacesFragment extends Fragment implements Animation.AnimationListener  {

    private static final String TAG = "TravelItemTourlistPlacesFragment";

    private static final String ARG_RESOURCE_ID = "resource_id";
    private static final String ARG_TITLE = "title";
    private static final String ARG_X = "x";
    private static final String ARG_Y = "y";
    private static final String ARG_WIDTH = "width";
    private static final String ARG_HEIGHT = "height";

    public static TravelItemTourlistPlacesFragment newInstance(int x, int y, int width, int height){
        TravelItemTourlistPlacesFragment fragment= new TravelItemTourlistPlacesFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_RESOURCE_ID, R.drawable.p5);
        args.putString(ARG_TITLE, TravelItemCategory.TYPE_STRING_TOURISTPLACES);
        args.putInt(ARG_X,x);
        args.putInt(ARG_Y,y);
        args.putInt(ARG_WIDTH,width);
        args.putInt(ARG_HEIGHT,height);
        fragment.setArguments(args);
        return fragment;
    }


    public TravelItemTourlistPlacesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_travelitem_detail_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
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
        ImageView image = (ImageView) parent.findViewById(R.id.image);
        image.setImageResource(args.getInt(ARG_RESOURCE_ID));
        TextView title = (TextView) parent.findViewById(R.id.title);
        title.setText(args.getString(ARG_TITLE));
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
