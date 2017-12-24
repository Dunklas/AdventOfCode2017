import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Day5 {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Missing filename");
            return;
        }

        String filename = args[0];
        int[] intInput = Files.lines(Paths.get(filename))
            .map(i -> i.trim())
            .mapToInt(i -> Integer.parseInt(i))
            .toArray();

        firstStar(Arrays.copyOf(intInput, intInput.length));
        secondStar(Arrays.copyOf(intInput, intInput.length));
    }

    private static void firstStar(int[] instructions) {
        
        int numSteps = 0;
        int index = 0;
        while (index < instructions.length && index >= 0) {
            int step = instructions[index]++;
            index += step;
            numSteps++;
        }
        System.out.println(numSteps);
    }

    private static void secondStar(int[] instructions) {

        int numSteps = 0;
        int index = 0;

        while (index < instructions.length && index >= 0) {
            int step = instructions[index];
            instructions[index] += step >= 3 ? -1 : 1;
            index += step;
            numSteps++;
        }
        System.out.println(numSteps);
    }
}
