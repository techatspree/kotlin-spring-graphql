package de.akquinet.demo.graphql.person

import com.graphql.spring.boot.test.GraphQLTestTemplate
import de.akquinet.demo.graphql.AbstractDbTest
import de.akquinet.demo.graphql.model.AddressEntity
import de.akquinet.demo.graphql.model.PersonEntity
import de.akquinet.demo.graphql.model.PetEntity
import de.akquinet.demo.graphql.model.PetType
import de.akquinet.demo.graphql.repository.PersonRepository
import de.akquinet.demo.graphql.repository.PetRepository
import org.assertj.core.api.Assertions.*
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

internal class PersonQueryResolverTest : AbstractDbTest() {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var petRepository: PetRepository

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    @Test
    fun `should list all persons`() {
        val p0 = personRepository.saveAndFlush(
                PersonEntity("myName",
                        AddressEntity("myStreet", "123", "Berlin")
                ))
        petRepository.saveAndFlush(PetEntity(p0, "Bello", PetType.DOG))

        val postMultipart = graphQLTestTemplate.postMultipart("""
            query {
                persons {
                    name, 
                    pets { name }
                }
            }
        """.trimIndent(), "{}")
        assertThat(postMultipart).isNotNull

        val expectedResponseBody = """
           {"data":{"persons":[{"name":"myName", pets: [{"name": "Bello"}]}]}}
        """
        JSONAssert.assertEquals(expectedResponseBody, postMultipart.rawResponse.body, JSONCompareMode.LENIENT)
    }
}

