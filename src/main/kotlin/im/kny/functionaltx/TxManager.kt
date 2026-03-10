package im.kny.functionaltx

interface TxManager {
    /**
     * Runs a block (function) in a transaction.
     * It commits the transaction if no exceptions and the transaction is not in rollback state.
     *
     * @param Block of code to run. DbCtx is the database context object that holds the EntityManager.
     * @param R Return type of the block.
     */
    fun <R> autoCommitTx(block: (dbCtx: DbCtx) -> R): R

    /**
     * Clean the database. Used for test.
     */
    fun cleanDb()
}