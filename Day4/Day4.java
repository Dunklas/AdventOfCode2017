import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Day4 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Missing filename");
            return;
        }

        String filename = args[0];
        Stream<String> duplicateStream;
        Stream<String> anagramStream;
        try {
            duplicateStream = Files.lines(Paths.get(filename));
            anagramStream = Files.lines(Paths.get(filename));
        } catch (IOException ioe) {
            System.out.printf("Could not read file: %s\n", filename);
            return;
        }

        long numDuplicateFree = duplicateStream
            .map((l) -> l.split(" "))
            .filter((a) -> !containDuplicates(a))
            .count();
        System.out.println("Lines without duplicates: " + numDuplicateFree);

        long numAnagramFree = anagramStream
            .map((l) -> l.split(" "))
            .filter((a) -> !containDuplicates(a))
            .filter((a) -> !containAnagrams(a))
            .count();
        System.out.println("Lines without anagrams: " + numAnagramFree);

    }

    private static boolean containDuplicates(String[] arr) {
        Set<String> stringSet = new HashSet<>();
        stringSet.addAll(Arrays.asList(arr));

        return stringSet.size() != arr.length;
    }

    private static boolean containAnagrams(String[] arr) {
        Set<String> wordSet = new HashSet<>();
        for (String word : arr) {
            if (!wordSet.add(sortString(word)))
                return true;
        }
        return false;
    }

    private static String sortString(String word) {
        char[] chars = word.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);   
        
        /*return word.toLowerCase().codePoints()
            .sorted()
            .collect(StringBuilder::new,
                    StringBuilder::appendCodePoint, StringBuilder::append)
            .toString(); */
    }
}
