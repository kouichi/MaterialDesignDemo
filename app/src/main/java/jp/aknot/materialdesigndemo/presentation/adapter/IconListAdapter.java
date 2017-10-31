package jp.aknot.materialdesigndemo.presentation.adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.aknot.materialdesigndemo.R;

public class IconListAdapter extends BaseAdapter {

    private final Context context;

    private List<Item> items = new ArrayList<>();

    public IconListAdapter(@NonNull Context context, @NonNull Item[] items) {
        this.context = context;
        Collections.addAll(this.items, items);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_icon, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = view.findViewById(R.id.icon);
            viewHolder.tvTitle = view.findViewById(R.id.title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Item item = (Item) getItem(position);
        if (item.iconResId != 0) {
            viewHolder.ivIcon.setImageResource(item.iconResId);
        }
        viewHolder.tvTitle.setText(item.title);

        return view;
    }

    public static class Item implements Parcelable {

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel in) {
                return new Item(in);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };
        @DrawableRes
        public final int iconResId;
        public final String title;

        public Item(int iconResId, @NonNull String title) {
            this.iconResId = iconResId;
            this.title = title;
        }

        protected Item(Parcel in) {
            iconResId = in.readInt();
            title = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(iconResId);
            dest.writeString(title);
        }
    }

    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
    }
}
