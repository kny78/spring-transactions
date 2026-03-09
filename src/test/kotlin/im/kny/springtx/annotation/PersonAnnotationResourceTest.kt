package im.kny.springtx.annotation

import im.kny.springtx.Person
import im.kny.springtx.TxManager
import im.kny.springtx.spring.MySpringConfig
import im.kny.springtx.txmanager.PersonTxManagerResource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [MySpringConfig::class]
)
class PersonAnnotationResourceTest @Autowired constructor(
    val personAnnotationResource: PersonAnnotationResource,
    val personAnnotationDao: PersonAnnotationDao,
    val txManager: TxManager
) {

    @BeforeEach
    fun beforeEach() {
        txManager.autoCommitTx { dbCtx -> dbCtx.person.deleteAll() }
    }

    @Test
    fun `different Tx Ok`() {

        val donald = personAnnotationResource.postPerson("Donald Duck")

        println(donald)
    }

    @Test
    fun `performance`() {
        PersonAnnotationResource.slowImageDelay=0L
        for (i in 1..1_000){
            personAnnotationResource.postPerson("Person $i")
        }
    }


}