package deserialization

import com.tym.tparse.BodyKey
import com.tym.tparse.CustomSerializer
import com.tym.tparse.HeadKey
import com.tym.tparse.deserialization.deserialize

fun testSimple(){
    val tele = """
        |H.1=tym
        |H.2=2567
        |H.3=2
        |H.4=2
        |B.1_0=tt
        |B.2_0=12
        |B.3_0=13
        |B.5_0=0
        |B.1_1=yy
        |B.2_1=14
        |B.3_1=15
        |B.5_1=1
        |B.1_2=mm
        |B.2_2=16
        |B.3_2=aa
        |B.3_2=17
        |B.5_2=0
    """.trimMargin()
    println(tele)
    println(deserialize<TestData>(tele))
}

data class TestData(
    @HeadKey("H.1") val name: String,
    @HeadKey("H.2") val room: String
)

data class ListData(
    @BodyKey("B.1") val param1: String,
    @BodyKey("B.2") val param2: String,
    @BodyKey("B.3") val param3: String,
    @BodyKey("B.4") val param4: String = "default",

    @CustomSerializer(BooleanSerializer::class)
    @BodyKey("B.5")
    val param5: Boolean
)

fun main(args: Array<String>) {
    val tele = """
        |H.1=tym
        |H.2=2567
        |H.3=2
        |H.4=2
        |B.1_0=tt
        |B.2_0=12
        |B.3_0=13
        |B.5_0=0
        |B.1_1=yy
        |B.2_1=14
        |B.3_1=15
        |B.5_1=1
        |B.1_2=mm
        |B.2_2=16
        |B.3_2=aa
        |B.3_2=17
        |B.5_2=0
    """.trimMargin()
    println(tele)
    println(deserialize<TestData>(tele))
}
