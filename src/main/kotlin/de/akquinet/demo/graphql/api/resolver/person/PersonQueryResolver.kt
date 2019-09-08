package de.akquinet.demo.graphql.api.resolver.person

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import de.akquinet.demo.graphql.model.PersonEntity
import de.akquinet.demo.graphql.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PersonQueryResolver @Autowired constructor(private val personRepository: PersonRepository) : GraphQLQueryResolver {

    fun persons(): List<PersonEntity> = personRepository.findAll()

    fun person(id: Long): PersonEntity = personRepository
            .findById(id)
            .orElseThrow { Exception("Can not find person with id: $id") }
}
