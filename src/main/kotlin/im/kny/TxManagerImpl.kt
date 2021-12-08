package im.kny

import org.springframework.stereotype.Component
import javax.persistence.EntityManagerFactory

@Component
class TxManagerImpl(
    val entityManagerFactory: EntityManagerFactory,
) : TxManager {

    override fun <R> autoCommitTx(block: (dbCtx: DbCtx) -> R): R {
        val entityManager = entityManagerFactory.createEntityManager()

        return DbCtx(
            entityManager = entityManager
        ).use {
            try {
                block(it)
            } catch (e: Exception) {
                it.setRollbackOnly()
                throw e
            }
        }
    }

    override suspend fun <R> autoCommitTxSuspended(block: suspend (dbCtx: DbCtx) -> R): R {
        val entityManager = entityManagerFactory.createEntityManager()

        return DbCtx(
            entityManager = entityManager
        ).use {
            try {
                block(it)
            } catch (e: Exception) {
                it.setRollbackOnly()
                throw e
            }
        }
    }
}
