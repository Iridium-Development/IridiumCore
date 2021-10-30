package com.iridium.iridiumcore.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class which formats numbers.
 */
public class NumberFormatter {

    public int numberAbbreviationDecimalPlaces = 2;
    public String thousandAbbreviation = "K";
    public String millionAbbreviation = "M";
    public String billionAbbreviation = "B";
    public String trillionAbbreviation = "T";
    public boolean displayNumberAbbreviations = true;
    @JsonIgnore
    private final BigDecimal ONE_THOUSAND = new BigDecimal(1_000L);
    @JsonIgnore
    private final BigDecimal ONE_MILLION = new BigDecimal(1_000_000L);
    @JsonIgnore
    private final BigDecimal ONE_BILLION = new BigDecimal(1_000_000_000L);
    @JsonIgnore
    private final BigDecimal ONE_TRILLION = new BigDecimal(1_000_000_000_000L);

    /**
     * Formats a provided number as configured by the user.
     * Automatically rounds it and adds a suffix if possible.
     *
     * @param number The number which should be formatted
     * @return The formatted version of the number. May not be parsed by {@link Double#valueOf(String)}
     */
    public String format(double number) {
        if (displayNumberAbbreviations) {
            return formatNumber(BigDecimal.valueOf(number));
        } else {
            return String.format("%." + numberAbbreviationDecimalPlaces + "f", number);
        }
    }

    /**
     * Formats a provided number as configured by the user.
     * Automatically shortens it and adds a suffix.
     *
     * @param bigDecimal The BigDecimal that should be formatted
     * @return The formatted version of the number. Cannot be parsed by {@link Double#valueOf(String)}
     */
    private String formatNumber(BigDecimal bigDecimal) {
        bigDecimal = bigDecimal.setScale(numberAbbreviationDecimalPlaces, RoundingMode.HALF_DOWN);
        StringBuilder outputStringBuilder = new StringBuilder();

        if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            outputStringBuilder
                    .append("-")
                    .append(formatNumber(bigDecimal.negate()));
        } else if (bigDecimal.compareTo(ONE_THOUSAND) < 0) {
            outputStringBuilder
                    .append(bigDecimal.stripTrailingZeros().toPlainString());
        } else if (bigDecimal.compareTo(ONE_MILLION) < 0) {
            outputStringBuilder
                    .append(bigDecimal.divide(ONE_THOUSAND, RoundingMode.HALF_DOWN).stripTrailingZeros().toPlainString())
                    .append(thousandAbbreviation);
        } else if (bigDecimal.compareTo(ONE_BILLION) < 0) {
            outputStringBuilder
                    .append(bigDecimal.divide(ONE_MILLION, RoundingMode.HALF_DOWN).stripTrailingZeros().toPlainString())
                    .append(millionAbbreviation);
        } else if (bigDecimal.compareTo(ONE_TRILLION) < 0) {
            outputStringBuilder
                    .append(bigDecimal.divide(ONE_BILLION, RoundingMode.HALF_DOWN).stripTrailingZeros().toPlainString())
                    .append(billionAbbreviation);
        } else {
            outputStringBuilder
                    .append(bigDecimal.divide(ONE_TRILLION, RoundingMode.HALF_DOWN).stripTrailingZeros().toPlainString())
                    .append(trillionAbbreviation);
        }

        return outputStringBuilder.toString();
    }

}