class Solution {
    public long maxSubarraySum(int[] nums, int k) {
        int n = nums.length;
        long[] minPrefix = new long[k];
        Arrays.fill(minPrefix, Long.MAX_VALUE);

        long prefix = 0L;
        long ans = Long.MIN_VALUE;
        minPrefix[0] = 0L;
        for (int i = 1; i <= n; i++) {
            prefix += nums[i - 1];
            int r = i % k;              
            if (minPrefix[r] != Long.MAX_VALUE) {
                long candidate = prefix - minPrefix[r];
                if (candidate > ans) {
                    ans = candidate;
                }
            }
            if (prefix < minPrefix[r]) {
                minPrefix[r] = prefix;
            }
        }
        return ans == Long.MIN_VALUE ? 0L : ans;
    }
}