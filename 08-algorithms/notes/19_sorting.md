# 排序算法基础知识

## 基本排序算法

### 1. 快速排序
```java
public void quickSort(int[] nums) {
    quickSort(nums, 0, nums.length - 1);
}

private void quickSort(int[] nums, int left, int right) {
    if (left >= right) return;
    
    int pivot = partition(nums, left, right);
    quickSort(nums, left, pivot - 1);
    quickSort(nums, pivot + 1, right);
}

private int partition(int[] nums, int left, int right) {
    int pivot = nums[right];
    int i = left;
    
    for (int j = left; j < right; j++) {
        if (nums[j] <= pivot) {
            swap(nums, i, j);
            i++;
        }
    }
    
    swap(nums, i, right);
    return i;
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

### 2. 归并排序
```java
public void mergeSort(int[] nums) {
    if (nums == null || nums.length <= 1) return;
    mergeSort(nums, 0, nums.length - 1);
}

private void mergeSort(int[] nums, int left, int right) {
    if (left >= right) return;
    
    int mid = left + (right - left) / 2;
    mergeSort(nums, left, mid);
    mergeSort(nums, mid + 1, right);
    merge(nums, left, mid, right);
}

private void merge(int[] nums, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;
    
    while (i <= mid && j <= right) {
        if (nums[i] <= nums[j]) {
            temp[k++] = nums[i++];
        } else {
            temp[k++] = nums[j++];
        }
    }
    
    while (i <= mid) temp[k++] = nums[i++];
    while (j <= right) temp[k++] = nums[j++];
    
    System.arraycopy(temp, 0, nums, left, temp.length);
}
```

### 3. 堆排序
```java
public void heapSort(int[] nums) {
    if (nums == null || nums.length <= 1) return;
    
    // 构建最大堆
    for (int i = nums.length / 2 - 1; i >= 0; i--) {
        heapify(nums, nums.length, i);
    }
    
    // 逐个取出堆顶元素
    for (int i = nums.length - 1; i > 0; i--) {
        swap(nums, 0, i);
        heapify(nums, i, 0);
    }
}

private void heapify(int[] nums, int n, int i) {
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    
    if (left < n && nums[left] > nums[largest]) {
        largest = left;
    }
    
    if (right < n && nums[right] > nums[largest]) {
        largest = right;
    }
    
    if (largest != i) {
        swap(nums, i, largest);
        heapify(nums, n, largest);
    }
}
```

## 排序算法比较

### 1. 时间复杂度
- 快速排序：平均O(nlogn)，最坏O(n²)
- 归并排序：稳定O(nlogn)
- 堆排序：稳定O(nlogn)

### 2. 空间复杂度
- 快速排序：O(logn)
- 归并排序：O(n)
- 堆排序：O(1)

### 3. 稳定性
- 快速排序：不稳定
- 归并排序：稳定
- 堆排序：不稳定

## 常见优化

### 1. 快速排序优化
- 三数取中选择pivot
- 处理重复元素
- 小数组使用插入排序

### 2. 归并排序优化
- 小数组使用插入排序
- 避免数组复制
- 利用数组已有的顺序

### 3. 实际应用考虑
- 数据规模
- 数据分布
- 稳定性要求
- 空间限制
