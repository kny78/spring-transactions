package im.kny

interface TxManager {
    fun <R> autoCommitTx(block: (dbCtx: DbCtx) -> R): R
    suspend fun <R> autoCommitTxSuspended(block: suspend (dbCtx: DbCtx) -> R): R
}