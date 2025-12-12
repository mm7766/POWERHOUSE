import java.util.*;

public class Q3_RecursionFileExplorer {

    static class Item {
        String type;
        String name;
        long size;
        List<Item> children;
        Item target;

        static Item file(String name, long size) {
            Item i = new Item();
            i.type = "file";
            i.name = name;
            i.size = size;
            return i;
        }

        static Item folder(String name) {
            Item i = new Item();
            i.type = "folder";
            i.name = name;
            i.children = new ArrayList<>();
            return i;
        }

        static Item link(String name, Item target) {
            Item i = new Item();
            i.type = "link";
            i.name = name;
            i.target = target;
            return i;
        }
    }

    static class Result {
        long totalSize;
        List<Item> top3;

        Result(long s, List<Item> t) {
            totalSize = s;
            top3 = t;
        }
    }

    static Result compute(Item x, Set<Item> visiting, Map<Item, Result> memo) {

        if (memo.containsKey(x))
            return memo.get(x);

        if (visiting.contains(x))
            return new Result(0, new ArrayList<>());

        visiting.add(x);

        Result res;

        if (x.type.equals("file")) {
            List<Item> list = new ArrayList<>();
            list.add(x);
            res = new Result(x.size, list);
        }
        else if (x.type.equals("link")) {
            res = compute(x.target, visiting, memo);
        }
        else {
            long sum = 0;
            List<Item> top = new ArrayList<>();

            for (Item child : x.children) {
                Result r = compute(child, visiting, memo);
                sum += r.totalSize;

                top.addAll(r.top3);
                top.sort((a, b) -> Long.compare(b.size, a.size));
                if (top.size() > 3) top = top.subList(0, 3);
            }

            res = new Result(sum, top);
        }

        visiting.remove(x);
        memo.put(x, res);
        return res;
    }

    public static void main(String[] args) {

        Item root = Item.folder("root");

        Item a = Item.file("A.txt", 100);
        root.children.add(a);

        Item pics = Item.folder("pics");
        Item b = Item.file("photo1.jpg", 500);
        Item c = Item.file("photo2.jpg", 200);
        pics.children.add(b);
        pics.children.add(c);

        root.children.add(pics);

        Item d = Item.file("movie.mp4", 800);
        root.children.add(d);

        Item link1 = Item.link("link_to_pics", pics);
        root.children.add(link1);

        Item cycle = Item.link("cycle_to_root", root);
        pics.children.add(cycle);

        Result r = compute(root, new HashSet<>(), new HashMap<>());

        System.out.println("Total Size = " + r.totalSize);
        System.out.println("Top 3 Files:");
        for (Item f : r.top3) {
            System.out.println(f.name + " : " + f.size);
        }
    }
}