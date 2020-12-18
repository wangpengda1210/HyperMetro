import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // put your code here
        ArrayList<TreeNode<Integer>> nodes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        int count = scanner.nextInt();
        for (int i = 0; i < count; i++) {
            TreeNode<Integer> node = new TreeNode<>(i);
            node.parent = new TreeNode<>(scanner.nextInt());
            nodes.add(node);
        }

        TreeNode<Integer> top = null;
        for (TreeNode<Integer> node : nodes) {
            int parentValue = node.parent.data;
            if (parentValue == -1) {
                node.parent = null;
                top = node;
            } else {
                node.parent = nodes.get(node.parent.data);
                node.parent.children.add(node);
            }
        }

        int height = 0;
        for (TreeNode<Integer> node : nodes) {
            int thisHeight = 0;
            TreeNode<Integer> curr = node;
            while (curr.parent != null) {
                thisHeight++;
                curr = curr.parent;
            }
            if (thisHeight > height) {
                height = thisHeight;
            }
        }
        height++;

        System.out.println(height);
    }

    public static class TreeNode<T> {
        public T data;
        public TreeNode<T> parent;
        public List<TreeNode<T>> children;

        public TreeNode(T data) {
            this.data = data;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public TreeNode<T> add(T value) {
            TreeNode<T> newVertex = new TreeNode<>(value);
            newVertex.parent = this;
            this.children.add(newVertex);
            return newVertex;
        }
    }
}