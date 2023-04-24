import java.util.stream.IntStream;

import static java.lang.Math.floorDiv;
import static java.lang.Math.random;

class DnDCharacter {
    private final static int numberSides = 6;
    private final static int numberDices = 4;

    public DnDCharacter() {
        for (Abilities ability : Abilities.values()) {
            ability.points = ability();
        }
    }

    int ability() {
        return IntStream.rangeClosed(1, numberDices)
                .map(o -> (int) (random() * numberSides + 1))
                .sorted()
                .skip(1)
                .sum();
    }

    int modifier(int input) {
        return floorDiv(input - 10, 2);
    }

    int getStrength() {
        return Abilities.STRENGTH.points;
    }

    int getDexterity() {
        return Abilities.DEXTERITY.points;
    }

    int getConstitution() {
        return Abilities.CONSTITUTION.points;
    }

    int getIntelligence() {
        return Abilities.INTELLIGENCE.points;
    }

    int getWisdom() {
        return Abilities.WISDOM.points;
    }

    int getCharisma() {
        return Abilities.CHARISMA.points;
    }

    int getHitpoints() {
        return 10 + modifier(Abilities.CONSTITUTION.points);
    }

    private enum Abilities {
        STRENGTH,
        DEXTERITY,
        CONSTITUTION,
        INTELLIGENCE,
        WISDOM,
        CHARISMA;
        private int points;
    }

}
