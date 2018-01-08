package florent37.github.com.expansionpanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sample)
    public void onSampleClicked(){
        startActivity(new Intent(this, SampleActivity.class));
    }

    @OnClick(R.id.sample_viewgroup)
    public void onSampleViewGroupClicked(){
        startActivity(new Intent(this, SampleActivityViewGroup.class));
    }

    @OnClick(R.id.programmatically)
    public void onProgrammaticallyClicked(){
        startActivity(new Intent(this, SampleActivityProgrammatically.class));
    }

    @OnClick(R.id.recyclerView)
    public void onRecyclerViewClicked(){
        startActivity(new Intent(this, SampleActivityRecycler.class));
    }
}
