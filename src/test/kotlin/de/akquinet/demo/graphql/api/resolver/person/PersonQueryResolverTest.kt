package de.akquinet.demo.graphql.api.resolver.person

import com.graphql.spring.boot.test.GraphQLTestTemplate
import de.akquinet.demo.graphql.AbstractGraphQLTest
import de.akquinet.demo.graphql.model.AddressEntity
import de.akquinet.demo.graphql.model.PersonEntity
import de.akquinet.demo.graphql.repository.PersonRepository
import org.assertj.core.api.Assertions.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Profile("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
internal class PersonQueryResolverTest {

    @MockBean
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    @Test
    fun `should get person by id`() {
        given(personRepository.findById(1))
                .willReturn(Optional.of(PersonEntity("myName",
                        AddressEntity("s", "z", "c"))))

        val postMultipart = graphQLTestTemplate.postMultipart(
                "query {person(id: 1) {name}}", "{}")
        assertThat(postMultipart).isNotNull

        val expectedResponseBody = """{"data":{"person":{"name":"myName"}}}"""
        JSONAssert.assertEquals(expectedResponseBody, postMultipart.rawResponse.body,
                JSONCompareMode.LENIENT)
    }
}

