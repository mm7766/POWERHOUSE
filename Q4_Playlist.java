import java.util.*;

class Node {
    String song;
    Node prev, next;

    Node(String song) {
        this.song = song;
    }
}

class Q4_Playlist {

    private Node head, tail, current;
    private HashMap<String, Node> map;

    public Q4_Playlist() {
        map = new HashMap<>();
        head = tail = current = null;
    }

    public boolean addSong(String song) {
        if (map.containsKey(song)) return false;

        Node newNode = new Node(song);
        map.put(song, newNode);

        if (head == null) {
            head = tail = current = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        return true;
    }

    public boolean removeSong(String song) {
        if (!map.containsKey(song)) return false;

        Node node = map.get(song);

        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;

        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;

        if (current == node) {
            current = (node.next != null) ? node.next : node.prev;
        }

        map.remove(song);
        return true;
    }

    public String playNext() {
        if (current == null) return null;
        if (current.next != null) current = current.next;
        return current.song;
    }

    public String playPrev() {
        if (current == null) return null;
        if (current.prev != null) current = current.prev;
        return current.song;
    }

    public String getCurrentSong() {
        return (current == null) ? null : current.song;
    }

    public List<String> exportForward() {
        List<String> list = new ArrayList<>();
        Node temp = head;
        while (temp != null) {
            list.add(temp.song);
            temp = temp.next;
        }
        return list;
    }

    public List<String> exportBackward() {
        List<String> list = new ArrayList<>();
        Node temp = tail;
        while (temp != null) {
            list.add(temp.song);
            temp = temp.prev;
        }
        return list;
    }
    public static void main(String[] args) {

        Q4_Playlist p = new Q4_Playlist();

        p.addSong("Song A");
        p.addSong("Song B");
        p.addSong("Song C");

        System.out.println(p.getCurrentSong());

        p.playNext();
        System.out.println(p.getCurrentSong());

        p.playNext();
        System.out.println(p.getCurrentSong());

        p.playPrev();
        System.out.println(p.getCurrentSong());

        p.removeSong("Song B");
        System.out.println(p.getCurrentSong());

        System.out.println(p.exportForward());
        System.out.println(p.exportBackward());
    }

}