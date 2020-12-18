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
                }
                childNode.parent = parentNode;
                if (parentNode.left == null) {
                    parentNode.left = childNode;
                } else if (parentNode.right == null) {
                    parentNode.right = childNode;
                } else {
                    System.out.println("no");
                    return;
                }
            } else {
                TreeNode<Integer> parentNode = new TreeNode<>(parent);
                TreeNode<Integer> childNode = new TreeNode<>(child);
                if (nodes.stream().filter(node -> node.data == child).count() == 1) {
                    childNode = nodes.stream().filter(node -> node.data == child).findFirst().get();
                } else {
                    nodes.add(childNode);
                }
                childNode.parent = parentNode;
                parentNode.left = childNode;
                nodes.add(parentNode);
            }
        }

        nodes.sort(Comparator.comparing(integerTreeNode -> integerTreeNode.data));

        boolean lastFound = false;
        for (TreeNode<Integer> node : nodes) {
            if (node.left == null && node.right != null) {
                System.out.println("no");
                return;
            } else if (node.left != null && node.right != null) {
                if (lastFound) {
                    System.out.println("no");
                    return;
                }
            } else if (node.left != null) {
                System.out.println("no");
                return;
            } else {
                lastFound = true;
            }
        }

        System.out.println("yes");
    }

    public static class TreeNode<T> {
        public T data;
        public TreeNode<T> parent;
        public TreeNode<T> left;
        public TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
            this.parent = null;
            this.left = null;
            this.right = null;
        }
    }
}