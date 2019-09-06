package de.akquinet.demo.graphql.api.resolver.person

import com.coxautodev.graphql.tools.GraphQLResolver
import de.akquinet.demo.graphql.model.PersonEntity
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class PersonResolver : GraphQLResolver<PersonEntity> {

//  "get" prefix is optional
//  default dataFetcher for a field is the PropertyDataFetcher -> use field of entity if name matches
//  If a resolver for a class is defined, then the field is revolved by it. No need to implement the field

    fun getReverseName(person: PersonEntity): String {
        return person.name.reversed()
    }

    suspend fun getStreet(person: PersonEntity): String {
        delay(1000L)
        return person.address.street
    }

    fun getZipCode(person: PersonEntity): String {
        return person.address.zipCode
    }

    fun getCity(person: PersonEntity): String {
        return person.address.city
    }
}
