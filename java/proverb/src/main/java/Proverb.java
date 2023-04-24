class Proverb {
    private final String[] words;

    Proverb(String[] words) {
        this.words = words;
    }

    String recite() {
        String recite = "";
        if (words.length == 0) {
            return recite;
        }
        for (int i = 0; i < words.length - 1; i++) {
            recite = recite.concat(String.format(
                    "For want of a %s the %s was lost.\n", words[i], words[i + 1]));
        }
        return recite.concat(String.format("And all for the want of a %s.", words[0]));
    }

}
