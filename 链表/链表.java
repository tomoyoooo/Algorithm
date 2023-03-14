//Leetcode 21.合并两个有序链表
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        while(list1!=null && list2!=null){
            if(list1.val <= list2.val){
                tmp.next = list1;
                list1 = list1.next;
            }
            else{
                tmp.next = list2;
                list2 = list2.next;
            }
            tmp = tmp.next;
        }
        if(list1!=null){
            tmp.next = list1;
        }
        if(list2!=null){
            tmp.next = list2;
        }
        return head.next;
    }
}

//Leetcode 86.分隔链表
class Solution {
    public ListNode partition(ListNode head, int x) {
        ListNode pre = new ListNode(-1);
        ListNode last = new ListNode(-1);
        ListNode preidx = pre;
        ListNode lastidx = last;
        int cnt = 0;

        while(head!=null){
            if(head.val<x){
                preidx.next = head;
                preidx = preidx.next;
            }
            else{
                lastidx.next = head;
                lastidx = lastidx.next;
            }

            ListNode temp = head.next;
            head.next = null;
            head = temp;

        }
        preidx.next = last.next;
        return pre.next;
    }
}

//Leetcode 23.合并k个有序链表
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length==0) return null;
        ListNode res = new ListNode(-1);
        ListNode residx = res;

        PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>((a,b)->(a.val-b.val));
        for(ListNode head : lists){
            if(head!=null){
                pq.add(head);
            }
        }

        while(!pq.isEmpty()){
            ListNode tmp = pq.poll();
            residx.next = tmp;
            if(tmp.next!=null) pq.add(tmp.next);
            residx = residx.next;
        }
        return res.next;
    }
}

//先让一个指针走k步，再两个指针一起走，直到第一个指针为空，此时第二个指针所处位置就是倒数第k个节点

//Leetcode 19.删除链表的倒数第N个节点
//我们找到倒数第N+1个节点就行，利用额外的节点头防止访问为空的情况
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode p1 = new ListNode(-1);
        p1.next = head;
        ListNode tmp = p1;

        for(int i = 0; i < n+1; ++i){
            tmp = tmp.next;
        }
        ListNode p2 = p1;
        while(tmp!=null){
            tmp = tmp.next;
            p2 = p2.next;
        }
        p2.next = p2.next.next;
        return p1.next;
    }
}

//Leetcode 876.链表的中间结点
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode p1 = head;
        ListNode p2 = head;
        while(p1!=null && p1.next!=null){
            p1 = p1.next.next;
            p2 = p2.next;
        }

        return p2;
    }
}

//判断是否有环只需要快慢指针，一个一次走两步，一个一次走一步
//剑指 Offer II 022. 链表中环的入口节点
ListNode detectCycle(ListNode head) {
    ListNode fast, slow;
    fast = slow = head;
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
        if (fast == slow) break;
    }
    // 上⾯的代码类似 hasCycle 函数
    if (fast == null || fast.next == null) {
        // fast 遇到空指针说明没有环
        return null;
    }
    // 重新指向头结点
    // 快结点比慢结点正好多走一圈
    slow = head;
    // 快慢指针同步前进，相交点就是环起点
    while (slow != fast) {
        fast = fast.next;
        slow = slow.next;
    }
    return slow;
}

//相当于拼接起来，有相同段则会走到一起
//面试题 02.07. 链表相交
ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    // p1 指向 A 链表头结点，p2 指向 B 链表头结点
    ListNode p1 = headA, p2 = headB;
    while (p1 != p2) {
        // p1 ⾛⼀步，如果⾛到 A 链表末尾，转到 B 链表
        if (p1 == null) p1 = headB;
        else p1 = p1.next;
        // p2 ⾛⼀步，如果⾛到 B 链表末尾，转到 A 链表
        if (p2 == null) p2 = headA;
        else p2 = p2.next;
    }
    return p1;
}

//Leetcode 206.反转链表
//子问题：反转除第一个结点外的整个链表
//base case：当head.next==null时，表示head为最后一个结点，用last记录最后一个结点，并从后往前依次处理每个结点的next
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head==null || head.next==null) return head;
        ListNode last = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }
}
//迭代
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head==null) return head;
        ListNode tail = head.next;
        head.next = null;
        while(tail!=null){
            ListNode tmp = tail.next;
            tail.next = head;
            head = tail;
            tail = tmp;
        }
        return head;
    }
}

//Leetcode 92.反转链表Ⅱ
//子问题：由于此时需要处理左右两个链表值，我们按照上一题的思路，先寻找到left所在位置，当left==1时进行[left,right]的反转
//ex: 1,2,3,4,5 left=2,right=4
//ans: 1,4,3,2,5
class Solution {
    ListNode suc = null;
    //子问题：反转前n个结点
    ListNode reverseN(ListNode head, int n){
        //记录后驱结点(ex中5)
        if(n==1){
            suc = head.next;
            return head;
        }
        ListNode last = reverseN(head.next, n-1);
        head.next.next = head;
        head.next = suc;
        return last;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        //left==1时，处理变为1,4,3,2,5
        if(left==1) return reverseN(head, right);
        //后续就依次回到最前面
        //此处head.next保证了前驱结点
        head.next = reverseBetween(head.next, left-1, right-1);
        return head;
    }
}
//迭代
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 设置 dummyNode 是这一类问题的一般做法
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode pre = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next;
        ListNode next;
        for (int i = 0; i < right - left; i++) {
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummyNode.next;
    }
}