package co.nz.tsb.interview.bankrecmatchmaker

data class AccountingRecord(
    val id: Int,
    val description: String,
    val amount: Double,
    var isSelected: Boolean = false
)
