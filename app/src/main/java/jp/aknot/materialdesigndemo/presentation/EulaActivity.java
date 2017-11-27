package jp.aknot.materialdesigndemo.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;

import jp.aknot.materialdesigndemo.R;
import jp.aknot.materialdesigndemo.presentation.util.TextViewLinkify;

public class EulaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eula);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        TextView textView = findViewById(R.id.textView);
        SparseArray<String> linkTexts = new SparseArray<>();
        linkTexts.put(1, "サービス利用規約");
        linkTexts.put(2, "個人情報保護方針");
        TextViewLinkify.linkify(textView, linkTexts,
                (linkId, linkText) -> Toast.makeText(this, "Clicked: [" + linkId + "] " + linkText, Toast.LENGTH_SHORT).show());
    }
}