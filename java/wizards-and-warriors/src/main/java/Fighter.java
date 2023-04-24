abstract class Fighter {
    private boolean vulnerable = false;

    boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    abstract int damagePoints(Fighter fighter);

    @Override
    public String toString() {
        return String.format("Fighter is a %s", this.getClass().getSimpleName());
    }
}

class Warrior extends Fighter {
    @Override
    int damagePoints(Fighter wizard) {
        return wizard.isVulnerable() ? 10 : 6;
    }
}

class Wizard extends Fighter {
    private boolean preparedSpell;

    @Override
    boolean isVulnerable() {
        return !preparedSpell;
    }

    @Override
    int damagePoints(Fighter warrior) {
        return preparedSpell ? 12 : 3;
    }

    void prepareSpell() {
        preparedSpell = true;
    }

}
