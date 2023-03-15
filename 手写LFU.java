class Node{
    int key,val,freq;
    Node pre,next;
    public Node(int key, int value){
        this.key = key;
        this.val = value;
        this.freq = 1;
    }
}

class DoubleLinkedList{
    Node head,tail;
    public DoubleLinkedList(){
        head = new Node(0,0);
        tail = new Node(0,0);
        head.next = tail;
        tail.pre = head;
    }
    public void remove(Node x){
        x.pre.next = x.next;
        x.next.pre = x.pre;
    }
    public void add(Node x){
        x.pre = tail.pre;
        x.pre.next = x;
        x.next = tail;
        tail.pre = x;
    }
}

class LFUCache {
    Map<Integer, Node> cache;
    Map<Integer, DoubleLinkedList> freqMap;
    int maxSize;
    int size;
    //需要记录最小频次
    int minFreq;

    public LFUCache(int capacity) {
        cache = new HashMap<>(capacity);
        freqMap = new HashMap<>();
        maxSize = capacity;
        size = 0;
        minFreq = 0;
    }
    
    public int get(int key) {
        if(!cache.containsKey(key)) return -1;
        //存在的话频次+1并放入链表末尾
        Node x = cache.get(key);
        freqIncrement(x);
        return x.val;
    }
    
    public void put(int key, int value) {
        if(cache.containsKey(key)){
            Node x = cache.get(key);
            x.val = value;
            freqIncrement(x);
            return;
        }
        //容量满了，删除使用次数最少中，最久未使用的节点
        if(maxSize == size){
            removeMinFreqAndLeastRecently();
        }
        //将新节点加入队列，并查看是否有freq为1的队列
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        DoubleLinkedList list;
        if(freqMap.containsKey(1)){
            list = freqMap.get(1);
        }
        else{
            list = new DoubleLinkedList();
            freqMap.put(1, list);
        }
        list.add(newNode);
        size++;
        minFreq = 1;
    }

    public void freqIncrement(Node x){
        int freq = x.freq;
        DoubleLinkedList list = freqMap.get(freq);
        //先去掉节点
        list.remove(x);
        //如果对应频次队列为空了，则最小频次需要+1
        if(minFreq==freq && list.tail.pre==list.head){
            minFreq = freq + 1;
        }
        x.freq++;
        if(!freqMap.containsKey(freq+1)){
            list = new DoubleLinkedList();
            freqMap.put(freq+1, list);
        }
        else{
            list = freqMap.get(freq+1);
        }
        list.add(x);
    }
	
    //删除频次最小队列中最久未使用的节点
    public void removeMinFreqAndLeastRecently(){
        DoubleLinkedList list = freqMap.get(minFreq);
        cache.remove(list.head.next.key);
        list.remove(list.head.next);
        size--;
    }
}