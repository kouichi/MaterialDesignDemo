package jp.aknot.materialdesigndemo.presentation;

import static jp.aknot.materialdesigndemo.widget.helper.DialogResHolder.UNKNOWN_RES_ID;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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

    private static final DialogResHolder[] DIALOG_RES_HOLDERS;

    static {
        DIALOG_RES_HOLDERS = new DialogResHolder[]{
                new AlertDialogResHolder(R.string.dialog_1_alert_title,
                        R.string.dialog_1_alert_msg,
                        R.string.dialog_1_alert_btn_positive,
                        R.string.dialog_1_alert_btn_negative,
                        UNKNOWN_RES_ID),
                new AlertDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert,
                        R.string.dialog_2_alert_title,
                        R.string.dialog_2_alert_msg,
                        R.string.dialog_2_alert_btn_positive,
                        R.string.dialog_2_alert_btn_negative,
                        UNKNOWN_RES_ID),
                new AlertDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert_Warn,
                        R.string.dialog_3_alert_title,
                        R.string.dialog_3_alert_msg,
                        R.string.dialog_3_alert_btn_positive,
                        R.string.dialog_3_alert_btn_negative,
                        UNKNOWN_RES_ID),
                new AlertDialogResHolder(R.string.dialog_4_alert_title,
                        R.string.dialog_4_alert_msg,
                        R.string.dialog_4_alert_btn_positive,
                        R.string.dialog_4_alert_btn_negative,
                        UNKNOWN_RES_ID),
                new AlertDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert,
                        R.string.dialog_5_alert_title,
                        R.string.dialog_5_alert_msg,
                        R.string.dialog_5_alert_btn_positive,
                        R.string.dialog_5_alert_btn_negative,
                        UNKNOWN_RES_ID),
                new AlertDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert_Warn,
                        R.string.dialog_6_alert_title,
                        R.string.dialog_6_alert_msg,
                        R.string.dialog_6_alert_btn_positive,
                        R.string.dialog_6_alert_btn_negative,
                        UNKNOWN_RES_ID),
                new ConfirmationDialogResHolder(R.string.dialog_7_confirmation_title,
                        R.array.dialog_7_confirmation_items),
                new ConfirmationDialogResHolder(R.style.MyThemeOverlay_Dialog_Alert,
                        R.string.dialog_8_confirmation_title,
                        R.array.dialog_8_confirmation_items),
                new SimpleDialogResHolder(R.string.dialog_9_simple_title,
                        R.array.dialog_9_simple_items),
                new ItemListDialogResHolder(R.string.dialog_10_item_list_title,
                        R.array.dialog_10_item_list_item_drawables,
                        R.array.dialog_10_item_list_items),
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        int index = 1;
        List<SimpleListAdapter.Item> itemList = new ArrayList<>(DIALOG_RES_HOLDERS.length);
        for (DialogResHolder holder : DIALOG_RES_HOLDERS) {
            String prefix = index + ". (" + holder.getClass().getSimpleName().substring(0, 1) + ") ";
            String title = getString(holder.titleResId);
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
            DialogResHolder dialogResHolder = DIALOG_RES_HOLDERS[position];
            AkDialogHelper.showDialog(this, position, dialogResHolder);
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
            case 0: // Alert
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
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
            case 6: // Confirmation
            case 7:
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
            case 8: // Simple
            case 9: // ItemList
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
