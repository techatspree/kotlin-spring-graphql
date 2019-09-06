package de.akquinet.demo.graphql.api.resolver.pet

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import de.akquinet.demo.graphql.model.PetEntity
import de.akquinet.demo.graphql.repository.PetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PetQueryResolver @Autowired constructor(private val petRepository: PetRepository) : GraphQLQueryResolver {

    fun pets(): List<PetEntity> {
        return petRepository.findAll()
    }
}
