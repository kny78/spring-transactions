package im.kny.springtx

import im.kny.springtx.txmanager.ImageDao
import im.kny.springtx.txmanager.PersonDao
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityTransaction
import org.hibernate.internal.SessionImpl
import org.postgresql.jdbc.PgConnection
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.getValue

class DbCtx(val entityManager: EntityManager) : AutoCloseable {

    /**
     * Represents the transaction we are in.
     */
    val transaction: EntityTransaction

    init {
        // LOG.info("backendPID: ${backendPID()}")
        transaction = entityManager.transaction
        transaction.begin()
    }

    /*
     * Declarations of Database-Access-Objects which is used to do actual work against the database.
     */
    val person: PersonDao by lazy { PersonDao(entityManager) }
    val image: ImageDao by lazy { ImageDao(entityManager) }

    override fun close() {
        @Suppress("SENSELESS_COMPARISON")
        when {
            transaction == null -> LOG.debug("Transaction is `null`, should not happen unless init \\{\\} failed.")
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

    fun getPgConn(): PgConnection = entityManager
        .unwrap(SessionImpl::class.java)
        .jdbcCoordinator
        .logicalConnection
        .physicalConnection
        .unwrap(PgConnection::class.java)

    fun backendPID(): Int {
        val pgConn = getPgConn()
        val backendPID = pgConn.backendPID
        return backendPID
    }

    fun cleanDb() {
        getPgConn().execSQLUpdate("""
            UPDATE person set image_id = null;
            delete from image;
            delete from person;
            """)

    }

    companion object {
        private val LOG: Logger by lazy(LazyThreadSafetyMode.NONE) { LoggerFactory.getLogger(javaClass.enclosingClass) }
    }
}