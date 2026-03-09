package im.kny.springtx.txmanager

import im.kny.springtx.TxManager
import im.kny.springtx.spring.MySpringConfig
import im.kny.springtx.txmanager.PersonTxManagerResource
import jakarta.persistence.EntityManager
import org.hibernate.internal.SessionImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.postgresql.jdbc.PgConnection
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
        //Thread.sleep(1000L)
    }

    @Test
    fun `performance`() {
        PersonTxManagerResource.slowImageDelay=0L
        for (i in 1..1_000){
            personTxManagerResource.postPerson("Person $i")
        }
    }
}