public enum Uom {
    G("gram"),
    KG("Kg"),
    UNIT("units");

    private final String label;

    private Uom(String label) {
        this.label = label;
    }

}
