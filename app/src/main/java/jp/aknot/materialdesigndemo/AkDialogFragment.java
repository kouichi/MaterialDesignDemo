package jp.aknot.materialdesigndemo;

import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_CANCELABLE;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_ERROR;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_EVENT_TRACKING_ENABLED;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_ICON_ITEMS_ID;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_ICON_RES_ID;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_ITEMS_RES_ID;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_MESSAGE;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_NEGATIVE_BTN_TEXT_RES_ID;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_NEUTRAL_BTN_TEXT_RES_ID;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_PARAMS;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_POSITIVE_BTN_TEXT_RES_ID;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_REQUEST_CODE;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_SINGLE_CHOICE_ITEMS_RES_ID;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_THEME_RES_ID;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.ARGS_TITLE;
import static jp.aknot.materialdesigndemo.AkDialogFragment.Builder.UNKNOWN_RES_ID;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import jp.aknot.materialdesigndemo.adapter.IconListAdapter;

public class AkDialogFragment extends DialogFragment {

    public static final String PARAM_CHECKED_ITEM = "dialog:checkedItem";
    public static final String PARAM_CHECKED_ITEM_VALUE = "dialog:checkedItemValue";
    private static final String TAG = "@" + AkDialogFragment.class.getSimpleName();
    private boolean eventTrackingEnabled;

    private Callback callback;

    public AkDialogFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Object callback = getActivity();
        if (callback == null || !(callback instanceof Callback)) {
            throw new IllegalStateException();
        }
        this.callback = (Callback) callback;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final FragmentActivity activity = getActivity();
        final Bundle args = getArguments();

        @StyleRes final int themeResId = args.getInt(ARGS_THEME_RES_ID, UNKNOWN_RES_ID);
        @DrawableRes final int iconResId = args.getInt(ARGS_ICON_RES_ID);
        final String title = args.getString(ARGS_TITLE);
        final String message = args.getString(ARGS_MESSAGE);
        @ArrayRes final int itemsResId = args.getInt(ARGS_ITEMS_RES_ID);
        @ArrayRes final int singleChoiceItemsResId = args.getInt(ARGS_SINGLE_CHOICE_ITEMS_RES_ID);
        final IconListAdapter.Item[] iconItems = (IconListAdapter.Item[]) args.getParcelableArray(ARGS_ICON_ITEMS_ID);
        @StringRes final int positiveBtnTextResId = args.getInt(ARGS_POSITIVE_BTN_TEXT_RES_ID);
        @StringRes final int negativeBtnTextResId = args.getInt(ARGS_NEGATIVE_BTN_TEXT_RES_ID);
        @StringRes final int neutralBtnTextResId = args.getInt(ARGS_NEUTRAL_BTN_TEXT_RES_ID);

        final boolean cancelable = args.getBoolean(ARGS_CANCELABLE);

        final Bundle params = args.getBundle(ARGS_PARAMS);
        final Bundle error = args.getBundle(ARGS_ERROR);
        final int requestCode = args.getInt(ARGS_REQUEST_CODE);

        this.eventTrackingEnabled = args.getBoolean(ARGS_EVENT_TRACKING_ENABLED);

        AlertDialog.Builder builder =
                themeResId != UNKNOWN_RES_ID ? new AlertDialog.Builder(activity, themeResId) : new AlertDialog.Builder(activity);
        if (iconResId != UNKNOWN_RES_ID) {
            builder.setIcon(iconResId);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }

        if (itemsResId != UNKNOWN_RES_ID) {
            builder.setItems(itemsResId, wrapOnItemClickListenerIfNeeds(title, itemsResId, args));
        }
        if (singleChoiceItemsResId != UNKNOWN_RES_ID) {
            builder.setSingleChoiceItems(singleChoiceItemsResId, -1 /* no items are checked. */, (dialog, which) -> {
                AlertDialog alertDialog = (AlertDialog) dialog;
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setEnabled(true);
            });
        }

        if (iconItems != null && iconItems.length > 0) {
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_content_list, null);
            ListView listView = view.findViewById(R.id.dialog_content_list_view);
            listView.setAdapter(new IconListAdapter(activity, iconItems));
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                IconListAdapter.Item item = (IconListAdapter.Item) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: position=" + position + ", id=" + id + ", item=" + item.title);

                Bundle p = new Bundle();
                if (params != null) {
                    p = new Bundle(params);
                }
                p.putInt(PARAM_CHECKED_ITEM, position);
                p.putString(PARAM_CHECKED_ITEM_VALUE, item.title);
                args.putBundle(ARGS_PARAMS, p);

                callback.onAkDialogClicked(requestCode, Activity.RESULT_OK, p);
                dismiss();
            });
            builder.setView(view);
        }

        if (positiveBtnTextResId != UNKNOWN_RES_ID) {
            builder.setPositiveButton(positiveBtnTextResId, wrapOnButtonClickListenerIfNeeds(title, positiveBtnTextResId, args));
        }
        if (negativeBtnTextResId != UNKNOWN_RES_ID) {
            builder.setNegativeButton(negativeBtnTextResId, wrapOnButtonClickListenerIfNeeds(title, negativeBtnTextResId, args));
        }
        if (neutralBtnTextResId != UNKNOWN_RES_ID) {
            builder.setNeutralButton(neutralBtnTextResId, wrapOnButtonClickListenerIfNeeds(title, neutralBtnTextResId, args));
        }

        AlertDialog dialog = builder.create();

        // AlertDialog.Builder#setCancelable(boolean) で設定が反映されないので注意
        setCancelable(cancelable);

        dialog.setOnShowListener(dlg -> {
            if (singleChoiceItemsResId != UNKNOWN_RES_ID) {
                AlertDialog alertDialog = (AlertDialog) dlg;
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setEnabled(false);
            }
        });

        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        Bundle args = getArguments();
        int requestCode = args.getInt(ARGS_REQUEST_CODE);
        Bundle params = args.getBundle(ARGS_PARAMS);
        callback.onAkDialogCancelled(requestCode, params);
    }

    private DialogInterface.OnClickListener wrapOnItemClickListenerIfNeeds(@Nullable String title, @ArrayRes int itemsResId, @NonNull Bundle args) {
        if (eventTrackingEnabled) {
            return new OnItemClickListener(title, itemsResId, callback, args);
        }
        return new OnClickListener(callback, args);
    }

    private DialogInterface.OnClickListener wrapOnButtonClickListenerIfNeeds(@Nullable String title, @StringRes int buttonResId, @NonNull Bundle args) {
        if (eventTrackingEnabled) {
            return new OnButtonClickListener(title, buttonResId, callback, args);
        }
        return new OnClickListener(callback, args);
    }

    public interface Callback {
        /**
         * @param requestCode Request Code
         * @param resultCode  Result Code(選択したボタンの識別子)
         * @param params      Parameters
         */
        void onAkDialogClicked(int requestCode, int resultCode, Bundle params);

        void onAkDialogCancelled(int requestCode, Bundle params);
    }

    public static class Builder {

        public static final String ARGS_THEME_RES_ID = "themeResId";
        public static final String ARGS_ICON_RES_ID = "iconResId";
        public static final String ARGS_TITLE = "title";
        public static final String ARGS_MESSAGE = "message";
        public static final String ARGS_ITEMS_RES_ID = "itemsResId";
        public static final String ARGS_SINGLE_CHOICE_ITEMS_RES_ID = "singleChoiceItemsResId";
        public static final String ARGS_ICON_ITEMS_ID = "iconItems";
        public static final String ARGS_POSITIVE_BTN_TEXT_RES_ID = "positiveBtnTextResId";
        public static final String ARGS_NEGATIVE_BTN_TEXT_RES_ID = "negativeBtnTextResId";
        public static final String ARGS_NEUTRAL_BTN_TEXT_RES_ID = "neutralBtnTextResId";
        public static final String ARGS_CANCELABLE = "cancelable";
        public static final String ARGS_PARAMS = "params";
        public static final String ARGS_ERROR = "error";
        public static final String ARGS_REQUEST_CODE = "requestCode";
        public static final String ARGS_EVENT_TRACKING_ENABLED = "eventTrackingEnabled";

        static final int UNKNOWN_RES_ID = 0;

        final AppCompatActivity activity;

        /** イベントトラッキングを有効にするか否か. */
        final boolean eventTrackingEnabled;

        @StyleRes
        int themeResId = UNKNOWN_RES_ID;

        @DrawableRes
        int iconResId = UNKNOWN_RES_ID;

        String title;

        String message;

        @ArrayRes
        int itemsResId = UNKNOWN_RES_ID;

        @ArrayRes
        int singleChoiceItemsResId = UNKNOWN_RES_ID;

        IconListAdapter.Item[] iconItems;

        @StringRes
        int positiveBtnTextResId = UNKNOWN_RES_ID;

        @StringRes
        int negativeBtnTextResId = UNKNOWN_RES_ID;

        @StringRes
        int neutralBtnTextResId = UNKNOWN_RES_ID;

        int requestCode = -1;

        Bundle params;

        boolean cancelable = true;

        Bundle error = null;

        String tag = "default";

        public <A extends AppCompatActivity & Callback> Builder(@NonNull final A activity) {
            this(activity, false);
        }

        // ------

        public <A extends AppCompatActivity & Callback> Builder(@NonNull final A activity, boolean eventTrackingEnabled) {
            this.activity = activity;
            this.eventTrackingEnabled = eventTrackingEnabled;
        }

        @NonNull
        public Builder theme(@StyleRes int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        @NonNull
        public Builder icon(@DrawableRes int iconResId) {
            this.iconResId = iconResId;
            return this;
        }

        @NonNull
        public Builder title(@NonNull String title) {
            this.title = title;
            return this;
        }

        @NonNull
        public Builder title(@StringRes int titleResId, @NonNull Object... formatArgs) {
            this.title = getContext().getString(titleResId, formatArgs);
            return this;
        }

        public Builder message(@NonNull String message) {
            this.message = message;
            return this;
        }

        // ------

        public Builder message(@StringRes int messageResId, Object... formatArgs) {
            this.message = getContext().getString(messageResId, formatArgs);
            return this;
        }

        // ------

        /**
         * Simple Dialog.
         * <ul>
         * <li>ダイアログの Message を設定すると本メソッドで設定した Item は<b>非表示</b>になるので注意する。</li>
         * </ul>
         *
         * @see <a href="https://material.io/guidelines/components/dialogs.html#dialogs-simple-dialogs">Components–Dialogs</a>
         */
        public Builder items(@ArrayRes int itemsResId) {
            this.itemsResId = itemsResId;
            return this;
        }

        /**
         * Confirmation dialogs.
         * <ul>
         * <li>選択式(ラジオボタン)のダイアログになります。(アイコンなし)</li>
         * </ul>
         *
         * @see <a href="https://material.io/guidelines/components/dialogs.html#dialogs-confirmation-dialogs">Components–Dialogs</a>
         */
        public Builder singleChoiceItems(@ArrayRes int itemsResId) {
            this.singleChoiceItemsResId = itemsResId;
            return this;
        }

        public Builder iconItems(@NonNull IconListAdapter.Item[] iconItems) {
            this.iconItems = iconItems;
            return this;
        }

        public Builder positiveButton(@StringRes int textResId) {
            this.positiveBtnTextResId = textResId;
            return this;
        }

        public Builder okButton() {
            return positiveButton(R.string.btn_label_ok);
        }

        public Builder negativeButton(@StringRes int textResId) {
            this.negativeBtnTextResId = textResId;
            return this;
        }

        public Builder neutralButton(@StringRes int textResId) {
            this.neutralBtnTextResId = textResId;
            return this;
        }

        public Builder cancelButton() {
            return negativeButton(R.string.btn_label_cancel);
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder params(@NonNull Bundle params) {
            this.params = params;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder error(@Nullable Bundle error) {
            this.error = error;
            return this;
        }

        public Builder tag(@NonNull String tag) {
            this.tag = tag;
            return this;
        }

        private Context getContext() {
            return activity.getApplicationContext();
        }

        public void show() {
            final Bundle args = new Bundle();
            args.putInt(ARGS_THEME_RES_ID, themeResId);
            args.putInt(ARGS_ICON_RES_ID, iconResId);
            args.putString(ARGS_TITLE, title);
            args.putString(ARGS_MESSAGE, message);
            args.putInt(ARGS_ITEMS_RES_ID, itemsResId);
            args.putInt(ARGS_SINGLE_CHOICE_ITEMS_RES_ID, singleChoiceItemsResId);
            args.putParcelableArray(ARGS_ICON_ITEMS_ID, iconItems);
            args.putInt(ARGS_POSITIVE_BTN_TEXT_RES_ID, positiveBtnTextResId);
            args.putInt(ARGS_NEGATIVE_BTN_TEXT_RES_ID, negativeBtnTextResId);
            args.putInt(ARGS_NEUTRAL_BTN_TEXT_RES_ID, neutralBtnTextResId);
            args.putBoolean(ARGS_CANCELABLE, cancelable);
            if (params != null) {
                args.putBundle(ARGS_PARAMS, params);
            }
            if (error != null) {
                args.putBundle(ARGS_ERROR, error);
            }
            args.putInt(ARGS_REQUEST_CODE, requestCode);
            args.putBoolean(ARGS_EVENT_TRACKING_ENABLED, eventTrackingEnabled);

            final AkDialogFragment fragment = new AkDialogFragment();
            fragment.setArguments(args);
            fragment.show(activity.getSupportFragmentManager(), tag);
        }
    }

    private static class OnClickListener implements DialogInterface.OnClickListener {

        @NonNull
        final Bundle args;
        @NonNull
        private final Callback callback;

        private OnClickListener(@NonNull Callback callback, @NonNull Bundle args) {
            this.callback = callback;
            this.args = args;
        }

        Bundle getParams() {
            return args.getBundle(ARGS_PARAMS);
        }

        private int getRequestCode() {
            return args.getInt(ARGS_REQUEST_CODE);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            callback.onAkDialogClicked(getRequestCode(), which, getParams());
        }
    }

    /**
     * Simple Dialog の選択肢(アイテム)のクリックリスナー.
     * <p>Event Tracking を行います。</p>
     * <ul>
     * <li>Category: Dialog</li>
     * <li>Action: ダイアログのタイトル文字列</li>
     * <li>Label: 押下したボタンのラベル</li>
     * </ul>
     */
    private static class OnItemClickListener extends OnClickListener {

        private static final String TAG = "@" + OnItemClickListener.class.getSimpleName();

        private final String title;

        @ArrayRes
        private final int itemsResId;

        private OnItemClickListener(@Nullable String title, @ArrayRes int itemsResId, @NonNull Callback callback, @NonNull Bundle args) {
            super(callback, args);
            this.title = title;
            this.itemsResId = itemsResId;
        }

        @Override
        public final void onClick(@NonNull DialogInterface dialog, int which) {
            AlertDialog alertDialog = (AlertDialog) dialog;
            if (itemsResId > 0) {
                Context context = alertDialog.getContext();
                Log.i(TAG, "Event tracking: " + title + " [" + context.getResources().getStringArray(itemsResId)[which] + "]");
            } else {
                Log.w(TAG, "Event tracking has been disabled: itemsResId is ArrayRes");
            }
            super.onClick(dialog, which);
        }
    }

    /**
     * Positive/Negative/Neutral ボタンのクリックリスナー.
     * <p>Event Tracking を行います。</p>
     * <ul>
     * <li>Category: Dialog</li>
     * <li>Action: ダイアログのタイトル文字列</li>
     * <li>Label: 押下したボタンのラベル</li>
     * </ul>
     */
    private static class OnButtonClickListener extends OnClickListener {

        private static final String TAG = "@" + OnButtonClickListener.class.getSimpleName();

        private final String title;

        @StringRes
        private final int btnTextResId;

        private OnButtonClickListener(@Nullable String title, @StringRes int btnTextResId, @NonNull Callback callback, @NonNull Bundle args) {
            super(callback, args);
            this.title = title;
            this.btnTextResId = btnTextResId;
        }

        @Override
        public final void onClick(@NonNull DialogInterface dialog, int which) {
            AlertDialog alertDialog = (AlertDialog) dialog;
            ListView listView = alertDialog.getListView();
            if (listView != null) {
                int position = listView.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    String checkedItem = (String) listView.getAdapter().getItem(position);
                    if (!TextUtils.isEmpty(checkedItem)) {
                        Log.i(TAG, "Event tracking: " + title + " [" + position + ":" + checkedItem + "]");
                    }

                    Bundle p = new Bundle();
                    Bundle params = getParams();
                    if (params != null) {
                        p = new Bundle(params);
                    }
                    p.putInt(PARAM_CHECKED_ITEM, position);
                    p.putString(PARAM_CHECKED_ITEM_VALUE, checkedItem);
                    args.putBundle(ARGS_PARAMS, p);
                }
                super.onClick(dialog, which);
            } else {
                if (btnTextResId > 0) {
                    Context context = alertDialog.getContext();
                    Log.i(TAG, "Event tracking: " + title + " [" + context.getString(btnTextResId) + "]");
                } else {
                    Log.w(TAG, "Event tracking has been disabled: btnTextResId is StringRes");
                }
                super.onClick(dialog, which);
            }
        }
    }
}
