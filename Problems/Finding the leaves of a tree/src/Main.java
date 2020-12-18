import java.util.*;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int count = scanner.nextInt();

        ArrayList<TreeNode<Integer>> nodes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int parent = scanner.nextInt();
            int child = scanner.nextInt();
            if (nodes.stream().filter(node -> node.data == parent).count() == 1) {
                TreeNode<Integer> parentNode = nodes.stream().filter(node -> node.data == parent).findFirst().get();
                TreeNode<Integer> childNode = new TreeNode<>(child);
                if (nodes.stream().filter(node -> node.data == child).count() == 1) {
                    childNode = nodes.stream().filter(node -> node.data == child).findFirst().get();
                } else {
                    nodes.add(childNode);
                }
                childNode.parent = parentNode;
                parentNode.children.add(childNode);
            } else {
                TreeNode<Integer> parentNode = new TreeNode<>(parent);
                TreeNode<Integer> childNode = new TreeNode<>(child);
                if (nodes.stream().filter(node -> node.data == child).count() == 1) {
                    childNode = nodes.stream().filter(node -> node.data == child).findFirst().get();
                } else {
                    nodes.add(childNode);
                }
                childNode.parent = parentNode;
                parentNode.children.add(childNode);
                nodes.add(parentNode);
            }
        }

        ArrayList<Integer> leaves = new ArrayList<>();
        for (TreeNode<Integer> node : nodes) {
            if (node.children.isEmpty()) {
                leaves.add(node.data);
            }
        }

        System.out.println(leaves.size());
        for (int leaf : leaves) {
            System.out.print(leaf + " ");
        }
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