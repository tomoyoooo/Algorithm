//Leetcode 1155.掷骰子的N种方法
class Solution {
    public int numRollsToTarget(int n, int k, int target) {
        //先用二维
        /*
        int[][] dp = new int[n+1][target+1];
        int mod = 1000000007;
        long tmp = 0;
        //dp[i][j]表示前i组能够恰好为j的方案数
        for(int i = 1; i <= Math.min(k,target); ++i) dp[1][i] = 1;
        for(int i = 2; i <= n; ++i){
            for(int j = 1; j <= target; ++j){
                tmp = 0;
                for(int m = 1; m <= Math.min(k,j); ++m){
                    tmp += dp[i-1][j-m];
                    tmp %= mod;
                }
                dp[i][j] = (int)tmp;
            }
        }
        return dp[n][target];
        */

        //滚动数组
        /*
        int[][] dp = new int[2][target+1];
        int mod = 1000000007;
        long tmp = 0;
        dp[0][0] = 1;
        for(int i = 1; i <= n; ++i){
            for(int j = 0; j <= target; ++j){
                tmp = 0;
                for(int m = 1; m <= k; ++m){
                    if(j>=m){
                        tmp += dp[(i-1)&1][j-m];
                        tmp %= mod;
                    }
                }
                dp[i&1][j] = (int)tmp;
            }
        }
        return dp[n&1][target];
        */

        //一维空间优化
        int[] dp = new int[target+1];
        int mod = 1000000007;
        long tmp = 0;
        dp[0] = 1;
        for(int i = 1; i <= n; ++i){
            for(int j = target; j >= 0; --j){
                tmp = 0 ;
                for(int m = 1; m <= k; ++m){
                    if(j>=m){
                        tmp += dp[j-m];
                        tmp %= mod;
                    }
                }
                dp[j] = (int)tmp;
            }
        }
        return dp[target];
    }
}