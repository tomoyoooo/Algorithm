//剑指 Offer 16. 数值的整数次方
//注意Java的int变量范围为[-2147483648, 2147483647]，由于需要n=-n操作，可能出现溢出出错，所以需要先用long类型变量存储n
class Solution {
    public double myPow(double x, int n) {
        long N = n;
        return n>0 ? QuickPow(x, N) : 1.0/QuickPow(x, -n);
    }
    public double QuickPow(double x, long n){
        if(n==0) return 1.0;
        double y = QuickPow(x, n/2);
        return n%2==0 ? y*y : y*y*x;
    }
}

//迭代写法
class Solution {
    public double myPow(double x, int n) {
        double ans = 1;
        long m = n;
        if(n<0) m = -m;
        while(m>0){
            if(m%2==1) ans*=x;
            x *= x;
            m >>= 1;
        }
        return n>0 ? ans : 1.0/ans;
    }
}


//矩阵快速幂模板

//快速幂求出m*m方阵的q次方
int[][] maxtrixQuickPow(int[][] a, int m, int q){
    int[][] res = new int[m][m];
    //初始矩阵应该是一个单位矩阵
    for(int i = 0; i < m; ++i){
        res[i][i] = 1;
    }
    while(q){
        if(q&1){
            res = maxtrixMultiply(res, a, m, m, m);
        }
        a = maxtrixMultiply(a, a, m, m, m);
        n >>= 1;
    }
    return res;
}
//m*s矩阵与s*n矩阵相乘
public int[][] maxtrixMultiply(int[][] a, int[][] b, int m, int s, int n){
    int[][] res = new int[m][n];
    for(int i = 0; i < m; ++i){
        for(int j = 0; j < n; ++j){
            for(int k = 0; k < s; ++k){
                res[i][j] = a[i][k]*b[k][j];
            }
        }
    }
    return res;
} 