import java.util.Arrays;

class ArmstrongNumbers {

    int armstrongSum(int numberToCheck) {
		int numberOfDigits = Integer.toString(numberToCheck).length();

        return Arrays.stream(String.valueOf(numberToCheck).split(""))
                .map(Integer::parseInt)
                .mapToInt(value -> (int) Math.pow(value, numberOfDigits))
                .sum();
    }

    boolean isArmstrongNumber(int numberToCheck) {
        return (numberToCheck == armstrongSum(numberToCheck));
    }

}
