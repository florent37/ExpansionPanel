package florent37.github.com.expansionpanel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.ExpansionLayoutCollection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static florent37.github.com.expansionpanel.Utils.dpToPx;

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

    @OnClick(R.id.recyclerView)
    public void onRecyclerViewClicked(){
        startActivity(new Intent(this, SampleActivityRecycler.class));
    }
}
