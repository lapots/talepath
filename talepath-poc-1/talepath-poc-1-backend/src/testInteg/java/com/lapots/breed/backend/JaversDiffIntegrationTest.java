package com.lapots.breed.backend;

import com.lapots.breed.backend.data.PersonCharacter;
import com.lapots.breed.backend.support.ITestCaseResourceReader;
import com.lapots.breed.backend.support.util.FileResourceUtils;
import com.lapots.breed.backend.support.util.GsonUtils;

import com.lapots.breed.backend.support.util.JsonAssertWrapper;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Tests for Javers deltas.
 */
@Test(dataProvider = "jsonDiff")
public class JaversDiffIntegrationTest implements ITestCaseResourceReader  {
    private Javers javers;

    private static final int TEST_OUTPUT_COUNT = 3;

    @BeforeClass
    public void setup() {
        javers = JaversBuilder.javers()
                .withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
                .build();
    }

    public void shouldCompareEntities(PersonCharacter input1, PersonCharacter input2, String expected) {
        Diff diff = javers.compare(input1, input2);
        String jsonDiff = javers.getJsonConverter().toJson(diff);
        JsonAssertWrapper.assertEquals(expected, jsonDiff);
    }

    @DataProvider(name = "jsonDiff", parallel = true)
    public Object[][] jsonDiffProvider() {
        List<Object[]> arrays = readTestCases("/data/diff").entrySet()
            .stream()
            .map(Entry::getValue)
            .map(caseFiles -> {
                String expectedJson = FileResourceUtils.pathResourceToString(caseFiles.get(0));
                PersonCharacter input1 = GsonUtils.toObject(caseFiles.get(1), PersonCharacter.class);
                PersonCharacter input2 = GsonUtils.toObject(caseFiles.get(2), PersonCharacter.class);
                return new Object[] { input1, input2, expectedJson }; })
            .collect(Collectors.toList());
        return toProvidedTestCase(arrays, TEST_OUTPUT_COUNT);
    }
}
