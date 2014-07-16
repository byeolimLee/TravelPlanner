package com.starim.android.apps.travelplanner.common;

import android.view.View;

/**
 * Created by starim on 14. 7. 9..
 */
public class CustomMenuItem {
    public final int titleRes;
    public final String title;
    public final int iconRes;
    public final View.OnClickListener onClickListener;

    public CustomMenuItem(int titleRes, int iconRes, View.OnClickListener onClickListener) {
        super();
        this.titleRes = titleRes;
        this.title = null;
        this.iconRes = iconRes;
        this.onClickListener = onClickListener;
    }

    public CustomMenuItem(String title, int iconRes, View.OnClickListener onClickListener) {
        super();
        this.titleRes = 0;
        this.title = title;
        this.iconRes = iconRes;
        this.onClickListener = onClickListener;
    }

    public CustomMenuItem(int titleRes, View.OnClickListener onClickListener) {
        this(titleRes, 0, onClickListener);
    }

    public CustomMenuItem(String title, View.OnClickListener onClickListener) {
        this(title, 0, onClickListener);
    }

    public CustomMenuItem(View.OnClickListener onClickListener) {
        this(0, 0, onClickListener);
    }

}
