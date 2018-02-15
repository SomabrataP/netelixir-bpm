/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netelixir.bpm.poc.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 *
 * @author som
 */
public class GoogleUtil {
    private static final DecimalFormat humanReadableFormat = new DecimalFormat("#0.00");
    /**
   * Formats the given {@code BigDecimal} to a readable String.
   *
   * @param number the {@code BigDecimal} to be formatted
   * @return the formatted number with precision two. Null in case of null object
   */
  public static String formatAsReadable(BigDecimal number) {
    if (number == null) {
      return null;
    }
    return humanReadableFormat.format(number);
  }
  
  /**
   * Finds out the number format in {@code String} format, and parse the number to
   * {@code BigDecimal} format.
   *
   *  The passed number can contain white spaces of any sort, and can have the default separators
   * such as ',' and '.'.
   *
   * @param numberString the number in {@code String} format
   * @return the {@code BigDecimal} that was parsed from the {@code String}. If the number format is
   *         not recognized, than {@code null} is returned.
   */
  public static BigDecimal parseFromNumberString(String numberString) {

    if (numberString != null) {

      String nonSpacedString =
          numberString.replaceAll("[ \\t\\n\\x0B\\f\\r]", "").replaceAll("%", "");

      int indexOfComma = nonSpacedString.indexOf(',');
      int indexOfDot = nonSpacedString.indexOf('.');
      NumberFormat format = null;

      if (indexOfComma < indexOfDot) {
        nonSpacedString = nonSpacedString.replaceAll("[,]", "");


        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        format = new DecimalFormat("##.#", otherSymbols);

      } else if (indexOfComma > indexOfDot) {
        nonSpacedString = nonSpacedString.replaceAll("[.]", "");

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        format = new DecimalFormat("##,#", otherSymbols);

      } else {
        format = new DecimalFormat();
      }

      try {
        return new BigDecimal(format.parse(nonSpacedString).doubleValue(), new MathContext(12));

      } catch (ParseException e) {
        // unrecognized number format
        return null;
      }
    }
    return null;
  }
}
