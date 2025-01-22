package Radix;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class RadixSortUtils {

	static int numLength(int num) {
		int counter = 0;
		while (num != 0) {
			num /= 10;
			counter++;
		}
		return counter;
	}

	static int getDigitAtIndexOfFromEnd(int num, int index) {
		int result = 0;
		do {
			result = num % 10;
			num /= 10;
			index--;
		} while (index >= 0);

		return result;
	}
}

public class Main {

	private static final int ASCENDING_ORDER = 1;

	private static void radixSort(int[] nums, boolean ascending) {
		int maxLength = 0;

		for (int i = 0; i < nums.length; i++) {
			int current = RadixSortUtils.numLength(nums[i]);
			if (current > maxLength) {
				maxLength = current;
			}
		}

		for (int i = 0; i < maxLength; i++) {
			int index = 0;
			ArrayList<ArrayDeque<Integer>> queue = new ArrayList();

			for (int j = 0; j <= 9; j++) {
				queue.add(new ArrayDeque<Integer>());
			}

			for (int n : nums) {
				int digit = RadixSortUtils.getDigitAtIndexOfFromEnd(n, i);
				queue.get(digit).add(n);
			}

			if (ascending) {
				for (int l = 0; l <= 9; l++) {
					for (int result : queue.get(l)) {
						nums[index] = result;
						index++;
					}
				}
			} else {
				for (int l = 9; l >= 0; l--) {
					for (int result : queue.get(l)) {
						nums[index] = result;
						index++;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int order = scanner.nextInt();
		int n = scanner.nextInt();
		int[] nums = new int[n];
		for (int i = 0; i < n; ++i) {
			nums[i] = scanner.nextInt();
		}

		boolean ascending = (order == ASCENDING_ORDER ? true : false);

		radixSort(nums, ascending);

		System.out.println(Arrays.toString(nums));
	}
}
