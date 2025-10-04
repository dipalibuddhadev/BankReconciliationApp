package co.nz.tsb.interview.bankrecmatchmaker;

import java.util.ArrayList;
import java.util.List;

public class SubsetMatchHelper {
    // Finds one subset of records that sum up to target
    public static List<MatchItem> findSubsetMatch(List<MatchItem> records, double target) {
        List<MatchItem> result = new ArrayList<>();
        if (backtrack(records, target, 0, result)) {
            return result; // Found valid subset
        }
        return new ArrayList<>(); // No match
    }

    // Recursive backtracking
    private static boolean backtrack(List<MatchItem> records, double target, int index, List<MatchItem> current) {
        if (target == 0) {
            return true; // Found exact match
        }
        if (target < 0 || index >= records.size()) {
            return false; // Overshoot or end of list
        }

        // Include current record
        current.add(records.get(index));
        if (backtrack(records, target - records.get(index).getTotal(), index + 1, current)) {
            return true;
        }

        // Exclude current record (backtrack)
        current.remove(current.size() - 1);
        return backtrack(records, target, index + 1, current);
    }
}
