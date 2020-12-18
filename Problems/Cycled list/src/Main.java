import java.io.*;
import java.util.*;

class Main {

    static class Node<E> {

        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(E element, Node<E> next, Node<E> prev) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }

        Node<E> getNext() {
            return next;
        }

        Node<E> getPrev() {
            return prev;
        }
    }

    public static class DoublyLinkedList<E> {

        public Node head;
        public Node tail;
        public int size;

        DoublyLinkedList() {
            size = 0;
        }

        public Node<E> getHead() {
            return head;
        }

        public Node<E> getTail() {
            return tail;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public String toString() {

            Node<E> tmp = head;
            StringBuilder result = new StringBuilder();

            while (tmp != null) {
                result.append(tmp.value).append(" ");
                tmp = tmp.next;
            }

            return result.toString();
        }

        void addFirst(E elem) {

            Node<E> tmp = new Node<>(elem, head, null);

            if (head != null) {
                head.prev = tmp;
            }

            head = tmp;

            if (tail == null) {
                tail = tmp;
            }
            size++;
        }

        void addLast(E elem) {

            Node<E> tmp = new Node<>(elem, null, tail);

            if (tail != null) {
                tail.next = tmp;
            }

            tail = tmp;

            if (head == null) {
                head = tmp;
            }
            size++;
        }

        void add(E elem, Node<E> curr) {

            if (curr == null) {
                throw new NoSuchElementException();
            }

            Node<E> tmp = new Node<>(elem, curr, null);

            if (curr.prev != null) {
                curr.prev.next = tmp;
                tmp.prev = curr.prev;
                curr.prev = tmp;
            } else {
                curr.prev = tmp;
                tmp.next = curr;
                head = tmp;
            }
            size++;
        }

        public void removeFirst() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            head = head.next;
            head.prev = null;
            size--;
        }

        public void removeLast() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            tail = tail.prev;
            tail.next = null;
            size--;
        }

        public void remove(Node<E> curr) {
            if (curr == null)
                throw new NoSuchElementException();
            if (curr.prev == null) {
                removeFirst();
                return;
            }
            if (curr.next == null) {
                removeLast();
                return;
            }
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
            size--;
        }

    }

    public static void main(String[] args) {
        // put your code here
        DoublyLinkedList<Integer> numbers = new DoublyLinkedList<>();

        Scanner scanner = new Scanner(System.in);

        int length = scanner.nextInt();
        int count = scanner.nextInt();

        for (int i = 0; i < length; i++) {
            numbers.addLast(scanner.nextInt());
        }

        try {
            Node<Integer> curr = numbers.getHead();
            for (int i = 0; i < count; i++) {
                String command = scanner.next();
                int position = scanner.nextInt();
                if ("l".equals(command)) {
                    for (int j = 0; j < position; j++) {
                        curr = curr.getPrev();
                        if (curr == null) {
                            curr = numbers.getTail();
                        }
                    }
                    numbers.remove(curr);
                    curr = curr.getPrev();
                    if (curr == null) {
                        curr = numbers.getTail();
                    }
                } else if ("r".equals(command)) {
                    for (int j = 0; j < position; j++) {
                        curr = curr.getNext();
                        if (curr == null) {
                            curr = numbers.getHead();
                        }
                    }
                    numbers.remove(curr);
                    curr = curr.getNext();
                    if (curr == null) {
                        curr = numbers.getHead();
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        System.out.println(numbers);
    }
}