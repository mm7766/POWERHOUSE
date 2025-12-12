import java.util.*;

public class Q9_HashingFraudDetection {

    static class Transaction {
        String id;
        long amount;
        long time;

        Transaction(String id, long amount, long time) {
            this.id = id;
            this.amount = amount;
            this.time = time;
        }

        String key() {
            return id + "|" + amount;
        }
    }

    static class Node {
        Transaction tx;
        Node prev;
        Node next;

        Node(Transaction tx) {
            this.tx = tx;
        }
    }

    long window;
    Map<String, Node> last;
    Set<String> suspicious;
    Node head;
    Node tail;

    public Q9_HashingFraudDetection(long windowSeconds) {
        window = windowSeconds;
        last = new HashMap<>();
        suspicious = new HashSet<>();
    }

    public boolean process(Transaction t) {
        removeOld(t.time);

        String key = t.key();
        Node prevNode = last.get(key);

        boolean dup = prevNode != null;
        if (dup) {
            suspicious.add(t.id);
            suspicious.add(prevNode.tx.id);
        }

        Node n = new Node(t);
        addLast(n);
        last.put(key, n);

        return dup;
    }

    void removeOld(long currentTime) {
        long limit = currentTime - window;

        while (head != null && head.tx.time < limit) {
            String key = head.tx.key();
            if (last.get(key) == head) {
                last.remove(key);
            }
            head = head.next;
            if (head != null) head.prev = null;
            else tail = null;
        }
    }

    void addLast(Node n) {
        if (tail == null) {
            head = tail = n;
        } else {
            tail.next = n;
            n.prev = tail;
            tail = n;
        }
    }

    public Set<String> getSuspicious() {
        return suspicious;
    }

    public static void main(String[] args) {
        Q9_HashingFraudDetection f = new Q9_HashingFraudDetection(10);

        long t = 1000;

        System.out.println(f.process(new Transaction("A", 5000, t+0)));
        System.out.println(f.process(new Transaction("B", 3000, t+2)));
        System.out.println(f.process(new Transaction("A", 5000, t+5)));
        System.out.println(f.getSuspicious());

        System.out.println(f.process(new Transaction("A", 5000, t+12)));
        System.out.println(f.process(new Transaction("C", 5000, t+13)));

        System.out.println(f.getSuspicious());
    }
}
