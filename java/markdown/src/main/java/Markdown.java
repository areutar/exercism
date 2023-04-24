class Markdown {
    private static final char HEADER_SIGN = '#';
    private static final char LIST_SIGN = '*';
    private static final String LIST_BEGIN_SIGN = "<ul>";
    private static final String LIST_END_SIGN = "</ul>";
    private final StringBuilder sb = new StringBuilder();

    String parse(String markdown) {
        String[] lines = markdown.split("\n");
        boolean activeList = false;
        boolean isListItem;
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            isListItem = false;
            String parsedLine;
            char firstChar = line.charAt(0);
            switch (firstChar) {
                case HEADER_SIGN: {
                    parsedLine = parseHeader(line);
                    break;
                }
                case LIST_SIGN: {
                    parsedLine = parseListItem(line);
                    isListItem = true;
                    break;
                }
                default:
                    parsedLine = parseParagraph(line);
            }

            if (isListItem && !activeList) {
                activeList = true;
                sb.append(LIST_BEGIN_SIGN);
            }

            if (!isListItem && activeList) {
                activeList = false;
                sb.append(LIST_END_SIGN);
            }

            sb.append(parsedLine);
        }

        if (activeList) {
            sb.append(LIST_END_SIGN);
        }

        return sb.toString();
    }

   // if count > 6 ? ...
    private String parseHeader(String markdown) {
        int count = 0;
        for (int i = 0; i < 7 && markdown.charAt(i) == HEADER_SIGN; i++) {
            count++;
        }
        return "<h" + count + ">" + markdown.substring(count + 1) + "</h" + count + ">";
    }

    private String parseListItem(String markdown) {
        String skipAsterisk = markdown.substring(2);
        String listItemString = parseSomeSymbols(skipAsterisk);
        return "<li>" + listItemString + "</li>";
    }

    private String parseParagraph(String markdown) {
        return "<p>" + parseSomeSymbols(markdown) + "</p>";
    }

    private String parseSomeSymbols(String markdown) {
        String lookingFor = "__(.+)__";
        String update = "<strong>$1</strong>";
        String workingOn = markdown.replaceAll(lookingFor, update);

        lookingFor = "_(.+)_";
        update = "<em>$1</em>";
        return workingOn.replaceAll(lookingFor, update);
    }
}
