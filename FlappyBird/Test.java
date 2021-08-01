package FlappyBird;

import java.util.LinkedList;

public class Test {
	public static void main(String[] args) {
		LinkedList<Integer> nums = new LinkedList<Integer>();
		nums.add(5);
		nums.add(6);
		nums.add(7);
		nums.add(8);
		nums.removeFirst();
		nums.addLast(9);
		System.out.println(nums.get(0));
	}
}
