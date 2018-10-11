package florent37.github.com.expansionpanel;

import android.graphics.Color;
import android.os.Bundle;
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
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import static florent37.github.com.expansionpanel.Utils.dpToPx;

public class ExpansionPanelSampleActivityProgrammatically extends AppCompatActivity {

    ViewGroup dynamicLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expansion_panel_sample_programmatically);
        this.dynamicLayoutContainer = findViewById(R.id.dynamicLayoutContainer);

        final ExpansionLayout ex1 = addDynamicLayout();
        final ExpansionLayout ex2 = addDynamicLayout();

        //example of how to add a listener
        ex1.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        final ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();
        expansionLayoutCollection.add(ex1).add(ex2);
        expansionLayoutCollection.openOnlyOne(true);
    }

    public ExpansionLayout addDynamicLayout() {

        final ExpansionHeader expansionHeader = createExpansionHeader();
        dynamicLayoutContainer.addView(expansionHeader, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final ExpansionLayout expansionLayout = createExpansionLayout();
        dynamicLayoutContainer.addView(expansionLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        expansionHeader.setExpansionLayout(expansionLayout);

        return expansionLayout;

    }

    @NonNull
    private ExpansionLayout createExpansionLayout() {
        final ExpansionLayout expansionLayout = new ExpansionLayout(this);

        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        expansionLayout.addView(layout, ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(this, 48)); //equivalent to addView(linearLayout)

        final TextView text = new TextView(this);
        text.setText("Content");
        text.setGravity(Gravity.CENTER);
        text.setTextColor(Color.parseColor("#3E3E3E"));
        text.setBackgroundColor(Color.parseColor("#EEEEEE"));
        layout.addView(text, ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(this, 200));

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View child = new View(ExpansionPanelSampleActivityProgrammatically.this);
                child.setBackgroundColor(Color.BLACK);
                layout.addView(child, ViewGroup.LayoutParams.MATCH_PARENT, 100);
            }
        });

        layout.addView(LayoutInflater.from(this).inflate(R.layout.expansion_panel_sample_panel, layout, false));

        return expansionLayout;
    }

    @NonNull
    private ExpansionHeader createExpansionHeader() {
        final ExpansionHeader expansionHeader = new ExpansionHeader(this);
        expansionHeader.setBackgroundColor(Color.WHITE);

        expansionHeader.setPadding(dpToPx(this, 16), dpToPx(this, 8), dpToPx(this, 16), dpToPx(this, 8));

        final RelativeLayout layout = new RelativeLayout(this);
        expansionHeader.addView(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //equivalent to addView(linearLayout)

        //image
        final ImageView expansionIndicator = new AppCompatImageView(this);
        expansionIndicator.setImageResource(R.drawable.ic_expansion_header_indicator_grey_24dp);
        final RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        imageLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layout.addView(expansionIndicator, imageLayoutParams);

        //label
        final TextView text = new TextView(this);
        text.setText("Trip name");
        text.setTextColor(Color.parseColor("#3E3E3E"));

        final RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

        layout.addView(text, textLayoutParams);

        expansionHeader.setExpansionHeaderIndicator(expansionIndicator);
        return expansionHeader;
    }
}
