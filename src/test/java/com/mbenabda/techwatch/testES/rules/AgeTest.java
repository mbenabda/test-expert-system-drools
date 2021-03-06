package com.mbenabda.techwatch.testES.rules;

import com.mbenabda.techwatch.testES.facts.age.IsOld;
import com.mbenabda.techwatch.testES.facts.age.IsYoung;
import com.mbenabda.techwatch.testES.facts.age.YouthLimitAge;
import com.mbenabda.techwatch.testES.facts.answers.Age;
import com.mbenabda.techwatch.testES.facts.answers.DateOfBirth;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class AgeTest {
    private static final Logger LOG = LoggerFactory.getLogger(AgeTest.class);

    private static final int MAJORITY = 18;

    @Rule
    public final StatefulKieSessionRule kie = new StatefulKieSessionRule();

    @Before
    public void setUp() {
        assertEquals(0, kie.session().getFactCount());
        kie.session().insert(new YouthLimitAge(MAJORITY));
    }

    @Test
    public void should_deduce_date_of_birth_from_age() {
        kie.session().insert(new Age(30));
        kie.session().fireAllRules();

        DateOfBirth deducedDateOfBirth = kie.session().getObjects(o -> o instanceof DateOfBirth).stream().findFirst().map(o -> (DateOfBirth) o).get();
        assertEquals(LocalDate.now().minusYears(30), deducedDateOfBirth.getValue());
    }

    @Test
    public void should_deduce_age_from_date_of_birth() {
        kie.session().insert(new DateOfBirth(LocalDate.now().minusYears(30)));
        kie.session().fireAllRules();

        Age deducedAge = kie.session().getObjects(o -> o instanceof Age).stream().findFirst().map(o -> (Age) o).get();
        assertEquals(30, deducedAge.getValue());
    }

    @Test
    public void should_infer_underage() {
        kie.session().insert(new Age(MAJORITY));
        kie.session().fireAllRules();

        Collection<?> inferred = kie.session().getObjects(o -> o instanceof IsYoung);
        assertEquals(1, inferred.size());
    }

    @Test
    public void should_infer_majority() {
        kie.session().insert(new Age(MAJORITY + 1));

        kie.session().fireAllRules();

        Collection<?> inferred = kie.session().getObjects(o -> o instanceof IsOld);
        assertEquals(1, inferred.size());
    }
}
