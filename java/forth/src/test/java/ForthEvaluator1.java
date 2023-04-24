import java.util.*;
import java.util.function.UnaryOperator;

public class ForthEvaluator1 {
    public static final String ADDITION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES =
            "Addition requires that the stack contain at least 2 values";
    public static final String SUBTRACTION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES =
            "Subtraction requires that the stack contain at least 2 values";
    public static final String MULTIPLICATION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES =
            "Multiplication requires that the stack contain at least 2 values";
    public static final String DIVISION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES =
            "Division requires that the stack contain at least 2 values";
    public static final String DIVISION_BY_0_IS_NOT_ALLOWED =
            "Division by 0 is not allowed";
    public static final String DUPLICATING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_1_VALUE =
            "Duplicating requires that the stack contain at least 1 value";
    public static final String DROPPING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_1_VALUE =
            "Dropping requires that the stack contain at least 1 value";
    public static final String SWAPPING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES =
            "Swapping requires that the stack contain at least 2 values";
    public static final String OVERING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES =
            "Overing requires that the stack contain at least 2 values";
    public static final String CANNOT_REDEFINE_NUMBERS =
            "Cannot redefine numbers";
    public static final String NO_DEFINITION_AVAILABLE_FOR_OPERATOR =
            "No definition available for operator \"%s\"";
    public static final String BEGIN_DEF = ":";
    public static final String END_DEF = ";";

    private Stack<Integer> stack = new Stack<>();
    private final Map<String, UnaryOperator<Stack<Integer>>> operators = new HashMap<>();
    private final List<String> basicNames =
            Arrays.asList("+", "-", "*", "/", "over", "dup", "drop", "swap");

    class Operator {
        UnaryOperator<Stack<Integer>> operator;
        String commands;
    }


    public ForthEvaluator1() {
        initializeOperators();
    }

    public List<Integer> evaluateProgram(List<String> inputs) {
        inputs.forEach(this::evaluateProgram);
        return stack;
    }

    private void evaluateProgram(String input) {
        String[] tokens = input.toLowerCase().split(" ");

        if (tokens[0].equals(BEGIN_DEF)) {
            defineNewOperator(tokens);
            return;
        }
        for (String token : tokens) {
            stack = applyOperator(stack, token);
        }
    }

    private void defineNewOperator(String[] tokens) {
        if (tokens[1].matches("\\d+")) {
            throw new IllegalArgumentException(CANNOT_REDEFINE_NUMBERS);
        }

        UnaryOperator<Stack<Integer>> operator = integers -> {
            for (int i = 2; i < tokens.length - 1; i++) {
                integers = applyOperator(integers, tokens[i]);
            }
            return integers;
        };
        operators.put(tokens[1], operator);

//        operators.put(tokens[1], integers -> {
//            for (int i = 2; i < tokens.length - 1; i++) {
//                integers = applyOperator(integers, tokens[i]);
//            }
//            return integers;
//        });
    }

    private Stack<Integer> applyOperator(Stack<Integer> integers, String operatorName) {
        if (operatorName.matches("\\d+")) {
            integers.push(Integer.parseInt(operatorName));
        } else {
            if (!operators.containsKey(operatorName)) {
                throw new IllegalArgumentException(
                        String.format(NO_DEFINITION_AVAILABLE_FOR_OPERATOR, operatorName));
            }
            integers = operators.get(operatorName).apply(integers);
        }
        return integers;
    }

    private void initializeOperators() {
        operators.put("+", integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg1 + arg2);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(ADDITION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return integers;
        });

        operators.put("-", integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg2 - arg1);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(SUBTRACTION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return integers;
        });

        operators.put("*", integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg1 * arg2);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(MULTIPLICATION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return integers;
        });

        operators.put("/", integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg2 / arg1);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(DIVISION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException(DIVISION_BY_0_IS_NOT_ALLOWED);
            }
            return integers;
        });

        operators.put("over", integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg2);
                integers.push(arg1);
                integers.push(arg2);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(OVERING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return stack;
        });

        operators.put("dup", integers -> {
            try {
                Integer arg = integers.pop();
                integers.push(arg);
                integers.push(arg);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(DUPLICATING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_1_VALUE);
            }
            return stack;
        });

        operators.put("drop", integers -> {
            try {
                integers.pop();
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(DROPPING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_1_VALUE);
            }
            return stack;
        });

        operators.put("swap", integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg1);
                integers.push(arg2);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(SWAPPING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return stack;
        });
    }
}
