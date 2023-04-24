import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Long.*;

class VariableLengthQuantity {
    public static final String INVALID_VARIABLE_LENGTH_QUANTITY_ENCODING =
            "Invalid variable-length quantity encoding";

    List<String> decode(List<Long> bytes) {
        List<String> result = new ArrayList<>();
        List<String> sequence = new ArrayList<>();

        for (int i = 0; i < bytes.size(); i++) {
            String binary = toBinaryString(bytes.get(i));
            binary = completeStringToOctet(binary);
            sequence.add(binary);
            if (binary.startsWith("0") || i == bytes.size() - 1) {
                result.add(decodeSequence(sequence));
                sequence.clear();
            }
        }
        return result;
    }

    private String decodeSequence(List<String> sequence) {
        if (!sequence.get(sequence.size() - 1).startsWith("0")) {
            throw new IllegalArgumentException(INVALID_VARIABLE_LENGTH_QUANTITY_ENCODING);
        }
        return convertBinaryToHex(sequence.stream()
                .map(s -> s.substring(1))
                .collect(Collectors.joining()));
    }

    List<String> encode(List<Long> numbers) {
        return numbers.stream()
                .flatMap(aLong -> encode(aLong).stream())
                .collect(Collectors.toList());
    }

    List<String> encode(Long number) {
        String binary = completeStringToMultipleOfSeven(toBinaryString(number));
        int count = binary.length() / 7;
        List<String> bytes =
                IntStream.range(0, count)
                        .mapToObj(i -> IntStream.range(0, 7)
                                .mapToObj(j -> binary.charAt(7 * i + j))
                                .map(String::valueOf)
                                .collect(Collectors.joining()))
                        .collect(Collectors.toList());

        return IntStream.range(0, bytes.size())
                .mapToObj(i -> {
                    String firstSign = i == bytes.size() - 1 ?
                            "0" :
                            "1";
                    return firstSign + bytes.get(i);
                })
                .map(this::convertBinaryToHex)
                .collect(Collectors.toList());
    }

    private String completeStringToMultipleOfSeven(String binary) {
        int needZeros = binary.length() % 7;
        if (needZeros != 0) {
            needZeros = 7 - needZeros;
        }
        return "0".repeat(needZeros) + binary;
    }

    private String convertBinaryToHex(String binary) {
        return "0x" + toHexString(parseLong(binary, 2));
    }

    private String completeStringToOctet(String binary) {
        int length = binary.length();
        if (length == 8) {
            return binary;
        } else {
            int nullsToAdd = 8 - length;
            return "0".repeat(nullsToAdd) + binary;
        }
    }
}
