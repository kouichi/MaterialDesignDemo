package jp.aknot.materialdesigndemo.presentation;

import static jp.aknot.materialdesigndemo.widget.AkDialogFragment.REQUEST_VIEW_MODE;
import static jp.aknot.materialdesigndemo.widget.AkDialogFragment.REQUEST_WEBVIEW_LOAD_URL;
import static jp.aknot.materialdesigndemo.widget.helper.DialogResHolder.UNKNOWN_RES_ID;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.aknot.materialdesigndemo.R;
import jp.aknot.materialdesigndemo.presentation.adapter.SimpleListAdapter;
import jp.aknot.materialdesigndemo.widget.AkDialogFragment;
import jp.aknot.materialdesigndemo.widget.helper.AkDialogHelper;
import jp.aknot.materialdesigndemo.widget.helper.AlertDialogResHolder;
import jp.aknot.materialdesigndemo.widget.helper.ConfirmationDialogResHolder;
import jp.aknot.materialdesigndemo.widget.helper.DialogResHolder;
import jp.aknot.materialdesigndemo.widget.helper.ItemListDialogResHolder;
import jp.aknot.materialdesigndemo.widget.helper.SimpleDialogResHolder;

public class DialogActivity extends AppCompatActivity implements AkDialogFragment.Callback {

    private static final String TAG = "@" + DialogActivity.class.getSimpleName();

    private static final int DLG_1_ALERT_ID = 1;
    private static final int DLG_2_ALERT_ID = 2;
    private static final int DLG_3_ALERT_ID = 3;
    private static final int DLG_4_ALERT_ID = 4;
    private static final int DLG_5_ALERT_ID = 5;
    private static final int DLG_6_ALERT_ID = 6;
    private static final int DLG_7_CONFIRMATION_ID = 7;
    private static final int DLG_8_CONFIRMATION_ID = 8;
    private static final int DLG_9_SIMPLE_ID = 9;
    private static final int DLG_10_ITEM_LIST_ID = 10;
    private static final int DLG_11_ALERT_ID = 11;
    private static final int DLG_12_CONFIRMATION_ID = 12;
    private static final int DLG_13_SIMPLE_ID = 13;
    private static final int DLG_14_ITEM_LIST_ID = 14;
    private static final int DLG_15_ALERT_ID = 15;
    private static final int DLG_16_ALERT_ID = 16;

    private static final SparseArray<DialogResHolder> DIALOG_RES_HOLDER_MAP = new SparseArray<>();

    static {
        DIALOG_RES_HOLDER_MAP.put(DLG_1_ALERT_ID,
                new AlertDialogResHolder(
                        R.string.dialog_1_alert_title,
                        R.string.dialog_1_alert_msg,
                        R.string.dialog_1_alert_btn_positive,
                        R.string.dialog_1_alert_btn_negative,
                        UNKNOWN_RES_ID));
        DIALOG_RES_HOLDER_MAP.put(DLG_2_ALERT_ID,
                new AlertDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert,
                        R.string.dialog_2_alert_title,
                        R.string.dialog_2_alert_msg,
                        R.string.dialog_2_alert_btn_positive,
                        R.string.dialog_2_alert_btn_negative,
                        UNKNOWN_RES_ID));
        DIALOG_RES_HOLDER_MAP.put(DLG_3_ALERT_ID,
                new AlertDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert_Warn,
                        R.string.dialog_3_alert_title,
                        R.string.dialog_3_alert_msg,
                        R.string.dialog_3_alert_btn_positive,
                        R.string.dialog_3_alert_btn_negative,
                        UNKNOWN_RES_ID));
        DIALOG_RES_HOLDER_MAP.put(DLG_4_ALERT_ID,
                new AlertDialogResHolder(
                        R.string.dialog_4_alert_title,
                        R.string.dialog_4_alert_msg,
                        R.string.dialog_4_alert_btn_positive,
                        R.string.dialog_4_alert_btn_negative,
                        UNKNOWN_RES_ID));
        DIALOG_RES_HOLDER_MAP.put(DLG_5_ALERT_ID,
                new AlertDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert,
                        R.string.dialog_5_alert_title,
                        R.string.dialog_5_alert_msg,
                        R.string.dialog_5_alert_btn_positive,
                        R.string.dialog_5_alert_btn_negative,
                        UNKNOWN_RES_ID));
        DIALOG_RES_HOLDER_MAP.put(DLG_6_ALERT_ID,
                new AlertDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert_Warn,
                        R.string.dialog_6_alert_title,
                        R.string.dialog_6_alert_msg,
                        R.string.dialog_6_alert_btn_positive,
                        R.string.dialog_6_alert_btn_negative,
                        UNKNOWN_RES_ID));
        DIALOG_RES_HOLDER_MAP.put(DLG_7_CONFIRMATION_ID,
                new ConfirmationDialogResHolder(R.string.dialog_7_confirmation_title,
                        R.array.dialog_7_confirmation_items));
        DIALOG_RES_HOLDER_MAP.put(DLG_8_CONFIRMATION_ID,
                new ConfirmationDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert,
                        R.string.dialog_8_confirmation_title,
                        R.array.dialog_8_confirmation_items));
        DIALOG_RES_HOLDER_MAP.put(DLG_9_SIMPLE_ID,
                new SimpleDialogResHolder(R.string.dialog_9_simple_title,
                        R.array.dialog_9_simple_items));
        DIALOG_RES_HOLDER_MAP.put(DLG_10_ITEM_LIST_ID,
                new ItemListDialogResHolder(R.string.dialog_10_item_list_title,
                        R.array.dialog_10_item_list_item_drawables,
                        R.array.dialog_10_item_list_items));
        DIALOG_RES_HOLDER_MAP.put(DLG_11_ALERT_ID,
                new AlertDialogResHolder(
                        R.string.dialog_11_alert_title,
                        R.string.dialog_11_alert_msg,
                        R.string.dialog_11_alert_btn_positive,
                        R.string.dialog_11_alert_btn_negative,
                        UNKNOWN_RES_ID));
        DIALOG_RES_HOLDER_MAP.put(DLG_12_CONFIRMATION_ID,
                new ConfirmationDialogResHolder(
                        R.string.dialog_12_confirmation_title,
                        R.array.dialog_12_confirmation_items));
        DIALOG_RES_HOLDER_MAP.put(DLG_13_SIMPLE_ID,
                new SimpleDialogResHolder(
                        R.string.dialog_13_simple_title,
                        R.array.dialog_13_simple_items));
        DIALOG_RES_HOLDER_MAP.put(DLG_14_ITEM_LIST_ID,
                new ItemListDialogResHolder(
                        R.string.dialog_14_item_list_title,
                        R.array.dialog_14_item_list_item_drawables,
                        R.array.dialog_14_item_list_items));
        DIALOG_RES_HOLDER_MAP.put(DLG_15_ALERT_ID,
                new AlertDialogResHolder(
                        R.string.dialog_15_alert_title,
                        UNKNOWN_RES_ID,
                        R.string.dialog_15_alert_btn_positive,
                        UNKNOWN_RES_ID,
                        UNKNOWN_RES_ID));
        DIALOG_RES_HOLDER_MAP.put(DLG_16_ALERT_ID,
                new AlertDialogResHolder(
                        R.string.dialog_16_alert_title,
                        R.string.dialog_16_alert_msg,
                        R.string.dialog_16_alert_btn_positive,
                        R.string.dialog_16_alert_btn_negative,
                        UNKNOWN_RES_ID));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        int index = 1;
        int size = DIALOG_RES_HOLDER_MAP.size();
        List<SimpleListAdapter.Item> itemList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DialogResHolder holder = DIALOG_RES_HOLDER_MAP.valueAt(i);
            String prefix = index + ". (" + holder.getClass().getSimpleName().substring(0, 1) + ") ";
            String title = holder.titleResId != UNKNOWN_RES_ID ? getString(holder.titleResId) : null;
            if (TextUtils.isEmpty(title)) {
                if (holder instanceof AlertDialogResHolder) {
                    title = getString(((AlertDialogResHolder) holder).msgResId);
                }
            }
            itemList.add(new SimpleListAdapter.Item(prefix + title));
            index++;
        }
        SimpleListAdapter.Item[] items = itemList.toArray(new SimpleListAdapter.Item[itemList.size()]);
        SimpleListAdapter adapter = new SimpleListAdapter(this, items);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            int dialogId = position + 1;
            DialogResHolder dialogResHolder = DIALOG_RES_HOLDER_MAP.get(dialogId);
            Bundle requests = new Bundle();
            if (dialogId >= DLG_11_ALERT_ID) {
                switch (dialogId) {
                    case DLG_11_ALERT_ID:
                        requests.putStringArray(AkDialogFragment.REQUEST_TITLE_VALUE_ARRAY, new String[]{"AKNOT"});
                        requests.putStringArray(AkDialogFragment.REQUEST_MESSAGE_VALUE_ARRAY, new String[]{"AKNOT"});
                        break;
                    case DLG_12_CONFIRMATION_ID:
                    case DLG_13_SIMPLE_ID:
                    case DLG_14_ITEM_LIST_ID:
                        requests.putStringArray(AkDialogFragment.REQUEST_TITLE_VALUE_ARRAY, new String[]{"AKNOT"});
                        break;
                    case DLG_15_ALERT_ID:
                        requests.putSerializable(REQUEST_VIEW_MODE, AkDialogFragment.ViewMode.WEBVIEW);
                        requests.putString(REQUEST_WEBVIEW_LOAD_URL, "https://www.google.co.jp/");
                        break;
                    case DLG_16_ALERT_ID:
                        requests.putSerializable(REQUEST_VIEW_MODE, AkDialogFragment.ViewMode.PASSWORD_INPUT);
                        break;
                    default:
                        break;
                }
            }
            AkDialogHelper.showDialog(this, dialogId, dialogResHolder, requests);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_vector_tinted_24dp);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onAkDialogClicked(int dialogId, int resultCode, Bundle responses) {
        Log.d(TAG, "onAkDialogClicked: dialogId=" + dialogId + ", resultCode=" + resultCode + ", requests=" + responses);
        String action = null;
        switch (dialogId) {
            case DLG_1_ALERT_ID:
            case DLG_2_ALERT_ID:
            case DLG_3_ALERT_ID:
            case DLG_4_ALERT_ID:
            case DLG_5_ALERT_ID:
            case DLG_6_ALERT_ID:
            case DLG_11_ALERT_ID:
            case DLG_15_ALERT_ID:
            case DLG_16_ALERT_ID:
                switch (resultCode) {
                    case DialogInterface.BUTTON_POSITIVE:
                        action = "pressed Positive Button";
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        action = "pressed Negative Button";
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        action = "pressed Neutral Button";
                        break;
                    default:
                        break;
                }
                break;
            case DLG_7_CONFIRMATION_ID:
            case DLG_8_CONFIRMATION_ID:
            case DLG_12_CONFIRMATION_ID:
                switch (resultCode) {
                    case DialogInterface.BUTTON_POSITIVE:
                        int checkedItemId = responses.getInt(AkDialogFragment.RESPONSE_CHECKED_ITEM_ID);
                        String checkedItemValue = responses.getString(AkDialogFragment.RESPONSE_CHECKED_ITEM_VALUE);
                        action = "checked " + checkedItemId + ":" + checkedItemValue + " and pressed Positive Button";
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        action = "pressed Negative Button";
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        action = "pressed Neutral Button";
                        break;
                    default:
                        break;
                }
                break;
            case DLG_9_SIMPLE_ID:
            case DLG_10_ITEM_LIST_ID:
            case DLG_13_SIMPLE_ID:
            case DLG_14_ITEM_LIST_ID:
                if (responses != null) {
                    int checkedItemId = responses.getInt(AkDialogFragment.RESPONSE_CHECKED_ITEM_ID);
                    String checkedItemValue = responses.getString(AkDialogFragment.RESPONSE_CHECKED_ITEM_VALUE);
                    action = "checked " + checkedItemId + ":" + checkedItemValue;
                }
                break;
            default:
                break;
        }
        String text = "[dialogId:" + dialogId + "] " + action;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Log.d(TAG, text);
        Log.d(TAG, "responses: " + responses);
    }

    @Override
    public void onAkDialogCancelled(int dialogId, Bundle responses) {
        String text = "[dialogId:" + dialogId + "] Cancelled";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Log.d(TAG, text);
        Log.d(TAG, "responses: " + responses);
    }
}
