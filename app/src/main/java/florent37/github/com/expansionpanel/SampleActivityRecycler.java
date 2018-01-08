package florent37.github.com.expansionpanel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SampleActivityRecycler extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_recycler);

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

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return RecyclerHolder.buildFor(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            holder.bind(list.get(position));
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

            private static final int LAYOUT = R.layout.recycler_cell;

            public static RecyclerHolder buildFor(ViewGroup viewGroup){
                   return new RecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
             }

            public RecyclerHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bind(Object object){

            }
        }
    }
}
