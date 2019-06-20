import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Anagrams {

    public Collection collectAnagrams(String[] dictionary, String searchAnagramsFor) {
        AnagramMetrics metrics = new AnagramMetrics(searchAnagramsFor);
        return Arrays
                .stream(dictionary)
                .filter(metrics::isAnagram)
                .collect(Collectors.toList());
    }

    private static class AnagramMetrics {
        int length;
        int minChar;
        int maxChar;
        int[] counters;

        AnagramMetrics(String s) {
            length = s.length();
            if (length > 0) {
                maxChar = Character.MIN_VALUE;
                minChar = Character.MAX_VALUE;
                int[] counters = new int[Character.MAX_VALUE - Character.MIN_VALUE + 1];
                for (int i = 0; i < length; ++i) {
                    int c = s.charAt(i);
                    counters[c] += 1;
                    minChar = Math.min(c, minChar);
                    maxChar = Math.max(c, maxChar);
                }
                this.counters = Arrays.copyOfRange(counters, minChar, maxChar + 1);
            }
        }

        boolean isAnagram(String s) {
            int length = s.length();
            if (length != this.length) {
                return false;
            }
            if (this.length == 0) {
                return true;
            }
            int[] counters = this.counters.clone();
            for (int i = 0; i < length; ++i) {
                char ch = s.charAt(i);
                if (ch < this.minChar || ch > this.maxChar) {
                    return false;
                }
                int offset = ch - this.minChar;
                int count = counters[offset];
                if (count < 1) {
                    return false;
                }
                counters[offset] = count - 1;
            }
            return true;
        }
    }
}
