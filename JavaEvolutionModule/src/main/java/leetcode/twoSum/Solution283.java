package leetcode.twoSum;

import java.util.Arrays;

import static evolution.java_8.streamProcessing.MyPrimitiveStream.numbers;

// https://leetcode.com/problems/move-zeroes/description/
// https://www.hellointerview.com/learn/code/two-pointers/move-zeroes
public class Solution283
{
    /*
    move all nonzero firsttonew aray then back fi empt item with zero
    */
    public void moveZeroes1(int[] nums) {
        int n = nums.length;
        int[] numbers = new int[n];
        int j = 0;
        for(int i =0; i < n ; i++){
            if (nums[i] != 0 ){
                numbers[j] = nums[i];
                j++;
            }
        }
        for (int k = j ; k < n ; k++){
            numbers[k] = 0;
        }
        System.out.println("\n==================");
        System.out.println(Arrays.toString(nums));
        for(int i = 0 ; i < n ; i++){
            nums[i] = numbers[i];
        }
        System.out.println(Arrays.toString(nums));
    }

    static void swap(int[] nums, int x, int y) {
        int temp = nums[x];
        nums[x] = nums[y];
        nums[y] = temp;
    }

    public void moveZeroes2(int[] nums) {
        int l = 0;  int r = 0;  int n = nums.length;
        for(int i = 0 ; i < n ; i++){
          if (nums[i] == 0){
              r = r+1;
              swap(nums,l,r);

          }
        }
    }


    public static void main(String[] args)
    {
        int[][] tests = {
                {2, 0, 4, 0, 9},
                {1, 0, 4, 0, 3, 0, 1},
                {0, -1, 2},
                {5}
        };
        Solution283 sol = new Solution283();

        for (int[] test : tests) sol.moveZeroes1(test);

    }
}

