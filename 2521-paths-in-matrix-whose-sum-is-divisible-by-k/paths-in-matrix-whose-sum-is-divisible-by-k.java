class Solution {
    private static final int MOD = 1_000_000_007;

    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] prev = new int[n][k];

        for (int i = 0; i < m; i++) {
            int[][] cur = new int[n][k];

            for (int j = 0; j < n; j++) {
                int add = grid[i][j] % k;

                if (i == 0 && j == 0) {
                    cur[0][add] = 1;
                    continue;
                }

                // from top: prev row, same column
                if (i > 0) {
                    for (int r = 0; r < k; r++) {
                        int count = prev[j][r];
                        if (count > 0) {
                            int newR = (r + add) % k;
                            cur[j][newR] = (cur[j][newR] + count) % MOD;
                        }
                    }
                }

                // from left: same row, previous column
                if (j > 0) {
                    for (int r = 0; r < k; r++) {
                        int count = cur[j - 1][r];
                        if (count > 0) {
                            int newR = (r + add) % k;
                            cur[j][newR] = (cur[j][newR] + count) % MOD;
                        }
                    }
                }
            }

            prev = cur;
        }

        return prev[n - 1][0]; // remainder 0 means divisible by k
    }
}

