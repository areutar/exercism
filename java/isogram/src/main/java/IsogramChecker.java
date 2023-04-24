class IsogramChecker {

    boolean isIsogram(String phrase) {
        String upPhrase = phrase.toUpperCase();

        for (int i = 0; i < phrase.length(); i++) {
            char c = upPhrase.charAt(i);
            if (c >= 'A' && c <= 'Z' && upPhrase.lastIndexOf(c) != i) {
                return false;
            }
        }
        return true;
    }

}
