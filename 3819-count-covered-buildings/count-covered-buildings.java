import java.util.*;
class Solution {
    public int countCoveredBuildings(int n, int[][] buildings) {
        Map<Integer, TreeSet<Integer>> rowSets = new HashMap<>();
        Map<Integer, TreeSet<Integer>> colSets = new HashMap<>();
        for (int[] b : buildings) {
            int x = b[0], y = b[1];
            rowSets.computeIfAbsent(x, k -> new TreeSet<>()).add(y);
            colSets.computeIfAbsent(y, k -> new TreeSet<>()).add(x);
        }
        int covered = 0;
        for (int[] b : buildings) {
            int x = b[0], y = b[1];

            TreeSet<Integer> row = rowSets.get(x);
            TreeSet<Integer> col = colSets.get(y);
            Integer left = row.lower(y);
            Integer right = row.higher(y);

            Integer above = col.lower(x);
            Integer below = col.higher(x);

            if (left != null && right != null && above != null && below != null) {
                covered++;
            }
        }
        return covered;
    }
}
