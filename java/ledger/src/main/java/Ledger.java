import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Ledger {
    private static final List<String> currencies = Arrays.asList("USD", "EUR");
    private static final List<String> locales = Arrays.asList("en-US", "nl-NL");
    private static final String formatEntry = "\n%s | %-25s | %13s";
    private static final Map<String, String> headers = Map.ofEntries(
            Map.entry(locales.get(0), "Date       | Description               | Change       "),
            Map.entry(locales.get(1), "Datum      | Omschrijving              | Verandering  ")
    );
    private static final Map<String, String> dateFormats = Map.ofEntries(
            Map.entry(locales.get(0), "MM/dd/yyyy"),
            Map.entry(locales.get(1), "dd/MM/yyyy")
    );

    public LedgerEntry createLedgerEntry(String date, String desc, double c) {
        LedgerEntry le = new LedgerEntry();
        le.setChange(c);
        le.setDescription(desc);
        le.setLocalDate(LocalDate.parse(date));
        return le;
    }

    public String format(String currencyString, String localeString, LedgerEntry[] entries) {
        EntriesFormatter formatter = new EntriesFormatter(currencyString, localeString);

        StringBuilder result = new StringBuilder(formatter.header());

        List<LedgerEntry> records = sortEntries(entries);

        records.forEach(entry -> {
            String date = entry.getLocalDate().format(DateTimeFormatter.ofPattern(formatter.datePattern()));

            String description = compressDescription(entry.description);

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(formatter.locale);
            numberFormat.setCurrency(formatter.currency);
            DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
            decimalFormat.setPositivePrefix(formatter.currency.getSymbol());
            decimalFormat.setNegativePrefix("-" + formatter.currency.getSymbol());
            String amount = decimalFormat.format(entry.change);
            result.append(String.format(formatEntry,
                    date,
                    description,
                    amount));
        });

        return result.toString();
    }

    private String compressDescription(String description) {
        if (description.length() > 25) {
            description = description.substring(0, 22);
            description = description + "...";
        }
        return description;
    }

    private List<LedgerEntry> sortEntries(LedgerEntry[] entries) {
        return Arrays.stream(entries)
                .sorted((o1, o2) -> {
                    if (o1.change * o2.change > 0) {
                        return o1.getLocalDate().compareTo(o2.getLocalDate());
                    }
                    return Double.compare(o1.change, o2.change);
                })
                .collect(Collectors.toList());
    }

    public static class LedgerEntry {
        LocalDate localDate;
        String description;
        double change;

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setChange(double change) {
            this.change = change;
        }
    }

    public static class EntriesFormatter {
        private static final String INVALID_CURRENCY = "Invalid currency";
        private static final String INVALID_LOCALE = "Invalid locale";
        final Currency currency;
        final Locale locale;

        public EntriesFormatter(String currencyString, String localeString) {
            checkInputs(currencyString, localeString);
            this.currency = Currency.getInstance(currencyString);
            this.locale = Locale.forLanguageTag(localeString);
        }

        public String header() {
            return headers.get(locale.toLanguageTag());
        }

        public String datePattern() {
            return dateFormats.get(locale.toLanguageTag());
        }

        private void checkInputs(String currencyString, String localeString) {
            if (!currencies.contains(currencyString)) {
                throw new IllegalArgumentException(INVALID_CURRENCY);
            }
            if (!locales.contains(localeString)) {
                throw new IllegalArgumentException(INVALID_LOCALE);
            }
        }
    }
}
