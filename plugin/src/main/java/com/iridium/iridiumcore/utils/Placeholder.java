package com.iridium.iridiumcore.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a placeholder used in configuration files.
 */
@Getter
@NoArgsConstructor
public class Placeholder {

    private String key;
    private String value;

    /**
     * The default constructor.
     *
     * @param key   The placeholder without curly brackets.
     * @param value The actual value of the placeholder
     */
    public Placeholder(String key, String value) {
        this.key = "%" + key + "%";
        this.value = value;
    }

    /**
     * Replaces this placeholder in the provided line with the value of this placeholder.
     *
     * @param line The line which potentially contains the placeholder
     * @return The processed line with this placeholder replaced, empty if the parameter is null
     */
    public String process(String line) {
        if (line == null) return "";
        return line.replace(key, value);
    }

}
