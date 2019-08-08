package domain

interface CrudDAO<E, ID> {

    @Throws(Exception::class)
    fun get(id: ID): E

    @Throws(Exception::class)
    fun add(e: E): E

    @Throws(Exception::class)
    fun edit(e: E): E
}