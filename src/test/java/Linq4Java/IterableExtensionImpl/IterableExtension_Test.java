/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import IterableHelper.GroupingConverter;
import IterableExtension.Grouping;
import java.util.HashMap;
import java.util.Map;
import IterableHelper.AssertExtension;
import Functional.Func2;
import Functional.Func1;
import IterableHelper.IterableConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import static IterableExtensionImpl.IterableUtils.createQuery;

/**
 *
 * @author aushakov
 */
public final class IterableExtension_Test {

    @Test public void aggregateWithAccumulator() {
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        Func2<String, Integer, String> accumulateFunc = new Func2<String, Integer, String>() {
            @Override
            public String func(String param1, Integer param2) {
                return param1 + param2;
            }
        };
        String result = createQuery(intList).aggregate("", accumulateFunc);
        Assert.assertEquals("123", result);
    }

    @Test public void aggregateWithResultSelector() {
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        Func2<String, Integer, String> accumulateFunc = new Func2<String, Integer, String>() {
            @Override
            public String func(String param1, Integer param2) {
                return param1 + param2;
            }
        };
        Func1<String, String> resultSelector = new Func1<String, String>() {
            @Override
            public String func(String param) {
                return new StringBuilder(param).reverse().toString();
            }
        };
        String result = createQuery(intList).aggregate("", accumulateFunc, resultSelector);
        Assert.assertEquals("321", result);
    }

    @Test public void all() {
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 0;
            }
        };
        Boolean result = createQuery(intList).all(predicate);
        Assert.assertTrue(result);
    }

    @Test public void any() {
        List<Integer> intList1 = new ArrayList<Integer>();
        intList1.add(1);
        List<Integer> intList2 = new ArrayList<Integer>();
        Boolean resultTrue = createQuery(intList1).any();
        Boolean resultFalse = createQuery(intList2).any();
        Assert.assertTrue(resultTrue);
        Assert.assertFalse(resultFalse);
    }

    @Test public void anyWithPredicate() {
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 2;
            }
        };
        Boolean result = createQuery(intList).any(predicate);
        Assert.assertTrue(result);
    }

    @Test public void concat() {
        List<Integer> intList1 = new ArrayList<Integer>();
        List<Integer> intList2 = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList1)
                .concat(intList2);
        intList1.add(1);
        intList1.add(2);
        intList2.add(-1);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 2, -1};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void distinct() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList)
                .distinct();
        intList.add(1);
        intList.add(2);
        intList.add(2);
        intList.add(0);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 2, 0};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void except() {
        List<Integer> intList1 = new ArrayList<Integer>();
        List<Integer> intList2 = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList1)
                .except(intList2);
        intList1.add(1);
        intList1.add(2);
        intList1.add(2);
        intList1.add(3);
        intList2.add(3);
        intList2.add(4);
        intList2.add(3);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 2};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void groupBy() {
        Func1<String, Integer> keySelector = new Func1<String, Integer>() {
            @Override
            public Integer func(String param) {
                return param.length();
            }
        };
        List<String> strList = new ArrayList<String>();
        Iterable<Grouping<Integer, String>> source = createQuery(strList).groupBy(keySelector);
        strList.add("bb");
        strList.add("a");
        strList.add("c");
        strList.add("ddd");
        strList.add("xx");
        String[] dest = GroupingConverter.toItemArray(source, String.class);
        String[] expected = {"bb", "xx", "a", "c", "ddd"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void groupByWithElementSelector() {
        Func1<String, Integer> keySelector = new Func1<String, Integer>() {
            @Override
            public Integer func(String param) {
                return param.length();
            }
        };
        Func1<String, Character> elementSelector = new Func1<String, Character>() {
            @Override
            public Character func(String param) {
                return param.charAt(0);
            }
        };
        List<String> strList = new ArrayList<String>();
        Iterable<Grouping<Integer, Character>> source = createQuery(strList).groupBy(keySelector, elementSelector);
        strList.add("bb");
        strList.add("a");
        strList.add("c");
        strList.add("ddd");
        strList.add("xx");
        Character[] dest = GroupingConverter.toItemArray(source, Character.class);
        Character[] expected = {'b', 'x', 'a', 'c', 'd'};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void groupByWithResultSelector() {
        Func1<String, Integer> keySelector = new Func1<String, Integer>() {
            @Override
            public Integer func(String param) {
                return param.length();
            }
        };
        Func2<Integer, Iterable<String>, String> resultSelector = new Func2<Integer, Iterable<String>, String>() {
            @Override
            public String func(Integer param1, Iterable<String> param2) {
                StringBuilder sb = new StringBuilder();
                sb.append(param1);
                sb.append("|");
                for(String str : param2) {
                    sb.append(str.charAt(0));
                }
                return sb.toString();
            }
        };
        List<String> strList = new ArrayList<String>();
        Iterable<String> source = createQuery(strList).groupBy(keySelector, resultSelector);
        strList.add("bb");
        strList.add("a");
        strList.add("c");
        strList.add("ddd");
        strList.add("xx");
        String[] dest = IterableConverter.toArray(source, String.class);
        String[] expected = {"2|bx", "1|ac", "3|d"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void groupByWithElementSelectorWithResultSelector() {
        Func1<String, Integer> keySelector = new Func1<String, Integer>() { public Integer func(String param) { return param.length(); }};
        Func1<String, Character> elementSelector = new Func1<String, Character>() {public Character func(String param) { return param.charAt(0); }};
        Func2<Integer, Iterable<Character>, String> resultSelector = new Func2<Integer, Iterable<Character>, String>() {
            @Override
            public String func(Integer param1, Iterable<Character> param2) {
                StringBuilder sb = new StringBuilder();
                sb.append(param1);
                sb.append("|");
                for(Character character : param2) {
                    sb.append(character);
                }
                return sb.toString();
            }
        };
        List<String> strList = new ArrayList<String>();
        Iterable<String> source = createQuery(strList).groupBy(keySelector, elementSelector, resultSelector);
        strList.add("bb");
        strList.add("a");
        strList.add("c");
        strList.add("ddd");
        strList.add("xx");
        String[] dest = IterableConverter.toArray(source, String.class);
        String[] expected = {"2|bx", "1|ac", "3|d"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void intersect() {
        List<Integer> intList1 = new ArrayList<Integer>();
        List<Integer> intList2 = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList1)
                .intersect(intList2);
        intList1.add(1);
        intList1.add(2);
        intList1.add(3);
        intList1.add(3);
        intList2.add(3);
        intList2.add(4);
        intList2.add(3);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {3};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void reverse() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList).reverse();
        intList.add(5);
        intList.add(4);
        intList.add(3);
        intList.add(2);
        intList.add(1);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 2, 3, 4, 5};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void select() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList)
                .select(new Func1<Integer, Integer>(){@Override public Integer func(Integer param) { return param * 2; } })
                .select(new Func1<Integer, Integer>(){@Override public Integer func(Integer param) { return param - 3; } });
        intList.add(1);
        intList.add(2);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {-1, 1};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectWithIndex() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList)
                .select(new Func2<Integer, Integer, Integer>(){ public Integer func(Integer param, Integer index) { return param * 2; } })
                .select(new Func2<Integer, Integer, Integer>(){ public Integer func(Integer param, Integer index) { return param + index; } });
        intList.add(1);
        intList.add(2);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {2, 5};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectMany() {
        Func1<Integer, Iterable<Integer>> collectionSelector = new Func1<Integer, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> func(Integer param) {
                List<Integer> collection = new ArrayList<Integer>();
                collection.add(param*3);
                collection.add(param*4);
                return collection;
            }
        };
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList).selectMany(collectionSelector);
        intList.add(1);
        intList.add(2);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {3, 4, 6, 8};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectManyWithIndex() {
        Func2<Integer, Integer, Iterable<Integer>> collectionSelector = new Func2<Integer, Integer, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> func(Integer param, Integer index) {
                List<Integer> collection = new ArrayList<Integer>();
                collection.add(index*3);
                collection.add(index*4);
                return collection;
            }
        };
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList).selectMany(collectionSelector);
        intList.add(1);
        intList.add(2);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {0, 0, 3, 4};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectManyWithResultSelector() {
        Func1<Integer, Iterable<Integer>> collectionSelector = new Func1<Integer, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> func(Integer param) {
                List<Integer> collection = new ArrayList<Integer>();
                collection.add(param*3);
                collection.add(param*4);
                return collection;
            }
        };
        Func2<Integer, Integer, String> resultSelector = new Func2<Integer, Integer, String>() {
            @Override
            public String func(Integer sourceItem, Integer collectionItem) {
                return sourceItem + "|" + collectionItem;
            }
        };
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<String> source = createQuery(intList).selectMany(collectionSelector, resultSelector);
        intList.add(1);
        intList.add(2);
        String[] dest = IterableConverter.toArray(source, String.class);
        String[] expected = {"1|3", "1|4", "2|6", "2|8"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void selectManyWithIndexWithResultSelector() {
        Func2<Integer, Integer, Iterable<Integer>> collectionSelector = new Func2<Integer, Integer, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> func(Integer param, Integer index) {
                List<Integer> collection = new ArrayList<Integer>();
                collection.add(index*3);
                collection.add(index*4);
                return collection;
            }
        };
        Func2<Integer, Integer, String> resultSelector = new Func2<Integer, Integer, String>() {
            @Override
            public String func(Integer sourceItem, Integer collectionItem) {
                return sourceItem + "|" + collectionItem;
            }
        };
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<String> source = createQuery(intList).selectMany(collectionSelector, resultSelector);
        intList.add(1);
        intList.add(2);
        String[] dest = IterableConverter.toArray(source, String.class);
        String[] expected = {"1|0", "1|0", "2|3", "2|4"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void skip() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList).skip(2);
        intList.add(1);
        intList.add(2);
        intList.add(3);
        intList.add(4);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {3, 4};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void skipWhile() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList)
                .skipWhile(new Func1<Integer, Boolean>(){ public Boolean func(Integer param) { return param != 2; }});
        intList.add(1);
        intList.add(2);
        intList.add(3);
        intList.add(4);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {2, 3, 4};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void skipWhileWithIndex() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList)
                .skipWhile(new Func2<Integer, Integer, Boolean>(){ public Boolean func(Integer param, Integer index) { return index != 2; }});
        intList.add(1);
        intList.add(2);
        intList.add(3);
        intList.add(4);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {3, 4};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void take() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList).take(2);
        intList.add(1);
        intList.add(2);
        intList.add(3);
        intList.add(4);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 2};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void takeWhile() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList)
                .takeWhile(new Func1<Integer, Boolean>(){ public Boolean func(Integer param) { return param != 3; }});
        intList.add(1);
        intList.add(2);
        intList.add(3);
        intList.add(4);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 2};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void takeWhileWithIndex() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList)
                .takeWhile(new Func2<Integer, Integer, Boolean>(){ public Boolean func(Integer param, Integer index) { return index != 2; }});
        intList.add(1);
        intList.add(2);
        intList.add(3);
        intList.add(4);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 2};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void toArray() {
        // wait for working version
    }

    @Test public void toList() {
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        List<Integer> dest = createQuery(intList).toList();
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(1);
        expected.add(2);
        AssertExtension.assertListEquals(expected, dest);
    }

    @Test public void toMap() {
        Func1<Integer, String> keySelector = new Func1<Integer, String>(){public String func(Integer param){return param.toString();}};
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        Map<String, Integer> dest = createQuery(intList).toMap(keySelector);
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("1", 1);
        expected.put("2", 2);
        AssertExtension.assertMapEquals(expected, dest);
    }

    @Test public void toMapWithElementSelector() {
        Func1<Integer, String> keySelector = new Func1<Integer, String>(){public String func(Integer param){return param.toString();}};
        Func1<Integer, Double> elementSelector = new Func1<Integer, Double>(){public Double func(Integer param){return param * 1.0;}};
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        Map<String, Double> dest = createQuery(intList).toMap(keySelector, elementSelector);
        Map<String, Double> expected = new HashMap<String, Double>();
        expected.put("1", 1.0);
        expected.put("2", 2.0);
        AssertExtension.assertMapEquals(expected, dest);
    }

    @Test public void union() {
        List<Integer> intList1 = new ArrayList<Integer>();
        List<Integer> intList2 = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList1)
                .union(intList2);
        intList1.add(1);
        intList1.add(2);
        intList1.add(1);
        intList1.add(3);
        intList2.add(1);
        intList2.add(1);
        intList2.add(1);
        intList2.add(5);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {1, 2, 3, 5};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void where() {
        List<Integer> intList = new ArrayList<Integer>();
        Iterable<Integer> source = createQuery(intList)
                .where(new Func1<Integer, Boolean>(){ public Boolean func(Integer param) { return param > 2; } })
                .select(new Func1<Integer, Integer>(){ public Integer func(Integer param) { return param - 3; } });
        intList.add(1);
        intList.add(2);
        intList.add(3);
        Integer[] dest = IterableConverter.toArray(source, Integer.class);
        Integer[] expected = {0};
        Assert.assertArrayEquals(expected, dest);
    }
}
