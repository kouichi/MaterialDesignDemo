package jp.aknot.materialdesigndemo.helper;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import jp.aknot.materialdesigndemo.AkDialogFragment;
import jp.aknot.materialdesigndemo.adapter.IconListAdapter;

public final class AkDialogHelper {

    public static <A extends AppCompatActivity & AkDialogFragment.Callback> void showDialog(@NonNull A activity,
            int dialogId, @NonNull DialogResHolder dialogResHolder) {
        if (dialogResHolder instanceof AlertDialogResHolder) {
            AlertDialogResHolder holder = (AlertDialogResHolder) dialogResHolder;
            new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId)
                    .title(holder.titleResId)
                    .message(holder.msgResId)
                    .positiveButton(holder.positiveBtnTextResId)
                    .negativeButton(holder.negativeBtnTextResId)
                    .neutralButton(holder.neutralBtnTextResId)
                    .cancelable(false)  // Alert は、選択は必須
                    .dialogId(dialogId)
                    .show();
        } else if (dialogResHolder instanceof ConfirmationDialogResHolder) {
            ConfirmationDialogResHolder holder = (ConfirmationDialogResHolder) dialogResHolder;
            new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId)
                    .title(holder.titleResId)
                    .singleChoiceItems(holder.itemsResId)
                    .okButton()
                    .cancelButton()
                    .dialogId(dialogId)
                    .show();
        } else if (dialogResHolder instanceof SimpleDialogResHolder) {
            SimpleDialogResHolder holder = (SimpleDialogResHolder) dialogResHolder;
            new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId)
                    .title(holder.titleResId)
                    .items(holder.itemsResId)
                    .dialogId(dialogId)
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

            new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId)
                    .title(holder.titleResId)
                    .iconItems(iconItems)
                    .dialogId(dialogId)
                    .show();
        }
    }
}
