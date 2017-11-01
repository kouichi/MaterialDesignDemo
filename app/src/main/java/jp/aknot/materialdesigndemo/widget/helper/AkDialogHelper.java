package jp.aknot.materialdesigndemo.widget.helper;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import jp.aknot.materialdesigndemo.presentation.adapter.IconListAdapter;
import jp.aknot.materialdesigndemo.widget.AkDialogFragment;

public final class AkDialogHelper {

    public static <A extends AppCompatActivity & AkDialogFragment.Callback> void showDialog(@NonNull A activity,
            int dialogId, @NonNull DialogResHolder dialogResHolder, @NonNull Bundle requests) {

        String[] titleValueArray = requests.getStringArray(AkDialogFragment.REQUEST_TITLE_VALUE_ARRAY);
        String[] messageValueArray = requests.getStringArray(AkDialogFragment.REQUEST_MESSAGE_VALUE_ARRAY);

        if (dialogResHolder instanceof AlertDialogResHolder) {
            AlertDialogResHolder holder = (AlertDialogResHolder) dialogResHolder;
            AkDialogFragment.Builder builder = new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId);
            if (titleValueArray != null) {
                builder.title(holder.titleResId, titleValueArray);
            } else {
                builder.title(holder.titleResId);
            }
            if (messageValueArray != null) {
                builder.message(holder.msgResId, messageValueArray);
            } else {
                builder.message(holder.msgResId);
            }
            builder.positiveButton(holder.positiveBtnTextResId)
                    .negativeButton(holder.negativeBtnTextResId)
                    .neutralButton(holder.neutralBtnTextResId)
                    .cancelable(false)  // Alert は、選択は必須
                    .dialogId(dialogId)
                    .requests(requests)
                    .show();
        } else if (dialogResHolder instanceof ConfirmationDialogResHolder) {
            ConfirmationDialogResHolder holder = (ConfirmationDialogResHolder) dialogResHolder;
            AkDialogFragment.Builder builder = new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId);
            if (titleValueArray != null) {
                builder.title(holder.titleResId, titleValueArray);
            } else {
                builder.title(holder.titleResId);
            }
            builder.singleChoiceItems(holder.itemsResId)
                    .okButton()
                    .cancelButton()
                    .dialogId(dialogId)
                    .requests(requests)
                    .show();
        } else if (dialogResHolder instanceof SimpleDialogResHolder) {
            SimpleDialogResHolder holder = (SimpleDialogResHolder) dialogResHolder;
            AkDialogFragment.Builder builder = new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId);
            if (titleValueArray != null) {
                builder.title(holder.titleResId, titleValueArray);
            } else {
                builder.title(holder.titleResId);
            }
            builder.items(holder.itemsResId)
                    .dialogId(dialogId)
                    .requests(requests)
                    .show();
        } else if (dialogResHolder instanceof ItemListDialogResHolder) {
            ItemListDialogResHolder holder = (ItemListDialogResHolder) dialogResHolder;
            List<IconListAdapter.Item> iconItemList = new ArrayList<>();
            Resources resources = activity.getResources();
            TypedArray drawableTypedArray = resources.obtainTypedArray(holder.itemDrawablesResId);
            TypedArray stringTypedArray = resources.obtainTypedArray(holder.itemsResId);
            try {
                int length = drawableTypedArray.length();
                for (int i = 0; i < length; i++) {
                    @DrawableRes int drawableResId = drawableTypedArray.getResourceId(i, DialogResHolder.UNKNOWN_RES_ID);
                    String title = stringTypedArray.getString(i);
                    if (TextUtils.isEmpty(title)) {
                        continue;
                    }
                    iconItemList.add(new IconListAdapter.Item(drawableResId, title));
                }
            } finally {
                drawableTypedArray.recycle();
                stringTypedArray.recycle();
            }
            IconListAdapter.Item[] iconItems = iconItemList.toArray(new IconListAdapter.Item[iconItemList.size()]);

            AkDialogFragment.Builder builder = new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId);
            if (titleValueArray != null) {
                builder.title(holder.titleResId, titleValueArray);
            } else {
                builder.title(holder.titleResId);
            }
            builder.iconItems(iconItems)
                    .dialogId(dialogId)
                    .requests(requests)
                    .show();
        }
    }
}
