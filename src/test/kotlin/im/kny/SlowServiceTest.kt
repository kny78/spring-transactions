package im.kny

import im.kny.springtransactions.MySpringConfig
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
class SlowServiceTest @Autowired constructor(
    val txManager: TxManager
) {

    @BeforeEach
    fun beforeEach() {
        txManager.autoCommitTx { dbCtx -> dbCtx.person.deleteAll() }
    }

    @Test
    fun `different Tx Ok`() {

        val donald = txManager.autoCommitTx { dbCtx ->
            dbCtx.person.persist(Person("Donald Duck", null))
        }

        val image = getSlowImage(donald.id!!)
        val donald2 = txManager.autoCommitTx { dbCtx ->
            val donaldInTx = dbCtx.person.byId(donald.id)
            donaldInTx.image = image
        }
        println(donald2)
    }


    private fun printCon(str: String, em: EntityManager) {
        val backendPID = backendPid(em)
        println("$str $backendPID")
    }

    private fun backendPid(em: EntityManager): Int {
        val session = em
            .unwrap(SessionImpl::class.java)

        val pgConn = session
            .jdbcCoordinator
            .logicalConnection
            .physicalConnection
            .unwrap(PgConnection::class.java)

        val backendPID = pgConn.backendPID
        return backendPID
    }


    private fun getSlowImage(personId: Long): String {
        println("Waiting for image")
        Thread.sleep(1_000L)
        println("Got image")
        return "Url for $personId"
    }
}
