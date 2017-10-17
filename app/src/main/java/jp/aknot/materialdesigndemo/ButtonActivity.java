package jp.aknot.materialdesigndemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ButtonActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_button);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_vector_tinted_24dp);
    toolbar.setNavigationOnClickListener(view -> finish());
  }
}
