//邻接矩阵实现，时间复杂度O(n^2)
class Solution {
    int N = 110, M = 6010;
    int[][] w = new int[N][N];
    int[] dist = new int[N];
    boolean[] vis = new boolean[N];
    static int INF = 0x3f3f3f3f;
    int n,k;
    public int networkDelayTime(int[][] times, int _n, int _k) {
        n = _n; k = _k;
        //初始化邻接矩阵
        for(int i = 1; i <= n; ++i){
            for(int j = 1; j <= n; ++j){
                w[i][j] = w[j][i] = i==j ? 0 : INF;
            }
        }
        for(int[] edges : times){
            int start = edges[0], tail = edges[1], weight = edges[2];
            w[start][tail] = weight;
        }
        dijkstra();
        int res = 0;
        for(int i = 1; i <= n; ++i){
            res = Math.max(res, dist[i]);
        }
        return res > INF/2 ? -1 : res;
    }

    public void dijkstra(){
        Arrays.fill(vis, false);
        Arrays.fill(dist, INF);
        dist[k] = 0;
        for(int i = 1; i <= n; ++i){
            //找到距起点距离最短且未被用来更新的点
            int minValIndex = -1;
            for(int j = 1; j <= n; ++j){
                if(!vis[j] && (minValIndex==-1 || dist[j]<dist[minValIndex])) minValIndex = j;
            }
            vis[minValIndex] = true;
            //根据找到的点来更新其余所有的点
            for(int j = 1; j <= n; ++j){
                dist[j] = Math.min(dist[j], dist[minValIndex]+w[minValIndex][j]);
            }
        }
    }
}

//邻接表实现，时间复杂度O(mlogn)                                                                                                       
class Solution {
    int N = 110, M = 6010;
    //链表头节点后第一条边
    int[] head = new int[N];
    //当前边指向的节点
    int[] edgeToPoint = new int[M];
    //找到下一条边
    int[] nextEdge = new int[M];
    //边的权重记录
    int[] weights = new int[M];
    int[] dist = new int[N];
    boolean[] vis = new boolean[N];
    int n,k,idx;
    static int INF = 0x3f3f3f3f;
    //本方法接在前面而不是后面，也可以反过来，一样的，不过会麻烦一点，每次都需要找到最后一个来接
    //举例[[2,1,1],[2,3,1]]，先2->1，然后变为2->3->1，2->3的边nextEdge为3->1的边
    public void add(int s, int t, int w){
        edgeToPoint[idx] = t;
        nextEdge[idx] = head[s];
        head[s] = idx;
        weights[idx] = w;
        idx++;
    }
    public int networkDelayTime(int[][] times, int _n, int _k) {
        n = _n; k = _k;
        //初始化邻接矩阵
        Arrays.fill(head, -1);
        //存图
        for(int[] edges : times){
            int start=edges[0], tail=edges[1], weight=edges[2];
            add(start, tail, weight);
        }
        dijkstra();
        int res = 0;
        for(int i = 1; i <= n; ++i){
            res = Math.max(res, dist[i]);
        }
        return res > INF/2 ? -1 : res;
    }
    public void dijkstra(){
        Arrays.fill(vis, false);
        Arrays.fill(dist, INF);
        dist[k] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1]-b[1]);
        pq.add(new int[]{k,0});
        while(!pq.isEmpty()){
            int[] now = pq.poll();
            int index = now[0], disNow = now[1];
            if(vis[index]) continue;
            vis[index] = true;
            for(int i = head[index]; i != -1; i = nextEdge[i]){
                int point = edgeToPoint[i];
                if(dist[point] > dist[index]+weights[i]){
                    dist[point] = dist[index]+weights[i];
                    pq.add(new int[]{point, dist[point]});
                }
            }
        }
    }
}