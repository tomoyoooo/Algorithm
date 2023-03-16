import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sortTest {
    @Test
    public void testarr() {
        int[] arr = {3,4,2,5,8,1,9,7,6,10};
        //Integer[] arr = {3,4,2,5,8,1,9,7,6,10};
        //List<Integer> list = Arrays.asList(arr);
        //1.冒泡排序：稳定，最好O(n)，最差O(n^2)，平均O(n^2)
        //arr = BubbleSort(arr);
        //2.选择排序：不稳定，最好O(n^2)，最差O(n^2)，平均O(n^2)
        //arr = SelectionSort(arr);
        //3.插入排序：稳定，最好O(n)，最差O(n^2)，平均O(n^2)
        //arr = InsertionSort(arr);
        //4.希尔排序：稳定，最好O(nlogn)，最差O(n^2)，平均O(nlogn)
        //arr = ShellSort(arr);
        //5.归并排序：稳定，最好O(nlogn)，最差O(nlogn)，平均O(nlogn)
        //arr = MergeSort(arr);
        //6.快速排序：不稳定，最好O(nlogn)，最差O(nlogn)，平均O(nlogn)
        //arr = QuickSort(arr, 0, arr.length-1);
        //7.堆排序：不稳定，最好O(nlogn)，最差O(nlogn)，平均O(nlogn)
        //arr = HeapSort(arr);
        //8.计数排序：稳定，最好O(n+k)，最差O(n+k)，平均O(n+k)，k为最大最小值的差
        //arr = CountingSort(arr);
        //9.桶排序：稳定，最好O(n+k)，最差O(n^2)，平均O(n+k)，k为初始桶数
        //arr = BucketSort(list, 5).toArray(new Integer[0]);
        //10.基数排序：稳定，最好O(n*k)，最差O(n*k)，平均O(n*k)
        //arr = RadixSort(arr);
        for (int j : arr) System.out.print(j + " ");
    }

    public int[] RadixSort(int[] arr){
        if(arr.length<2) return arr;
        int N = 1;
        int maxx = arr[0];
        for(int i : arr) maxx = Math.max(maxx, i);
        while(maxx/10 != 0){
            maxx /= 10;
            N++;
        }
        for(int i = 0; i < N; ++i){
            List<List<Integer>> radix = new ArrayList<>();
            for(int k = 0; k < 10; ++k){
                radix.add(new ArrayList<Integer>());
            }
            for(int element : arr){
                int idx = (element/(int)Math.pow(10,i))%10;
                radix.get(idx).add(element);
            }
            int idx = 0;
            for(List<Integer> l : radix){
                for(int n : l){
                    arr[idx++] = n;
                }
            }
        }
        return arr;
    }

    public int[] getMinAndMax(List<Integer> arr){
        int maxx = arr.get(0);
        int minn = arr.get(0);
        for(int i : arr){
            if(i < minn) minn = i;
            if(i > maxx) maxx = i;
        }
        return new int[]{minn, maxx};
    }
    public List<Integer> BucketSort(List<Integer> arr, int bucketSize){
        if(arr.size()<2 || bucketSize==0) return arr;
        int[] extremum = getMinAndMax(arr);
        int minn = extremum[0];
        int maxx = extremum[1];
        int bucketNum = (maxx-minn)/bucketSize+1;
        List<List<Integer>> buckets = new ArrayList<>();
        for(int i = 0; i < bucketNum; ++i){
            buckets.add(new ArrayList<Integer>());
        }

        for(int element : arr){
            int idx = (element-minn)/bucketSize;
            buckets.get(idx).add(element);
        }
        for(int i = 0; i < buckets.size(); ++i){
            if(buckets.get(i).size()>1){
                buckets.set(i, BucketSort(buckets.get(i), bucketSize/2));
            }
        }
        ArrayList<Integer> res = new ArrayList<>();
        for(List<Integer> bucket : buckets){
            res.addAll(bucket);
        }
        return res;
    }

    public int[] getMinAndMax(int[] arr){
        int maxx = arr[0];
        int minn = arr[0];
        for(int i = 0; i < arr.length; ++i){
            if(arr[i] < minn) minn = arr[i];
            if(arr[i] > maxx) maxx = arr[i];
        }
        return new int[]{minn, maxx};
    }
    public int[] CountingSort(int[] arr){
        if(arr.length < 2) return arr;
        int[] extremum = getMinAndMax(arr);
        int minn = extremum[0];
        int maxx = extremum[1];
        int[] countArr = new int[maxx-minn+1];
        int[] res = new int[arr.length];

        for(int i = 0; i < arr.length; ++i) countArr[arr[i]-minn]++;
        //算上自己前面有多少个数
        for(int i = 1; i < countArr.length; ++i) countArr[i] += countArr[i-1];
        for(int i = arr.length-1; i>=0; --i){
            int idx = countArr[arr[i]-minn]-1;
            res[idx] = arr[i];
            countArr[arr[i]-minn]--;
        }
        return res;
    }

    int heapLen;
    public int[] HeapSort(int[] arr){
        heapLen = arr.length;
        buildMaxHeap(arr);
        for(int i = arr.length-1; i > 0; --i){
            swap(arr, 0, i);
            heapLen -= 1;
            heapify(arr, 0);
        }
        return arr;
    }
    public void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    //维护最大堆
    public void heapify(int[] arr, int i){
        int left = 2*i+1;
        int right = 2*i+2;
        int largest = i;
        if(right<heapLen && arr[right]>arr[largest]) largest = right;
        if(left<heapLen && arr[left]>arr[largest]) largest = left;
        if(largest!=i){
            swap(arr, largest, i);
            //递归处理交换后的子堆
            heapify(arr, largest);
        }
    }
    public void buildMaxHeap(int[] arr){
        for(int i = arr.length/2-1; i>=0; --i){
            heapify(arr, i);
        }
    }

    public int[] QuickSort(int[] arr, int low, int high){
        if(low < high){
            int position = partition(arr, low, high);
            QuickSort(arr, low, position-1);
            QuickSort(arr, position+1, high);
        }
        return arr;
    }
    public int partition(int[] arr, int low, int high){
        int pivot = arr[high];
        int pointer = low;
        for(int i = low; i < high; ++i){
            if(arr[i] <= pivot){
                int tmp = arr[i];
                arr[i] = arr[pointer];
                arr[pointer] = tmp;
                pointer++;
            }
        }
        int tmp = arr[pointer];
        arr[pointer] = arr[high];
        arr[high] = tmp;
        return pointer;
    }

    public int[] MergeSort(int[] arr){
        if(arr.length <= 1) return arr;
        int mid = arr.length/2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        return merge(MergeSort(left), MergeSort(right));
    }
    public int[] merge(int[] left, int[] right){
        int[] res = new int[left.length+right.length];
        int i = 0, j = 0;
        int idx = 0;
        for(int k = 0; k < res.length; ++k){
            if(i==left.length){
                res[k] = right[j++];
            }
            else if(j==right.length){
                res[k] = left[i++];
            }
            else if(left[i] < right[j]){
                res[k] = left[i++];
            }
            else{
                res[k] = right[j++];
            }
        }
        return res;
    }

    public int[] ShellSort(int[] arr){
        int gap = arr.length/2;
        while(gap > 0){
            for(int i = gap; i < arr.length; ++i){
                int cur = arr[i];
                int preIndex = i-gap;
                while(preIndex>=0 && arr[preIndex]>cur){
                    arr[preIndex+gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex+gap] = cur;
            }
            gap /= 2;
        }
        return arr;
    }

    public int[] InsertionSort(int[] arr){
        for(int i = 1; i < arr.length; ++i){
            int preIndex = i-1;
            int current = arr[i];
            while(preIndex>=0 && current<arr[preIndex]){
                arr[preIndex+1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex+1] = current;
        }
        return arr;
    }

    public int[] SelectionSort(int[] arr){
        for(int i = 0; i < arr.length; ++i){
            int minIndex = i;
            for(int j = i+1; j < arr.length; ++j){
                if(arr[minIndex] > arr[j]) minIndex = j;
            }
            if(minIndex != i){
                int tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
        return arr;
    }

    public int[] BubbleSort(int[] arr){
        for(int i = 1; i < arr.length; ++i){
            boolean flag = true;//如果flag为true，代表本次循环无交换操作，排序结束
            for(int j = 0; j < arr.length-i; ++j){
                if(arr[j] > arr[j+1]){
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                    flag = false;
                }
            }
            if(flag) break;
        }
        return arr;
    }
}
