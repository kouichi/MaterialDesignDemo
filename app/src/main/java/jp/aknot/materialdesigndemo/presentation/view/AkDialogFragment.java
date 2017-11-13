package jp.aknot.materialdesigndemo.presentation.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.aknot.materialdesigndemo.R;

public class AkDialogFragment extends DialogFragment {

    private static final String TAG = "@" + AkDialogFragment.class.getSimpleName();

    public static final String REQUEST_VIEW_MODE = "dialog:request_view_mode";
    public static final String RESPONSE_VIEW_MODE = "dialog:response_view_mode";
    public static final String REQUEST_WEBVIEW_LOAD_URL = "dialog:request_webview_load_url";

    public static final String REQUEST_INTENT = "dialog:request_intent";
    public static final String RESPONSE_INTENT = "dialog:response_intent";

    public static final String REQUEST_TITLE_VALUE_ARRAY = "dialog:request_title_value_array";
    public static final String REQUEST_MESSAGE_VALUE_ARRAY = "dialog:request_message_value_array";
    public static final String REQUEST_ERROR = "dialog:request_error";

    public static final String RESPONSE_CHECKED_ITEM_ID = "dialog:response_checked_item_id";
    public static final String RESPONSE_CHECKED_ITEM_VALUE = "dialog:response_checked_item_value";

    public static final int NO_ITEM_ID = -1;

    private static final String ARGS_THEME_RES_ID = "themeResId";
    private static final String ARGS_ICON_RES_ID = "iconResId";
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "message";
    private static final String ARGS_ICON_ITEMS_RES_ID = "iconItemsResId";
    private static final String ARGS_ITEMS_RES_ID = "itemsResId";
    private static final String ARGS_SINGLE_CHOICE_ITEMS_RES_ID = "singleChoiceItemsResId";
    private static final String ARGS_POSITIVE_BTN_TEXT_RES_ID = "positiveBtnTextResId";
    private static final String ARGS_NEGATIVE_BTN_TEXT_RES_ID = "negativeBtnTextResId";
    private static final String ARGS_NEUTRAL_BTN_TEXT_RES_ID = "neutralBtnTextResId";
    private static final String ARGS_CANCELABLE = "cancelable";
    private static final String ARGS_REQUESTS = "requests";
    private static final String ARGS_DIALOG_ID = "dialogId";
    private static final String ARGS_EVENT_TRACKING_ENABLED = "eventTrackingEnabled";

    public static final int UNKNOWN_RES_ID = 0;

    private boolean eventTrackingEnabled;

    private Callback callback;

    public enum ViewMode {
        DEFAULT,
        WEBVIEW,
        PASSWORD_INPUT,
    }

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
        @ArrayRes final int iconItemsResId = args.getInt(ARGS_ICON_ITEMS_RES_ID);
        @ArrayRes final int itemsResId = args.getInt(ARGS_ITEMS_RES_ID);
        @ArrayRes final int singleChoiceItemsResId = args.getInt(ARGS_SINGLE_CHOICE_ITEMS_RES_ID);
        @StringRes final int positiveBtnTextResId = args.getInt(ARGS_POSITIVE_BTN_TEXT_RES_ID);
        @StringRes final int negativeBtnTextResId = args.getInt(ARGS_NEGATIVE_BTN_TEXT_RES_ID);
        @StringRes final int neutralBtnTextResId = args.getInt(ARGS_NEUTRAL_BTN_TEXT_RES_ID);

        final boolean cancelable = args.getBoolean(ARGS_CANCELABLE);

        final Bundle requests = args.getBundle(ARGS_REQUESTS);

        final ViewMode viewMode = InternalUtil.getViewMode(args);
        final int dialogId = InternalUtil.getDialogId(args);

        this.eventTrackingEnabled = args.getBoolean(ARGS_EVENT_TRACKING_ENABLED);

        AlertDialog.Builder builder = themeResId != UNKNOWN_RES_ID ? new AlertDialog.Builder(activity, themeResId) : new AlertDialog.Builder(activity);
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
            DialogItemAdapter adapter;
            if (iconItemsResId != UNKNOWN_RES_ID) {
                adapter = new DialogItemAdapter(activity, R.layout.select_dialog_item, R.id.text, iconItemsResId, itemsResId);
            } else {
                adapter = new DialogItemAdapter(activity, R.layout.select_dialog_item, R.id.text, itemsResId);
            }
            builder.setAdapter(adapter, wrapOnItemClickListenerIfNeeds(title, itemsResId, args));
        }

        if (singleChoiceItemsResId != UNKNOWN_RES_ID) {
            DialogItemAdapter adapter = new DialogItemAdapter(activity, R.layout.select_dialog_single_choice, R.id.text, singleChoiceItemsResId);
            builder.setSingleChoiceItems(adapter, -1 /* no items are checked. */, (dialog, which) -> {
                AlertDialog alertDialog = (AlertDialog) dialog;
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setEnabled(true);
            });
        }

        switch (viewMode) {
            case WEBVIEW: {
                String url = requests.getString(REQUEST_WEBVIEW_LOAD_URL);
                if (TextUtils.isEmpty(url)) {
                    throw new IllegalStateException("This following requests parameter is mandatory: " + REQUEST_WEBVIEW_LOAD_URL);
                }
                View view = LayoutInflater.from(activity).inflate(R.layout.dialog_content_webview, null);
                WebView webView = view.findViewById(R.id.dialog_content_webview);
                webView.setWebViewClient(new WebViewClient()); // Browser 起動防止
                webView.loadUrl(url);
                builder.setView(view);
                break;
            }
            case PASSWORD_INPUT: {
                View view = LayoutInflater.from(activity).inflate(R.layout.dialog_content_password_input, null);
                EditText editText = view.findViewById(R.id.dialog_content_password_input);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        AlertDialog alertDialog = (AlertDialog) getDialog();
                        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        if (s != null && s.length() > 0) {
                            positiveButton.setEnabled(true);
                        } else {
                            positiveButton.setEnabled(false);
                        }
                    }
                });
                builder.setView(view);
                break;
            }
            case DEFAULT:
            default:
                break;
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
            AlertDialog alertDialog = (AlertDialog) dlg;
            TextView textView = alertDialog.findViewById(android.R.id.message);
            if (textView != null) {
                textView.setLineSpacing(0, 1.3f); // TODO: 値は適当になので調整が必要
                TextViewCompat.setTextAppearance(textView, R.style.MyTextAppearance_Alert_Dialog_Message);
            }

            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            switch (viewMode) {
                case PASSWORD_INPUT:
                    positiveButton.setEnabled(false);
                    break;
                case DEFAULT:
                case WEBVIEW:
                default:
                    break;
            }

            if (singleChoiceItemsResId != UNKNOWN_RES_ID) {
                positiveButton.setEnabled(false);
            }
        });

        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        Bundle args = getArguments();
        callback.onAkDialogCancelled(InternalUtil.getDialogId(args), InternalUtil.createMandatoryResponses(args));
    }

    @NonNull
    private DialogInterface.OnClickListener wrapOnItemClickListenerIfNeeds(@Nullable String title, @ArrayRes int itemsResId, @NonNull Bundle args) {
        if (eventTrackingEnabled) {
            return new OnItemClickListener(title, itemsResId, callback, args);
        }
        return new OnClickListener(callback, args);
    }

    @NonNull
    private DialogInterface.OnClickListener wrapOnButtonClickListenerIfNeeds(@Nullable String title, @StringRes int buttonResId, @NonNull Bundle args) {
        if (eventTrackingEnabled) {
            return new OnButtonClickListener(title, buttonResId, callback, args);
        }
        return new OnClickListener(callback, args);
    }

    public interface Callback {
        void onAkDialogClicked(int dialogId, int resultCode, Bundle responses);

        void onAkDialogCancelled(int dialogId, Bundle responses);
    }

    public static class Builder {

        private final AppCompatActivity activity;

        /** イベントトラッキングを有効にするか否か. */
        private final boolean eventTrackingEnabled;

        @StyleRes
        private int themeResId = UNKNOWN_RES_ID;

        @DrawableRes
        private int iconResId = UNKNOWN_RES_ID;

        private String title;

        private String message;

        @ArrayRes
        private int iconItemsResId = UNKNOWN_RES_ID;

        @ArrayRes
        private int itemsResId = UNKNOWN_RES_ID;

        @ArrayRes
        private int singleChoiceItemsResId = UNKNOWN_RES_ID;

        @StringRes
        private int positiveBtnTextResId = UNKNOWN_RES_ID;

        @StringRes
        private int negativeBtnTextResId = UNKNOWN_RES_ID;

        @StringRes
        private int neutralBtnTextResId = UNKNOWN_RES_ID;

        private int dialogId = -1;

        private Bundle requests = new Bundle();

        private boolean cancelable = true;

        private String tag = "default";

        public <A extends AppCompatActivity & Callback> Builder(@NonNull final A activity) {
            this(activity, false);
        }

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
        public Builder title(@StringRes int titleResId, @NonNull String... formatArgs) {
            if (titleResId != UNKNOWN_RES_ID) {
                this.title = getContext().getString(titleResId, (Object[]) formatArgs);
            }
            return this;
        }

        @NonNull
        public Builder message(@NonNull String message) {
            this.message = message;
            return this;
        }

        @NonNull
        public Builder message(@StringRes int messageResId, String... formatArgs) {
            if (messageResId != UNKNOWN_RES_ID) {
                this.message = getContext().getString(messageResId, (Object[]) formatArgs);
            }
            return this;
        }

        /**
         * Simple Dialog.
         * <ul>
         * <li>ダイアログの Message を設定すると本メソッドで設定した Item は<b>非表示</b>になるので注意する。</li>
         * </ul>
         *
         * @see <a href="https://material.io/guidelines/components/dialogs.html#dialogs-simple-dialogs">Components–Dialogs</a>
         */
        @NonNull
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
        @NonNull
        public Builder singleChoiceItems(@ArrayRes int itemsResId) {
            this.singleChoiceItemsResId = itemsResId;
            return this;
        }

        @NonNull
        public Builder iconItems(@ArrayRes int iconItemsResId, @ArrayRes int itemsResId) {
            this.iconItemsResId = iconItemsResId;
            this.itemsResId = itemsResId;
            return this;
        }

        @NonNull
        public Builder positiveButton(@StringRes int textResId) {
            this.positiveBtnTextResId = textResId;
            return this;
        }

        @NonNull
        public Builder okButton() {
            return positiveButton(R.string.btn_label_ok);
        }

        @NonNull
        public Builder negativeButton(@StringRes int textResId) {
            this.negativeBtnTextResId = textResId;
            return this;
        }

        @NonNull
        public Builder neutralButton(@StringRes int textResId) {
            this.neutralBtnTextResId = textResId;
            return this;
        }

        @NonNull
        public Builder cancelButton() {
            return negativeButton(R.string.btn_label_cancel);
        }

        @NonNull
        public Builder dialogId(int dialogId) {
            this.dialogId = dialogId;
            return this;
        }

        @NonNull
        public Builder requests(@NonNull Bundle requests) {
            this.requests = requests;
            return this;
        }

        @NonNull
        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        @NonNull
        public Builder tag(@NonNull String tag) {
            this.tag = tag;
            return this;
        }

        @NonNull
        private Context getContext() {
            return activity.getApplicationContext();
        }

        public void show() {
            final Bundle args = new Bundle();
            args.putInt(ARGS_THEME_RES_ID, themeResId);
            args.putInt(ARGS_ICON_RES_ID, iconResId);
            args.putString(ARGS_TITLE, title);
            args.putString(ARGS_MESSAGE, message);
            args.putInt(ARGS_ICON_ITEMS_RES_ID, iconItemsResId);
            args.putInt(ARGS_ITEMS_RES_ID, itemsResId);
            args.putInt(ARGS_SINGLE_CHOICE_ITEMS_RES_ID, singleChoiceItemsResId);
            args.putInt(ARGS_POSITIVE_BTN_TEXT_RES_ID, positiveBtnTextResId);
            args.putInt(ARGS_NEGATIVE_BTN_TEXT_RES_ID, negativeBtnTextResId);
            args.putInt(ARGS_NEUTRAL_BTN_TEXT_RES_ID, neutralBtnTextResId);
            args.putBoolean(ARGS_CANCELABLE, cancelable);
            args.putBundle(ARGS_REQUESTS, requests);
            args.putInt(ARGS_DIALOG_ID, dialogId);
            args.putBoolean(ARGS_EVENT_TRACKING_ENABLED, eventTrackingEnabled);

            final AkDialogFragment fragment = new AkDialogFragment();
            fragment.setArguments(args);
            fragment.show(activity.getSupportFragmentManager(), tag);
        }
    }

    private static class InternalUtil {

        static int getDialogId(@NonNull Bundle args) {
            return args.getInt(ARGS_DIALOG_ID);
        }

        @NonNull
        static ViewMode getViewMode(@NonNull Bundle args) {
            final Bundle requests = args.getBundle(ARGS_REQUESTS);
            if (requests == null) {
                return ViewMode.DEFAULT;
            }
            Serializable enumValue = requests.getSerializable(REQUEST_VIEW_MODE);
            return enumValue != null ? (ViewMode) enumValue : ViewMode.DEFAULT;
        }

        @NonNull
        static Bundle createMandatoryResponses(@NonNull Bundle args) {
            Bundle requests = args.getBundle(ARGS_REQUESTS);
            if (requests == null) {
                return new Bundle();
            }
            Bundle responses = new Bundle();
            responses.putSerializable(RESPONSE_VIEW_MODE, requests.getSerializable(REQUEST_VIEW_MODE));
            responses.putParcelable(RESPONSE_INTENT, requests.getParcelable(REQUEST_INTENT));
            return responses;
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

        @NonNull
        Bundle createResponses(@NonNull DialogInterface dialog) {
            return InternalUtil.createMandatoryResponses(args);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            callback.onAkDialogClicked(InternalUtil.getDialogId(args), which, createResponses(dialog));
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

        private int checkedItemId = NO_ITEM_ID;

        private String checkedItem;

        private OnItemClickListener(@Nullable String title, @ArrayRes int itemsResId, @NonNull Callback callback, @NonNull Bundle args) {
            super(callback, args);
            this.title = title;
            this.itemsResId = itemsResId;
        }

        @NonNull
        @Override
        Bundle createResponses(@NonNull DialogInterface dialog) {
            Bundle responses = InternalUtil.createMandatoryResponses(args);
            responses.putInt(RESPONSE_CHECKED_ITEM_ID, checkedItemId);
            responses.putString(RESPONSE_CHECKED_ITEM_VALUE, checkedItem);
            return responses;
        }

        @Override
        public final void onClick(@NonNull DialogInterface dialog, int which) {
            AlertDialog alertDialog = (AlertDialog) dialog;
            if (itemsResId > 0) {
                Context context = alertDialog.getContext();
                this.checkedItemId = which;
                this.checkedItem = context.getResources().getStringArray(itemsResId)[which];
                Log.i(TAG, "Event tracking: " + title + " [" + checkedItem + "]");
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

        private int checkedItemId = NO_ITEM_ID;

        private String checkedItem;

        private OnButtonClickListener(@Nullable String title, @StringRes int btnTextResId, @NonNull Callback callback, @NonNull Bundle args) {
            super(callback, args);
            this.title = title;
            this.btnTextResId = btnTextResId;
        }

        @NonNull
        @Override
        Bundle createResponses(@NonNull DialogInterface dialog) {
            Bundle responses = InternalUtil.createMandatoryResponses(args);
            responses.putInt(RESPONSE_CHECKED_ITEM_ID, checkedItemId);
            responses.putString(RESPONSE_CHECKED_ITEM_VALUE, checkedItem);
            return responses;
        }

        @Override
        public final void onClick(@NonNull DialogInterface dialog, int which) {
            AlertDialog alertDialog = (AlertDialog) dialog;
            ListView listView = alertDialog.getListView();
            if (listView != null) {
                int position = listView.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    this.checkedItemId = position;
                    DialogItemAdapter.Item item = (DialogItemAdapter.Item) listView.getAdapter().getItem(position);
                    this.checkedItem = item.text;
                    Log.i(TAG, "Event tracking: " + title + " [" + position + ":" + checkedItem + "]");
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

    private static class DialogItemAdapter extends BaseAdapter {

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

        private static class Item {

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
}
