import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

public class Day6 {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Missing filename");
            return;
        }

        String filename = args[0];
        String input = Files.lines(Paths.get(filename))
            .findFirst().get();
        String[] tokens = input.split(" ");
        
        int[] intInput = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            intInput[i] = Integer.parseInt(tokens[i]);
        }

        firstStar(Arrays.copyOf(intInput, intInput.length));
        secondStar(Arrays.copyOf(intInput, intInput.length));
    }

    private static void firstStar(int[] input) {
        List<int[]> previous = new ArrayList<>();

        int cycleCount = 0;
        while (!contains(previous, input)) {
            previous.add(Arrays.copyOf(input, input.length));

            int redistIndex = findMax(input);
            int numBlocks = input[redistIndex];
            input[redistIndex] = 0;

            while (numBlocks > 0) {
                redistIndex++;
                if (redistIndex >= input.length)
                    redistIndex = 0;

                input[redistIndex]++;
                numBlocks--;
            }
            cycleCount++;
        }
        System.out.println(cycleCount);
    }

    private static void secondStar(int[] input) {
        Map<int[], Integer> previous = new HashMap<>();

        int cycleCount = 0;
        while (!contains(previous.keySet(), input)) {
            previous.put(Arrays.copyOf(input, input.length), cycleCount);

            int redistIndex = findMax(input);
            int numBlocks = input[redistIndex];
            input[redistIndex] = 0;

            while (numBlocks > 0) {
                redistIndex++;
                if (redistIndex >= input.length)
                    redistIndex = 0;

                input[redistIndex]++;
                numBlocks--;
            }
            cycleCount++;
        }

        int prevCycle = 0;
        for (int[] p : previous.keySet()) {
            if (Arrays.equals(p, input)) {
                prevCycle = previous.get(p);
            }
        }
        System.out.println(cycleCount-prevCycle);

    }

    private static int findMax(int[] arr) {
        int max = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[max]) {
                max = i;
            }
        }
        return max;
    }

    private static boolean contains(Collection<int[]> arrs, int[] target) {
        for (int[] arr : arrs) {
            if (Arrays.equals(arr, target))
                return true;
        }
        return false;
    }
}
