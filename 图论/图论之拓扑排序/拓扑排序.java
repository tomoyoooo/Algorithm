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