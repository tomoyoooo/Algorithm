//用类存图
class Solution {
    class Edge {
        int start,tail,weight;
        Edge(int _start, int _tail, int _weight){
            start = _start; tail = _tail; weight = _weight;
        }
    }
    int N = 110, M = 6010;
    int[] dist = new int[N];
    static int INF = 0x3f3f3f3f;
    int n,m,k;
    List<Edge> edgeList = new ArrayList<>();
    public int networkDelayTime(int[][] times, int _n, int _k) {
        n = _n; k = _k;
        m = times.length;
        for(int[] edges : times){
            int start=edges[0], tail=edges[1], weight=edges[2];
            edgeList.add(new Edge(start, tail, weight));
        }
        bellman_ford();
        int res = 0;
        for(int i = 1; i <= n; ++i){
            res = Math.max(res, dist[i]);
        }
        return res > INF/2 ? -1 : res;
    }
    public void bellman_ford(){
        Arrays.fill(dist, INF);
        dist[k] = 0;
        for(int p = 1; p <= n; ++p){
            int[] pre = dist.clone();
            for(Edge e : edgeList){
                int start=e.start, tail=e.tail, weight=e.weight;
                dist[tail] = Math.min(dist[tail], pre[start]+weight);
            }
        }
    }
}

//邻接表存图
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
    int n,k,m,idx;
    static int INF = 0x3f3f3f3f;
    //举例[[2,1,1],[2,3,1]]，先2->1，然后变为2->3->1，2->3的边nextEdge为3->1的边
    public void add(int s, int t, int w){
        edgeToPoint[idx] = t;
        nextEdge[idx] = head[s];
        head[s] = idx;
        weights[idx] = w;
        idx++;
    }
    public int networkDelayTime(int[][] times, int _n, int _k) {
        n = _n; k = _k; m = times.length;
        Arrays.fill(head, -1);
        for(int[] edges : times){
            int start=edges[0], tail=edges[1], weight=edges[2];
            add(start, tail, weight);
        }
        bellman_ford();
        int res = 0;
        for(int i = 1; i <= n; ++i){
            res = Math.max(res, dist[i]);
        }
        return res > INF/2 ? -1 : res;
    }
    public void bellman_ford(){
        Arrays.fill(dist, INF);
        dist[k] = 0;
        for(int p = 1; p < n; ++p){
            int[] pre = dist.clone();
            for(int start = 1; start <= n; ++start){
                for(int i = head[start]; i != -1; i = nextEdge[i]){
                    int tail = edgeToPoint[i];
                    dist[tail] = Math.min(dist[tail], pre[start] + weights[i]);
                }
            }
        }
    } 

}