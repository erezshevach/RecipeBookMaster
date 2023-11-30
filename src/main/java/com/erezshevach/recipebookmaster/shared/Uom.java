package com.erezshevach.recipebookmaster.shared;

import java.util.HashMap;
import java.util.Map;

public enum Uom {
    G("gram"),
    KG("Kg"),
    UNIT("units");

    private static final Map<String, Uom> MAP = new HashMap<>();
    private final String label;

    Uom(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static Uom getByLabel(String label) {
        return MAP.get(label);
    }

    static {
        for (Uom field : Uom.values()) {
            MAP.put(field.getLabel(), field);
        }
    }

}
