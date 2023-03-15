//解法1：手写双向链表
class Node{
    public int key,val;
    public Node next, prev;
    public Node(int k, int v){
        this.key = k;
        this.val = v;
    }
}

class DoubleList{
    //空的头节点和尾节点
    private Node head,tail;
    private int size;
    public DoubleList(){
        head = new Node(0,0);
        tail = new Node(0,0);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }
	
    //加入在链表最后
    public void addLast(Node x){
        x.prev = tail.prev;
        x.next = tail;
        tail.prev.next = x;
        tail.prev = x;
        size++;
    }
    
    //删除节点通用方法
    public void remove(Node x){
        x.prev.next = x.next;
        x.next.prev = x.prev;
        size--;
    }
	
    //删除第一个节点
    public Node removeFirst(){
        if(head.next==tail) return null;
        Node first = head.next;
        remove(first);
        return first;
    }
	
    //获取链表大小
    public int size(){
        return size;
    }
}

class LRUCache {
    private HashMap<Integer, Node> mp;
    private DoubleList cache;
    private int maxsize;

    public LRUCache(int capacity) {
        this.maxsize = capacity;
        mp = new HashMap<>();
        cache = new DoubleList();
    }
    
    public int get(int key) {
        if(!mp.containsKey(key)) return -1;
        //存在则将其记录为最近使用的节点，即放在链表最后
        makeRecently(key);
        return mp.get(key).val;
    }
    
    public void put(int key, int value) {
        //存在的话直接修改值并将其放在链表最后
        if(mp.containsKey(key)){
            mp.get(key).val = value;
            makeRecently(key);
            //也可以先删除在添加
            //deleteKey(key);
            //addRecently(key, val);
            return;
        }
        //链表已满的话，则去掉最近最少使用的节点，即为链表头
        if(maxsize==cache.size()){
            removeLeastRecently();
        }
        addRecently(key, value);
    }
	
    //将节点重新放入链表末尾，代表新使用过
    private void makeRecently(int key){
        Node x = mp.get(key);
        cache.remove(x);
        cache.addLast(x);
    }
	
    //添加新节点
    private void addRecently(int key, int val){
        Node x = new Node(key, val);
        cache.addLast(x);
        mp.put(key,x);
    }

    private void deleteKey(int key){
        Node x = mp.get(key);
        cache.remove(x);
        mp.remove(key);
    }
	
    //将最近最少使用节点删除，即删除链表第一个非头节点
    private void removeLeastRecently(){
        Node x = cache.removeFirst();
        int deleteKey = x.key;
        mp.remove(deleteKey);
    }
}






//解法2：LinkedHashMap实现
class LRUCache {
    LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();
    private int maxsize;

    public LRUCache(int capacity) {
        this.maxsize = capacity;
    }
    
    public int get(int key) {
        if(!cache.containsKey(key)) return -1;
        makeRecently(key);
        return cache.get(key);
    }
    
    public void put(int key, int value) {
        if(cache.containsKey(key)){
            cache.put(key, value);
            makeRecently(key);
            return;
        }
        if(cache.size() >= maxsize){
            int deleteKey = cache.keySet().iterator().next();
            cache.remove(deleteKey);
        }
        cache.put(key, value);
    }

    private void makeRecently(int key){
        int val = cache.get(key);
        cache.remove(key);
        cache.put(key, val);
    }

}