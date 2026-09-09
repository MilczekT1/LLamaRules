package io.github.milczekt1.corral.rules.testing.nojunit4;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.milczekt1.corral.DocumentedRule;
import io.github.milczekt1.corral.doc.RuleDoc;
import io.github.milczekt1.corral.scope.TestScope;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * No class compiled into test output may depend on anything {@code junit:junit} ships.
 *
 * <p>Scoped to test <em>output</em>, so base classes, helpers and fixtures with no test of their
 * own are in scope.
 *
 * <p>Inspects <em>test</em> classes, so consumers must not set
 * {@code ImportOption.DoNotIncludeTests} — it would pass vacuously.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoJUnit4Rule implements DocumentedRule {

    static final RuleDoc DOC = RuleDoc.builder()
            .id("corral.test.no-junit4")
            .why("""
                    org.junit.Test compiles whenever junit:junit is on the test classpath — usually \
                    transitively, from a dependency nobody chose — and the Jupiter engine does not \
                    recognise it. The class is discovered as containing no tests: green build, no \
                    assertion ever evaluated, nothing reported as skipped. One package segment separates \
                    it from org.junit.jupiter.api.Test. Half-migrated is worse: Jupiter's @Test beside \
                    JUnit 4's @Before runs the test but never the setup, so it executes against null \
                    fields, and JUnit 4's assertEquals takes the message first where Jupiter's takes it \
                    last, so switching only the static import still compiles and now compares the \
                    message to the expected value. The vector is rarely the test class — it is the base \
                    class, the @Rule on a helper, the fixture builder importing org.junit.Assert: code \
                    with no tests of its own that every test in the package extends or calls.""")
            .howToFix("""
                    The violation names the JUnit 4 type. Most annotations are mechanical and the \
                    compiler catches what you miss: the same-named Jupiter annotation, with Each or All \
                    where JUnit 4 left the scope implicit. Three do not map by name — @RunWith takes the \
                    matching @ExtendWith (MockitoJUnitRunner to MockitoExtension), @Rule and @ClassRule \
                    take the JUnit 5 extension their library ships, and @Ignore means delete the test, \
                    not @Disabled. Assert, Assume and TestCase are not mechanical: switch to \
                    org.junit.jupiter.api.Assertions AND move the message argument to the end of every \
                    call you touch, one call site at a time, because the compiler will not tell you if \
                    you forget. Take anything richer than equality, and every Hamcrest assertThat, to \
                    AssertJ. Then drop junit:junit, or find the parent bringing it in with mvn \
                    dependency:tree — its presence there is what lets the mixed state compile.""")
            .howNotToFix("""
                    Do NOT add junit-vintage-engine to make the annotations run again: that buys green by \
                    keeping two engines, two annotation vocabularies and two lifecycle models alive at \
                    once, the state this rule exists to end. Do NOT change the import and leave the \
                    argument order — that is the bug half this rule exists to stop, and it stays green. \
                    Do NOT wrap the assertions in a project helper: the argument-order hazard survives \
                    the wrapper, now invisible, and the helper is flagged instead. Do NOT swap @Test for \
                    @Ignore or drop it so the method quietly becomes dead code, do NOT delete \
                    assertions to make a migrated test compile, and do NOT leave assertTrue(true) as a \
                    placeholder — nothing else catches that one. Do NOT hand-edit the freeze store to \
                    admit a new entry: it records debt you inherited, not debt you just wrote. One dodge \
                    this predicate does NOT catch: re-exporting org.junit.Test as a meta-annotation on \
                    your own @FastTest. dependOnClassesThat is not transitive, so it flags FastTest and \
                    goes quiet on the classes using it — and since JUnit 4 ignores meta-annotations at \
                    runtime those tests still do not run, so do not read the silence as a fix.""")
            .build();

    /**
     * Excluded from {@code org.junit..}: JUnit 5's own API, the Platform, and the vintage engine.
     * Configuring vintage is not a violation — only using JUnit 4's API is.
     */
    static final List<String> NOT_JUNIT4_PACKAGES = List.of(
            "org.junit.jupiter..",
            "org.junit.platform..",
            "org.junit.vintage..");

    /**
     * Everything {@code junit:junit:4.13.2} ships, matched by package string so a consumer without the
     * artifact still compiles and runs the rule.
     */
    private static final DescribedPredicate<JavaClass> JUNIT4_TYPES =
            resideInAnyPackage("junit.framework..", "junit.extensions..")
                    .or(resideInAPackage("org.junit..")
                            .and(not(resideInAnyPackage(NOT_JUNIT4_PACKAGES.toArray(String[]::new)))))
                    .as("a JUnit 4 or JUnit 3 type");

    /**
     * {@code dependOnClassesThat} covers annotations, superclasses, field and parameter types and
     * method calls in one check. Violations are per-dependency, not per-class: adding a fourth JUnit 4
     * usage to an already-frozen class is a new violation.
     */
    static final ArchRule DEFINITION = noClasses()
            .that(TestScope.TEST_CLASSES)
            .should().dependOnClassesThat(JUNIT4_TYPES);

    @ArchTest
    public static final ArchRule rule = new NoJUnit4Rule().guard();

    @Override
    public ArchRule definition() {
        return DEFINITION;
    }

    @Override
    public RuleDoc doc() {
        return DOC;
    }
}
