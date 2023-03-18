//剑指 Offer 15. 二进制中1的个数
//n&(n-1)可以使得数字n二进制表示中低位的第一个1消除
public class Solution {
    public int hammingWeight(int n) {
        int ret = 0;
        while (n != 0) {
            n &= n - 1;
            ret++;
        }
        return ret;
    }
}