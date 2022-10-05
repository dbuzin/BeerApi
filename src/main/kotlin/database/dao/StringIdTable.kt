package database.dao

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Column

open class StringIdTable(name: String = "", idColumnName: String = "id", idColumnSize: Int = 32) : IdTable<String>(name) {
    override val id: Column<EntityID<String>> = varchar(idColumnName, idColumnSize).primaryKey().entityId().autoinc()
}

abstract class StringEntity(id: EntityID<String>) : Entity<String>(id)

abstract class StringEntityClass<E: StringEntity>(table: IdTable<String>) : EntityClass<String, E>(table)