import java.util.function.IntBinaryOperator;

class WordProblemSolver {
    public static final String DONT_UNDERSTAND = "I'm sorry, I don't understand the question!";
    public static final String START_WORDS = "what is";
    public static final String BY = "by";
    public static final String TO = "to";
    public static final String THE = "the";
    public static final String END_WORDS = "?";
    public static final String POWER = "power";

    public static void raiseException() {
        throw new IllegalArgumentException(DONT_UNDERSTAND);
    }

    int solve(final String input) {
        String processedInput = input.toLowerCase();
        if (!processedInput.startsWith(START_WORDS) || !processedInput.endsWith(END_WORDS)) {
            raiseException();
        }
        processedInput = processedInput.substring(
                START_WORDS.length(), processedInput.length() - END_WORDS.length()).strip();

        String[] words = processedInput.split(" ");
        int res = parseSimpleArg(words, 0);

        int k = 1;
        while (k < words.length) {
            Operators operator = Operators.findOperatorByName(words[k]);
            k += operator.length(words, k);
            int arg;
            if (operator.equals(Operators.POWER)) {
                arg = parsePowerArg(words, k);
                k++;
            } else {
                arg = parseSimpleArg(words, k);
            }
            res = operator.operator.applyAsInt(res, arg);
            k++;
        }

        return res;
    }

    private int parseSimpleArg(String[] words, int k) {
        int res = 0;
        try {
            res = Integer.parseInt(words[k]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            raiseException();
        }
        return res;
    }

    private int parsePowerArg(String[] words, int k) {
        if (k + 1 >= words.length || !words[k + 1].equals(POWER)) {
            raiseException();
        }

        int res = 0;
        try {
            res = Integer.parseInt(words[k].substring(0, words[k].length() - 2));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            raiseException();
        }
        return res;
    }

    public enum Operators {
        PLUS("plus", (left, right) -> left + right) {
            @Override
            int length(String[] words, int k) {
                return 1;
            }
        },
        MINUS("minus", (left, right) -> left - right) {
            @Override
            int length(String[] words, int k) {
                return 1;
            }
        },
        MULTIPLY("multiplied", (left, right) -> left * right) {
            @Override
            int length(String[] words, int k) {
                if (k + 2 > words.length || !words[k + 1].equals(BY)) {
                    raiseException();
                }
                return 2;
            }
        },
        DIVIDE("divided", (left, right) -> left / right) {
            @Override
            int length(String[] words, int k) {
                if (k + 2 > words.length || !words[k + 1].equals(BY)) {
                    raiseException();
                }
                return 2;
            }
        },
        POWER("raised", (left, right) -> (int) Math.pow(left, right)) {
            @Override
            int length(String[] words, int k) {
                if (k + 3 > words.length || !words[k + 1].equals(TO) || !words[k + 2].equals(THE)) {
                    raiseException();
                }
                return 3;
            }
        };

        private final String action;
        private final IntBinaryOperator operator;

        Operators(String action, IntBinaryOperator operator) {
            this.action = action;
            this.operator = operator;
        }

        public static Operators findOperatorByName(String str) {
            for (Operators operator : Operators.values()) {
                if (operator.action.equals(str)) {
                    return operator;
                }
            }
            throw new IllegalArgumentException(DONT_UNDERSTAND);
        }

        abstract int length(String[] words, int k);
    }
}
