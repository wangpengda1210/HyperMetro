import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        int totalDepth = new Scanner(System.in).nextInt();
        System.out.print(0 + " ");
        printChildren(0, 0, totalDepth);
    }

    public static void printChildren(int parent, int currentDepth, int totalDepth) {
        for (int i = parent * 3 + 1; i <= parent * 3 + 3; i++) {
            System.out.print(i + " ");
            if (currentDepth < totalDepth - 1) {
                printChildren(i, currentDepth + 1, totalDepth);
            }
        }
    }
}