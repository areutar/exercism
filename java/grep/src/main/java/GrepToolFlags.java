import java.util.List;

public class GrepToolFlags {

    private final boolean printLineNumber;

    private final boolean caseInsensitive;

    private final boolean printFileNameOnly;

    private final boolean matchEntireLineOnly;

    private final boolean inverseMatch;

    public GrepToolFlags(final List<String> args) {

        this.printLineNumber = args.contains("-n");

        this.caseInsensitive = args.contains("-i");

        this.printFileNameOnly = args.contains("-l");

        this.matchEntireLineOnly = args.contains("-x");

        this.inverseMatch = args.contains("-v");

    }

    public boolean isPrintLineNumber() {

        return this.printLineNumber;

    }

    public boolean isCaseInsensitive() {

        return this.caseInsensitive;

    }

    public boolean isPrintFileNameOnly() {

        return this.printFileNameOnly;

    }

    public boolean isMatchEntireLineOnly() {

        return this.matchEntireLineOnly;

    }

    public boolean isInverseMatch() {

        return this.inverseMatch;

    }

}