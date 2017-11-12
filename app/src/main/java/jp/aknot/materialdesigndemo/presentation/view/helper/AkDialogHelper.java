package jp.aknot.materialdesigndemo.presentation.view.helper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import jp.aknot.materialdesigndemo.presentation.view.AkDialogFragment;

public final class AkDialogHelper {

    public static <A extends AppCompatActivity & AkDialogFragment.Callback> void showDialog(@NonNull A activity,
            int dialogId, @NonNull DialogResHolder dialogResHolder, @Nullable Bundle requests) {

        Bundle realRequests = requests != null ? requests : new Bundle();

        String[] titleValueArray = realRequests.getStringArray(AkDialogFragment.REQUEST_TITLE_VALUE_ARRAY);
        String[] messageValueArray = realRequests.getStringArray(AkDialogFragment.REQUEST_MESSAGE_VALUE_ARRAY);

        if (dialogResHolder instanceof AlertDialogResHolder) {
            AlertDialogResHolder holder = (AlertDialogResHolder) dialogResHolder;
            AkDialogFragment.Builder builder = new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId);
            if (titleValueArray != null) {
                builder = builder.title(holder.titleResId, titleValueArray);
            } else {
                builder = builder.title(holder.titleResId);
            }
            if (messageValueArray != null) {
                builder = builder.message(holder.msgResId, messageValueArray);
            } else {
                builder = builder.message(holder.msgResId);
            }
            builder.positiveButton(holder.positiveBtnTextResId)
                    .negativeButton(holder.negativeBtnTextResId)
                    .neutralButton(holder.neutralBtnTextResId)
                    .cancelable(false)  // Alert は、選択は必須
                    .dialogId(dialogId)
                    .requests(realRequests)
                    .show();
        } else if (dialogResHolder instanceof ConfirmationDialogResHolder) {
            ConfirmationDialogResHolder holder = (ConfirmationDialogResHolder) dialogResHolder;
            AkDialogFragment.Builder builder = new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId);
            if (titleValueArray != null) {
                builder = builder.title(holder.titleResId, titleValueArray);
            } else {
                builder = builder.title(holder.titleResId);
            }
            builder.singleChoiceItems(holder.itemsResId)
                    .okButton()
                    .cancelButton()
                    .dialogId(dialogId)
                    .requests(realRequests)
                    .show();
        } else if (dialogResHolder instanceof SimpleDialogResHolder) {
            SimpleDialogResHolder holder = (SimpleDialogResHolder) dialogResHolder;
            AkDialogFragment.Builder builder = new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId);
            if (titleValueArray != null) {
                builder = builder.title(holder.titleResId, titleValueArray);
            } else {
                builder = builder.title(holder.titleResId);
            }
            builder.items(holder.itemsResId)
                    .dialogId(dialogId)
                    .requests(realRequests)
                    .show();
        } else if (dialogResHolder instanceof ItemListDialogResHolder) {
            ItemListDialogResHolder holder = (ItemListDialogResHolder) dialogResHolder;
            AkDialogFragment.Builder builder = new AkDialogFragment.Builder(activity, true)
                    .theme(holder.themeResId);
            if (titleValueArray != null) {
                builder = builder.title(holder.titleResId, titleValueArray);
            } else {
                builder = builder.title(holder.titleResId);
            }
            builder.iconItems(holder.itemDrawablesResId, holder.itemsResId)
                    .dialogId(dialogId)
                    .requests(realRequests)
                    .show();
        }
    }
}
