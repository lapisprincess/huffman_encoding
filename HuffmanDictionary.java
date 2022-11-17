import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanDictionary implements CodeDictionary {
    public HuffmanTree<Node> htree;

    public HuffmanDictionary(String infile) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File(infile));
        HashMap<String, Node> characterFrequency = new HashMap<>();

        int b = 0;
        while (b != -1) {
            try {
                b = fis.read();
            } catch (IOException E) {
                System.out.println("Error reading file: " + E.toString());
                b = -1;
            }
            String out = convertCharacter(b);
            characterFrequency.putIfAbsent(out, new Node(out));
        }

        PriorityQueue<HuffmanTree<Node>> tree = new PriorityQueue<>();

        characterFrequency.forEach(
            (key, value)
                -> tree.add(new HuffmanTree<Node>(value))
        );

        while (tree.size() > 1) {
            HuffmanTree<Node> tree1 = trees.poll();
            HuffmanTree<Node> tree2 = trees.poll();
            tree.offer(new HuffmanTree<Node>(new Node(
                tree1.getData().getData() + tree2.getData().getData(),
                tree1.getData().getFrequency() + tree2.getData().getFrequency()),
                tree1, tree2));
        }

        this.htree = tree.poll();
    }

    private String convertCharacter(int b) {
        char c = (char) b;
        switch(c) {
            case '\n':
                return "\\n";
            case '\t':
                return "\\t";
            case '\r':
                return "\\r";
            default:
                return c + "";
        }
    }
    
    private class HuffmanTree<E extends comparable> 
        extends BinaryTree<Node> 
        implements Comparable {

        public HuffmanTree(E root) {
            super(root, null, null);
        }

        public String getRootData() {
            return ((Node) this).getData();
        @Override
        public int compare(HuffmanTree<Node> compareTree) {
            return this.getData().compareTo(compareTree.getData());
        }
    }
            
 
    private class Node<E> implements Comparable<Node> {
        private String c;
        private int frequency;

        public Node(String c) {
            this(c, 0);
        }
            
        public Node(String c, int frequency) {
            this.c = c;
            this.frequency = frequency;
        }

        public String getData() {
            return this.c;
        }

        public int getFrequency() {
            return this.frequency;
        }

        public void incrementFrequency() {
            this.frequency++;
        }

        @Override
        public int compareTo(Node n) {
            return this.getFrequency() - n.getFrequency();
        }
    }

    @Override
    public String lookup(String key) {
        
    }

    @Override
    public String reverseLookup(String value) {
        
    }
}
