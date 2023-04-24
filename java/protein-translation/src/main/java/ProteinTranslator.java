import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ProteinTranslator {
    private static final Map<String, String> codonsProteinsMap = Map.ofEntries(
            Map.entry("AUG", "Methionine"),
            Map.entry("UUU", "Phenylalanine"),
            Map.entry("UUC", "Phenylalanine"),
            Map.entry("UUA", "Leucine"),
            Map.entry("UUG", "Leucine"),
            Map.entry("UCU", "Serine"),
            Map.entry("UCC", "Serine"),
            Map.entry("UCA", "Serine"),
            Map.entry("UCG", "Serine"),
            Map.entry("UAU", "Tyrosine"),
            Map.entry("UAC", "Tyrosine"),
            Map.entry("UGU", "Cysteine"),
            Map.entry("UGC", "Cysteine"),
            Map.entry("UGG", "Tryptophan"),
            Map.entry("UAA", "STOP"),
            Map.entry("UAG", "STOP"),
            Map.entry("UGA", "STOP")
    );

    List<String> translate(String rnaSequence) {
        List<String> proteins = new ArrayList<>();
        int countCodons = rnaSequence.length() / 3;
        for (int i = 0; i < countCodons; i++) {
            String codon = rnaSequence.substring(i * 3, i * 3 + 3);
            String protein = codonsProteinsMap.get(codon);
            if (protein.equalsIgnoreCase("STOP")) {
                return proteins;
            } else {
                proteins.add(protein);
            }
        }
        return proteins;
    }
}
