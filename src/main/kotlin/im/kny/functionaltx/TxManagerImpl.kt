package im.kny.functionaltx

import org.springframework.stereotype.Component
import jakarta.persistence.EntityManagerFactory

@Component
class TxManagerImpl(
    val entityManagerFactory: EntityManagerFactory,
) : TxManager {

    override fun <R> autoCommitTx(block: (dbCtx: DbCtx) -> R): R {
        val entityManager = entityManagerFactory.createEntityManager()

        return DbCtx(entityManager = entityManager).use {
            try {
                block(it)
            } catch (e: Exception) {
                it.setRollbackOnly()
                throw e
            }
        }
    }

    override fun cleanDb() {
        autoCommitTx { dbCtx ->
            dbCtx.cleanDb()
        }
    }
}
