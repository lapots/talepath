package com.lapots.breed.backend;

import static org.testng.AssertJUnit.assertNotNull;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.metamodel.annotation.TypeName;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Experimental tests.
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

        List<Path> resourceFolder = listResourceFolder("/json");
        Map<Path, List<Path>> testFiles = aggregateCaseFiles(resourceFolder);

        assertNotNull(testFiles);

        List<Object[]> arrays = testFiles.entrySet()
            .stream()
            .map(entry -> {
                List<Path> caseFiles = entry.getValue(); // sorted -> expected, input-1, input-2

                JsonReader input1Reader = fileToReader(caseFiles.get(1));
                JsonReader input2Reader = fileToReader(caseFiles.get(2));
                String expectedJson = fileToString(caseFiles.get(0));

                Person input1 = toObject(gson, input1Reader, Person.class);
                Person input2 = toObject(gson, input2Reader, Person.class);
                return new Object[] { input1, input2, expectedJson }; })
            .collect(Collectors.toList());

        Object[][] cases = new Object[arrays.size()][3];
        int i = 0;
        for (Object[] arr : arrays) {
            System.arraycopy(arr, 0, cases[i++], 0, 3);
        }

        return cases;
    }

    private <T> T toObject(Gson gson, JsonReader input, Class<T> clazz) {
        return gson.fromJson(input, clazz);
    }

    private JsonReader fileToReader(Path file) {
        try {
            return new JsonReader(Files.newBufferedReader(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String fileToString(Path file) {
        try {
            return new String(Files.readAllBytes(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ignores empty folders!!!
    private List<Path> listResourceFolder(String folder) {
        try {
            URI uri = this.getClass().getResource(folder).toURI();
            return Files
                .list(Paths.get(uri))
                .filter(Files::isDirectory)
                .map(Path::toAbsolutePath)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Path, List<Path>> aggregateCaseFiles(List<Path> caseFolders) {
        return caseFolders
            .stream()
            .collect(Collectors.toMap(Path::getFileName, this::iterateFiles));
    }

    private List<Path> iterateFiles(Path folder) {
        try (Stream<Path> paths = Files.walk(folder)) {
            return paths
                .filter(Files::isRegularFile)
                .map(Path::toAbsolutePath)
                .sorted()
                .collect(Collectors.toList());
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
    }
}
