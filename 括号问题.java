//Leetcode20.有效的括号
//右括号必须匹配栈顶左括号
//匹配对数多的话可以用哈希表记录
class Solution {
    public boolean isValid(String s) {
        Deque<Character> dq = new ArrayDeque<>();
        for(char c : s.toCharArray()){
            if(c=='(' || c=='{' || c=='[') dq.addFirst(c);
            else{
                if(dq.isEmpty()) return false;
                char now = dq.poll();
                if(c==')' && now!='(') return false;
                else if(c==']' && now!='[') return false;
                else if(c=='}' && now!='{') return false;
            }
        }
        return dq.isEmpty();
    }
}

//-------------------------------------------------------------------------------
//Leetcode22.括号生成
//由于需要得到所有情况，需要使用DFS+回溯进行记录
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        dfs(0, 2*n, 0, n, "", res);
        return res;
    }
    //len表示现在字符串的长度，达到dn则进行判断
    //cnt表示分数记录，左括号+1，右括号-1，最终为0表示括号串合法
    //maxx表示最大可能分数，此题中为n，也就是最多只能有一半的左括号
    //path表示现在拼接的字符串
    //res为最终答案
    public void dfs(int len, int dn, int cnt, int maxx, String path, List<String> res){
        if(len==dn){
            if(cnt==0) res.add(path);
        } 
        else{
            //如果分数+1还没达到最大分数，可以继续加入左括号
            if(cnt+1<=maxx){
                dfs(len+1, n, cnt+1, maxx, path+"(", res);
            }
            //如果分数-1还没小于0，可以继续加入右括号
            if(cnt-1>=0){
                dfs(len+1, n, cnt-1, maxx, path+")", res);
            }
        }
    }
}

//-------------------------------------------------------------------------------
//Leetcode32.最长有效括号
//当前字符为')'时，分为两种情况

//1.前一个字符为'('，则最大长度=当前组成的"()"+前面紧邻的最大长度括号串
//即dp[i] = dp[i-2] + 2，判断i-2是否>=0即可

//2.前一个字符为')'，则最大长度=前一个')'匹配的'('中间dp[i-1]+前面dp[i-dp[i-1]-2]
// 则需要计算的长度为....  (  (.....)  )
//                 ↑          ↑
//                 ↑       dp[i-1]
//           dp[i-dp[i-1]-2]
//即dp[i-1] + dp[i-dp[i-1]-2] + 2(当前两个)，判断i-dp[i-1]-2是否>=0即可
class Solution {
    public int longestValidParentheses(String s) {
        int n = s.length();
        int[] dp = new int[n];
        int res = 0;
        for(int i = 1; i < n; ++i){
            char pre = s.charAt(i-1);
            char c = s.charAt(i);
            if(c==')'){
                if(pre=='(') dp[i] = (i>=2 ? dp[i-2] : 0)+2;
                else{
                    if(i-dp[i-1]>0 && s.charAt(i-dp[i-1]-1)=='('){
                        dp[i] = dp[i-1] + (i-dp[i-1]-2>=0 ? dp[i-dp[i-1]-2] : 0) + 2;
                    }
                    else dp[i] = 0;
                }
                res = Math.max(res, dp[i]);
            }
        }
        return res;
    }
}

//解法2：栈
//栈顶用于记录当前最后未匹配的右括号位置
//左括号直接把下标压入栈中

//右括号我们先把栈顶元素弹出用于匹配该右括号，此时栈顶的元素就是前一个未匹配的左括号下标或未匹配的右括号下标
//此时从当前下标到栈顶下标即为最长合法括号串
class Solution {
    public int longestValidParentheses(String s) {
        int n = s.length();
        char[] cs = s.toCharArray();
        Deque<Integer> dq = new ArrayDeque<>();
        //栈最开始为空，为了满足定义，需要初始化-1进栈
        dq.addFirst(-1);
        int res = 0;
        for(int i = 0; i < n; ++i){
            if(cs[i]=='(') dq.addFirst(i);
            else{
                dq.pollFirst();
                //当前左括号和右括号进行了匹配，计算长度即可
                if(!dq.isEmpty()){
                    res = Math.max(res, i-dq.peekFirst());
                }
                //栈为空，则记录下这个不能进行匹配的右括号下标
                else{
                    dq.addFirst(i);
                }
            }
        }
        return res;
    }
}

//-------------------------------------------------------------------------------
//Leetcode301.删除无效的括号
//和例题2一个思路，差别在于此题是进行删除，可能出现相同的答案，所以用HashSet存储
//此外，由于需要保证删除最小数量，等价于删除后长度最长，记录一下最大长度即可
class Solution {
    int len;
    public List<String> removeInvalidParentheses(String s) {
        char[] cs = s.toCharArray();
        int left = 0, right = 0;
        for(char c : cs){
            if(c=='(') left++;
            if(c==')') right++;
        }
        int maxx = Math.min(left, right);
        Set<String> set = new HashSet<>();
        dfs(cs, 0, 0, maxx, "", set);
        List<String> res = new ArrayList<>();
        for(String str : set){
            if(len==str.length()) res.add(str);
        }
        return res;
    }

    public void dfs(char[] cs, int idx, int score, int maxx, String path, Set<String> set){
        if(idx==cs.length){
            if(score==0 && path.length()>=len){
                len = Math.max(len, path.length());
                set.add(path);
            }
            return;
        }
        if(cs[idx]=='('){
            if(score+1 <= maxx) dfs(cs, idx+1, score+1, maxx, path+'(', set);
            dfs(cs, idx+1, score, maxx, path, set);
        }
        else if(cs[idx]==')'){
            if(score-1 >= 0) dfs(cs, idx+1, score-1, maxx, path+')', set);
            dfs(cs, idx+1, score, maxx, path, set);
        }
        else{
            dfs(cs, idx+1, score, maxx, path+String.valueOf(cs[idx]), set);
        }

    }
}

//-------------------------------------------------------------------------------
//Leetcode278.有效的括号字符串
//1.right不能比star和left加起来多
//2.多余左括号不能在star右边
class Solution {
    public boolean checkValidString(String s) {
        Deque<Integer> stackLeft = new ArrayDeque<>();
        Deque<Integer> stackStar = new ArrayDeque<>();
        for(int i = 0; i < s.length(); ++i){
            char c = s.charAt(i);
            if(c=='('){
                stackLeft.push(i);
            }
            else if(c=='*'){
                stackStar.push(i);
            }
            else{
                if(!stackLeft.isEmpty()){
                    stackLeft.pop();
                }
                else if(!stackStar.isEmpty()){
                    stackStar.pop();
                }
                else return false;
            }
        }
        while(!stackLeft.isEmpty() && !stackStar.isEmpty()){
            int left = stackLeft.poll();
            int star = stackStar.poll();
            if(left > star) return false;
        }
        return stackLeft.isEmpty();
    }
}

//解法2：贪心模拟
//由于*的存在，我们需要记录数量的区间
//左括号：最小值和最大值+1
//右括号：最小值和最大值-1
//*：最小值-1，最大值+1

//最大值<0代表右括号的数量大于左括号和*的和，此时返回false
//需要保证最小值>=0，也就是多余的*不会继续充当右括号

//最终最小值为0则表示字符串为合法字符串，因为左括号和*的数量一直都>=右括号，且多余的左括号被*消除了
class Solution {
    public boolean checkValidString(String s) {
        int minn = 0, maxx = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                minn++;
                minn++;
            } else if (c == ')') {
                minn = Math.max(minn - 1, 0);
                maxx--;
                if (maxx < 0) return false;
            } else {
                minn = Math.max(minn - 1, 0);
                maxx++;
            }
        }
        return minn == 0;
    }
}