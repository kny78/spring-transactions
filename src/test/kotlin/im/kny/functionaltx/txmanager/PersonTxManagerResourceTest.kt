package im.kny.functionaltx.txmanager

import im.kny.functionaltx.TxManager
import im.kny.functionaltx.spring.MySpringConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [MySpringConfig::class]
)
class PersonTxManagerResourceTest @Autowired constructor(
    val personTxManagerResource: PersonTxManagerResource,
    val txManager: TxManager
) {

    @BeforeEach
    fun beforeEach() {
        txManager.cleanDb()
    }

    @Test
    fun `different Tx Ok`() {
        val donald = personTxManagerResource.postPerson("Donald Duck")
        println(donald)
    }

    @Test
    fun `performance`() {
        PersonTxManagerResource.slowImageDelay=0L
        for (i in 1..1_000){
            personTxManagerResource.postPerson("Person $i")
        }
    }
}