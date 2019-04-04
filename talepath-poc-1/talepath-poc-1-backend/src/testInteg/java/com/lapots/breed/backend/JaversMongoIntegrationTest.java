package com.lapots.breed.backend;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.lapots.breed.backend.data.PersonCharacter;
import com.lapots.breed.backend.support.ITestCaseResourceReader;
import com.lapots.breed.backend.support.util.GsonUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.repository.jql.QueryBuilder;
import org.javers.repository.mongo.MongoRepository;
import org.javers.shadow.Shadow;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Tests for Javers repositories.
 */
@Test(dataProvider = "domains")
public class JaversMongoIntegrationTest implements ITestCaseResourceReader {
    private Javers javers;

    private static final int TEST_INPUT_COUNT = 2;

    @BeforeClass
    public void setup() {
        MongoDatabase mongoDb = new MongoClient("localhost", 10101)
                .getDatabase("test");
        MongoRepository mongoRepository = new MongoRepository(mongoDb);
        javers = JaversBuilder.javers()
                .withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
                .registerJaversRepository(mongoRepository).build();
    }

    public void shouldStoreDelta(PersonCharacter origCharacter, PersonCharacter updatedCharacter) {
        javers.commit("test-author-1", origCharacter); // original character
        javers.commit("test-author-2", updatedCharacter); // updated character

        List<Shadow<PersonCharacter>> shadows = javers.findShadows(QueryBuilder
            .byInstanceId(origCharacter.getId(), PersonCharacter.class).build());

        assertEquals(2, shadows.size());
        assertTrue(shadows.stream().anyMatch(entry -> entry.get().equals(origCharacter)));
        assertTrue(shadows.stream().anyMatch(entry -> entry.get().equals(updatedCharacter)));
    }

    @DataProvider(name = "domains", parallel = true)
    public Object[][] domainProvider() {
        List<Object[]> arrays = readTestCases("/data/mongo").entrySet()
            .stream()
            .map(Entry::getValue)
            .map(caseFiles -> {
                PersonCharacter original = GsonUtils.toObject(caseFiles.get(0), PersonCharacter.class);
                PersonCharacter updated = GsonUtils.toObject(caseFiles.get(1), PersonCharacter.class);
                return new Object[]{original, updated}; })
            .collect(Collectors.toList());
        return toProvidedTestCase(arrays, TEST_INPUT_COUNT);
    }
}
