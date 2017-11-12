package jp.aknot.materialdesigndemo.presentation.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DialogItemAdapter extends BaseAdapter {

    private final Context context;

    @LayoutRes
    private final int layoutResId;

    @IdRes
    private final int textViewResId;

    private List<Item> iconItems = new ArrayList<>();

    private static final int NO_ICON_RES_ID = 0;

    public DialogItemAdapter(@NonNull Context context, @LayoutRes int layoutResId, @IdRes int textViewResId, @ArrayRes int itemsResId) {
        this(context, layoutResId, textViewResId, NO_ICON_RES_ID, itemsResId);
    }

    public DialogItemAdapter(@NonNull Context context,
            @LayoutRes int layoutResId, @IdRes int textViewResId, @ArrayRes int iconItemsResId, @ArrayRes int itemsResId) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.textViewResId = textViewResId;

        Resources resources = context.getResources();
        TypedArray drawableTypedArray = null;
        if (iconItemsResId != NO_ICON_RES_ID) {
            drawableTypedArray = resources.obtainTypedArray(iconItemsResId);
        }
        TypedArray stringTypedArray = resources.obtainTypedArray(itemsResId);
        try {
            int length = stringTypedArray.length();
            for (int i = 0; i < length; i++) {
                String text = stringTypedArray.getString(i);
                if (TextUtils.isEmpty(text)) {
                    continue;
                }
                @DrawableRes int drawableResId = (drawableTypedArray != null) ? drawableTypedArray.getResourceId(i, NO_ICON_RES_ID) : NO_ICON_RES_ID;
                iconItems.add(new Item(drawableResId, text));
            }
        } finally {
            if (drawableTypedArray != null) {
                drawableTypedArray.recycle();
            }
            stringTypedArray.recycle();
        }
    }

    @Override
    public int getCount() {
        return iconItems.size();
    }

    @Override
    public Object getItem(int position) {
        return iconItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(layoutResId, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvText = view.findViewById(textViewResId);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Item item = (Item) getItem(position);
        if (item.iconResId != NO_ICON_RES_ID) {
            viewHolder.tvText.setCompoundDrawablesWithIntrinsicBounds(item.iconResId, 0, 0, 0);
        }
        viewHolder.tvText.setText(item.text);

        return view;
    }

    public static class Item {

        @DrawableRes
        public final int iconResId;

        @NonNull
        public final String text;

        public Item(@DrawableRes int iconResId, @NonNull String text) {
            this.iconResId = iconResId;
            this.text = text;
        }
    }

    private static class ViewHolder {
        TextView tvText;
    }
}
