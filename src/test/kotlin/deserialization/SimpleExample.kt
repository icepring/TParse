package deserialization

import kotlin.tym.tparse.BodyKey
import kotlin.tym.tparse.HeadKey
import kotlin.tym.tparse.deserialization.deserialize

data class TestData(
    @HeadKey("H.1") val name: String,
    @HeadKey("H.2") val room: String,
    @BodyKey("B.2") val time: String,
    @BodyKey("B.1") val age: String
)

fun main(args: Array<String>) {
    val tele = """
        |H.1=tym
        |H.2=2567
        |H.3=2
        |B.1_0=12
        |B.2_0=tt
        |B.1_1=13
        |B.2_1=yy
    """.trimMargin()
    println(tele)
    println(deserialize<TestData>(tele))
}
