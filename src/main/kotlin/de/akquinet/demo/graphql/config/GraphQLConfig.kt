package de.akquinet.demo.graphql.config

import de.akquinet.demo.graphql.service.AsyncTransactionalExecutionStrategyService
import java.util.HashMap
import graphql.execution.ExecutionStrategy
import graphql.servlet.core.GraphQLErrorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/** see https://stackoverflow.com/a/48046402/6324593
 */
@Configuration
class GraphQLConfig @Autowired constructor(
        val asyncTransactionalExecutionStrategyService: AsyncTransactionalExecutionStrategyService) {

    @Bean
    fun executionStrategies(): Map<String, ExecutionStrategy> {
        val executionStrategyMap = HashMap<String, ExecutionStrategy>()
        executionStrategyMap["queryExecutionStrategy"] = asyncTransactionalExecutionStrategyService
        return executionStrategyMap
    }

    @Bean
    fun graphQLErrorHandler(): GraphQLErrorHandler {
        return GraphQLErrorHandler { errors -> errors }
    }
}
