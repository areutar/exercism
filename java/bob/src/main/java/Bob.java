class Bob {
    public String hey(String input) {
        return parseInput(input).getAnswer();
    }

    private Answers parseInput(String input) {
        boolean question = input.trim().endsWith("?");
        boolean yell = input.matches(".*[A-Z].*") && !input.matches(".*[a-z]+.*");

        if (input.isBlank()) {
            return Answers.BLANK;
        }

        if (yell && question) {
            return Answers.YELL_QUESTION_AT_HIM;
        }

        if (yell) {
            return Answers.YELL_AT_HIM;
        }

        if (question) {
            return Answers.QUESTION;
        }

        return Answers.WHATEVER;

    }

    public enum Answers {
        QUESTION("Sure."),
        YELL_AT_HIM("Whoa, chill out!"),
        YELL_QUESTION_AT_HIM("Calm down, I know what I'm doing!"),
        BLANK("Fine. Be that way!"),
        WHATEVER("Whatever.");

        private final String answer;

        Answers(String answer) {
            this.answer = answer;
        }

        public String getAnswer() {
            return answer;
        }

    }
}