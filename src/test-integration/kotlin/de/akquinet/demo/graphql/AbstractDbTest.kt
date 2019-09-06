package de.akquinet.demo.graphql

import de.akquinet.demo.graphql.utils.logger
import org.junit.ClassRule
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.containers.wait.strategy.Wait

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [ApplicationInitializer::class])
@DirtiesContext
@Profile("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
abstract class AbstractDbTest {

    companion object {

        private val logger = logger()

        var started = false

        val db = KMariaDBContainer("mariadb:10.4")
                .withEnv("MYSQL_INITDB_SKIP_TZINFO", "1")
                .waitingFor(Wait.forListeningPort())

        @JvmField
        @ClassRule
        val containers = object : ExternalResource() {

            override fun before() {

                logger.info("starting db")
                db.start()

                started = true
            }

            override fun after() {
                db.stop()
            }
        }
    }
}

class KMariaDBContainer(image: String) : MariaDBContainer<KMariaDBContainer>(image)
