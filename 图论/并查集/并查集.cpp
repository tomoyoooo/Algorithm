//并查集类
class DisJointSetUnion
{
private:
    // 所有根结点相同的结点位于同一个集合中
    vector<int> parent;    // 双亲结点数组，记录该结点的双亲结点，用于查找该结点的根结点
    vector<int> rank;      // 秩数组，记录以该结点为根结点的树的深度，主要用于优化，在合并两个集合的时候，rank大的集合合并rank小的集合

public:
    DisJointSetUnion(int n)          //构造函数
    {
        for (int i = 0; i < n; i++)
        {
            parent.push_back(i);      //此时各自为王，自己就是一个集合
            rank.push_back(1);        //rank=1，此时每个结点自己就是一颗深度为1的树
        }
    }

    //查找根结点
    int find(int x)
    {
        if(x==parent[x])
            return x;
        else
        {
            parent[x] = find(parent[x]);   // 路径压缩， 遍历过程中的所有双亲结点直接指向根结点，减少后续查找次数
            return parent[x];
        }
    }

    void merge(int x,int y)
    {
        int rx = find(x);                    //查找x的根结点，即x所在集合的代表元素
        int ry = find(y);

        if (rx != ry)                           //如果不是同一个集合
        {
            if (rank[rx] < rank[ry])     //rank大的集合合并rank小的集合
            {
                swap(rx, ry);               //这里进行交换是为了保证rx的rank大于ry的rank，方便下面合并
            }

             parent[ry] = rx;              //rx 合并 ry

            if (rank[rx] == rank[ry])
                rank[rx] += 1;
        }
    }
};