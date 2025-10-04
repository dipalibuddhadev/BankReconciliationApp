package co.nz.tsb.interview.bankrecmatchmaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FindMatchActivity extends AppCompatActivity {

    public static final String TARGET_MATCH_VALUE = "co.nz.tsb.interview.target_match_value";
    private double transactionTotal = 1000.0; // Example total
    private double remainingTotal;
    private MatchAdapter adapter;
    private TextView matchText;
    private List<MatchItem> items;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        Toolbar toolbar = findViewById(R.id.toolbar);
        matchText = findViewById(R.id.match_text);
        recyclerView = findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.title_find_match);


        float target = getIntent().getFloatExtra(TARGET_MATCH_VALUE, 10000f);
        //matchText.setText(getString(R.string.select_matches, (int) transactionTotal));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items = buildMockData();
         adapter = new MatchAdapter(items, this::onRecordSelected);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        remainingTotal = transactionTotal;
        updateRemainingTotal();

        //autoSelectExactMatch();
        // First try exact match
        if (!autoSelectExactMatch()) {
            // If no single exact match found, try subset
            autoSelectSubsetMatch();
        }
        matchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindMatchActivity.this, ReconciliationActivity.class));
            }
        });
    }

    private List<MatchItem> buildMockData() {
        List<MatchItem> items = new ArrayList<>();
        items.add(new MatchItem("City Limousines", "30 Aug", 250, "Sales Invoice"));
        items.add(new MatchItem("Ridgeway University", "12 Sep", 150, "Sales Invoice"));
        items.add(new MatchItem("Cube Land", "22 Sep", 200, "Sales Invoice"));
        items.add(new MatchItem("Bayside Club", "23 Sep", 400, "Sales Invoice"));
        items.add(new MatchItem("SMART Agency", "12 Sep", 350, "Sales Invoice"));
        items.add(new MatchItem("PowerDirect", "11 Sep", 1000, "Sales Invoice"));
        items.add(new MatchItem("PC Complete", "17 Sep", 450, "Sales Invoice"));
        items.add(new MatchItem("Truxton Properties", "17 Sep", 300, "Sales Invoice"));
        items.add(new MatchItem("MCO Cleaning Services", "17 Sep", 100, "Sales Invoice"));
        items.add(new MatchItem("Gateway Motors", "18 Sep", 400, "Sales Invoice"));
        return items;
    }

    private void onRecordSelected(MatchItem record) {
        if (record.isSelected()) {
            remainingTotal -= record.getTotal();
            //record.setSelected(true);
        } else {
            remainingTotal += record.getTotal(); // Deselect case
            //record.setSelected(false);
        }
        updateRemainingTotal();
        //autoSelectExactMatch();
        //autoSelectSubsetMatch();
        //adapter.notifyDataSetChanged();
    }

    private void updateRemainingTotal() {
        matchText.setText("Remaining: $" + remainingTotal);
    }

    // Auto select if a record matches transaction total
    private void autoSelectExactMatch1() {
        for (MatchItem record : items) {
            if (record.getTotal() == transactionTotal) {
                record.setSelected(true);
                remainingTotal -= record.getTotal();
                updateRemainingTotal();
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    // Auto select a single exact match
    private boolean autoSelectExactMatch() {
        for (MatchItem record : items) {
            if (record.getTotal() == transactionTotal) {
                record.setSelected(true);
                remainingTotal -= record.getTotal();
                updateRemainingTotal();
                adapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    private void autoSelectSubsetMatch() {
        List<MatchItem> subset = SubsetMatchHelper.findSubsetMatch(items, transactionTotal);
        if (!subset.isEmpty()) {
            for (MatchItem record : subset) {
                record.setSelected(true);
                remainingTotal -= record.getTotal();
            }
            updateRemainingTotal();
            adapter.notifyDataSetChanged();
        }
    }

}