class MicroBlog {
    public String truncate(String input) {
        StringBuilder sb = new StringBuilder();
        input.codePoints()
                .limit(5)
                .forEach(value -> {
                    // тут проверяется, что символ состоит из одного char
                    if (Character.isBmpCodePoint(value)) {
                        sb.append((char) value);
                        // иначе добавляются два именованных символа
                    } else {
                        sb.append(Character.highSurrogate(value));
                        sb.append(Character.lowSurrogate(value));
                    }
                });
        return sb.toString();
    }
}
