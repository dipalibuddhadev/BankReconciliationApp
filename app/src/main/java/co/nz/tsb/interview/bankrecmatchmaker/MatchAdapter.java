package co.nz.tsb.interview.bankrecmatchmaker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mainText;
        private TextView total;
        private TextView subtextLeft;
        private TextView subtextRight;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mainText = itemView.findViewById(R.id.text_main);
            total = itemView.findViewById(R.id.text_total);
            subtextLeft = itemView.findViewById(R.id.text_sub_left);
            subtextRight = itemView.findViewById(R.id.text_sub_right);
            checkBox = itemView.findViewById(R.id.checkbox);

        }

        public void bind(MatchItem matchItem) {
            mainText.setText(matchItem.getPaidTo());
            total.setText(Float.toString(matchItem.getTotal()));
            subtextLeft.setText(matchItem.getTransactionDate());
            subtextRight.setText(matchItem.getDocType());
        }

    }

    public interface OnRecordClickListener {
        void onRecordClick(MatchItem record);
    }

    private List<MatchItem> matchItems;
    private OnRecordClickListener listener;

    public MatchAdapter(List<MatchItem> matchItems, OnRecordClickListener listener) {
        this.matchItems = matchItems;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_match, parent, false);;
        //LinearLayout listItem = (LinearLayout) layoutInflater.inflate(R.layout.list_item_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MatchItem matchItem = matchItems.get(position);
        holder.bind(matchItem);
        //holder.itemView.setBackgroundColor(matchItem.isSelected() ? Color.LTGRAY : Color.WHITE);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecordClick(matchItem);
            }
        });*/
        // Bind checkbox state
        holder.checkBox.setOnCheckedChangeListener(null); // avoid recycling issues
        holder.checkBox.setChecked(matchItem.isSelected());

        // Handle checkbox click
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchItem.setSelected(isChecked);
            listener.onRecordClick(matchItem);
        });
    }

    @Override
    public int getItemCount() {
        return matchItems.size();
    }

}