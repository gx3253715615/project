package com.github.project.test.algo;

import com.github.project.test.Student;
import jakarta.validation.constraints.Min;
import org.bouncycastle.pqc.crypto.picnic.PicnicPublicKeyParameters;

import java.util.Arrays;
import java.util.Random;

/**
 * 复杂排序算法
 * <p>
 * 稳定：冒泡、插入、归并
 * 不稳定：选择、快排、堆排序
 *
 * @author gaoxinyu
 * @date 2025/9/17 0:05
 **/
class ComplexSortAlgo {
    public void bubbleSort(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len - 1; i++) {
            boolean isSwap = false;
            for (int j = 0; j < len - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    isSwap = true;
                }
            }
            if (!isSwap) break;
        }
    }

    //
    public void selectionSort(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(nums, i, minIndex);
            }
        }
    }

    public void insertionSort(int[] nums) {
        int len = nums.length;
        for (int i = 1; i < len; i++) {
            int key = nums[i];
            int j = i - 1;
            while (j >= 0 && nums[j] > key) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = key;
        }
    }

    /**
     * 快排 nlogn 最差 on2（当基准选择不合适时） logn
     */
    public void quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right) return;
        int index = partition(nums, left, right);
        quickSort(nums, left, index - 1);
        quickSort(nums, index + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        int base = nums[right];
        int slow = left, fast = left;
        while (fast < right) {
            if (nums[fast] < base) {
                swap(nums, slow, fast);
                slow++;
            }
            fast++;
        }
        swap(nums, slow, right);
        return slow;
    }

    /**
     * 归并排序 nlogn on（用于存储临时子数组）
     */
    public void mergeSort(int[] nums) {
        if (nums.length < 2) return;
        int[] temp = new int[nums.length];
        mergeSort(nums, temp, 0, nums.length - 1);
    }

    private void mergeSort(int[] nums, int[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(nums, temp, left, mid);
            mergeSort(nums, temp, mid + 1, right);
            merge(nums, temp, left, mid, right);
        }
    }

    private void merge(int[] nums, int[] temp, int left, int mid, int right) {
        // 赋值到临时数组
        for (int i = left; i <= right; i++) {
            temp[i] = nums[i];
        }

        // 分两个数组
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                nums[k++] = temp[i++];
            } else {
                nums[k++] = temp[j++];
            }
        }
        // 处理剩余数据
        while (i <= mid) {
            nums[k++] = temp[i++];
        }
        while (j <= right) {
            nums[k++] = temp[j++];
        }
    }

    /**
     * 堆排序 nlogn o1
     */
    public void heapSort(int[] nums) {
        int len = nums.length;

        // 从下到上构建大顶堆
        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(nums, len, i);
        }

        for (int i = len - 1; i > 0; i--) {
            // 交换根节点和最后一个节点
            swap(nums, 0, i);

            // 从根节点递归构建大顶堆
            heapify(nums, i, 0); // 🌟 这里每次交换完之后总节点数量减一
        }
    }

    private void heapify(int[] nums, int len, int i) {
        int lastIndex = i;
        int left = i * 2 + 1;
        int right = i * 2 + 2;

        if (left < len && nums[left] > nums[lastIndex]) {
            lastIndex = left;
        }

        if (right < len && nums[right] > nums[lastIndex]) {
            lastIndex = right;
        }

        if (lastIndex != i) {
            // 交换父子节点
            swap(nums, i, lastIndex);

            // 递归交换后的节点 lastIndex是被交换的节点
            heapify(nums, len, lastIndex);
        }

    }


    /**
     * 三路快排
     */
    Random random = new Random();

    public void quickSort3Way(int[] nums) {
        quickSort3Way(nums, 0, nums.length - 1);
    }

    private void quickSort3Way(int[] nums, int left, int right) {
        if (left > right) return;
        int randomIndex = left + new Random().nextInt(right - left + 1);
        swap(nums, left, randomIndex);
        int pivot = nums[left];
        int lt = left;
        int gt = right;
        int eq = left + 1;

        while (eq <= gt) {
            if (nums[eq] == pivot) {
                eq++;
            } else if (nums[eq] < pivot) {
                swap(nums, eq, lt + 1);
                eq++;
                lt++;
            } else {
                swap(nums, eq, gt);
                gt--;
            }
        }
        swap(nums, left, lt);
        quickSort3Way(nums, left, lt - 1);
        quickSort3Way(nums, gt + 1, right);
    }


    private void swap(int[] nums, int slow, int right) {
        int temp = nums[slow];
        nums[slow] = nums[right];
        nums[right] = temp;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] nums = {1, 5, 7, 2, 0, 9, 4, 3, 8, 6};
        ComplexSortAlgo complexSortAlgo = new ComplexSortAlgo();
        //complexSortAlgo.quickSort(nums);
        //complexSortAlgo.bubbleSort(nums);
        //complexSortAlgo.selectionSort(nums);
        //complexSortAlgo.insertionSort(nums);
        //complexSortAlgo.mergeSort(nums);
        //complexSortAlgo.heapSort(nums);
        complexSortAlgo.quickSort3Way(nums);
        Arrays.stream(nums).forEach(System.out::print);
    }
}
