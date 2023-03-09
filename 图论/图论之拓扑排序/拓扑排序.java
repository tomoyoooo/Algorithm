//leetcode 802. 找到最终的安全状态
class Solution {
    int N = (int)1e4+10, M = 4*N;
    int idx;
    int[] head = new int[N];
    int[] edgeToPoint = new int[M];
    int[] nextEdge = new int[M];
    int[] degrees = new int[N];
    void add(int s, int t){
        edgeToPoint[idx] = t;
        nextEdge[idx] = head[s];
        head[s] = idx++;
    }
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        Arrays.fill(head, -1);
        //反向存图
        for(int i = 0; i < n; ++i){
            for(int j : graph[i]){
                add(j, i);
                degrees[i]++;
            }
        }

        Deque<Integer> dq = new ArrayDeque<>();
        for(int i = 0; i < n; ++i){
            if(degrees[i]==0) dq.offer(i);
        }
        while(!dq.isEmpty()){
            int now = dq.poll();
            for(int i = head[now]; i != -1; i = nextEdge[i]){
                int j = edgeToPoint[i];
                if(--degrees[j]==0) dq.offer(j);
            }
        }

        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < n; ++i){
            if(degrees[i]==0) res.add(i);
        }
        return res;
    }
}


//Leetcode 2192.有向无环图中一个节点的所有祖先
//参考：https://leetcode.cn/problems/all-ancestors-of-a-node-in-a-directed-acyclic-graph/solution/java-tuo-bu-pai-xu-jian-dan-si-lu-by-rel-95yw/
class Solution {
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        //构建两个线性表，一个作为返回值，另一个使用`TreeSet`，可以在去重的同时进行元素的排序
        List<List<Integer>> ans = new ArrayList<>();
        List<Set<Integer>> demo = new ArrayList<>();

        //构建零阶矩阵(邻接表会更好)，以及入度数组
        int[] system = new int[n];
        int[][] grid = new int[n][n];

        //遍历`edges`，分别构建图中的边的关系以及各个节点的入度
        for (int[] edge : edges) {
            system[edge[1]]++;
            grid[edge[0]][edge[1]] = 1;
        }

        //将图中入度为0的节点入队列，同时“填充好”我们最初构建的两个线性表
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (system[i] == 0) {
                queue.offer(i);
                system[i]--;
            }
            ans.add(new ArrayList<>());
            demo.add(new TreeSet<>());
        }

        //分别对整个入度数组进行遍历，若入度为0，则加入队列，同时将该父节点以及该父节点的所有父节点加入线性表中
        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                int idx = queue.poll();
                for (int i = 0; i < n; i++) {
                    if (grid[idx][i] == 1) {
                        system[i]--;
                        demo.get(i).add(idx);
                        demo.get(i).addAll(demo.get(idx));
                    }
                    if (system[i] == 0) {
                        queue.offer(i);
                        system[i]--;
                    }
                }
            }
        }

        //将最终答案汇整到返回值`ans`中
        for (int i = 0; i < n; i++) {
            ans.get(i).addAll(demo.get(i));
        }

        return ans;
    }
}

