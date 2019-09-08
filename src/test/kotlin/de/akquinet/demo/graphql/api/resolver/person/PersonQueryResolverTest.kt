package de.akquinet.demo.graphql.api.resolver.person

import com.graphql.spring.boot.test.GraphQLTestTemplate
import de.akquinet.demo.graphql.AbstractGraphQLTest
import de.akquinet.demo.graphql.model.AddressEntity
import de.akquinet.demo.graphql.model.PersonEntity
import de.akquinet.demo.graphql.repository.PersonRepository
import org.assertj.core.api.Assertions.*
import org.junit.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import java.util.*


internal class PersonQueryResolverTest : AbstractGraphQLTest() {

    @MockBean
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate


    @Test
    fun `should list all persons`() {
        given(personRepository.findAll())
                .willReturn(listOf(PersonEntity("myName", AddressEntity("s", "z", "c"))))

        val postMultipart = graphQLTestTemplate.postMultipart("query {persons {name}}", "{}")
        assertThat(postMultipart).isNotNull

        val expectedResponseBody = """
           {"data":{"persons":[{"name":"myName"}]}}
        """
        JSONAssert.assertEquals(expectedResponseBody, postMultipart.rawResponse.body, JSONCompareMode.LENIENT)
    }

    @Test
    fun `should get person by id`() {
        given(personRepository.findById(1))
                .willReturn(Optional.of(PersonEntity("myName", AddressEntity("s", "z", "c"))))

        val postMultipart = graphQLTestTemplate.postMultipart("query {person(id: 1) {name}}", "{}")
        assertThat(postMultipart).isNotNull

        val expectedResponseBody = """
           {"data":{"person":{"name":"myName"}}}
        """
        JSONAssert.assertEquals(expectedResponseBody, postMultipart.rawResponse.body, JSONCompareMode.LENIENT)
    }
}

