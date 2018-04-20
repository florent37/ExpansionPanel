package florent37.github.com.expansionpanel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpansionPanelSampleActivityRecycler extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expansion_panel_sample_recycler);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        //fill with empty objects
        final List<Object> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Object());
        }
        adapter.setItems(list);
    }

    public final static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

        private final List<Object> list = new ArrayList<>();

        private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

        public RecyclerAdapter() {
            expansionsCollection.openOnlyOne(true);
        }

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return RecyclerHolder.buildFor(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            holder.bind(list.get(position));

            expansionsCollection.add(holder.getExpansionLayout());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setItems(List<Object> items) {
            this.list.addAll(items);
            notifyDataSetChanged();
        }

        public final static class RecyclerHolder extends RecyclerView.ViewHolder {

            private static final int LAYOUT = R.layout.expansion_panel_recycler_cell;

            @BindView(R.id.expansionLayout)
            ExpansionLayout expansionLayout;

            public static RecyclerHolder buildFor(ViewGroup viewGroup){
                   return new RecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
             }

            public RecyclerHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bind(Object object){
                expansionLayout.collapse(false);
            }

            public ExpansionLayout getExpansionLayout() {
                return expansionLayout;
            }
        }
    }
}
