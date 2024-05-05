package com.iridium.iridiumcore.utils;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.iridium.iridiumcore.DefaultFontInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Various utils for modifying Strings.
 */
public class StringUtils {

    private final static int CENTER_PX = 154;

    /**
     * Applies colors to the provided string.
     * Supports hex colors in Minecraft 1.16+.
     *
     * @param string The string which should have colors
     * @return The new String with applied colors
     */
    public static String color(String string) {
        return IridiumColorAPI.process(string);
    }

    /**
     * Applies colors to the provided strings.
     * Supports hex colors in Minecraft 1.16+.
     * The order of the elements in the list will be the same.
     *
     * @param strings The strings which should have colors
     * @return A list of the same strings with colors
     */
    public static List<String> color(List<String> strings) {
        return strings.stream()
                .map(StringUtils::color)
                .collect(Collectors.toList());
    }

    /**
     * Applies placeholders to the provided strings.
     * Also adds colors.
     * The order of the Strings will be the same.
     *
     * @param lines        The lines which potentially have placeholders
     * @param placeholders The placeholders which should be replaced
     * @return The same lines with replaced placeholders
     */
    public static List<String> processMultiplePlaceholders(List<String> lines, List<Placeholder> placeholders) {
        return lines.stream()
                .map(s -> processMultiplePlaceholders(s, placeholders))
                .collect(Collectors.toList());
    }

    /**
     * Applies Placeholders to the provided string.
     * Also adds colors.
     *
     * @param line         The line which potentially has placeholders
     * @param placeholders The placeholders which should be replaced
     * @return The same line with replaced placeholders
     */
    public static String processMultiplePlaceholders(String line, List<Placeholder> placeholders) {
        String processedLine = line;
        for (Placeholder placeholder : placeholders) {
            processedLine = placeholder.process(processedLine);
        }
        return color(processedLine);
    }

    public static String getCenteredMessage(String message, String buffer) {
        if (message == null || message.equals("")) return "";
        message = StringUtils.color(message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '\u00A7') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(StringUtils.color(buffer));
            compensated += spaceLength;
        }
        return sb + message + sb;
    }

}
