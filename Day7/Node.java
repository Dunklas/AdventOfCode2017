import java.util.Map;

public class Node {

    public String name;
    public int totalWeight;
    public int weight;
    public Map<Node, Integer> children;

    public Node(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}
