package examples

import org.junit.Test
import com.tym.tparse.DeserializeInterface
import com.tym.tparse.examples.jsonSerializerTest.testJsonSerializer

interface Company {
    val name: String
}

data class CompanyImpl(override val name: String) : Company

data class Person(
        val name: String,
        @DeserializeInterface(CompanyImpl::class) val company: Company
)

class DeserializeInterfaceTest {
    @Test fun test() = testJsonSerializer(
            value = Person("Alice", CompanyImpl("JetBrains")),
            json = """{"company": {"name": "JetBrains"}, "name": "Alice"}"""
    )
}