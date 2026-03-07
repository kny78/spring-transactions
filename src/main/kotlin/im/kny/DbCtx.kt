package im.kny

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityTransaction
import org.hibernate.internal.SessionImpl
import org.postgresql.jdbc.PgConnection
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DbCtx(val entityManager: EntityManager) : AutoCloseable {

    /**
     * Represents the transaction we are in.
     */
    val transaction: EntityTransaction

    init {
        LOG.info("backendPID: ${backendPID()}")
        transaction = entityManager.transaction
        transaction.begin()
    }

    val person: PersonDao by lazy { PersonDao(entityManager) }

    override fun close() {
        when {
            transaction == null -> LOG.debug("Transaction is `null`, should not happen.")
            !transaction.rollbackOnly -> transaction.commit()
            else -> {
                val msg = "Transaction is not committed or rolled back. Rolling back!"
                LOG.warn(msg)
                LOG.trace(msg, RuntimeException("Stacktrace"))
                transaction.rollback()
            }
        }
        if (this.entityManager.isOpen) this.entityManager.close()
    }

    fun setRollbackOnly() {
        transaction.setRollbackOnly()
    }


    fun backendPID(): Int {

        val pgConn = entityManager
            .unwrap(SessionImpl::class.java)
            .jdbcCoordinator
            .logicalConnection
            .physicalConnection
            .unwrap(PgConnection::class.java)

        val backendPID = pgConn.backendPID
        return backendPID
    }

    companion object {
        private val LOG: Logger by lazy(LazyThreadSafetyMode.NONE) { LoggerFactory.getLogger(javaClass.enclosingClass) }
    }
}