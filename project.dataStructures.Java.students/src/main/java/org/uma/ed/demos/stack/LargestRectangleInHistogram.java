package org.uma.ed.demos.stack;

import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.stack.ArrayStack;
import org.uma.ed.datastructures.stack.Stack;
import org.uma.ed.datastructures.tuple.Tuple2;

import java.util.Arrays;

/**
 * Solves the "Largest Rectangle in Histogram" problem using a Stack.
 * <p>
 * The problem: Given an array of non-negative integers representing the heights of
 * bars in a histogram (where each bar has a width of 1), find the area of the
 * largest rectangle that can be formed within the histogram.
 * <p>
 * This O(n) solution uses a stack to keep track of the indices of bars that are in
 * increasing order of height. When a smaller bar is encountered, it signals the end
 * of a potential rectangle for the taller bars on the stack. We can then calculate
 * the area for those bars.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class LargestRectangleInHistogram {

  /**
   * Calculates the area of the largest rectangle in a histogram.
   *
   * @param heights An array of bar heights.
   * @return The area of the largest possible rectangle.
   */
  public static int largestRectangleArea(int[] heights) {
    if (heights == null || heights.length == 0) {
      return 0;
    }

    // The stack will store the *indices* of the bars.
    Stack<Integer> stack = ArrayStack.empty();
    int maxArea = 0;
    int n = heights.length;

    for (int i = 0; i <= n; i++) {
      // Use a virtual height of 0 at the end to ensure all bars are processed.
      int currentHeight = (i == n) ? 0 : heights[i];

      // While the stack is not empty and the bar at the top of the stack
      // is taller than the current bar...
      while (!stack.isEmpty() && heights[stack.top()] >= currentHeight) {
        // ... we've found the right boundary for the bar at the top of the stack.
        // So, we pop it and calculate its maximum possible rectangle area.
        int heightOfPoppedBar = heights[stack.top()];
        stack.pop();

        // The left boundary is the index of the element now at the top of the stack.
        // If the stack is empty, the left boundary is the beginning of the histogram.
        int leftBoundary = stack.isEmpty() ? -1 : stack.top();

        // The right boundary is the current index 'i'.
        int width = i - leftBoundary - 1;

        int area = heightOfPoppedBar * width;
        maxArea = Math.max(maxArea, area);
      }

      // Push the current index onto the stack.
      stack.push(i);
    }

    return maxArea;
  }

  public static void main(String[] args) {
    System.out.println("--- Finding the Largest Rectangle in a Histogram ---");

    List<Tuple2<int[], Integer>> testCases = JDKArrayList.of(
        Tuple2.of(new int[]{2, 1, 5, 6, 2, 3}, 10),
        Tuple2.of(new int[]{2, 4}, 4),
        Tuple2.of(new int[]{6, 2, 5, 4, 5, 1, 6}, 12),
        Tuple2.of(new int[]{1, 1, 1, 1, 1}, 5),
        Tuple2.of(new int[]{4, 2, 0, 3, 2, 5}, 6)
    );

    for (var entry : testCases) {
      int[] heights = entry.first();
      int expected = entry.second();
      int result = largestRectangleArea(heights);

      System.out.printf("Heights:  %-30s%n", Arrays.toString(heights));
      System.out.printf("Result:   %-30d -> %s%n", result, result == expected ? "SUCCESS" : "FAILURE");
      if (result != expected) {
        System.out.printf("Expected: %d%n", expected);
      }
      System.out.println();
    }
  }
}