import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class GrepTool1 {

    private static final String LINE_NUMBER = "%d:%s";

    private static final String FILE_NAME_LINE = "%s:%s";

    String grep(final String pattern, final List<String> args, final List<String> fileNames) {

        final GrepToolFlags flags = new GrepToolFlags(args);

        return fileNames.stream()

                .map(fileName -> this.grep(pattern, fileName, flags).stream()

                        .map(line -> appendFileName(line, fileName, fileNames.size(), flags)))

                .flatMap(identity())

                .collect(joining("\n"));

    }

    private String appendFileName(final String line, final String fileName, final int fileCount, final GrepToolFlags flags) {

        return fileCount > 1 && !flags.isPrintFileNameOnly()

                ? String.format(FILE_NAME_LINE, fileName, line) : line;

    }

    private List<String> grep(final String pattern, final String fileName, final GrepToolFlags flags) {

        try (Stream<String> fileLines = Files.lines(Paths.get(fileName))) {

            return flags.isPrintFileNameOnly()

                    ? this.grepFileName(fileLines, pattern, fileName, flags)

                    : this.grepLines(fileLines, pattern, flags);

        } catch (IOException e) {

            return emptyList();

        }

    }

    private List<String> grepFileName(final Stream<String> fileLines,

                                      final String pattern,

                                      final String fileName,

                                      final GrepToolFlags flags) {

        return fileLines

                .filter(line -> matchesPattern(line, pattern, flags))

                .map(line -> fileName)

                .limit(1)

                .collect(toList());

    }

    private List<String> grepLines(final Stream<String> fileLines,

                                   final String pattern,

                                   final GrepToolFlags flags) {

        List<String> lines = fileLines.collect(toList());

        return IntStream.range(0, lines.size())

                .filter(i -> flags.isInverseMatch() != matchesPattern(lines.get(i), pattern, flags))

                .mapToObj(i -> flags.isPrintLineNumber() ? String.format(LINE_NUMBER, i + 1, lines.get(i)) : lines.get(i))

                .collect(toList());

    }

    private boolean matchesPattern(final String line, final String pattern, final GrepToolFlags flags) {

        if (flags.isMatchEntireLineOnly()) {

            return flags.isCaseInsensitive()

                    ? line.equalsIgnoreCase(pattern) : line.equals(pattern);

        } else {

            return flags.isCaseInsensitive()

                    ? line.toUpperCase().contains(pattern.toUpperCase()) : line.contains(pattern);

        }

    }

}