//Leetcode 179.最大数
class Solution {
    public String largestNumber(int[] nums) {
        PriorityQueue<String> heap = new PriorityQueue<>((x, y) -> (y + x).compareTo(x + y));
        for(int x: nums) heap.offer(String.valueOf(x));
        StringBuffer tmp = new StringBuffer();
        while(heap.size() > 0) tmp.append(heap.poll());
        String res = tmp.toString();
        if(res.charAt(0) == '0') return "0";
        return res;
    }
}
