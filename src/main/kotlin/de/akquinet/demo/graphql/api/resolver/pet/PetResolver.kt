package de.akquinet.demo.graphql.api.resolver.pet

import com.coxautodev.graphql.tools.GraphQLResolver
import de.akquinet.demo.graphql.model.PetEntity
import graphql.GraphQLException
import org.springframework.stereotype.Component

@Component
class PetResolver : GraphQLResolver<PetEntity> {

    fun getReverseName(pet: PetEntity): String {
        return pet.name.reversed()
    }
}
