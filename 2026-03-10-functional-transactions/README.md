Functional Transactions - Hibernate and Spring
==============================================

# Problems with JTA's @Transactional

1. Do I need to make a new bean to start a TX?
1. Am I in a TX?
1. My Tx failed, how can I fix that?
1. What about 2-phase commit?


# A better approach

Fucntional transaction

```
val person1 = txManager.autoCommitTx { dbCtx ->
    dbCtx.person.searchByName("Donald Duck").single()
}

val imageBlob = slowRestService.getSlowImage(person1.id)
val person2 = txManager.autoCommitTx { dbCtx ->
    val personInTx = dbCtx.person.byId(person1.id)
    personInTx.image = imageBlob
    personInTx
}
```

# Parts of the solution

* TxManager:
    * Creation of connections
    * Database context: DbCtx
* DbCtx:
    * Keeps connection
    * Auto commit
    * Rollback
    * Provide `Data-Access-Object` (Dao)
* Dao:
    * Actual operations towards database
    * Uses either JDBC or Hibernate

# TxManagerImpl




1. What is GIT
2. New Repo and clone, commit
3. Remote, local repo, Index and Working directory
4. Git branches
   1. Local and remote branches
   2. Visualization of GIT commits
5. GIT data structure
   1. .git katalogen
   2. git 
