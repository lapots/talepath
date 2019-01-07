package com.lapots.breed.diff;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.DiffNode.Visitor;
import de.danielbechler.diff.node.Visit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Example of usage Java-Object-Diff library.
 */
public class JavaObjectDiffExample {

    public void runExample() {
        case4();
    }

    // no changes
    private void case1() {
        Person personOld = new Person("Chuck", "1111", Collections.emptyList());
        Person personNew = new Person("Chuck", "1111", Collections.emptyList());

        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(personNew, personOld);
        printDelta(diff);
    }

    // single update
    private void case2() {
        Person personOld = new Person("Chuck", "1111", Collections.emptyList());
        Person personNew = new Person("Chuck", "1234", Collections.emptyList());

        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(personNew, personOld);
        printDelta(diff);
    }

    // double update
    private void case3() {
        Person personOld = new Person("Chuck", "1111", Collections.emptyList());
        Person personNew = new Person("Chucke", "1234", Collections.emptyList());

        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(personNew, personOld);
        printDelta(diff);
    }

    // double update with list
    private void case4() {
        Person personOld = new Person("Chuck", "1111", Collections.emptyList());
        Person personNew = new Person("Chucke", "1234",
            Collections.singletonList(new Person("ExtraPerson", "0000", Collections.emptyList())));

        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(personNew, personOld);
        printDelta(diff, personOld, personNew);
    }

    private void printDelta(DiffNode diff) {
        diff.visit((node, visit) -> System.out.println(node.getPath() + " => " + node.getState()));
    }

    private <T> void printDelta(DiffNode diff, T oldObject, T newObject) {
        diff.visit((node, visit) -> {
            System.out.print(node.getPath() + " => " + node.getState() + "; ");
            System.out.println(node.canonicalGet(oldObject) + " => " + node.canonicalGet(newObject));
        });

    }

    @Data
    @AllArgsConstructor
    private static class Person {
        private String name;
        private String id;
        private List<Person> friends = new ArrayList<>();
    }
}
