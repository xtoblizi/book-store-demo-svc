package com.book.store.svc.commons.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalUtil {
    private static final int scale = 11;

    public static BigDecimal toBigDecimal(Long value){
       return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal toBigDecimal(BigDecimal value){
        return value.setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal toBigDecimal(double value){
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * This is the standard scale used by google and internation tools for calculation.
     * A scale of 11 with a Half up rounding mode.If a value exceeds 11 digits on its decimal places, then round-off
     * Note this scale (11) is only used at computation level.
     * When computation is complete, at the point to committing result to the db,
     * it is rounded-off to 5decimal places. I.E decimal(19,5)
     * @param value
     * @return
     */
    public static BigDecimal standardRound(BigDecimal value){
        return value.setScale(scale, RoundingMode.HALF_UP);
    }

}
