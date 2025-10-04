package co.nz.tsb.interview.bankrecmatchmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter(
    private var items: List<AccountingRecord>,
    private val onItemClick: (AccountingRecord) -> Unit
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val desc: TextView = view.findViewById(R.id.tvDesc)
        val amount: TextView = view.findViewById(R.id.tvAmount)
        val checkbox: CheckBox = view.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val item = items[position]
        holder.desc.text = item.description
        holder.amount.text = "$${item.amount}"
        holder.checkbox.isChecked = item.isSelected

        holder.itemView.setOnClickListener { onItemClick(item) }
        holder.checkbox.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<AccountingRecord>) {
        this.items = newItems
        notifyDataSetChanged()
    }

}