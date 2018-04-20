package florent37.github.com.expansionpanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpansionPanelMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expansion_panel_activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sample)
    public void onSampleClicked(){
        startActivity(new Intent(this, ExpansionPanelSampleActivity.class));
    }

    @OnClick(R.id.sample_viewgroup)
    public void onSampleViewGroupClicked(){
        startActivity(new Intent(this, ExpansionPanelSampleActivityViewGroup.class));
    }

    @OnClick(R.id.programmatically)
    public void onProgrammaticallyClicked(){
        startActivity(new Intent(this, ExpansionPanelSampleActivityProgrammatically.class));
    }

    @OnClick(R.id.recyclerView)
    public void onRecyclerViewClicked(){
        startActivity(new Intent(this, ExpansionPanelSampleActivityRecycler.class));
    }
}
