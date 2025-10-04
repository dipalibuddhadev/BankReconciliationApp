package co.nz.tsb.interview.bankrecmatchmaker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReconciliationViewModel : ViewModel() {
    private val _transactionTotal = MutableLiveData<Double>()
    val transactionTotal: LiveData<Double> get() = _transactionTotal

    private val _remainingTotal = MutableLiveData<Double>()
    val remainingTotal: LiveData<Double> get() = _remainingTotal

    private val _records = MutableLiveData<List<AccountingRecord>>()
    val records: LiveData<List<AccountingRecord>> get() = _records

    fun loadData(transaction: Double, recordsList: List<AccountingRecord>) {
        _transactionTotal.value = transaction
        _remainingTotal.value = transaction
        _records.value = recordsList

        autoSelectExactMatch()
        autoSelectSubsetMatch()
    }

    fun toggleSelection(record: AccountingRecord) {
        val updated = _records.value?.map {
            if (it.id == record.id) it.copy(isSelected = !it.isSelected) else it
        } ?: emptyList()

        _records.value = updated
        recalcRemaining(updated)
    }

    private fun recalcRemaining(records: List<AccountingRecord>) {
        val selectedSum = records.filter { it.isSelected }.sumOf { it.amount }
        _remainingTotal.value = (_transactionTotal.value ?: 0.0) - selectedSum
    }

    private fun autoSelectExactMatch() {
        val transaction = _transactionTotal.value ?: return
        val records = _records.value ?: return

        val match = records.firstOrNull { it.amount == transaction }
        if (match != null) {
            val updated = records.map {
                if (it.id == match.id) it.copy(isSelected = true) else it
            }
            _records.value = updated
            _remainingTotal.value = 0.0
        }
    }

    fun autoSelectSubsetMatch() {
        val recordsList = _records.value ?: return
        val remaining = _remainingTotal.value ?: return

        val subset = findSubsetMatch(recordsList, remaining)
        if (subset != null) {
            val updated = recordsList.map {
                if (subset.contains(it)) it.copy(isSelected = true) else it
            }
            _records.value = updated
            recalcRemaining(updated)
        }
    }

    /**
     * Returns a list of records whose amounts sum exactly to remaining total.
     * Returns null if no subset matches.
     */
    fun findSubsetMatch(records: List<AccountingRecord>, target: Double): List<AccountingRecord>? {
        val n = records.size
        val amounts = records.map { it.amount }

        // DP table: dp[i][j] = true if sum j can be made using first i records
        val dp = Array(n + 1) { BooleanArray((target.toInt() + 1)) }
        dp[0][0] = true

        for (i in 1..n) {
            for (j in 0..target.toInt()) {
                dp[i][j] = dp[i - 1][j] || (j >= amounts[i - 1] && dp[i - 1][j - amounts[i - 1].toInt()])
            }
        }

        if (!dp[n][target.toInt()]) return null // no subset sums exactly

        // Backtrack to find which records are selected
        val selected = mutableListOf<AccountingRecord>()
        var j = target.toInt()
        for (i in n downTo 1) {
            if (!dp[i - 1][j]) {
                selected.add(records[i - 1])
                j -= amounts[i - 1].toInt()
            }
        }
        return selected
    }
}