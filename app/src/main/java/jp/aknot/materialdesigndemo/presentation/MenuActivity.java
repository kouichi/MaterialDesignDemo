package jp.aknot.materialdesigndemo.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import jp.aknot.materialdesigndemo.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_menu);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_action_refresh:
                case R.id.menu_action_add:
                    Toast.makeText(this, R.string.toast_pressed_menu_item_msg, Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        });
    }
}
