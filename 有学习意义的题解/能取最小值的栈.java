//Leetcode 剑指Offer30.包含min函数的栈
//注意：min值需要取long，因为加减计算可能导致int溢出
class MinStack {
    Deque<Long> stack;
    Long min; 
    /** initialize your data structure here. */
    public MinStack() {
        stack = new ArrayDeque<>();
    }
    
    public void push(int x) {
        if(stack.isEmpty()){
            stack.push(0L);
            min = Long.valueOf(x);
        }
        else{
            stack.push((long)(x-min));
            min = Math.min(x, min);
        }
    }
    
    public void pop() {
        if(stack.peek()<0){
            min = min - stack.pop();
            return;
        }
        stack.pop();
    }
    
    public int top() {
        if(stack.peek()>0){
            return (int)(min+stack.peek());
        }
        else{
            return Math.toIntExact(min);
        }
    }
    
    public int min() {
        return Math.toIntExact(min);
    }
}