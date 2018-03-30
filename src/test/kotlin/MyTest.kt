
import io.kotlintest.matchers.*
import io.kotlintest.matchers.collections.containNoNulls
import io.kotlintest.matchers.collections.containOnlyNulls
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.*
import java.io.File
import java.nio.file.Files


class MyTests : StringSpec() {
    init {

        "length should return size of string" {
            "hello".length shouldBe 5
        }

        ("String.length should return the length of the string") {
            "sammy".length shouldBe 5
            "".length shouldBe 0
        }

    }

}

class PropertyExample: StringSpec() {
    init {

        "String size" {
            forAll(Gen.string(), Gen.string(), { a: String, b: String ->
                (a + b).length == a.length + b.length
            })
        }

    }
}

class FunTests : FunSpec() {
    init {
        test("Fun - String.length should return the length of the string") {
            "sammy".length shouldBe 5
            "".length shouldBe 0
        }
    }
}

class ShouldTests : ShouldSpec() {
    init {
        "should - String.length" {
            should("return the length of the string") {
                "sammy".length shouldBe 5
                "".length shouldBe 0
            }
        }
    }
}

class CollectionMatchersTest : WordSpec() {

    init {

        "sorted" should {
            "test that a collect is sorted" {
                listOf(1, 2, 3, 4) shouldBe sorted<Int>()
                shouldThrow<AssertionError> {
                    listOf(2, 1) shouldBe sorted<Int>()
                }
            }
        }

        "singleElement" should {
            "test that a collection contains a single given element"  {
                listOf(1) shouldBe singleElement(1)
                shouldThrow<AssertionError> {
                    listOf(1) shouldBe singleElement(2)
                }
                shouldThrow<AssertionError> {
                    listOf(1, 2) shouldBe singleElement(2)
                }
            }
        }

        "should contain element" should {
            "test that a collection contains an element"  {
                val col = listOf(1, 2, 3)

                col should contain(2)

                shouldThrow<AssertionError> {
                    col should contain(4)
                }
            }
        }

        "haveSize" should {
            "test that a collection has a certain size" {
                val col1 = listOf(1, 2, 3)
                col1 should haveSize(3)
                shouldThrow<AssertionError> {
                    col1 should haveSize(2)
                }

                val col2 = emptyList<String>()
                col2 should haveSize(0)
                shouldThrow<AssertionError> {
                    col2 should haveSize(1)
                }
            }
        }

        "contain" should {
            "test that a collection contains element x"  {
                val col = listOf(1, 2, 3)
                shouldThrow<AssertionError> {
                    col should contain(4)
                }
                col should contain(2)
            }
        }

        "empty" should {
            "test that a collection contains an element"  {
                val col = listOf(1, 2, 3)

                shouldThrow<AssertionError> {
                    col should beEmpty()
                }

                ArrayList<String>() should beEmpty()
            }
        }

        "containNoNulls" should {
            "test that a collection contains zero nulls"  {
                emptyList<String>() should containNoNulls()
                listOf(1, 2, 3) should containNoNulls()
                listOf(null, null, null) shouldNot containNoNulls()
                listOf(1, null, null) shouldNot containNoNulls()

            }
        }

        "containOnlyNulls" should {
            "test that a collection contains only nulls"  {
                emptyList<String>() should containOnlyNulls()
                listOf(null, null, null) should containOnlyNulls()
                listOf(1, null, null) shouldNot containOnlyNulls()
                listOf(1, 2, 3) shouldNot containOnlyNulls()
            }
        }

        "containInOrder" should {
            "test that a collection contains the same elements in the given order, duplicates permitted" {
                val col = listOf(1, 1, 2, 2, 3, 3)

                col should containsInOrder(1, 2, 3)
                col should containsInOrder(1)

                shouldThrow<AssertionError> {
                    col should containsInOrder(1, 2, 6)
                }

                shouldThrow<AssertionError> {
                    col should containsInOrder(4)
                }

                shouldThrow<AssertionError> {
                    col should containsInOrder(2, 1, 3)
                }
            }
        }

        "containsAll" should {
            "test that a collection contains all the elements but in any order" {
                val col = listOf(1, 2, 3, 4, 5)

                col should containAll(1, 2, 3)
                col should containAll(3, 2, 1)
                col should containAll(5, 1)
                col should containAll(1, 5)
                col should containAll(1)
                col should containAll(5)

                shouldThrow<AssertionError> {
                    col should containAll(1, 2, 6)
                }

                shouldThrow<AssertionError> {
                    col should containAll(6)
                }

                shouldThrow<AssertionError> {
                    col should containAll(0, 1, 2)
                }

                shouldThrow<AssertionError> {
                    col should containAll(3, 2, 0)
                }
            }
        }
    }
}

class FileMatchersTest : FunSpec() {
    init {

        test("exist() file matcher") {
            val file = Files.createTempFile("test", "test").toFile()
            file should exist()

            shouldThrow<AssertionError> {
                File("qweqwewqewqewee") should exist()
            }

            file.delete()
        }

        test("haveExtension") {
            val file = Files.createTempFile("test", ".test").toFile()
            file should haveExtension(".test")

            shouldThrow<AssertionError> {
                file should haveExtension(".jpeg")
            }

            file.delete()
        }

        test("aFile() file matcher") {
            val file = Files.createTempFile("test", "test").toFile()
            file shouldBe aFile()

            shouldThrow<AssertionError> {
                file shouldBe aDirectory()
            }

            file.delete()
        }

        test("aDirectory() file matcher") {
            val dir = Files.createTempDirectory("testdir").toFile()
            dir shouldBe aDirectory()

            shouldThrow<AssertionError> {
                dir shouldBe aFile()
            }
        }
    }
}

class BehaviorSpecTest : BehaviorSpec() {
    init {
        given("a ListStack") {
            `when`("pop is invoked") {
                then("the last element is removed") {
                    val stack = ListStack<String>()
                    stack.push("hello")
                    stack.peek() shouldBe "hello"
                    stack.push("world")
                    println(stack)
                    stack.peek() shouldBe "world"
                    stack.size() shouldBe 2

                    stack.pop() shouldBe "world"
                    stack.peek() shouldBe "hello"
                    stack.size() shouldBe 1
                    println(stack)

                    stack.pop() shouldBe "hello"
                    stack.size() shouldBe 0
                    shouldThrow<NoSuchElementException> {
                        stack.peek()
                    }
                    shouldThrow<NoSuchElementException> {
                        stack.pop()
                    }

                }
            }
        }
    }
}

interface Stack<T> {
    fun push(t: T): Unit
    fun pop(): T
    fun peek(): T
    fun size(): Int
    fun isEmpty(): Boolean
}
class ListStack<T> : Stack<T> {

    var list = listOf<T>()

    override fun peek(): T = list.last()

    override fun pop(): T {
        val t = peek()
        list = list.dropLast(1)
        return t
    }

    override fun push(t: T): Unit {
        list += t
    }

    override fun size(): Int = list.size

    override fun isEmpty(): Boolean = list.isEmpty()
    override fun toString(): String {
        return "ListStack(list=$list)"
    }

}

