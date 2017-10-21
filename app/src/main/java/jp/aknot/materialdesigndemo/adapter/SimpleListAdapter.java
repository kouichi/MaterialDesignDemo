package jp.aknot.materialdesigndemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.aknot.materialdesigndemo.R;

public class SimpleListAdapter extends BaseAdapter {

    private final Context context;

    private List<Item> items = new ArrayList<>();

    public SimpleListAdapter(@NonNull Context context, @NonNull Item[] items) {
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
            view = LayoutInflater.from(context).inflate(R.layout.list_item_simple, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = view.findViewById(R.id.title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Item item = (Item) getItem(position);
        viewHolder.tvTitle.setText(item.title);

        return view;
    }

    public static class Item {
        public final String title;

        public Item(@NonNull String title) {
            this.title = title;
        }
    }

    private static class ViewHolder {
        TextView tvTitle;
    }
}
