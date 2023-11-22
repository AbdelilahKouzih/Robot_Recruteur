package stageapp;

import java.awt.EventQueue;

public class main {
	 public static void f5(int[] T, int n) {
	        int i = 0, j = n, temp;
	        while (true) {
	            if (T[i] >= T[j - 1]) {
	                j--;
	                temp = T[i];
	                T[i] = T[j - 1];
	                T[j - 1] = temp;
	            } else {
	                i++;
	            }
	            if (i > j)
	                break;
	        }
	    }

	    public static void main(String[] args) {
	        int[] T = {3, 7, 9, 6, 8, 11, 33, 5, 20, 2};
	        f5(T, 10);
	        for (int i = 0; i < 10; i++) {
	            System.out.print(T[i] + " ");
	        }
	    }
}
