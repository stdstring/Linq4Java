/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import java.util.HashMap;
import java.util.Map;
import Functional.Action0;
import Functional.EqualsHelper;
import java.util.ArrayList;
import java.lang.reflect.Array;
import Functional.Tuple4;
import Functional.Func1;
import Functional.Func2;
import Functional.HashCodeHelper;
import Functional.Tuple2;
import IterableExtension.IterableExtension;
import IterableExtensionHelper.TrivialFuncs;
import IterableHelper.IterableConverter;
import IterableExtension.Grouping;
import IterableHelper.AssertExtension;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

import static IterableExtensionImpl.IterableUtils.createQuery;

/**
 *
 * @author A.Ushakov
 */
public final class MSDNSamples_Test {
    
    @Test public void aggregateWithDefaultSeed() {
        String sentence = "the quick brown fox jumps over the lazy dog";
        // Split the string into individual words.
        String[] words = sentence.split(" ");
        // Prepend each word to the beginning of the new sentence to reverse the word order.
        String reversed = createQuery(Arrays.asList(words))
                .aggregate(String.class, new Func2<String, String, String>(){@Override public String func(String acc, String next){ return acc == null ? next : next + " " + acc; }});
        Assert.assertEquals("dog lazy the over jumps fox brown quick the", reversed);
    }
    
    @Test public void aggregate() {
        List<Integer> ints = Arrays.asList(4, 8, 8, 3, 9, 0, 7, 8, 2);
        // Count the even numbers in the array, using a seed value of 0.
        int numEven = createQuery(ints)
                .aggregate(0, new Func2<Integer, Integer, Integer>(){@Override public Integer func(Integer total, Integer next){ return next % 2 == 0 ? total + 1 : total; }});
        Assert.assertEquals(6, numEven);
    }
    
    @Test public void aggregateWithResultSelector() {
        List<String> fruits = Arrays.asList("apple", "mango", "orange", "passionfruit", "grape");
        // Determine whether any string in the array is longer than "banana".
        String longestName = createQuery(fruits)
                .aggregate("banana",
                           new Func2<String, String, String>(){@Override public String func(String longest, String next){ return next.length() > longest.length() ? next : longest; }},
                           new Func1<String, String>(){@Override public String func(String fruit){ return fruit.toUpperCase(); }});
        Assert.assertEquals("PASSIONFRUIT", longestName);
    }
    
    @Test public void all() {
        List<PetWithIntegerAge> pets = Arrays.asList(new PetWithIntegerAge("Barley", 10), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 6));
        // Determine whether all pet names in the array start with 'B'.
        boolean allStartWithB = createQuery(pets)
                .all(new Func1<PetWithIntegerAge, Boolean>(){@Override public Boolean func(PetWithIntegerAge pet){ return pet.Name.startsWith("B"); }});
        Assert.assertFalse(allStartWithB);
    }
    
    @Test public void any() {
        List<Integer> numbers = Arrays.asList(1, 2);
        boolean hasElements = createQuery(numbers).any();
        Assert.assertTrue(hasElements);
    }
    
    @Test public void anyWithPredicate() {
        List<PetWithAgeVaccinationMark> pets = Arrays.asList(new PetWithAgeVaccinationMark("Barley", 8, true), new PetWithAgeVaccinationMark("Boots", 4, false), new PetWithAgeVaccinationMark("Whiskers", 1, false));
        // Determine whether any pets over age 1 are also unvaccinated.
        boolean unvaccinated = createQuery(pets)
                .any(new Func1<PetWithAgeVaccinationMark, Boolean>(){@Override public Boolean func(PetWithAgeVaccinationMark p){ return p.Age > 1 && !p.Vaccinated; }});
        Assert.assertTrue(unvaccinated);
    }
    
    @Test public void average() {
        List<Integer> grades = Arrays.asList(78, 92, 100, 37, 81);
        double average = (Double)createQuery(grades).average(Integer.class);
        Assert.assertEquals(77.6, average, 0.0001);
    }
    
    @Test public void average2() {
        List<Long> longs = Arrays.asList(null, 10007L, 37L, 399846234235L);
        double average = (Double)createQuery(longs).average(Long.class);
        Assert.assertEquals(133282081426.333, average, 0.001);
    }
    
    @Test public void averageWithSelector() {
        List<String> numbers = Arrays.asList("10007", "37", "299846234235");
        double average = (Double)createQuery(numbers)
                .average(new Func1<String, Long>(){@Override public Long func(String num){ return Long.parseLong(num); }}, Long.class);
        Assert.assertEquals(99948748093.0, average, 0.0001);
    }
    
    @Test public void averageWithSelector2() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        double average = (Double)createQuery(fruits)
                .average(new Func1<String, Integer>(){@Override public Integer func(String s){ return s.length(); }}, Integer.class);
        Assert.assertEquals(6.5, average, 0.0001);
    }
    
    @Test public void cast() {
        List fruits = new ArrayList();
        fruits.add("apple");
        fruits.add("mango");
        Iterable<String> query = IterableUtils.<String>createCastQuery(fruits).select(TrivialFuncs.<String>getTrivialSelector());
        String[] actual = IterableConverter.toArray(query, String.class);
        String[] expected = { "apple", "mango" };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void castWithException() {
        List fruits = new ArrayList();
        fruits.add("apple");
        fruits.add("mango");
        fruits.add(666);
        final Iterable<String> query = IterableUtils.<String>createCastQuery(fruits).select(TrivialFuncs.<String>getTrivialSelector());
        AssertExtension.assertThrows(new Action0(){@Override public void action(){ for(String fruit : query) {} }}, ClassCastException.class);
    }
    
    @Test public void concat() {
        List<PetWithIntegerAge> cats = Arrays.asList(new PetWithIntegerAge("Barley", 8), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 1));
        List<PetWithIntegerAge> dogs = Arrays.asList(new PetWithIntegerAge("Bounder", 3), new PetWithIntegerAge("Snoopy", 14), new PetWithIntegerAge("Fido", 9));
        Iterable<String> query = createQuery(cats)
                .select(new Func1<PetWithIntegerAge, String>(){@Override public String func(PetWithIntegerAge cat){ return cat.Name; }})
                .concat(createQuery(dogs).select(new Func1<PetWithIntegerAge, String>(){@Override public String func(PetWithIntegerAge dog){ return dog.Name; }}));
        String[] actual = IterableConverter.toArray(query, String.class);
        String[] expected = { "Barley", "Boots", "Whiskers", "Bounder", "Snoopy", "Fido" };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void contains() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        final String fruit = "mango";
        boolean hasMango = createQuery(fruits).contains(fruit);
        Assert.assertTrue(hasMango);
    }
    
    @Test public void count() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        int numberOfFruits = createQuery(fruits).count();
        Assert.assertEquals(6, numberOfFruits);
    }
    
    @Test public void countWithPredicate() {
         List<PetWithVaccinationMark> pets = Arrays.asList(new PetWithVaccinationMark("Barley", true), new PetWithVaccinationMark("Boots", false), new PetWithVaccinationMark("Whiskers", false));
         int numberUnvaccinated = createQuery(pets)
                 .count(new Func1<PetWithVaccinationMark, Boolean>(){@Override public Boolean func(PetWithVaccinationMark pet){ return !pet.Vaccinated; }});
         Assert.assertEquals(2, numberUnvaccinated);
    }
    
    @Test public void defaultIfEmpty() {
        List<PetWithIntegerAge> pets = Arrays.asList(new PetWithIntegerAge("Barley", 8), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 1));
        Iterable<String> query = createQuery(pets)
                .defaultIfEmpty(PetWithIntegerAge.class)
                .select(new Func1<PetWithIntegerAge, String>(){@Override public String func(PetWithIntegerAge pet){ return pet.Name; }});
        String[] actual = IterableConverter.toArray(query, String.class);
        String[] expected = { "Barley", "Boots", "Whiskers" };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void defaultIfEmpty2() {
        List<Integer> numbers = new ArrayList<Integer>();
        Iterable<Integer> query = createQuery(numbers).defaultIfEmpty(Integer.class);
        Integer[] actual = IterableConverter.toArray(query, Integer.class);
        Integer[] expected = { 0 };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void defaultIfEmptyWithDefaultValue() {
        PetWithIntegerAge defaultPet = new PetWithIntegerAge("Default Pet", 0);
        List<PetWithIntegerAge> pets = Arrays.asList(new PetWithIntegerAge("Barley", 8), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 1));
        Iterable<String> query = createQuery(pets)
                .defaultIfEmpty(defaultPet)
                .select(new Func1<PetWithIntegerAge, String>(){@Override public String func(PetWithIntegerAge pet){ return pet.Name; }});
        String[] actual = IterableConverter.toArray(query, String.class);
        String[] expected = { "Barley", "Boots", "Whiskers" };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void defaultIfEmptyWithDefaultValue2() {
        PetWithIntegerAge defaultPet = new PetWithIntegerAge("Default Pet", 0);
        List<PetWithIntegerAge> pets = new ArrayList<PetWithIntegerAge>();
        Iterable<String> query = createQuery(pets)
                .defaultIfEmpty(defaultPet)
                .select(new Func1<PetWithIntegerAge, String>(){@Override public String func(PetWithIntegerAge pet){ return pet.Name; }});
        String[] actual = IterableConverter.toArray(query, String.class);
        String[] expected = { "Default Pet" };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void distinct() {
        List<Integer> ages = Arrays.asList(21, 46, 46, 55, 17, 21, 55, 55);
        Iterable<Integer> distinctAges = createQuery(ages).distinct();
        Integer[] actual = IterableConverter.toArray(distinctAges, Integer.class);
        Integer[] expected = { 21, 46, 55, 17 };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void elementAt() {
        List<String> names = Arrays.asList("Hartono, Tommy", "Adams, Terry", "Andersen, Henriette Thaulow", "Hedlund, Magnus", "Ito, Shu");
        String name = createQuery(names).elementAt(names.size()-1);
        Assert.assertEquals("Ito, Shu", name);
    }
    
    @Test public void elementAtOrDefault() {
        List<String> names = Arrays.asList("Hartono, Tommy", "Adams, Terry", "Andersen, Henriette Thaulow", "Hedlund, Magnus", "Ito, Shu");
        String name = createQuery(names).elementAtOrDefault(String.class, 100);
        Assert.assertEquals(null, name);
    }
    
    @Test public void empty() {
        List<String> names1 = Arrays.asList("Hartono, Tommy");
        List<String> names2 = Arrays.asList("Adams, Terry", "Andersen, Henriette Thaulow", "Hedlund, Magnus", "Ito, Shu");
        List<String> names3 = Arrays.asList("Solanki, Ajay", "Hoeing, Helge", "Andersen, Henriette Thaulow", "Potra, Cristina", "Iallo, Lucio");
        List<List<String>> namesList = Arrays.asList(names1, names2, names3);
        // Only include arrays that have four or more elements
        Iterable<String> allNames = createQuery(namesList)
                .aggregate(IterableUtils.<String>empty(),
                           new Func2<IterableExtension<String>, List<String>, IterableExtension<String>>(){@Override public IterableExtension<String> func(IterableExtension<String> current, List<String>next){ return next.size() > 3 ? current.union(next) : current; }});
        String[] actual = IterableConverter.toArray(allNames, String.class);
        String[] expected = { "Adams, Terry", "Andersen, Henriette Thaulow", "Hedlund, Magnus", "Ito, Shu", "Solanki, Ajay", "Hoeing, Helge", "Potra, Cristina", "Iallo, Lucio" };
        Assert.assertArrayEquals(actual, expected);
    }

    @Test public void except() {
        List<Double> numbers1 = Arrays.asList(2.0, 2.1, 2.2, 2.3, 2.4, 2.5);
        List<Double> numbers2 = Arrays.asList(2.2);
        Iterable<Double> onlyInFirstSet = createQuery(numbers1).except(numbers2);
        Double[] actual = IterableConverter.toArray(onlyInFirstSet, Double.class);
        Double[] expected = { 2.0, 2.1, 2.3, 2.4, 2.5 };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void first() {
        List<Integer> numbers = Arrays.asList(9, 34, 65, 92, 87, 435, 3, 54, 83, 23, 87, 435, 67, 12, 19);
        int first = createQuery(numbers).first();
        Assert.assertEquals(9, first);
    }
    
    @Test public void firstWithPredicate() {
        List<Integer> numbers = Arrays.asList(9, 34, 65, 92, 87, 435, 3, 54, 83, 23, 87, 435, 67, 12, 19);
        int first = createQuery(numbers)
                .first(new Func1<Integer, Boolean>(){@Override public Boolean func(Integer number){ return number > 80; }});
        Assert.assertEquals(92, first);
    }
    
    @Test public void firstOrDefault() {
        List<Integer> numbers = new ArrayList<Integer>();
        int first = createQuery(numbers).firstOrDefault(Integer.class);
        Assert.assertEquals(0, first);
    }
    
    @Test public void firstOrDefaultWithPredicate() {
        List<String> names = Arrays.asList("Hartono, Tommy", "Adams, Terry", "Andersen, Henriette Thaulow", "Hedlund, Magnus", "Ito, Shu");
        String firstLongName = createQuery(names)
                .firstOrDefault(String.class, new Func1<String, Boolean>(){@Override public Boolean func(String name){ return name.length() > 20; }});
        Assert.assertEquals("Andersen, Henriette Thaulow", firstLongName);
        String firstVeryLongName = createQuery(names)
                .firstOrDefault(String.class, new Func1<String, Boolean>(){@Override public Boolean func(String name){ return name.length() > 30; }});
        Assert.assertEquals(null, firstVeryLongName);
    }
    
    @Test public void groupByWithElementSelector() {
        List<PetWithIntegerAge> pets = Arrays.asList(new PetWithIntegerAge("Barley", 8), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 1), new PetWithIntegerAge("Daisy", 4));
        // Group the pets using Age as the key value and selecting only the pet's Name for each value.
        Iterable<Grouping<Integer, String>> actual = createQuery(pets)
                .groupBy(new Func1<PetWithIntegerAge, Integer>(){@Override public Integer func(PetWithIntegerAge pet){ return pet.Age; }},
                         new Func1<PetWithIntegerAge, String>(){@Override public String func(PetWithIntegerAge pet){ return pet.Name; }});
        Grouping<Integer, String> group8 = new GroupingImpl(8, Arrays.asList("Barley"));
        Grouping<Integer, String> group4 = new GroupingImpl(4, Arrays.asList("Boots", "Daisy"));
        Grouping<Integer, String> group1 = new GroupingImpl(1, Arrays.asList("Whiskers"));
        List<Grouping<Integer, String>> expected = Arrays.asList(group8, group4, group1);
        AssertExtension.assertGroupEquals(expected, actual);
    }

    @Test public void groupByWithResultSelector() {
        List<PetWithDoubleAge> petsList = Arrays.asList(new PetWithDoubleAge("Barley", 8.3), new PetWithDoubleAge("Boots", 4.9), new PetWithDoubleAge("Whiskers", 1.5), new PetWithDoubleAge("Daisy", 4.3));
        // Group Pet objects by the Math.Floor of their age.
        // Then project an anonymous type from each group that consists of the key, the count of the group's elements, and the minimum and maximum age in the group.
        Iterable<Tuple4<Integer, Integer, Double, Double>> query = createQuery(petsList)
                .groupBy(new Func1<PetWithDoubleAge, Integer>(){@Override public Integer func(PetWithDoubleAge pet){ return (int)pet.Age; }},
                         new Func2<Integer, Iterable<PetWithDoubleAge>, Tuple4<Integer, Integer, Double, Double>>() {
                             @Override
                             public Tuple4<Integer, Integer, Double, Double> func(Integer key, Iterable<PetWithDoubleAge> pets) {
                                 int count = createQuery(pets).count();
                                 double min = createQuery(pets).min(new Func1<PetWithDoubleAge, Double>(){@Override public Double func(PetWithDoubleAge pet){ return pet.Age; }});
                                 double max = createQuery(pets).max(new Func1<PetWithDoubleAge, Double>(){@Override public Double func(PetWithDoubleAge pet){ return pet.Age; }});
                                 return new Tuple4<Integer, Integer, Double, Double>(key, count, min, max);
                             }
                         });
        Tuple4/*<Integer, Integer, Double, Double>*/[] actual = IterableConverter.toArray(query, (Tuple4<Integer, Integer, Double, Double>[])Array.newInstance(Tuple4.class, 0));
        Tuple4/*<Integer, Integer, Double, Double>*/[] expected = { new Tuple4<Integer, Integer, Double, Double>(8, 1, 8.3, 8.3), new Tuple4<Integer, Integer, Double, Double>(4, 2, 4.3, 4.9), new Tuple4<Integer, Integer, Double, Double>(1, 1, 1.5, 1.5) };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void groupByWithElementAndResultSelector() {
        List<PetWithDoubleAge> petsList = Arrays.asList(new PetWithDoubleAge("Barley", 8.3), new PetWithDoubleAge("Boots", 4.9), new PetWithDoubleAge("Whiskers", 1.5), new PetWithDoubleAge("Daisy", 4.3));
        // Group Pet.Age values by the Math.Floor of the age.
        // Then project an anonymous type from each group that consists of the key, the count of the group's elements, and the minimum and maximum age in the group.
        Iterable<Tuple4<Integer, Integer, Double, Double>> query = createQuery(petsList)
                .groupBy(new Func1<PetWithDoubleAge, Integer>(){@Override public Integer func(PetWithDoubleAge pet){ return (int)pet.Age; }},
                         new Func1<PetWithDoubleAge, Double>(){@Override public Double func(PetWithDoubleAge pet){ return pet.Age; }},
                         new Func2<Integer, Iterable<Double>, Tuple4<Integer, Integer, Double, Double>>() {
                             @Override
                             public Tuple4<Integer, Integer, Double, Double> func(Integer key, Iterable<Double> ages) {
                                 int count = createQuery(ages).count();
                                 double min = createQuery(ages).min(TrivialFuncs.<Double>getTrivialSelector());
                                 double max = createQuery(ages).max(TrivialFuncs.<Double>getTrivialSelector());
                                 return new Tuple4<Integer, Integer, Double, Double>(key, count, min, max);
                             }
                         });
        Tuple4/*<Integer, Integer, Double, Double>*/[] actual = IterableConverter.toArray(query, (Tuple4<Integer, Integer, Double, Double>[])Array.newInstance(Tuple4.class, 0));
        Tuple4/*<Integer, Integer, Double, Double>*/[] expected = { new Tuple4<Integer, Integer, Double, Double>(8, 1, 8.3, 8.3), new Tuple4<Integer, Integer, Double, Double>(4, 2, 4.3, 4.9), new Tuple4<Integer, Integer, Double, Double>(1, 1, 1.5, 1.5) };
        Assert.assertArrayEquals(expected, actual);
    }
    
    @Test public void groupJoin() {
        Person magnus = new Person("Hedlund, Magnus");
        Person terry = new Person("Adams, Terry");
        Person charlotte = new Person("Weiss, Charlotte");

        Pet barley = new Pet("Barley", terry);
        Pet boots = new Pet("Boots", terry);
        Pet whiskers = new Pet("Whiskers", charlotte);
        Pet daisy = new Pet("Daisy", magnus);

        List<Person> people = Arrays.asList(magnus, terry, charlotte);
        List<Pet> pets = Arrays.asList(barley, boots, whiskers, daisy);

        Func2<Person, Iterable<Pet>, String> resultSelector = new Func2<Person, Iterable<Pet>, String>() {
            @Override
            public String func(Person param1, Iterable<Pet> param2){
                StringBuilder result = new StringBuilder();
                result.append(param1.Name).append(":");
                for(Pet pet : param2) {
                    result.append(" ").append(pet.Name);
                }
                return result.toString();
            }
        };
        Iterable<String> query = createQuery(people)
                .groupJoin(pets, TrivialFuncs.<Person>getTrivialSelector(), new Func1<Pet, Person>(){@Override public Person func(Pet param){ return param.Owner; }}, resultSelector);
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = { "Hedlund, Magnus: Daisy", "Adams, Terry: Barley Boots", "Weiss, Charlotte: Whiskers" };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void intersect() {
        List<Integer> id1 = Arrays.asList(44, 26, 92, 30, 71, 38);
        List<Integer> id2 = Arrays.asList(39, 59, 83, 47, 26, 4, 30);
        Iterable<Integer> both = createQuery(id1).intersect(id2);
        Integer[] dest = IterableConverter.toArray(both, Integer.class);
        Integer[] expected = { 26, 30 };
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void join() {
        Person magnus = new Person("Hedlund, Magnus");
        Person terry = new Person("Adams, Terry");
        Person charlotte = new Person("Weiss, Charlotte");

        Pet barley = new Pet("Barley", terry);
        Pet boots = new Pet("Boots", terry);
        Pet whiskers = new Pet("Whiskers", charlotte);
        Pet daisy = new Pet("Daisy", magnus);

        List<Person> people = Arrays.asList(magnus, terry, charlotte);
        List<Pet> pets = Arrays.asList(barley, boots, whiskers, daisy);

        Iterable<String> query = createQuery(people)
                .join(pets,
                TrivialFuncs.<Person>getTrivialSelector(),
                new Func1<Pet, Person>(){@Override public Person func(Pet param){ return param.Owner; }},
                new Func2<Person, Pet, String>(){@Override public String func(Person param1, Pet param2){ return param1.Name + " - " + param2.Name; }});
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = { "Hedlund, Magnus - Daisy", "Adams, Terry - Barley", "Adams, Terry - Boots", "Weiss, Charlotte - Whiskers" };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void last() {
        List<Integer> numbers = Arrays.asList(9, 34, 65, 92, 87, 435, 3, 54, 83, 23, 87, 67, 12, 19);
        int last = createQuery(numbers).last();
        Assert.assertEquals(19, last);
    }
    
    @Test public void lastWithPredicate() {
        List<Integer> numbers = Arrays.asList(9, 34, 65, 92, 87, 435, 3, 54, 83, 23, 87, 67, 12, 19);
        int last = createQuery(numbers)
                .last(new Func1<Integer, Boolean>(){@Override public Boolean func(Integer num){ return num > 80; }});
        Assert.assertEquals(87, last);
    }
    
    @Test public void lastOrDefault() {
        List<String> fruits = new ArrayList<String>();
        String last = createQuery(fruits).lastOrDefault(String.class);
        Assert.assertEquals(null, last);
    }
    
    @Test public void lastOrDefaultWithPredicate() {
        List<Double> numbers = Arrays.asList(49.6, 52.3, 51.0, 49.4, 50.2, 48.3);
        double last50 = createQuery(numbers)
                .lastOrDefault(Double.class, new Func1<Double, Boolean>(){@Override public Boolean func(Double n){ return Math.round(n) == 50.0; }});
        Assert.assertEquals(50.2, last50, 0.0);
        double last40 = createQuery(numbers)
                .lastOrDefault(Double.class, new Func1<Double, Boolean>(){@Override public Boolean func(Double n){ return Math.round(n) == 40.0; }});
        Assert.assertEquals(0.0, last40, 0.0);
    }
    
    @Test public void longCount() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        long count = createQuery(fruits).longCount();
        Assert.assertEquals(6, count);
    }
    
    @Test public void longCountWithPredicate() {
        List<PetWithIntegerAge> pets = Arrays.asList(new PetWithIntegerAge("Barley", 8), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 1));
        final int Age = 3;
        long count = createQuery(pets)
                .longCount(new Func1<PetWithIntegerAge, Boolean>(){@Override public Boolean func(PetWithIntegerAge pet){ return pet.Age > Age; }});
        Assert.assertEquals(2, count);
    }
    
    @Test public void max() {
        List<Long> longs = Arrays.asList(4294967296L, 466855135L, 81125L);
        long maxValue = createQuery(longs).max();
        Assert.assertEquals(4294967296L, maxValue);
    }
    
    @Test public void max2() {
        List<Double> doubles = Arrays.asList(null, 1.5E+104, 9E+103, -2E+103);
        double maxValue = createQuery(doubles).max();
        Assert.assertEquals(1.5E+104, maxValue, 0.0);
    }
    
    @Test public void max3() {
        List<ComparablePet> pets = Arrays.asList(new ComparablePet("Barley", 8), new ComparablePet("Boots", 4), new ComparablePet("Whiskers", 1));
        ComparablePet maxValue = createQuery(pets).max();
        Assert.assertEquals("Barley", maxValue.Name);
    }
    
    @Test public void maxWithSelector() {
        List<PetWithIntegerAge> pets = Arrays.asList(new PetWithIntegerAge("Barley", 8), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 1));
        int maxValue = createQuery(pets)
                .max(new Func1<PetWithIntegerAge, Integer>(){@Override public Integer func(PetWithIntegerAge pet){ return pet.Age + pet.Name.length(); }});
        Assert.assertEquals(14, maxValue);
    }
    
    @Test public void min() {
        List<Double> doubles = Arrays.asList(1.5E+104, 9E+103, -2E+103);
        double minValue = createQuery(doubles).min();
        Assert.assertEquals(-2E+103, minValue, 0.0);
    }
    
    @Test public void min2() {
        List<Integer> grades = Arrays.asList(78, 92, null, 99, 37, 81);
        int minValue = createQuery(grades).min();
        Assert.assertEquals(37, minValue);
    }
    
    @Test public void min3() {
        List<ComparableByAgePet> pets = Arrays.asList(new ComparableByAgePet("Barley", 8), new ComparableByAgePet("Boots", 4), new ComparableByAgePet("Whiskers", 1));
        ComparableByAgePet minValue = createQuery(pets).min();
        Assert.assertEquals("Whiskers", minValue.Name);
    }
    
    @Test public void minWithSelector() {
        List<PetWithIntegerAge> pets = Arrays.asList(new PetWithIntegerAge("Barley", 8), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 1));
        int minValue = createQuery(pets)
                .min(new Func1<PetWithIntegerAge, Integer>(){@Override public Integer func(PetWithIntegerAge pet){ return pet.Age; }});
        Assert.assertEquals(1, minValue);
    }
    
    @Test public void ofType() {
        List fruits = new ArrayList(4);
        fruits.add("Mango");
        fruits.add("Orange");
        fruits.add("Apple");
        fruits.add(3.0);
        fruits.add("Banana");        
        Iterable<String> query1 = IterableUtils.<String>createOfTypeQuery(fruits, String.class);
        String[] dest1 = IterableConverter.toArray(query1, String.class);
        String[] expected1 = { "Mango", "Orange", "Apple", "Banana" };
        Assert.assertArrayEquals(expected1, dest1);
        Iterable<String> query2 = IterableUtils.<String>createOfTypeQuery(fruits, String.class)
                .where(new Func1<String, Boolean>(){@Override public Boolean func(String fruit){ return fruit.toLowerCase().contains("n"); }});
        String[] dest2 = IterableConverter.toArray(query2, String.class);
        String[] expected2 = { "Mango", "Orange", "Banana" };
        Assert.assertArrayEquals(expected2, dest2);
    }
    
    @Test public void orderBy() {
        List<PetWithIntegerAge> pets = Arrays.asList(new PetWithIntegerAge("Barley", 8), new PetWithIntegerAge("Boots", 4), new PetWithIntegerAge("Whiskers", 1));
        Iterable<String> query = createQuery(pets)
                .orderBy(new Func1<PetWithIntegerAge, Integer>(){@Override public Integer func(PetWithIntegerAge pet){ return pet.Age; }})
                .select(new Func1<PetWithIntegerAge, String>(){@Override public String func(PetWithIntegerAge pet){ return pet.Name + " - " + pet.Age; }});
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = { "Whiskers - 1", "Boots - 4", "Barley - 8" };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void orderByDescending() {
        List<BigDecimal> decimals = Arrays.asList(new BigDecimal("6.2"), new BigDecimal("8.3"), new BigDecimal("0.5"), new BigDecimal("1.3"), new BigDecimal("6.3"), new BigDecimal("9.7"));
        Iterable<BigDecimal> query = createQuery(decimals)
                .orderByDescending(TrivialFuncs.<BigDecimal>getTrivialSelector(), new SpecialBigDecimalComparator());
        BigDecimal[] dest = IterableConverter.toArray(query, BigDecimal.class);
        BigDecimal[] expected = { new BigDecimal("9.7"), new BigDecimal("0.5"), new BigDecimal("8.3"), new BigDecimal("6.3"), new BigDecimal("1.3"), new BigDecimal("6.2") };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void range() {
        Iterable<Integer> squares = IterableUtils.range(1, 10)
                .select(new Func1<Integer, Integer>(){@Override public Integer func(Integer x){ return x*x; }});
        Integer[] dest = IterableConverter.toArray(squares, Integer.class);
        Integer[] expected = { 1, 4, 9, 16, 25, 36, 49, 64, 81, 100 };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void repeat() {
        Iterable<String> strings = IterableUtils.repeat("I like programming.", 15);
        String[] dest = IterableConverter.toArray(strings, String.class);
        final int size = 15;
        final String value = "I like programming.";
        String[] expected = Collections.nCopies(size, value).toArray(new String[0]);
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void reverse() {
        List<Character> apple = Arrays.asList('a', 'p', 'p', 'l', 'e');
        Iterable<Character> reversed = createQuery(apple).reverse();
        Character[] dest = IterableConverter.toArray(reversed, Character.class);
        Character[] expected = { 'e', 'l', 'p', 'p', 'a' };
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void select() {
        Iterable<Integer> source = IterableUtils.range(1, 10)
                .select(new Func1<Integer, Integer>(){@Override public Integer func(Integer param){ return param*param; }});
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 4, 9, 16, 25, 36, 49, 64, 81, 100};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectWithIndex() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        IterableExtension<Tuple2<Integer, String>> query = createQuery(fruits)
                .select(new Func2<String, Integer, Tuple2<Integer, String>>(){@Override public Tuple2<Integer, String> func(String param, Integer index){ return new Tuple2<Integer, String>(index, param.substring(0, index)); }});
        Iterable<String> result = query
                .select(new Func1<Tuple2<Integer, String>, String>(){@Override public String func(Tuple2<Integer, String> param){ return param.getItem1() + ", " + param.getItem2(); }});
        String[] dest = IterableConverter.toArray(result, String.class);
        String[] expected = {"0, ", "1, b", "2, ma", "3, ora", "4, pass", "5, grape"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectMany() {
        List<PetOwner> petOwners = Arrays.asList(new PetOwner("Higa, Sidney", Arrays.asList( "Scruffy", "Sam" )),
                new PetOwner("Ashkenazi, Ronen", Arrays.asList("Walker", "Sugar")),
                new PetOwner("Price, Vernette", Arrays.asList("Scratches", "Diesel")));
        Iterable<String> query = createQuery(petOwners).selectMany(new Func1<PetOwner, Iterable<String>>(){@Override public Iterable<String> func(PetOwner param){ return param.Pets; }});
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = { "Scruffy", "Sam", "Walker", "Sugar", "Scratches", "Diesel" };
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectManyWithIndex() {
        List<PetOwner> petOwners = Arrays.asList(new PetOwner("Higa, Sidney", Arrays.asList( "Scruffy", "Sam" )),
                new PetOwner("Ashkenazi, Ronen", Arrays.asList("Walker", "Sugar")),
                new PetOwner("Price, Vernette", Arrays.asList("Scratches", "Diesel")),
                new PetOwner("Hines, Patrick", Arrays.asList("Dusty")));
        Iterable<String> query = createQuery(petOwners)
                .selectMany(new Func2<PetOwner, Integer, Iterable<String>>() {@Override public Iterable<String> func(PetOwner petOwner, final Integer index){ return createQuery(petOwner.Pets).select(new Func1<String, String>(){@Override public String func(String pet){ return index + pet; }}); }});
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = { "0Scruffy", "0Sam", "1Walker", "1Sugar", "2Scratches", "2Diesel", "3Dusty" };
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectManyWithResultSelector() {
        List<PetOwner> petOwners = Arrays.asList(new PetOwner("Higa", Arrays.asList( "Scruffy", "Sam" )),
                new PetOwner("Ashkenazi", Arrays.asList("Walker", "Sugar")),
                new PetOwner("Price", Arrays.asList("Scratches", "Diesel")),
                new PetOwner("Hines", Arrays.asList("Dusty")));
        Iterable<String> query = createQuery(petOwners)
                .selectMany(new Func1<PetOwner, Iterable<String>>(){@Override public Iterable<String> func(PetOwner param){ return param.Pets; }} , new Func2<PetOwner, String, Tuple2<PetOwner, String>>(){@Override public Tuple2<PetOwner, String> func(PetOwner param1, String param2){ return new Tuple2<PetOwner, String>(param1, param2); }})
                .where(new Func1<Tuple2<PetOwner, String>, Boolean>(){@Override public Boolean func(Tuple2<PetOwner, String> param){ return param.getItem2().startsWith("S"); }})
                .select(new Func1<Tuple2<PetOwner, String>, String>(){@Override public String func(Tuple2<PetOwner, String> param){ return param.getItem1().Name+","+param.getItem2(); }});
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = { "Higa,Scruffy", "Higa,Sam", "Ashkenazi,Sugar", "Price,Scratches" };
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void sequenceEqual() {
        PetWithIntegerAge pet1 = new PetWithIntegerAge("Turbo", 2);
        PetWithIntegerAge pet2 = new PetWithIntegerAge("Peanut", 8);
        List<PetWithIntegerAge> pets1 = Arrays.asList(pet1, pet2);
        List<PetWithIntegerAge> pets2 = Arrays.asList(pet1, pet2);
        List<PetWithIntegerAge> pets3 = Arrays.asList(new PetWithIntegerAge("Turbo", 2), new PetWithIntegerAge("Peanut", 8));
        Assert.assertTrue(createQuery(pets1).sequenceEqual(pets2));
        Assert.assertFalse(createQuery(pets1).sequenceEqual(pets3));
    }
    
    @Test public void single() {
        List<String> fruits = Arrays.asList("orange");
        String fruit = createQuery(fruits).single();
        Assert.assertEquals("orange", fruit);
    }
    
    @Test public void single2() {
        List<String> fruits = Arrays.asList("orange", "apple");
        String fruit = null;
        try {
            fruit = createQuery(fruits).single();
            Assert.fail();
        }
        catch(IllegalArgumentException e) {            
        }
        Assert.assertEquals(null, fruit);
    }
    
    @Test public void singleWithPredicate() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        String fruit = createQuery(fruits)
                .single(new Func1<String, Boolean>(){@Override public Boolean func(String fruit){ return fruit.length() > 10; }});
        Assert.assertEquals("passionfruit", fruit);
    }
    
    @Test public void singleWithPredicate2() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        String fruit = null;
        try {
            fruit = createQuery(fruits)
                    .single(new Func1<String, Boolean>(){@Override public Boolean func(String fruit){ return fruit.length() > 15; }});
            Assert.fail();
        }
        catch(IllegalArgumentException e) {            
        }
        Assert.assertEquals(null, fruit);
    }
    
    @Test public void singleOrDefault() {
        List<String> fruits = Arrays.asList("orange");
        String fruit = createQuery(fruits).singleOrDefault(String.class);
        Assert.assertEquals("orange", fruit);
    }
    
    @Test public void singleOrDefault2() {
        List<String> fruits = new ArrayList<String>();
        String fruit = createQuery(fruits).singleOrDefault(String.class);
        Assert.assertEquals(null, fruit);
    }
    
    @Test public void singleOrDefault3() {
        List<String> fruits = Arrays.asList("orange", "apple");
        String fruit = null;
        try {
            fruit = createQuery(fruits).singleOrDefault(String.class);
            Assert.fail();
        }
        catch(IllegalArgumentException e) {            
        }
        Assert.assertEquals(null, fruit);
    }
    
    @Test public void singleOrDefaultWithPredicate() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        String fruit = createQuery(fruits)
                .singleOrDefault(String.class, new Func1<String, Boolean>(){@Override public Boolean func(String fruit){ return fruit.length() > 10; }});
        Assert.assertEquals("passionfruit", fruit);
    }
    
    @Test public void singleOrDefaultWithPredicate2() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        String fruit = createQuery(fruits)
                .singleOrDefault(String.class, new Func1<String, Boolean>(){@Override public Boolean func(String fruit){ return fruit.length() > 15; }});
        Assert.assertEquals(null, fruit);
    }

    @Test public void skip() {
        List<Integer> grades = Arrays.asList(59, 82, 70, 56, 92, 98, 85);
        Iterable<Integer> lowerGrades = createQuery(grades)
                .orderByDescending(TrivialFuncs.<Integer>getTrivialSelector())
                .skip(3);
        Integer[] dest = IterableConverter.toArray(lowerGrades, Integer.class);
        Integer[] expected = { 82, 70, 59, 56 };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void skipWhile() {
        List<Integer> grades = Arrays.asList(59, 82, 70, 56, 92, 98, 85);
        Iterable<Integer> lowerGrades = createQuery(grades)
                .orderByDescending(TrivialFuncs.<Integer>getTrivialSelector())
                .skipWhile(new Func1<Integer, Boolean>(){@Override public Boolean func(Integer grade){ return grade >= 80; }});
        Integer[] dest = IterableConverter.toArray(lowerGrades, Integer.class);
        Integer[] expected = { 70, 59, 56 };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void skipWhileWithIndex() {
        List<Integer> amounts = Arrays.asList(5000, 2500, 9000, 8000, 6500, 4000, 1500, 5500);
        Iterable<Integer> query = createQuery(amounts)
                .skipWhile(new Func2<Integer, Integer, Boolean>(){@Override public Boolean func(Integer param, Integer index){ return param > index * 1000; }});
        Integer[] dest = IterableConverter.toArray(query, Integer.class);
        Integer[] expected = { 4000, 1500, 5500 };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void sum() {
        List<Float> numbers = Arrays.asList(43.68F, 1.25F, 583.7F, 6.5F);
        float sum = (Float)createQuery(numbers).sum(Float.class);
        Assert.assertEquals(635.13, sum, 0.00001);
    }
    
    @Test public void sum2() {
        List<Float> points = Arrays.asList(null, 0F, 92.83F, null, 100.0F, 37.46F, 81.1F);
        float sum = (Float)createQuery(points).sum(Float.class);
        Assert.assertEquals(311.39, sum, 0.0001);
    }
    
    @Test public void sumWithSelector() {
        List<Package> packages = Arrays.asList(new Package("Coho Vineyard", 25.2), new Package("Lucerne Publishing", 18.7), new Package("Wingtip Toys", 6.0), new Package("Adventure Works", 33.8));
        double totalWeight = (Double)createQuery(packages)
                .sum(new Func1<Package, Double>(){@Override public Double func(Package pkg){ return pkg.Weight; }}, Double.class);
        Assert.assertEquals(83.7, totalWeight, 0.00001);
    }

    @Test public void take() {
        List<Integer> grades = Arrays.asList(59, 82, 70, 56, 92, 98, 85);
        Iterable<Integer> topThreeGrades = createQuery(grades)
                .orderByDescending(TrivialFuncs.<Integer>getTrivialSelector())
                .take(3);
        Integer[] dest = IterableConverter.toArray(topThreeGrades, Integer.class);
        Integer[] expected = { 98, 92, 85 };
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void takeWhile() {
        List<String> fruits = Arrays.asList("apple", "banana", "mango", "orange", "passionfruit", "grape");
        Iterable<String> query = createQuery(fruits)
                .takeWhile(new Func1<String, Boolean>(){@Override public Boolean func(String param){ return param.compareToIgnoreCase("orange") != 0; }});
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = { "apple", "banana", "mango" };
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void takeWhileWithIndex() {
        List<String> fruits = Arrays.asList("apple", "passionfruit", "banana", "mango", "orange", "blueberry", "grape", "strawberry");
        Iterable<String> query = createQuery(fruits)
                .takeWhile(new Func2<String, Integer, Boolean>(){@Override public Boolean func(String param, Integer index){ return param.length() >= index; }});
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = { "apple", "passionfruit", "banana", "mango", "orange", "blueberry" };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void thenBy() {
        List<String> fruits = Arrays.asList("grape", "passionfruit", "banana", "mango", "orange", "raspberry", "apple", "blueberry");
        Iterable<String> query = createQuery(fruits)
                .orderBy(new Func1<String, Integer>(){@Override public Integer func(String fruit){ return fruit.length(); }})
                .thenBy(TrivialFuncs.<String>getTrivialSelector());
        String[] dest = IterableConverter.toArray(query, String.class);        
        String[] expected = { "apple", "grape", "mango", "banana", "orange", "blueberry", "raspberry", "passionfruit" };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void thenByDescending() {
        List<String> fruits = Arrays.asList("apPLe", "baNanA", "apple", "APple", "orange", "BAnana", "ORANGE", "apPLE");
        // Sort the strings first ascending by their length and then descending using a case insensitive comparer.
        Iterable<String> query = createQuery(fruits)
                .orderBy(new Func1<String, Integer>(){@Override public Integer func(String fruit){ return fruit.length(); }})
                .thenByDescending(TrivialFuncs.<String>getTrivialSelector(), String.CASE_INSENSITIVE_ORDER);
        String[] dest = IterableConverter.toArray(query, String.class);        
        String[] expected = { "apPLe", "apple", "APple", "apPLE", "orange", "ORANGE", "baNanA", "BAnana" };
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void toArray() {
        List<Package> packages = Arrays.asList(new Package("Coho Vineyard", 25.2), new Package("Lucerne Publishing", 18.7), new Package("Wingtip Toys", 6.0), new Package("Adventure Works", 33.8));
        String[] companies = createQuery(packages)
                .select(new Func1<Package, String>(){@Override public String func(Package pkg){ return pkg.Company; }})
                .toArray(String.class);
        String[] expected = {"Coho Vineyard", "Lucerne Publishing", "Wingtip Toys", "Adventure Works"};
        Assert.assertArrayEquals(expected, companies);
    }
    
    @Test public void toMap() {
        List<PackageWithTrackNumber> packages = Arrays.asList(new PackageWithTrackNumber("Coho Vineyard", 25.2, 89453312L),
                new PackageWithTrackNumber("Lucerne Publishing", 18.7, 89112755L),
                new PackageWithTrackNumber("Wingtip Toys", 6.0, 299456122L),
                new PackageWithTrackNumber("Adventure Works", 33.8, 4665518773L));
        Map<Long, PackageWithTrackNumber> dictionary = createQuery(packages)
                .toMap(new Func1<PackageWithTrackNumber, Long>(){@Override public Long func(PackageWithTrackNumber p){ return p.TrackingNumber; }});
        Map<Long, PackageWithTrackNumber> expected = new HashMap<Long, PackageWithTrackNumber>();
        expected.put(89453312L, new PackageWithTrackNumber("Coho Vineyard", 25.2, 89453312L));
        expected.put(89112755L, new PackageWithTrackNumber("Lucerne Publishing", 18.7, 89112755L));
        expected.put(299456122L, new PackageWithTrackNumber("Wingtip Toys", 6.0, 299456122L));
        expected.put(4665518773L, new PackageWithTrackNumber("Adventure Works", 33.8, 4665518773L));
        AssertExtension.assertMapEquals(expected, dictionary);
    }
    
    @Test public void toList() {
        List<String> fruits = Arrays.asList("apple", "passionfruit", "banana", "mango", "orange", "blueberry", "grape", "strawberry");
        List<Integer> lengths = createQuery(fruits)
                .select(new Func1<String, Integer>(){@Override public Integer func(String fruit){ return fruit.length(); }})
                .toList();
        List<Integer> expected = Arrays.asList(5, 12, 6, 5, 6, 9, 5, 10);
        AssertExtension.assertListEquals(expected, lengths);
    }

    @Test public void union() {
        List<Integer> ints1 = Arrays.asList( 5, 3, 9, 7, 5, 9, 3, 7 );
        List<Integer> ints2 = Arrays.asList( 8, 3, 6, 4, 4, 9, 1, 0 );
        Iterable<Integer> union = createQuery(ints1).union(ints2);
        Integer[] dest = IterableConverter.toArray(union, Integer.class);
        Integer[] expected = { 5, 3, 9, 7, 8, 6, 4, 1, 0 };
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void where() {
        List<String> fruits = Arrays.asList("apple", "passionfruit", "banana", "mango", "orange", "blueberry", "grape", "strawberry");
        Iterable<String> query = createQuery(fruits)
                .where(new Func1<String, Boolean>(){@Override public Boolean func(String param){ return param.length() < 6; }});
        String[] dest = IterableConverter.toArray(query, String.class);
        String[] expected = {"apple", "mango", "grape"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void whereWithIndex() {
        List<Integer> numbers = Arrays.asList(0, 30, 20, 15, 90, 85, 40, 75 );
        Iterable<Integer> query = createQuery(numbers)
                .where(new Func2<Integer, Integer, Boolean>(){@Override public Boolean func(Integer param, Integer index){ return param <= index * 10; }});
        Integer[] dest = IterableConverter.toArray(query, Integer.class);
        Integer[] expected = { 0, 20, 15, 40 };
        Assert.assertArrayEquals(expected, dest);
    }

    private static class Person {
        public Person(String name) {
            Name = name;
        }
        
        public final String Name;
    }
    
    private static class Pet {
        public Pet(String name, Person owner) {
            Name = name;
            Owner = owner;
        }
        
        public final String Name;
        public final Person Owner;
    }
    
    private static class PetWithIntegerAge {
        public PetWithIntegerAge(String name, int age) {
            Name = name;
            Age = age;
        }
        
        public final String Name;
        public final int Age;
    }
    
    private static class PetWithDoubleAge {
        public PetWithDoubleAge(String name, double age) {
            Name = name;
            Age = age;
        }
        
        public final String Name;
        public final double Age;
    }
    
    private static class PetWithVaccinationMark {
        public PetWithVaccinationMark(String name, boolean vaccinated) {
            Name = name;
            Vaccinated = vaccinated;
        }
        
        public final String Name;
        public final boolean Vaccinated;
    }
    
    private static class PetWithAgeVaccinationMark {
        public PetWithAgeVaccinationMark(String name, int age, boolean vaccinated) {
            Name = name;
            Age = age;
            Vaccinated = vaccinated;
        }
        
        public final String Name;
        public final int Age;
        public final boolean Vaccinated;
    }
    
    private static class ComparablePet implements Comparable<ComparablePet> {
        public ComparablePet(String name, int age) {
            Name = name;
            Age = age;
        }
        
        public final String Name;
        public final int Age;

        @Override
        public int compareTo(ComparablePet other) {
            int sumOther = other.Age + other.Name.length();
            int sumThis = this.Age + this.Name.length();
            if (sumOther > sumThis)
                return -1;
            else if (sumOther == sumThis)
                return 0;
            else
                return 1;
        }
    }
    
    private static class ComparableByAgePet implements Comparable<ComparableByAgePet> {
        public ComparableByAgePet(String name, int age) {
            Name = name;
            Age = age;
        }
        
        public final String Name;
        public final int Age;

        @Override
        public int compareTo(ComparableByAgePet other) {
            if (other.Age > this.Age)
                return -1;
            else if (other.Age == this.Age)
                return 0;
            else
                return 1;

        }
    }
    
    private static class PetOwner {
        public PetOwner(String name, List<String> pets) {
            Name = name;
            Pets = pets;
        }
        
        public final String Name;
        public final List<String> Pets;
    }
    
    private static class SpecialBigDecimalComparator implements Comparator<BigDecimal> {
        @Override
        public int compare(BigDecimal d1, BigDecimal d2) {
            // Get the fractional part of the first number.
            int floor1 = d1.intValue();
            BigDecimal fractional1 = (floor1 == 0 ? d1 : d1.subtract(new BigDecimal(floor1)));
            // Get the fractional part of the second number.
            int floor2 = d2.intValue();
            BigDecimal fractional2 = (floor2 == 0 ? d2 : d2.subtract(new BigDecimal(floor2)));

            int fractionalComparision = fractional1.compareTo(fractional2);
            if (fractionalComparision == 0)
                return d1.compareTo(d2);
            else if (fractionalComparision > 0)
                return 1;
            else
                return -1;
        }
    }
    
    private static class Package {
        public Package(String company, double weight) {
            Company = company;
            Weight = weight;
        }
        
        public final String Company;
        public final double Weight;
    }
    
    private static class PackageWithTrackNumber {
        public PackageWithTrackNumber(String company, double weight, long trackingNumber) {
            Company = company;
            Weight = weight;
            TrackingNumber = trackingNumber;
        }
        
        @Override
        public boolean equals(Object other) {
            if(other == null) return false;
            return (other instanceof PackageWithTrackNumber) ? equals((PackageWithTrackNumber)other) : false;
        }
        
        public boolean equals(PackageWithTrackNumber other) {
            if(other == null) return false;
            return EqualsHelper.equals(Company, other.Company) && (Weight == other.Weight) && (TrackingNumber == other.TrackingNumber);
        }

        @Override
        public int hashCode() {
            return HashCodeHelper.hashCode(Company, Weight, TrackingNumber);
        }
        
        public final String Company;
        public final double Weight;
        public final long TrackingNumber;
    }
}
