package de.akquinet.demo.graphql

import de.akquinet.demo.graphql.utils.logger
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class ApplicationInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    private val logger = logger()

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {

        val datasourceUrl = "spring.datasource.url=" + AbstractDbTest.db.jdbcUrl
        val username = "spring.datasource.username=" + AbstractDbTest.db.username
        val password = "spring.datasource.password=" + AbstractDbTest.db.password

        val values = TestPropertyValues.of(datasourceUrl,
                username,
                password)

        logger.info("using test properties {}, {}, {}", datasourceUrl, username, password)
        values.applyTo(configurableApplicationContext)
    }
}
