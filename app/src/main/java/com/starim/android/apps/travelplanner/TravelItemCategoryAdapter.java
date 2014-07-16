package com.starim.android.apps.travelplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by starim on 14. 7. 9..
 */
public class TravelItemCategoryAdapter extends BaseAdapter {
    private final LayoutInflater mLayoutInflater;
    private final int mResourceId;

    public TravelItemCategoryAdapter(LayoutInflater inflater, int resourceId) {
        mLayoutInflater = inflater;
        mResourceId = resourceId;
    }

    @Override
    public int getCount() {
        return TravelItemCategory.CATEGORIES.length;
    }

    @Override
    public TravelItemCategory getItem(int position) {
        return TravelItemCategory.CATEGORIES[position];
    }

    @Override
    public long getItemId(int position) {
        return TravelItemCategory.CATEGORIES[position].resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        final ViewHolder holder;
        if (null == convertView) {
            view = mLayoutInflater.inflate(mResourceId, parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.image = (ImageView) view.findViewById(R.id.image);
            holder.title = (TextView) view.findViewById(R.id.title);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        bindView(holder, position);
        return view;
    }

    public void bindView(ViewHolder holder, int position) {
        TravelItemCategory category = getItem(position);
        holder.image.setImageResource(category.resourceId);
        holder.title.setText(category.typeString);
    }

    public static class ViewHolder {
        public ImageView image;
        public TextView title;
    }
}
