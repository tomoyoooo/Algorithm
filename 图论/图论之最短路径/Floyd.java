class Solution {
    int N = 110, M = 6010;
    int[][] weights = new int[N][N];
    static int INF = 0x3f3f3f3f;
    int n,k;
    public int networkDelayTime(int[][] times, int _n, int _k) {
        n = _n; k = _k;
        //初始化邻接矩阵
        for(int i = 1; i <= n; ++i){
            for(int j = 1; j <= n; ++j){
                weights[i][j] = weights[j][i] = i==j ? 0 : INF;
            }
        }
        //存图
        for(int[] edges : times){
            int start=edges[0], tail=edges[1], weight=edges[2];
            weights[start][tail] = weight;
        }
        floyd();
        int res = 0;
        for(int i = 1; i <= n; ++i){
            res = Math.max(res, weights[k][i]);
        }
        return res >= INF/2 ? -1 : res;
    }
    public void floyd(){
        //三层循环：枚举中转点---枚举起点---枚举终点并松弛
        for(int p = 1; p <= n; ++p){
            for(int i = 1; i <= n; ++i){
                if(i==p) continue;
                for(int j = 1; j <= n; ++j){
                    weights[i][j] = Math.min(weights[i][j], weights[i][p]+weights[p][j]);
                }
            }
        }
    }

}