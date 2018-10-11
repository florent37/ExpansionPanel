package florent37.github.com.expansionpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ExpansionPanelMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expansion_panel_activity_main);

        findViewById(R.id.sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpansionPanelMainActivity.this, ExpansionPanelSampleActivity.class));
            }
        });

        findViewById(R.id.sample_viewgroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpansionPanelMainActivity.this, ExpansionPanelSampleActivityViewGroup.class));
            }
        });

        findViewById(R.id.programmatically).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpansionPanelMainActivity.this, ExpansionPanelSampleActivityProgrammatically.class));
            }
        });

        findViewById(R.id.recyclerView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpansionPanelMainActivity.this, ExpansionPanelSampleActivityRecycler.class));
            }
        });
    }
}
