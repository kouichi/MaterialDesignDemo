package jp.aknot.materialdesigndemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import jp.aknot.materialdesigndemo.adapter.IconListAdapter;
import jp.aknot.materialdesigndemo.adapter.SimpleListAdapter;

public class DialogActivity extends AppCompatActivity implements AkDialogFragment.Callback {

    private static final String TAG = "@" + DialogActivity.class.getSimpleName();

    private static final int REQ_POSITIVE_NEGATIVE_BUTTONS_CODE = 1;
    private static final int REQ_STACKED_FULL_WIDTH_BUTTONS_CODE = 2;
    private static final int REQ_SINGLE_CHOICES_ITEMS_CODE = 3;
    private static final int REQ_ICON_ITEMS_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        SimpleListAdapter.Item[] items = {
                new SimpleListAdapter.Item("Positive/Negative Buttons"),
                new SimpleListAdapter.Item("Stacked Full-width Buttons"),
                new SimpleListAdapter.Item("Single Choice Items"),
                new SimpleListAdapter.Item("Icon Items"),
        };
        SimpleListAdapter adapter = new SimpleListAdapter(this, items);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            switch (position) {
                case 0:
                    new AkDialogFragment.Builder(DialogActivity.this, true)
                            .theme(R.style.MyThemeOverlay_Dialog_Alert)
                            .title(R.string.dialog_positive_negative_buttons_title)
                            .message(R.string.dialog_positive_negative_buttons_msg)
                            .positiveButton(R.string.btn_label_agree)
                            .negativeButton(R.string.btn_label_disagree)
                            .cancelable(false)
                            .requestCode(REQ_POSITIVE_NEGATIVE_BUTTONS_CODE)
                            .show();
                    break;
                case 1:
                    new AkDialogFragment.Builder(DialogActivity.this, true)
                            .theme(R.style.MyThemeOverlay_Dialog_Alert_Warn)
                            .title(R.string.dialog_stacked_full_width_buttons_title)
                            .message(R.string.dialog_stacked_full_width_buttons_msg)
                            .positiveButton(R.string.dialog_stacked_full_width_buttons_btn_positive)
                            .negativeButton(R.string.dialog_stacked_full_width_buttons_btn_negative)
                            .requestCode(REQ_STACKED_FULL_WIDTH_BUTTONS_CODE)
                            .show();
                    break;
                case 2:
                    new AkDialogFragment.Builder(DialogActivity.this)
                            .title(R.string.dialog_single_choices_title)
                            .singleChoiceItems(R.array.dialog_single_choices_items)
                            .okButton()
                            .cancelButton()
                            .requestCode(REQ_SINGLE_CHOICES_ITEMS_CODE)
                            .show();
                    break;
                case 3:
                    IconListAdapter.Item[] itemList = {
                            new IconListAdapter.Item(R.drawable.ic_person_vector_tinted_40dp, "taro@example.com"),
                            new IconListAdapter.Item(R.drawable.ic_person_vector_tinted_40dp, "jiro@example.com"),
                            new IconListAdapter.Item(R.drawable.ic_person_vector_tinted_40dp, "hanako@example.com"),
                            new IconListAdapter.Item(R.drawable.ic_add_circle_vector_tinted_40dp, "add account"),
                    };
                    new AkDialogFragment.Builder(this)
                            .title(R.string.dialog_simple_title)
                            .iconItems(itemList)
                            .requestCode(REQ_ICON_ITEMS_CODE)
                            .show();
                    break;
                default:
                    break;
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
