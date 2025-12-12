package Day9;
import java.util.*;

class Nodes {
    String name;
    int parent;
    int depth;
    Map<String, Integer> children = new HashMap<>();

    Nodes(String name, int parent, int depth) {
        this.name = name;
        this.parent = parent;
        this.depth = depth;
    }
}

class FileSystemLCA {

    private int N = 200000;
    private Nodes[] nodes = new Nodes[N];
    private int[][] up;    
    private int LOG = 18;   
    private int nodeCount = 0;

    public FileSystemLCA() {
        up = new int[LOG][N];

        nodes[0] = new Nodes("/", -1, 0);
        up[0][0] = -1;
        nodeCount = 1;
    }

    public int createPath(String path) {
        if (path.equals("/")) return 0;

        String[] tokens = path.split("/");
        int cur = 0;

        for (String t : tokens) {
            if (t.isEmpty()) continue; 

            if (!nodes[cur].children.containsKey(t)) {
                nodes[cur].children.put(t, nodeCount);
                nodes[nodeCount] = new Nodes(t, cur, nodes[cur].depth + 1);

                up[0][nodeCount] = cur; 

                nodeCount++;
            }
            cur = nodes[cur].children.get(t);
        }

        return cur;
    }

    public int pathToDirectoryNode(String path) {
        String[] parts = path.split("/");

        if (parts.length == 0) return 0;

        if (parts[parts.length - 1].contains(".")) {
            path = path.substring(0, path.lastIndexOf('/'));
            if (path.isEmpty()) path = "/";
        }

        return createPath(path);
    }

    public void buildLCA() {
        for (int k = 1; k < LOG; k++) {
            for (int v = 0; v < nodeCount; v++) {
                int mid = up[k - 1][v];
                up[k][v] = (mid == -1) ? -1 : up[k - 1][mid];
            }
        }
    }

    public int lca(int a, int b) {

        if (nodes[a].depth < nodes[b].depth) {
            int temp = a; a = b; b = temp;
        }

        int diff = nodes[a].depth - nodes[b].depth;
        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0)
                a = up[k][a];
        }

        if (a == b) return a;

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][a] != up[k][b]) {
                a = up[k][a];
                b = up[k][b];
            }
        }

        return nodes[a].parent;
    }


    public String reconstructPath(int v) {
        if (v == 0) return "/";

        List<String> list = new ArrayList<>();
        while (v != 0) {
            list.add(nodes[v].name);
            v = nodes[v].parent;
        }
        Collections.reverse(list);

        return "/" + String.join("/", list);
    }
}

public class Tree_LowestCommonDictionaryInFileSystem {
	public static void main(String[] args) {

	    FileSystemLCA fs = new FileSystemLCA();

	    fs.createPath("/a/b/c");
	    fs.createPath("/a/b/d/e");
	    fs.createPath("/x/y/z");
	    
	    fs.buildLCA();

	    int u = fs.pathToDirectoryNode("/a/b/c/file1.txt");
	    int v = fs.pathToDirectoryNode("/a/b/d/e/img.png");

	    int lca = fs.lca(u, v);
	    System.out.println(fs.reconstructPath(lca));  
	}


}
