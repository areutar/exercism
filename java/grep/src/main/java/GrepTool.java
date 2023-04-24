import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

public class GrepTool {
    private final EnumSet<Flags> flags = EnumSet.noneOf(Flags.class);
    private Pattern pattern;

    public String grep(String toSearch, List<String> flagList, List<String> files) {
        prepareFlags(flagList, files);
        preparePattern(toSearch);

        return files.stream()
                .map(this::getResultFromFile)
                .filter(not(String::isEmpty))
                .collect(Collectors.joining("\n"));
    }

    private String getResultFromFile(String fileName) {
        List<String> lines = readFile(fileName);
        String res = IntStream.range(0, lines.size())
                .mapToObj(i -> getResultFromLine(lines.get(i), i + 1))
                .filter(not(String::isEmpty))
                .map(s -> {
                    if (flags.contains(Flags.FILE_NAMES)) {
                        s = fileName + ":" + s;
                    }
                    return s;
                })
                .collect(Collectors.joining("\n"));

        return flags.contains(Flags.ONLY_NAMES) && !res.isEmpty() ?
                fileName :
                res;
    }

    private String getResultFromLine(String line, int number) {
        String res = "";
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            if (flags.contains(Flags.LINE_NUMBER)) {
                res += number + ":";
            }
            res += line;
        }

        return res;
    }

    private void preparePattern(String toSearch) {
        if (flags.contains(Flags.NOT_MATCH)) {
            toSearch = "^((?!" + toSearch + ").)*$";
        }
        if (flags.contains(Flags.ENTIRE_LINE)) {
            toSearch = "^" + toSearch + "$";
        }
        pattern = Pattern.compile(toSearch,
                flags.contains(Flags.CASE_INSENSITIVE) ?
                        Pattern.CASE_INSENSITIVE :
                        0);
    }

    private List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void prepareFlags(List<String> flagList, List<String> files) {
        flagList.forEach(s -> {
            Optional<Flags> flag = Flags.findFlag(s);
            flag.ifPresent(flags::add);
        });
        if (files.size() > 1) {
            flags.add(Flags.FILE_NAMES);
        }
    }

    enum Flags {
        CASE_INSENSITIVE("-i"),
        ENTIRE_LINE("-x"),
        LINE_NUMBER("-n"),
        NOT_MATCH("-v"),
        ONLY_NAMES("-l"),
        FILE_NAMES("-f");
        private final String flag;

        Flags(final String flag) {
            this.flag = flag;
        }

        public static Optional<Flags> findFlag(String flagStr) {
            for (final Flags f : values()) {
                if (f.flag.equals(flagStr)) {
                    return Optional.of(f);
                }
            }
            return Optional.empty();
        }
    }
}