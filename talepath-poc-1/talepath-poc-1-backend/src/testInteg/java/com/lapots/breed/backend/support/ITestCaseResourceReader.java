package com.lapots.breed.backend.support;

import static org.testng.Assert.assertNotNull;

import com.lapots.breed.backend.support.util.FileResourceUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Trait for integration tests.
 */
public interface ITestCaseResourceReader {

    // TODO: probably list of lists will suffice
    /**
     * Reads test case files from /resources folder.
     * @param testCaseFolder test case folder path like /resources/data/testcases
     * @return map of folders and files
     */
    default Map<Path, List<Path>> readTestCases(String testCaseFolder) {
        List<Path> resourceFolder = FileResourceUtils.findResourceSubfolders(testCaseFolder);
        Map<Path, List<Path>> testFiles = resourceFolder
            .stream()
            .collect(Collectors.toMap(Path::getFileName, FileResourceUtils::listSortedFiles));
        assertNotNull(testFiles);
        return testFiles;
    }

    // TODO: do I really need this method in that trait?

    /**
     * Convert list of arrays into array of arrays.
     * @param testCases test cases list
     * @param inputParameters number of test inputs
     * @return array of arrays
     */
    default Object[][] toProvidedTestCase(List<Object[]> testCases, int inputParameters) {
        Object[][] cases = new Object[testCases.size()][inputParameters];
        int i = 0;
        for (Object[] arr : testCases) {
            System.arraycopy(arr, 0, cases[i++], 0, inputParameters);
        }
        return cases;
    }
}
