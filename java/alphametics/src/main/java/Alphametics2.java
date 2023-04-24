import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Alphametics2 {

    private final String result;

    private final String[] terms;

    private List<Character> chars;

    private List<Character> firsts;

    public Alphametics2(String ecuation) {

        String[] parts = ecuation.split("\\s==\\s");

        result = parts[1];

        terms = parts[0].split("\\s\\+\\s");

        Set<Character> charSet = stringToSet(result);

        Arrays.stream(terms).forEach(s -> charSet.addAll(stringToSet(s)));

        chars = charSet.stream().collect(Collectors.toList());

        Set<Character> firstsSet = new HashSet<>();

        firstsSet.add(result.charAt(0));

        Arrays.stream(terms).forEach(s -> firstsSet.add(s.charAt(0)));

        firsts = firstsSet.stream().collect(Collectors.toList());

    }

    public Map<Character, Integer> solve() throws UnsolvablePuzzleException {

        int[] nums = IntStream.range(0, chars.size()).toArray();

        Map<Character, Integer> map = updateMap(new HashMap<>(), nums);

        while (nums != null && !isSolution(map)) {

            nums = next(nums);

            if (nums == null) {

                throw new UnsolvablePuzzleException();

            }

            updateMap(map, nums);

        }

        return map;

    }

    private boolean isSolution(Map<Character, Integer> map) {

        int resultVal = wordValue(result, map);

        int total = Arrays.stream(terms).mapToInt(w -> wordValue(w, map)).sum();

        return firsts.stream().noneMatch(c -> map.get(c) == 0) && total == resultVal;

    }

    private Map<Character, Integer> updateMap(Map<Character, Integer> map, int[] nums) {

        for (int i = 0; i < chars.size(); i++) {

            map.put(chars.get(i), nums[i]);

        }

        return map;

    }

    private int[] next(int[] nums) {

        for (int i = nums.length - 1; i >= 0; i--) {

            nums[i] = smallestBiggerAvailable(nums[i] + 1, nums, i);

            if (nums[i] <= 9)

                return filllSmallests(nums, i + 1);

        }

        return null;

    }

    private int[] filllSmallests(int[] nums, int from) {

        for (int i = from; i < nums.length; i++) {

            nums[i] = smallestBiggerAvailable(0, nums, i);

        }

        return nums;

    }

    private int smallestBiggerAvailable(int atLeast, int[] nums, int upto) {

        int last;

        do {

            last = atLeast;

            for (int i = 0; i < upto; i++) {

                if (nums[i] == atLeast)

                    atLeast++;

            }

        } while (last != atLeast);

        return atLeast;

    }

    private Set<Character> stringToSet(String s) {

        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());

    }

    private int wordValue(String word, Map<Character, Integer> map) {

        char[] letters = word.toCharArray();

        int tot = 0;

        for (int i = 0; i < letters.length; i++) {

            tot = tot * 10 + map.get(letters[i]);

        }

        return tot;

    }

}
