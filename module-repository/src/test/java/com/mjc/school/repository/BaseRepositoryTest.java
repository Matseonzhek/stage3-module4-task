package com.mjc.school.repository;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = ConfigureTestDatabase.class)
class BaseRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private List<BaseRepository<?, Long>> allRepositories;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    @Order(1)
    void expected_number_of_repository_beans_should_be_found_in_context() {
        // given
        final var expectedNumberOfRepositories = 3;

        // when
        final var actualNumberOfRepositories = allRepositories.size();

        // then
        assertThat(actualNumberOfRepositories)
            .describedAs("Number of beans implementing %s interface", BaseRepository.class.getName())
            .isEqualTo(expectedNumberOfRepositories);
    }

    @TestFactory
    Stream<DynamicNode> testAllRepositories() {
        return allRepositories.stream().map(it -> dynamicContainer(getName(it), getTests(it)));
    }

    private String getName(final Object repository) {
        return repository.getClass().getSimpleName() + "Test";
    }

    private Stream<DynamicTest> getTests(final BaseRepository<?, Long> repository) {
        return Stream.of(

            dynamicTest("repository readAll() should return a list without exceptions", () -> {
                // when
                var result = repository.readAll();

                // then
                assertThat(result).isNotNull();
            }),

            dynamicTest("repository readById() should return a list without exceptions", () -> {
                // when
                var result = repository.readById(0L);

                // then
                assertThat(result).isNotNull();
            })

        );
    }

    private Executable withTransaction(final Executable executable) {
        return () -> {
            LOGGER.debug("start test transaction");
            var transaction = transactionManager.getTransaction(TestTransactionDefinition.INSTANCE);
            try {
                executable.execute();
            } finally {
                transactionManager.rollback(transaction);
                LOGGER.debug("test transaction rolled back");
            }
        };
    }

    private Executable withTransaction(final Consumer<TransactionStatus> executable) {
        return () -> {
            LOGGER.debug("start test transaction");
            var transaction = transactionManager.getTransaction(TestTransactionDefinition.INSTANCE);
            try {
                executable.accept(transaction);
            } finally {
                transactionManager.rollback(transaction);
                LOGGER.debug("test transaction rolled back");
            }
        };
    }

    private enum TestTransactionDefinition implements TransactionDefinition {
        INSTANCE {
            @Override
            public String getName() {
                return "test-transaction";
            }
        }
    }
}
