package florent37.github.com.expansionpanel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class ExpansionPanelSampleActivityViewGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expansion_panel_sample_main_viewgroup);
        ButterKnife.bind(this);
    }
}
