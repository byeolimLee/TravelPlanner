package com.starim.android.apps.travelplanner.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.app.Dialog;

import com.starim.android.apps.travelplanner.R;

import java.util.List;

/**
 * Created by starim on 14. 7. 9..
 */
public class CustomContextMenu extends Dialog {
    private Builder mBuilder;

    public CustomContextMenu(Context context, int theme) {
        super(context, theme);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_context_menu);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (mBuilder != null) {
            TextView titleTextView = (TextView) findViewById(R.id.contextMenu_title);
            if (StringUtils.isExist(mBuilder.mTitle)) {
                titleTextView.setText(mBuilder.mTitle);
            } else {
                findViewById(R.id.contextMenu_titleLayout).setVisibility(View.GONE);
            }

            ListView listView = (ListView) findViewById(R.id.contextMenu_list);
            if (StringUtils.isEmpty(mBuilder.mFooterText) == false) {
                TextView footerView = (TextView) View.inflate(getContext(), R.layout.popup_context_menu_footer, null);
                footerView.setText(mBuilder.mFooterText);
                listView.addFooterView(footerView);
            }

            ContextMenuExtAdapter adapter = new ContextMenuExtAdapter(getContext(), R.layout.popup_context_menu_row, mBuilder.menus);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mBuilder.menus.get(position).onClickListener.onClick(view);
                    dismiss();
                }
            });
        }
    }

    public static void show(Activity activity, String title, String footerText, List<CustomMenuItem> menus) {
        CustomContextMenu.Builder.newInstance(activity).setTitle(title).setFooterText(footerText).setMenus(menus).create().show();
    }

    public static void show(Activity activity, String title, List<CustomMenuItem> menus) {
        CustomContextMenu.Builder.newInstance(activity).setTitle(title).setMenus(menus).create().show();
    }

    private class ContextMenuExtAdapter extends ArrayAdapter<CustomMenuItem> {
        public ContextMenuExtAdapter(Context context, int resource, List<CustomMenuItem> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = mInflater.inflate(R.layout.popup_context_menu_row, null);
            }

            CustomMenuItem item = getItem(position);

            if (item != null) {
                TextView title = (TextView) view.findViewById(R.id.messagePopup_title);
                if (item.titleRes > 0) {
                    title.setText(getContext().getString(item.titleRes));
                } else if (StringUtils.isExist(item.title)) {
                    title.setText(item.title);
                }

            }
            return view;
        }
    }

    public static class Builder {
        private Context mContext;
        private int mTheme = android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth;
        private CharSequence mTitle;
        private CharSequence mFooterText;
        private List<CustomMenuItem> menus;

        public static CustomContextMenu.Builder newInstance(Context context) {
            return new CustomContextMenu.Builder(context);
        }

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(int titleRes) {
            mTitle = titleRes == 0 ? null : mContext.getString(titleRes);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        public Builder setFooterText(int messageId) {
            mFooterText = messageId == 0 ? null : mContext.getText(messageId);
            return this;
        }

        public Builder setFooterText(CharSequence message) {
            mFooterText = message;
            return this;
        }

        public Builder setMenus(List<CustomMenuItem> menus) {
            this.menus = menus;
            return this;
        }

        public CustomContextMenu create() {
            CustomContextMenu dialog = new CustomContextMenu(mContext, mTheme);
            dialog.mBuilder = this;
            return dialog;
        }
    }
}
