import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();

        ArrayList<Integer> roots = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            if (a * i * i * i + b * i * i + c * i + d == 0) {
                roots.add(i);

                if (roots.size() == 3) {
                    break;
                }
            }
        }

        for (int i : roots) {
            System.out.println(i);
        }
    }
}