import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Missing filename");
            return;
        }

        String filename = args[0];

        List<String> input = Files.lines(Paths.get(filename))
            .map(s -> s.replace("(", "").replace(")", "").replace(",", ""))
            .collect(Collectors.toList());

        String root = firstStar(input);
        System.out.println("Root: " + root);
        secondStar(input, root);
    }

    private static String firstStar(List<String> input) {

        Set<String> children = new HashSet<>();
        input.stream()
            .map(s -> s.split(" "))
            .filter(s -> s.length > 2)
            .map(arr -> Arrays.copyOfRange(arr, 3, arr.length))
            .forEach(arr -> children.addAll(Arrays.asList(arr)));

        String root = input.stream()
            .map(s -> s.split(" "))
            .filter(arr -> arr.length > 2)
            .map(arr -> arr[0])
            .filter(s -> !children.contains(s))
            .findAny()
            .get();

        return root;
    }

    private static void secondStar(List<String> input, String root) {
        Map<String, Node> nodeMap = new HashMap<>();
        
        input.stream()
            .map(s -> s.split(" "))
            .forEach(arr -> {
                Node n = new Node(arr[0], Integer.parseInt(arr[1]));
                nodeMap.put(arr[0], n);
            });
        input.stream()
            .map(s -> s.split(" "))
            .filter(arr -> arr.length > 2)
            .forEach(arr -> {
                String[] children = Arrays.copyOfRange(arr, 3, arr.length);
                Map<Node, Integer> childrenNodes = new HashMap<>();
                for (String s : children) {
                    childrenNodes.put(nodeMap.get(s), 0);
                }
                nodeMap.get(arr[0]).children = childrenNodes;
            });

        calculateWeights(nodeMap.get(root));
        findFaulty(nodeMap.get(root), -1);
    }

    private static int calculateWeights(Node node) {

        if (node.children == null) {
            node.totalWeight = node.weight;
            return node.weight;
        }

        int totalWeight = node.weight;
        for (Node c : node.children.keySet()) {
            int childWeight = calculateWeights(c);
            node.children.put(c, childWeight);
            totalWeight += childWeight;
        }

        node.totalWeight = totalWeight;
        return totalWeight;
    }

    private static void findFaulty(Node node, int expectedWeight) {

        if (node.children == null) {
            return;
        }

        if (nodesBalanced(node.children.keySet())) {

            System.out.println("Faulty program: " + node.name);
            int offset = expectedWeight - node.totalWeight;
            System.out.printf("Node weight is %d, should have been: %d\n", node.weight, node.weight+offset);

        } else {
            Node n = findUnbalanced(node.children.keySet());
            int expected = expectedWeight(node.children.keySet());
            findFaulty(n, expected);
        }
    }

    private static boolean nodesBalanced(Collection<Node> nodes) {
        if (nodes == null || nodes.size() < 1) return false;

        Set<Integer> tmp = new HashSet<>();
        for (Node n : nodes) {
            tmp.add(n.totalWeight);
        }
        return tmp.size() == 1;
    }

    private static Node findUnbalanced(Collection<Node> nodes) {
        Map<Integer, Integer> weightFreq = new HashMap<>();

        for (Node n : nodes) {
            Integer prevWeight = weightFreq.get(n.totalWeight);
            if (prevWeight == null) {
                weightFreq.put(n.totalWeight, 1);
            } else {
                weightFreq.put(n.totalWeight, ++prevWeight);
            }
        }

        int unbalancedWeight = -1;
        int lowestFreq = Integer.MAX_VALUE;
        for (Integer w : weightFreq.keySet()) {
            if (weightFreq.get(w) < lowestFreq) {
                lowestFreq = weightFreq.get(w);
                unbalancedWeight = w;
            } 
        }

        for (Node n : nodes) {
            if (n.totalWeight == unbalancedWeight) {
                return n;
            }
        }
        return null;
    }

    private static int expectedWeight(Collection<Node> nodes) {
        Map<Integer, Integer> weightFreq = new HashMap<>();

        for (Node n : nodes) {
            Integer prevWeight = weightFreq.get(n.totalWeight);
            if (prevWeight == null) {
                weightFreq.put(n.totalWeight, 1);
            } else {
                weightFreq.put(n.totalWeight, ++prevWeight);
            }
        }

        int balancedWeight = -1;
        int highestFreq = 0;
        for (Integer w : weightFreq.keySet()) {
            if (weightFreq.get(w) > highestFreq) {
                highestFreq = weightFreq.get(w);
                balancedWeight = w;
            }
        }

        return balancedWeight;
    }
}
