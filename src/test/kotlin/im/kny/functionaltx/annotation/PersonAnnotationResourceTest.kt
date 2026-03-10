package im.kny.functionaltx.annotation

import im.kny.functionaltx.TxManager
import im.kny.functionaltx.spring.MySpringConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [MySpringConfig::class]
)
class PersonAnnotationResourceTest @Autowired constructor(
    val personAnnotationResource: PersonAnnotationResource,
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