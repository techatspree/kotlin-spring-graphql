package de.akquinet.demo.graphql.api.resolver.person

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import de.akquinet.demo.graphql.model.PersonEntity
import de.akquinet.demo.graphql.repository.PersonRepository
import kotlinx.coroutines.delay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class PersonQueryResolver @Autowired constructor(private val personRepository: PersonRepository) : GraphQLQueryResolver {

    suspend fun persons(): List<PersonEntity> {
        delay(1000L) // some async stuff
        return personRepository.findAll()
    }

    fun person(id: Long): Optional<PersonEntity> =
            personRepository.findById(id)
}
