package com.lapots.breed.backend;

import static org.testng.AssertJUnit.assertNotNull;

import com.google.gson.Gson;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.metamodel.annotation.TypeName;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Sample test using TestNG.
 */
@Test(dataProvider = "jsonDiff")
public class JaversDiffIntegrationTest {

    public void shouldCompareEntities(Person input1, Person input2, String expected) throws Exception {
        Javers javers = JaversBuilder.javers()
            .withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
            .build();
        Diff diff = javers.compare(input1, input2);
        String jsonDiff = javers.getJsonConverter().toJson(diff);

        JSONAssert.assertEquals(expected, jsonDiff, JSONCompareMode.STRICT);
    }

    @TypeName("TestEntityPerson")
    @Data
    @AllArgsConstructor
    private class Person {
        private String id;
        private String name;
    }

    @DataProvider(name = "jsonDiff", parallel = true) // runs in a separate of test pool
    public Object[][] jsonDiffProvider() {
        Gson gson = new Gson();

        List<Path> resourceFolder = FileResourceUtils.findResourceSubfolders("/json");
        Map<Path, List<Path>> testFiles = aggregateCaseFiles(resourceFolder);

        assertNotNull(testFiles);

        List<Object[]> arrays = testFiles.entrySet()
            .stream()
            .map(entry -> {
                List<Path> caseFiles = entry.getValue(); // sorted -> expected, input-1, input-2
                String expectedJson = FileResourceUtils.pathResourceToString(caseFiles.get(0));
                Person input1 = GsonUtils.toObject(caseFiles.get(1), Person.class);
                Person input2 = GsonUtils.toObject(caseFiles.get(2), Person.class);
                return new Object[] { input1, input2, expectedJson }; })
            .collect(Collectors.toList());

        Object[][] cases = new Object[arrays.size()][3];
        int i = 0;
        for (Object[] arr : arrays) {
            System.arraycopy(arr, 0, cases[i++], 0, 3);
        }

        return cases;
    }

    private Map<Path, List<Path>> aggregateCaseFiles(List<Path> caseFolders) {
        return caseFolders
            .stream()
            .collect(Collectors.toMap(Path::getFileName, FileResourceUtils::listSortedFiles));
    }
}
