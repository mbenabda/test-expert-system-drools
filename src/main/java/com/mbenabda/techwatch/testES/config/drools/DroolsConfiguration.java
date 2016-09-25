package com.mbenabda.techwatch.testES.config.drools;

import com.mbenabda.techwatch.testES.facts.age.YouthLimitAge;
import com.mbenabda.techwatch.testES.facts.noise.LoudnessThreshold;
import com.mbenabda.techwatch.testES.repository.GenreRepository;
import com.mbenabda.techwatch.testES.repository.InstrumentRepository;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DroolsConfiguration {
    private static final int LIMIT_AGE = 18;
    @Autowired
    GenreRepository genreRepository;

    @Autowired
    InstrumentRepository instrumentRepository;

    @PostConstruct
    public void fireAllRules() {
        populateDatabase();

        kieSession().fireAllRules();
    }

    private void populateDatabase() {
        new FakeDataInitializer(genreRepository, instrumentRepository).init();
    }

    // load up the knowledge base
    KieSession session;
    @Bean
    public KieSession kieSession() {
        if(session == null) {
            KieServices services = KieServices.Factory.get();
            KieContainer container = services.getKieClasspathContainer();
            session = container.newKieSession("ksession-test");

            new LoadedRulesLogger().logRulesLoadedIn(session.getKieBase());

            session.insert(new YouthLimitAge(LIMIT_AGE));
            session.insert(new LoudnessThreshold(.5f));

            genreRepository.findAll().stream().forEach(session::insert);
            instrumentRepository.findAll().stream().forEach(session::insert);
        }
        return session;
    }
}
