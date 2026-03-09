package im.kny.springtx

import im.kny.springtx.spring.MySpringConfig
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.hibernate.internal.SessionImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.postgresql.jdbc.PgConnection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [MySpringConfig::class]
)
class NonUniqueTransaction_ok_Test @Autowired constructor(
    val entityManagerFactory: EntityManagerFactory
) {

    @Test
    fun `different Tx Ok`() {
        val em1 = entityManagerFactory.createEntityManager()!!
        val em2 = entityManagerFactory.createEntityManager()!!

        Assertions.assertNotEquals(
            backendPid(em1),
            backendPid(em2),
            "Open connection and get different backendPids"
        )

        val tx1 = em1.transaction
        tx1.begin()

        val tx2 = em2.transaction
        tx2.begin()

        try {
            printCon("em1", em1)
            printCon("em2", em2)

            Assertions.assertNotEquals(
                backendPid(em1),
                backendPid(em2),
                "Expect different backendPids"
            )
        } finally {
            tx2.rollback()
            tx1.rollback()
        }

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


}