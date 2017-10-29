package jp.aknot.materialdesigndemo;

import static jp.aknot.materialdesigndemo.helper.DialogResHolder.UNKNOWN_RES_ID;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jp.aknot.materialdesigndemo.adapter.IconListAdapter;
import jp.aknot.materialdesigndemo.adapter.SimpleListAdapter;
import jp.aknot.materialdesigndemo.helper.AlertDialogResHolder;
import jp.aknot.materialdesigndemo.helper.ConfirmationDialogResHolder;
import jp.aknot.materialdesigndemo.helper.DialogResHolder;
import jp.aknot.materialdesigndemo.helper.ItemListDialogResHolder;
import jp.aknot.materialdesigndemo.helper.SimpleDialogResHolder;

public class DialogActivity extends AppCompatActivity implements AkDialogFragment.Callback {

    private static final String TAG = "@" + DialogActivity.class.getSimpleName();

    private static final int REQ_POSITIVE_NEGATIVE_BUTTONS_CODE = 1;
    private static final int REQ_STACKED_FULL_WIDTH_BUTTONS_CODE = 2;
    private static final int REQ_SINGLE_CHOICES_ITEMS_CODE = 3;
    private static final int REQ_ICON_ITEMS_CODE = 4;

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
            DialogResHolder baseHolder = DIALOG_RES_HOLDERS[position];
            if (baseHolder instanceof AlertDialogResHolder) {
                AlertDialogResHolder holder = (AlertDialogResHolder) baseHolder;
                new AkDialogFragment.Builder(this, true)
                        .theme(holder.themeResId)
                        .title(holder.titleResId)
                        .message(holder.msgResId)
                        .positiveButton(holder.positiveBtnTextResId)
                        .negativeButton(holder.negativeBtnTextResId)
                        .neutralButton(holder.neutralBtnTextResId)
                        .cancelable(false)  // Alert は、選択は必須
                        .show();
            } else if (baseHolder instanceof ConfirmationDialogResHolder) {
                ConfirmationDialogResHolder holder = (ConfirmationDialogResHolder) baseHolder;
                new AkDialogFragment.Builder(this, true)
                        .theme(holder.themeResId)
                        .title(holder.titleResId)
                        .singleChoiceItems(holder.itemsResId)
                        .okButton()
                        .cancelButton()
                        .show();
            } else if (baseHolder instanceof SimpleDialogResHolder) {
                SimpleDialogResHolder holder = (SimpleDialogResHolder) baseHolder;
                new AkDialogFragment.Builder(this, true)
                        .theme(holder.themeResId)
                        .title(holder.titleResId)
                        .items(holder.itemsResId)
                        .show();
            } else if (baseHolder instanceof ItemListDialogResHolder) {
                ItemListDialogResHolder holder = (ItemListDialogResHolder) baseHolder;
                List<IconListAdapter.Item> iconItemList = new ArrayList<>();
                TypedArray drawableTypedArray = getResources().obtainTypedArray(holder.itemDrawablesResId);
                TypedArray stringTypedArray = getResources().obtainTypedArray(holder.itemsResId);
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

                new AkDialogFragment.Builder(this, true)
                        .theme(holder.themeResId)
                        .title(holder.titleResId)
                        .iconItems(iconItems)
                        .show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_vector_tinted_24dp);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onAkDialogClicked(int requestCode, int resultCode, Bundle params) {
        Log.d(TAG, "onAkDialogClicked: requestCode=" + requestCode + ", resultCode=" + resultCode + ", params=" + params);
    }

    @Override
    public void onAkDialogCancelled(int requestCode, Bundle params) {
        Log.d(TAG, "onAkDialogCancelled: requestCode=" + requestCode + ", params=" + params);
    }
}
