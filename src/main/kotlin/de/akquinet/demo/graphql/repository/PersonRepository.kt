package de.akquinet.demo.graphql.repository

import de.akquinet.demo.graphql.model.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface PersonRepository : JpaRepository<PersonEntity, Long>
