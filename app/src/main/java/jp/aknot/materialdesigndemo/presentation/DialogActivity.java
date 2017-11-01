package jp.aknot.materialdesigndemo.presentation;

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
            DialogResHolder dialogResHolder = DIALOG_RES_HOLDER_MAP.valueAt(dialogId);
            AkDialogHelper.showDialog(this, dialogId, dialogResHolder);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_vector_tinted_24dp);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onAkDialogClicked(int dialogId, int resultCode, Bundle params) {
        Log.d(TAG, "onAkDialogClicked: dialogId=" + dialogId + ", resultCode=" + resultCode + ", params=" + params);
        String action = null;
        switch (dialogId) {
            case DLG_1_ALERT_ID:
            case DLG_2_ALERT_ID:
            case DLG_3_ALERT_ID:
            case DLG_4_ALERT_ID:
            case DLG_5_ALERT_ID:
            case DLG_6_ALERT_ID:
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
                switch (resultCode) {
                    case DialogInterface.BUTTON_POSITIVE:
                        int checkedItemId = params.getInt(AkDialogFragment.PARAM_CHECKED_ITEM_ID);
                        String checkedItemValue = params.getString(AkDialogFragment.PARAM_CHECKED_ITEM_VALUE);
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
                if (params != null) {
                    int checkedItemId = params.getInt(AkDialogFragment.PARAM_CHECKED_ITEM_ID);
                    String checkedItemValue = params.getString(AkDialogFragment.PARAM_CHECKED_ITEM_VALUE);
                    action = "checked " + checkedItemId + ":" + checkedItemValue;
                }
                break;
            default:
                break;
        }
        String text = "[dialogId:" + dialogId + "] " + action;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Log.d(TAG, text);
    }

    @Override
    public void onAkDialogCancelled(int dialogId, Bundle params) {
        String text = "[dialogId:" + dialogId + "] Cancelled";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Log.d(TAG, text);
    }
}
