//leetcode 2192.有向无环图中一个节点的所有祖先
class Solution {
public:
    vector<vector<int>> getAncestors(int n, vector<vector<int>>& edges) {
        vector<vector<int>> relation(n);
        vector<int> indegree(n);
        for(auto &item : edges){
            int p = item[0];
            int c = item[1];
            relation[p].push_back(c);
            ++indegree[c];
        }

        unordered_map<int, unordered_set<int>> parent;
        vector<vector<int>> ans(n);
        queue<int> q;
        //找到入度为0的点
        for(int i = 0; i < n; ++i){
            if(indegree[i]==0) q.push(i);
        }

        while(!q.empty()){
            //取出当前入度为0的点
            int cur = q.front();
            q.pop();
            //遍历该点所有的子节点
            for(int i = 0; i < relation[cur].size(); ++i){
                int child = relation[cur][i];
                //将这些子节点的入度-1，如果子节点入度-1后为0，则加入队列
                if(--indegree[child]==0){
                    q.push(child);
                }
                //记录下该子节点的父节点为当前入度为0的节点
                parent[child].insert(cur);
                //并且当前入度为0的节点的所有祖先节点都是当前遍历子节点的祖先节点
                for(auto &item : parent[cur]){
                    parent[child].insert(item);
                }
            }
        }
        for(int i = 0; i < n; ++i){
            for(auto &item : parent[i]){
                ans[i].push_back(item);
            }
            //将每个节点的祖先节点升序排列
            sort(ans[i].begin(), ans[i].end());
        }
        return ans;
    }
};