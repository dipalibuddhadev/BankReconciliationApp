Bank Reconciliation App

This is an Android Developer Exercise for bank reconciliation.
The purpose is to demonstrate logic for matching bank transactions against accounting records (invoices, bills, etc.).

Features Implemented

Dynamic Remaining Total

When user selects/deselects a record, its amount is subtracted/added to the remaining total.

Auto-Select Exact Match

If a single accounting record matches the transaction total, it is auto-selected on launch.

Auto-Select Subset Match

If multiple records together sum up to the transaction total, they are auto-selected automatically.

CheckBox UI

Users select/deselect records via checkboxes (instead of background highlight).

User-Friendly UI

Clean RecyclerView list with description and amount.

Real-time updates to remaining balance at the top.

Tech Stack

Language: Java

Framework: Android SDK

UI: RecyclerView + CheckBox + TextView

Algorithm: Subset Sum (Backtracking) for auto-selection
