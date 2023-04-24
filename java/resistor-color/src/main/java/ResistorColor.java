import java.util.Arrays;

class ResistorColor {
    int colorCode(String color) {
       return ColorCodes.valueOf(color.toUpperCase()).getCode();
    }

    String[] colors() {
        return Arrays.stream(ColorCodes.values())
                .map(colorCodes -> colorCodes.name().toLowerCase())
                .toArray(String[]::new);
    }

    public enum ColorCodes {
        BLACK(0),
        BROWN(1),
        RED(2),
        ORANGE(3),
        YELLOW(4),
        GREEN(5),
        BLUE(6),
        VIOLET(7),
        GREY(8),
        WHITE(9);
        private final int code;

        ColorCodes(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
