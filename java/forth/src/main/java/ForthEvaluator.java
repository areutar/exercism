import java.util.*;
import java.util.function.UnaryOperator;

import static text.Constant.*;

public class ForthEvaluator {
    private final Map<String, Operator> operators = new HashMap<>();
    private Stack<Integer> stack = new Stack<>();

    public ForthEvaluator() {
        initializeDictionary();
    }

    public List<Integer> evaluateProgram(List<String> input) {
        input.forEach(this::evaluateLine);
        return stack;
    }

    private void evaluateLine(String line) {
        String[] commands = line.toLowerCase().split(" ");

        if (commands[0].equals(BEGIN_DEF)) {
            compileNewWord(commands);
        } else {
            for (String command : commands) {
                stack = executeCommand(stack, command);
            }
        }
    }

    private void compileNewWord(String[] tokens) {
        if (tokens[1].matches("\\d+")) {
            throw new IllegalArgumentException(CANNOT_REDEFINE_NUMBERS);
        }

        String interpretation = interpretCommand(tokens[2]);
        for (int i = 3; i < tokens.length - 1; i++) {
            interpretation = interpretation + " " + interpretCommand(tokens[i]);
        }

        String[] commands = interpretation.split(" ");

        operators.put(tokens[1], new Operator(integers -> {
            for (int i = 0; i < commands.length - 1; i++) {
                integers = executeCommand(integers, commands[i]);
            }
            return integers;
        }, interpretation));

    }

    private String interpretCommand(String command) {
        if (command.matches("\\d+")) {
            return command;
        }
        if (!operators.containsKey(command)) {
            throw new IllegalArgumentException(
                    String.format(NO_DEFINITION_AVAILABLE_FOR_OPERATOR, command));
        } else {
            return operators.get(command).interpretation;
        }
    }

    private Stack<Integer> executeCommand(Stack<Integer> integers, String command) {
        if (command.matches("\\d+")) {
            integers.push(Integer.parseInt(command));
        } else {
            if (!operators.containsKey(command)) {
                throw new IllegalArgumentException(
                        String.format(NO_DEFINITION_AVAILABLE_FOR_OPERATOR, command));
            }

            String[] commands = operators.get(command).interpretation.split(" ");
            for (String name : commands) {
                if (name.matches("\\d+")) {
                    integers.push(Integer.parseInt(name));
                } else {
                    integers = operators.get(name).command.apply(integers);
                }
            }
        }
        return integers;
    }

    private void initializeDictionary() {
        operators.put("+", new Operator(integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg1 + arg2);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(ADDITION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return integers;
        }, "+"));

        operators.put("-", new Operator(integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg2 - arg1);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(SUBTRACTION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return integers;
        }, "-"));

        operators.put("*", new Operator(integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg1 * arg2);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(MULTIPLICATION_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return integers;
        }, "*"));

        operators.put("/", new Operator(integers -> {
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
        }, "/"));

        operators.put(OVER, new Operator(integers -> {
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
        }, OVER));

        operators.put(DUP, new Operator(integers -> {
            try {
                Integer arg = integers.pop();
                integers.push(arg);
                integers.push(arg);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(DUPLICATING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_1_VALUE);
            }
            return stack;
        }, DUP));

        operators.put(DROP, new Operator(integers -> {
            try {
                integers.pop();
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(DROPPING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_1_VALUE);
            }
            return stack;
        }, DROP));

        operators.put(SWAP, new Operator(integers -> {
            try {
                Integer arg1 = integers.pop();
                Integer arg2 = integers.pop();
                integers.push(arg1);
                integers.push(arg2);
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException(SWAPPING_REQUIRES_THAT_THE_STACK_CONTAIN_AT_LEAST_2_VALUES);
            }
            return stack;
        }, SWAP));
    }

    class Operator {
        UnaryOperator<Stack<Integer>> command;
        String interpretation;

        public Operator(UnaryOperator<Stack<Integer>> command, String interpretation) {
            this.command = command;
            this.interpretation = interpretation;
        }
    }
}
