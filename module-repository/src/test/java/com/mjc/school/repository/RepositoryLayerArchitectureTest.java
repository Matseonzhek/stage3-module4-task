package com.mjc.school.repository;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTag;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.core.domain.JavaModifier.ABSTRACT;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

@ArchTag("architecture")
@AnalyzeClasses(packagesOf = RepositoryLayerArchitectureTest.class, importOptions = DoNotIncludeTests.class)
class RepositoryLayerArchitectureTest {

    @ArchTest
    void classes_annotated_with_Entity_should_implement_BaseEntity_interface(
        final JavaClasses repositoryLayerClasses
    ) {
        classes()
            .that()
            .areAnnotatedWith("javax.persistence.Entity")
            .or()
            .areAnnotatedWith("jakarta.persistence.Entity")
            .should()
            .implement("com.mjc.school.repository.model.BaseEntity")
            .because("it's general requirement for the entities - to be annotated with @Entity and implement BaseEntity interface")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void classes_implementing_BaseEntity_interface_should_be_annotated_with_Entity(
        final JavaClasses repositoryLayerClasses
    ) {
        classes()
            .that()
            .implement("com.mjc.school.repository.model.BaseEntity")
            .should()
            .beAnnotatedWith("javax.persistence.Entity")
            .orShould()
            .beAnnotatedWith("jakarta.persistence.Entity")
            .orShould()
            .haveModifier(ABSTRACT)
            .because("it's general requirement for the entities - to be annotated with @Entity and implement BaseEntity interface")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void classes_annotated_with_Repository_should_implement_BaseRepository_interface(
        final JavaClasses repositoryLayerClasses
    ) {
        classes()
            .that()
            .areAnnotatedWith("org.springframework.stereotype.Repository")
            .should()
            .implement("com.mjc.school.repository.BaseRepository")
            .because("it's general requirement for the repositories - to be annotated with @Repository and implement BaseRepository interface")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void classes_implementing_BaseRepository_interface_should_be_annotated_with_Repository(
        final JavaClasses repositoryLayerClasses
    ) {
        classes()
            .that()
            .implement("com.mjc.school.repository.BaseRepository")
            .should()
            .beAnnotatedWith("org.springframework.stereotype.Repository")
            .orShould()
            .haveModifier(ABSTRACT)
            .because("it's general requirement for the repositories - to be annotated with @Repository and implement BaseRepository interface")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void classes_implementing_spring_Repository_interface_should_not_be_used(
        final JavaClasses repositoryLayerClasses
    ) {
        noClasses()
            .should()
            .beAssignableTo("org.springframework.data.repository.Repository")
            .because("it's strictly forbidden to use any kind of auto-generated spring-data repositories")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void classes_from_repository_packages_should_not_use_JdbcTemplates(
        final JavaClasses repositoryLayerClasses
    ) {
        noClasses()
            .should()
            .dependOnClassesThat()
            .areAssignableTo("org.springframework.jdbc.core.JdbcTemplate")
            .orShould()
            .dependOnClassesThat()
            .areAssignableTo("org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate")
            .because("EntityManager should be used to access database")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void at_least_one_class_annotated_with_Repository_should_be_present_in_module_packages(
        final JavaClasses repositoryLayerClasses
    ) {
        classes()
            .that()
            .areAnnotatedWith("org.springframework.stereotype.Repository")
            .should()
            .resideInAPackage("com.mjc.school.repository..")
            .because("at least one class annotated with @Repository should be observed")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void at_least_one_class_annotated_with_Entity_should_be_present_in_module_packages(
        final JavaClasses repositoryLayerClasses
    ) {
        classes()
            .that()
            .areAnnotatedWith("javax.persistence.Entity")
            .should()
            .resideInAPackage("com.mjc.school.repository..")
            .because("at least one JPA Entity class should be observed")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void hibernate_specific_classes_should_not_be_used_directly(
        final JavaClasses repositoryLayerClasses
    ) {
        noClasses()
            .should()
            .dependOnClassesThat()
            .resideInAPackage("org.hibernate..")
            .because("only JPA should be used for the solution")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void repositories_should_not_be_annotated_with_Transactional(
        final JavaClasses repositoryLayerClasses
    ) {
        noClasses()
            .that()
            .implement("com.mjc.school.repository.BaseRepository")
            .should()
            .beAnnotatedWith("javax.transaction.Transactional")
            .orShould()
            .beAnnotatedWith("jakarta.transaction.Transactional")
            .orShould()
            .beAnnotatedWith("org.springframework.transaction.annotation.Transactional")
            .because("each repository method should represent atomic operation and doesn't require transaction management")
            .check(repositoryLayerClasses);
    }

    @ArchTest
    void repositories_methods_should_not_be_annotated_with_Transactional(
        final JavaClasses repositoryLayerClasses
    ) {
        noMethods()
            .that()
            .areDeclaredInClassesThat()
            .implement("com.mjc.school.repository.BaseRepository")
            .should()
            .beAnnotatedWith("javax.transaction.Transactional")
            .orShould()
            .beAnnotatedWith("jakarta.transaction.Transactional")
            .orShould()
            .beAnnotatedWith("org.springframework.transaction.annotation.Transactional")
            .because("each repository method should represent atomic operation and doesn't require transaction management")
            .check(repositoryLayerClasses);
    }
}
