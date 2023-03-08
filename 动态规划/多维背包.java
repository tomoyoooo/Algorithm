//Leetcode 474.一和零
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        //需要记录每个字符串的0和1个数
        int[][] cnt = new int[len+1][2];
        for(int i = 1; i <= len; ++i){
            String s = strs[i-1];
            int zero = 0;
            int one = 0;
            for(char c : s.toCharArray()){
                if(c=='0') zero++;
                else one++;
            }
            cnt[i] = new int[]{zero, one};
        }
        //System.out.print(cnt[2][0]);
        /*
        int[][][] dp = new int[len+1][m+1][n+1]; 
        for(int i = 1; i <= len; ++i){
            int zero = cnt[i][0];
            int one = cnt[i][1];
            for(int j = 0; j <= m; ++j){
                for(int k = 0; k <= n; ++k){
                    int a = dp[i-1][j][k];
                    int b = (j>=zero&&k>=one) ? dp[i-1][j-zero][k-one]+1 : 0;

                    dp[i][j][k] = Math.max(a, b);
                }
            }
        }
        return dp[len][m][n];
        */

        //滚动数组
        /*
        int[][][] dp = new int[2][m+1][n+1]; 
        for(int i = 1; i <= len; ++i){
            int zero = cnt[i][0];
            int one = cnt[i][1];
            for(int j = 0; j <= m; ++j){
                for(int k = 0; k <= n; ++k){
                    int a = dp[(i-1)&1][j][k];
                    int b = (j>=zero&&k>=one) ? dp[(i-1)&1][j-zero][k-one]+1 : 0;

                    dp[i&1][j][k] = Math.max(a, b);
                }
            }
        }
        return dp[len&1][m][n];
        */

        //一维空间优化
        int[][] dp = new int[m+1][n+1];
        for(int i = 1; i <= len; ++i){
            int zero = cnt[i][0];
            int one = cnt[i][1];
            for(int j = m; j >= zero; --j){
                for(int k = n; k >= one; --k){
                    dp[j][k] = Math.max(dp[j][k],dp[j-zero][k-one]+1);
                }
            }
        }
        return dp[m][n];
    }
}