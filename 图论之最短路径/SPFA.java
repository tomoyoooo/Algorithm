//邻接表实现
class Solution {
    int N = 110, M = 6010;
    // 邻接表
    int[] he = new int[N], e = new int[M], ne = new int[M], w = new int[M];
    // dist[x] = y 代表从「源点/起点」到 x 的最短距离为 y
    int[] dist = new int[N];
    // 记录哪一个点「已在队列」中
    boolean[] vis = new boolean[N];
    int INF = 0x3f3f3f3f;
    int n, k, idx;
    void add(int a, int b, int c) {
        e[idx] = b;
        ne[idx] = he[a];
        he[a] = idx;
        w[idx] = c;
        idx++;
    }
    public int networkDelayTime(int[][] ts, int _n, int _k) {
        n = _n; k = _k;
        // 初始化链表头
        Arrays.fill(he, -1);
        // 存图
        for (int[] t : ts) {
            int u = t[0], v = t[1], c = t[2];
            add(u, v, c);
        }
        // 最短路
        spfa();
        // 遍历答案
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            ans = Math.max(ans, dist[i]);
        }
        return ans > INF / 2 ? -1 : ans;
    }

    public void spfa() {
        // 起始先将所有的点标记为「未入队」和「距离为正无穷」
        Arrays.fill(vis, false);
        Arrays.fill(dist, INF);
        // 只有起点最短距离为 0
        dist[k] = 0;
        // 使用「双端队列」存储，存储的是点编号
        Deque<Integer> d = new ArrayDeque<>();
        // 将「源点/起点」进行入队，并标记「已入队」
        d.addLast(k);
        vis[k] = true;
        while (!d.isEmpty()) {
            // 每次从「双端队列」中取出，并标记「未入队」
            int poll = d.pollFirst();
            vis[poll] = false;
            // 尝试使用该点，更新其他点的最短距离
            // 如果更新的点，本身「未入队」则加入队列中，并标记「已入队」
            for (int i = he[poll]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] > dist[poll] + w[i]) {
                    dist[j] = dist[poll] + w[i];
                    if (vis[j]) continue;
                    d.addLast(j);
                    vis[j] = true;
                }
            }
        }
    }
}