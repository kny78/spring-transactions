package im.kny

import jakarta.persistence.EntityManager


class PersonDao(val entityManager: EntityManager) {

    fun persist(person: Person): Person {
        entityManager.persist(person)
        return person
    }

    fun list(): List<Person> {
        return entityManager
            .createQuery("SELECT p from Person p", Person::class.java)
            .resultList
    }

    fun byId(id: Long?): Person {
        return entityManager
            .createQuery("""SELECT p from Person p where p.id=:id""", Person::class.java)
            .setParameter("id", id)
            .singleResult
    }

    fun deleteAll() {
        entityManager
            .createQuery("DELETE from Person")
            .executeUpdate()
    }
}
