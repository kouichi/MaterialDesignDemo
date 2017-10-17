package jp.aknot.materialdesigndemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import jp.aknot.materialdesigndemo.adapter.SimpleListAdapter;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SimpleListAdapter.Item[] items = {
        new SimpleListAdapter.Item("Buttons")
    };
    SimpleListAdapter adapter = new SimpleListAdapter(this, items);

    ListView listView = findViewById(R.id.list_view);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener((adapterView, view, position, id) -> {
      switch (position) {
        case 0:
          startNextActivity(ButtonActivity.class, view);
          break;
        default:
          break;
      }
    });

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.inflateMenu(R.menu.menu_main);
    toolbar.setOnMenuItemClickListener(item -> {
      switch (item.getItemId()) {
        case R.id.menu_action_refresh_text:
          Toast.makeText(this, R.string.toast_pressed_menu_item_msg, Toast.LENGTH_SHORT).show();
          return true;
        case R.id.menu_action_other_menu:
          startNextActivity(MenuActivity.class, toolbar);
          return true;
        case R.id.menu_action_refresh_text_disabled:
        default:
          return false;
      }
    });
  }

  private void startNextActivity(@NonNull Class<? extends AppCompatActivity> clazz, View view) {
    Intent intent = new Intent(this, clazz);
    startActivity(intent);
  }
}
