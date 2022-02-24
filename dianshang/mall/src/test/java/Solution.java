public class Solution {
    public static int[] twoSum(int[] nums, int target) {
        /*for (int i = 0; i < nums.length; i++) {
            for (int j = i + i; j < nums.length; j++) {
                target = nums[i] + nums[j];
                return new int[]{i, j};
            }
        }
        return new int[0];
    }*/
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];

    }


}
