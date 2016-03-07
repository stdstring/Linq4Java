/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IterableExtensionHelper;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author A.Ushakov
 */
public class DefaultValueFactory {

    public static <TObject> TObject get(Class<TObject> objectClass) {
        if(objectClass.equals(Boolean.class)) return (TObject)defaultBoolean;
        if(objectClass.equals(Byte.class)) return (TObject)defaultByte;
        if(objectClass.equals(Short.class)) return (TObject)defaultShort;
        if(objectClass.equals(Integer.class)) return (TObject)defaultInteger;
        if(objectClass.equals(Long.class)) return (TObject)defaultLong;
        if(objectClass.equals(Float.class)) return (TObject)defaultFloat;
        if(objectClass.equals(Double.class)) return (TObject)defaultDouble;
        if(objectClass.equals(BigInteger.class)) return (TObject)defaultBigInteger;
        if(objectClass.equals(BigDecimal.class)) return (TObject)defaultBigDecimal;
        return null;
    }
    
    private static Boolean defaultBoolean = false;
    private static Byte defaultByte = 0;
    private static Short defaultShort = 0;
    private static Integer defaultInteger = 0;
    private static Long defaultLong = 0L;
    private static Float defaultFloat = 0F;
    private static Double defaultDouble = 0D;
    private static BigInteger defaultBigInteger = new BigInteger("0");
    private static BigDecimal defaultBigDecimal = new BigDecimal(0);
    
    private DefaultValueFactory() {
    }
}
