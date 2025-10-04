package co.nz.tsb.interview.bankrecmatchmaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ReconciliationActivity : AppCompatActivity() {

    private val viewModel: ReconciliationViewModel by viewModels()
    private lateinit var adapter: RecordAdapter
    private lateinit var tvRemaining: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reconciliation)
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        tvRemaining = findViewById(R.id.tvRemaining)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        /*tvRemaining.setOnClickListener(View.OnClickListener { view ->
            // Do some work here
            startActivity(Intent(this, FindMatchActivity::class.java))
        })*/



        val window: Window = this@ReconciliationActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@ReconciliationActivity, R.color.colorPrimary)

        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false)
        getSupportActionBar()!!.setTitle(R.string.title_find_match)

        adapter = RecordAdapter(emptyList()) { record ->
            viewModel.toggleSelection(record)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe changes
        viewModel.records.observe(this, Observer {
            adapter.updateData(it)
        })

        viewModel.remainingTotal.observe(this, Observer {
            tvRemaining.text = "Remaining: $${it}"
        })

        // Load sample data
        val sampleRecords = listOf(
            AccountingRecord(1, "Invoice #2510101", 40.0),
            AccountingRecord(2, "Invoice #2510102", 60.0),
            AccountingRecord(3, "Invoice #2510103", 100.0),
            AccountingRecord(4, "Invoice #2510104", 70.0),
            AccountingRecord(5, "Invoice #2510105", 30.0)
        )
        viewModel.loadData(200.0, sampleRecords)
    }
}