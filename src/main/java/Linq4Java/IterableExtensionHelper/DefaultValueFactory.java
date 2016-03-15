package Linq4Java.IterableExtensionHelper;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author std_string
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
    
    private static final Boolean defaultBoolean = false;
    private static final Byte defaultByte = 0;
    private static final Short defaultShort = 0;
    private static final Integer defaultInteger = 0;
    private static final Long defaultLong = 0L;
    private static final Float defaultFloat = 0F;
    private static final Double defaultDouble = 0D;
    private static final BigInteger defaultBigInteger = new BigInteger("0");
    private static final BigDecimal defaultBigDecimal = new BigDecimal(0);
    
    private DefaultValueFactory() {
    }
}
