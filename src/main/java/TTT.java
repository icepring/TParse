import com.tym.tparse.deserialization.DeserializerKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KFunction;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;

/**
 * @author icepring
 * @date 03/04/2022
 */

public class TTT {
    public static void main(String[] args) {
        String tele = "H.1=tym\nH.2=2567\nH.3=2\nH.4=2\nB.1_0=tt\nB.2_0=12\nB.3_0=13\nB.5_0=0\nB.1_1=yy\nB.2_1=14\nB.3_1=15\nB.5_1=1\nB.1_2=mm\nB.2_2=16\nB.3_2=aa\nB.3_2=17\nB.5_2=0";
        System.out.println(tele);
        Reader json$iv$iv = new StringReader(tele);
        Object var5 = DeserializerKt.deserialize(json$iv$iv, Reflection.getOrCreateKotlinClass(TestData.class));
        System.out.println("result:"+var5);
    }

    public static <T> Constructor<T> findPrimaryConstructor(Class<T> clazz) {
        try {
            KFunction<T> primaryCtor = KClasses.getPrimaryConstructor(JvmClassMappingKt.getKotlinClass(clazz));
            if (primaryCtor == null) {
                return null;
            }
            Constructor<T> constructor = ReflectJvmMapping.getJavaConstructor(primaryCtor);
            if (constructor == null) {
                throw new IllegalStateException(
                        "Failed to find Java constructor for Kotlin primary constructor: " + clazz.getName());
            }
            return constructor;
        }
        catch (UnsupportedOperationException ex) {
            return null;
        }
    }
}
