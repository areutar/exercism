import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrepTool2 {

    private static final String LINE_NUMBERS_FLAG = "-n";

    private static final String ONLY_FILENAME_FLAG = "-l";

    private static final String CASE_INSENSITIVE_FLAG = "-i";

    private static final String INVERT_PROGRAM_FLAG = "-v";

    private static final String FULL_LINE_FLAG = "-x";

    private static final String LINE_BREAK = "\n";

    private static final String FIELDS_SEPARATOR = ":";

    private static final String EMPTY = "";

    private static List<String> findInFile(String pattern, List<String> flags, String file,

                                           boolean multipleFiles) {

        List<String> matched = new ArrayList<>();

        try {

            Path filePath = Paths.get(file);

            List<String> allLines = Files.lines(filePath).collect(Collectors.toList());

            for (int i = 0; i < allLines.size(); i++) {

                String line = allLines.get(i);

                if (test(line, pattern, flags)) {

                    matched.add(formatLine(line, file, i + 1, multipleFiles, flags));

                }

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return matched;

    }

    private static boolean test(String line, String pattern, List<String> flags) {

        String lineToCompare = (flags.contains(CASE_INSENSITIVE_FLAG)) ? line.toUpperCase() : line;

        String patternToCompare = (flags.contains(CASE_INSENSITIVE_FLAG)) ? pattern.toUpperCase() : pattern;

        boolean matched = (flags.contains(FULL_LINE_FLAG)) ? lineToCompare.equals(patternToCompare)

                : lineToCompare.contains(patternToCompare);

        return (flags.contains(INVERT_PROGRAM_FLAG)) ? !matched : matched;

    }

    private static String formatLine(String line, String fileName, Integer lineNumber, boolean multipleFiles,

                                     List<String> flags) {

        if (flags.contains(ONLY_FILENAME_FLAG)) {

            return fileName;

        }

        String fileNameField = (multipleFiles) ? fileName.concat(FIELDS_SEPARATOR) : EMPTY;

        String lineNumberField = (flags.contains(LINE_NUMBERS_FLAG))

                ? String.valueOf(lineNumber).concat(FIELDS_SEPARATOR) : EMPTY;

        return Stream.of(fileNameField, lineNumberField, line).collect(Collectors.joining());

    }

    public String grep(String pattern, List<String> flags, List<String> files) {

        List<String> matched = new ArrayList<>();

        boolean multipleFiles = files.size() > 1;

        files.forEach(file -> matched.addAll(findInFile(pattern, flags, file, multipleFiles)));

        return matched.stream().distinct().collect(Collectors.joining(LINE_BREAK));

    }

}