package pl.sda.jpatraining;

import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import pl.sda.jpatraining.jpa.Customer;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdasTest {

    @Test
    public void lambdasInStream() {

        List<String> animals = Lists.newArrayList("cat", "dog", "mouse", "rat", "pig",
                "rabbit", "hamster", "parrot");

        for (String sth : animals) { // Tradycyjna pętla
            System.out.println(sth + ";");
        }

        animals.stream().forEach(something -> System.out.println(something + ";")); //wersja z lambdą

        animals.sort(new MyComparator()); // użycie własnej klasy

        animals.sort(new Comparator<String>() { //użycie klasy anonimowej
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });

        animals.sort((a, b) -> a.compareTo(b));
        animals.sort(String::compareTo);


        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("John", 40000);
        salaries.put("Freddy", 30000);
        salaries.put("Samuel", 50000);

        salaries.replaceAll((key, val) ->
                key.equals("Freddy") ? val + 100 : val);

        List<String> names = Arrays.asList("Angela", "Aaron", "Bob", "Claire", "David");

        List<String> strings = names.stream().filter(e -> e.startsWith("A"))
                .map(e -> e.toUpperCase()).collect(Collectors.toList());


        names.stream().filter(e -> e.startsWith("A"))
                .map(e -> e.toUpperCase()).forEach(System.out::println);


    }

    @FunctionalInterface
    public interface SuperChecker {   // "to jest lambda"
        boolean check(Integer value);
    }

    private SuperChecker isOddAnonymous = new SuperChecker() {
        @Override
        public boolean check(Integer value) {
            return value % 2 != 0;
        }
    };

    private SuperChecker isOddLambda = val -> val % 2 != 0;

    @Test
    @Ignore
    public void check() {
        System.out.println(isOddAnonymous.check(123));
        System.out.println(isOddAnonymous.check(124));
        System.out.println(isOddLambda.check(123));
        System.out.println(isOddLambda.check(124));
    }

    @FunctionalInterface
    interface MathOperation {
        int operate(int x, int y);
    }

    private int calculator(int x, int y, MathOperation mathOperationLambda) {
        return mathOperationLambda.operate(x, y);
    }

    BiFunction<Integer, Integer, Integer> adder = (a, b) -> a + b;

    @Test
    public void calculatorTest() {
        calculator(1, 2, (a, b) -> a + b);
        calculator(1, 2, (a, b) -> a - b);
        calculator(1, 2, (a, b) -> a / b);
    }

    @FunctionalInterface
    public interface StateChangeListener {
        void onStateChange(String oldState, String newState);
    }

    public class StateOwner {

        private StateChangeListener stateChangeListener;
        private String currentStage = "bardzo stary stan";

        public void setStateChangeListener(StateChangeListener listener) {
            this.stateChangeListener = listener;
        }

        public void changeState(String newStage) {
            stateChangeListener.onStateChange(currentStage, newStage);
            currentStage = newStage;
        }

    }


    @Test
    @Ignore
    public void stateTest() {
        StateOwner stateOwner = new StateOwner();

        stateOwner.setStateChangeListener(new StateChangeListener() {
            public void onStateChange(String oldState, String newState) {
                System.out.println("Old state: " + oldState);
                System.out.println("New state: " + newState);
            }
        });
        stateOwner.changeState("nowy stan");


        stateOwner.setStateChangeListener(
                (oldState, newState) -> {
                    System.out.println("Old state: " + oldState);
                    System.out.println("New state: " + newState);
                }
        );

        stateOwner.changeState("następny nowy stan");


        stateOwner.setStateChangeListener(
                (oldState, newState) -> {
                    System.out.println("Old state new Lambda: " + oldState);
                    System.out.println("New state new lambda: " + newState);
                }
        );

        stateOwner.changeState("kolejny nowy stan");
    }

    @Test
    public void name() {
        Customer customer = new Customer();
        System.out.println();
    }
}
