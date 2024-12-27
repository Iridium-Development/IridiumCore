package com.iridium.iridiumcore.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.Getter;

/**
 * Represents a placeholder used in configuration files.
 */
@Getter
public class Placeholder {

    private final String key;
    private final String formattedKey;
    private String value;
    private Supplier<String> supplier;
    private Function<Integer, String> int_supplier;

    /**
     * The default constructor.
     *
     * @param key   The placeholder without curly brackets.
     * @param value The actual value of the placeholder
     */
    public Placeholder(String key, String value) {
        this.key = key;
        this.formattedKey = "%" + key + "%";
        this.value = value;
    }

    /**
     * The default constructor.
     *
     * @param key   The placeholder without curly brackets.
     * @param value The actual value of the placeholder
     */
    public Placeholder(String key, Supplier<String> supplier) {
        this.key = key;
        this.formattedKey = "%" + key + "%";
        this.supplier = supplier;
    }

    /**
     * The default constructor.
     *
     * @param key   The placeholder without curly brackets.
     * @param value The actual value of the placeholder
     */
    public Placeholder(String key, Function<Integer, String> supplier) {
        this.key = key;
        this.formattedKey = "%" + key + "%";
        this.int_supplier = supplier;
    }

    public String getValue() {
        if(value==null && supplier!=null) value = supplier.get();
        return value;
    }

    public String getValue(int i) {
        if(this.int_supplier!=null)
            return this.int_supplier.apply(i);
        return getValue();
    }

    /**
     * Replaces this placeholder in the provided line with the value of this
     * placeholder.
     *
     * @param line The line which potentially contains the placeholder
     * @return The processed line with this placeholder replaced, empty if the
     *         parameter is null
     */
    public String process(String line) {
        if (line == null || line.isEmpty())
            return "";
        if (line.contains(formattedKey))
            return line.replace(formattedKey, getValue());
        else
            return line;
    }

}
