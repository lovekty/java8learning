package me.tony.java8.streamapi;

import me.tony.java8.Base;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream API示例
 */
public class StreamExamples extends Base {

    static final int SIZE = 10_000;
    static List<Student> students = Arrays.asList(new Student("1001", "Bob", Student.Gender.MALE, 3.7f),
            new Student("1002", "Tom", Student.Gender.MALE, 4.2f), new Student("1003", "John", Student.Gender.MALE, 4.4f),
            new Student("1004", "Mary", Student.Gender.FEMAIL, 4.9f), new Student("1005", "Lily", Student.Gender.FEMAIL, 4.0f),
            new Student("1006", "Sam", Student.Gender.MALE, 5.0f), new Student("1007", "Jimmy", Student.Gender.MALE, 2.9f),
            new Student("1008", "Alex", Student.Gender.MALE, 3.3f), new Student("1009", "Lucy", Student.Gender.FEMAIL, 3.7f),
            new Student("1010", "Paul", Student.Gender.MALE, 3.2f), new Student("1011", "Bella", Student.Gender.FEMAIL, 3.4f),
            new Student("1012", "LiLei", Student.Gender.MALE, 3.4f), new Student("1013", "HanMeimei", Student.Gender.FEMAIL, 4.7f));
    static List<Integer> integers = IntStream.rangeClosed(1, SIZE - 2).collect(() -> new ArrayList<Integer>(SIZE), ArrayList::add, ArrayList::addAll);
    static List<Integer> unorderedIntegers = Stream.generate(new Random()::nextInt).limit(SIZE - 2).collect(Collectors.toList());
    static Comparator<Float> descFloatComparator = ((Comparator<Float>) Float::compare).reversed();

    static {
        integers.add(0, Integer.MIN_VALUE);
        integers.add(SIZE - 1, Integer.MAX_VALUE);
        unorderedIntegers.add(Integer.MIN_VALUE);
        unorderedIntegers.add(Integer.MAX_VALUE);
    }

    static void printStudents(List<Student> students) {
        students.forEach(student -> logger.info(student.toString()));
    }

    /**
     * String Array filter
     * 过滤String数组中的空值
     */
    @Test
    public void rebuildStringArray() {
        String[] orig = new String[]{"abc", "def", "ghi", "java", "f**k", "", null, "", "hello", null, "boring", "exciting"};
//        String[] mid = new String[orig.length];
//        int num = 0;
//        for (String str : orig) {
//            if (str != null && !str.isEmpty()) {
//                mid[num] = str;
//                num++;
//            }
//        }
//        String[] result = Arrays.copyOf(mid, num);
        String[] result = Arrays.stream(orig).filter(s -> s != null && !s.isEmpty()).toArray(String[]::new);

        logger.info("result length is:{}", result.length);
        Arrays.stream(result).forEach(logger::info);

    }

    /**
     * 取偶数
     */
    @Test
    public void pickEvenNumber() {
        long start = System.nanoTime();
//        List<Integer> evenNumbers = integers.stream().filter(Base::isEvenInteger).collect(Collectors.toList());
        List<Integer> evenNumbers = new ArrayList<>(integers.size());
        evenNumbers.addAll(integers.stream().filter(Base::isEvenInteger).collect(Collectors.toList()));
        long end = System.nanoTime();
        logger.info("Even Number size:{}, cost time:{}ms", evenNumbers.size(), TimeUnit.NANOSECONDS.toMillis(end - start));
    }

    /**
     * 将一个列表的整型数字乘2
     */
    @Test
    public void doubleIntegers() {
        List<Integer> doubleIntegers = new ArrayList<>(SIZE);
        long start = System.nanoTime();
        doubleIntegers.addAll(integers.stream().map(integer -> integer * 2).collect(Collectors.toList()));
//        doubleIntegers.addAll(integers.stream().map(integer -> {
//            try {
//                Thread.sleep(0, 10);
//            } catch (InterruptedException e) {
//                logger.debug("sleep error", e);
//            }
//            return integer * 2;
//        }).collect(Collectors.toList()));
        long end = System.nanoTime();
        logger.info("result size is:{} and cost time:{}ms", doubleIntegers.size(), TimeUnit.NANOSECONDS.toMillis(end - start));
    }

    /**
     * 用并行流将一个列表的整型数字乘2
     * 一开始会发现并行流效率反而不如串行流
     * 每次操作睡10纳秒模拟cpu密集型计算任务，并行流优势明显
     */
    @Test
    public void parallelDoubleIntegers() {
        List<Integer> doubleIntegers = new ArrayList<>(SIZE);
        long start = System.nanoTime();
        doubleIntegers.addAll(integers.parallelStream().map(integer -> integer * 2).collect(Collectors.toList()));
//        doubleIntegers.addAll(integers.parallelStream().map(integer -> {
//            try {
//                Thread.sleep(0, 10);
//            } catch (InterruptedException e) {
//                logger.debug("sleep error", e);
//            }
//            return integer * 2;
//        }).collect(Collectors.toList()));
        long end = System.nanoTime();
        logger.info("result size is:{} and cost time:{}ms", doubleIntegers.size(), TimeUnit.NANOSECONDS.toMillis(end - start));
    }

    /**
     * 并行流使用ForkJoinPool.commonPool，多个并行流之间会互相影响
     * 对于非计算密集型的并行流的任务，建议采用自定义的ForkJoinPool
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void customForkJoinPool() throws ExecutionException, InterruptedException {
        final List<Integer> doubleIntegers = new ArrayList<>(SIZE);
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        long start = System.nanoTime();
        forkJoinPool.submit(() -> doubleIntegers.addAll(integers.parallelStream().map(integer -> {
            try {
                Thread.sleep(0, 10);
            } catch (InterruptedException e) {
                logger.error("sleep error", e);
            }
            return integer * 2;
        }).collect(Collectors.toList()))).invoke();
        long end = System.nanoTime();
        logger.info("result size is:{} and cost time:{}ms", doubleIntegers.size(), TimeUnit.NANOSECONDS.toMillis(end - start));
    }

    /**
     * 利用Comparator排序
     */
    @Test
    public void sort() {
        List<Integer> orderedIntegers = new ArrayList<>(SIZE);
        long start = System.nanoTime();
        orderedIntegers.addAll(unorderedIntegers.parallelStream().sorted().collect(Collectors.toList()));
        long end = System.nanoTime();
        logger.info("result size is:{} and cost time:{}ms, first is:{}, last is:{}", orderedIntegers.size(), TimeUnit.NANOSECONDS.toMillis(end - start), orderedIntegers.get(0), orderedIntegers.get(orderedIntegers.size() - 1));
    }

    /**
     * 寻找一个很长的整型数字列表中的最大值，预先知道最大值为{@code Integer.MAX_VALUE}
     */
    @Test
    public void max() {
        long start = System.nanoTime();
        Integer max = unorderedIntegers.stream().max(Integer::compareTo).orElseGet(() -> null);
        long end = System.nanoTime();
        logger.info("max is:{} and cost time:{}ms", max, TimeUnit.NANOSECONDS.toMillis(end - start));
    }

    /**
     * 并行流查找最大值
     */
    @Test
    public void parallelMax() {
        long start = System.nanoTime();
        Integer max = unorderedIntegers.parallelStream().max(Integer::compareTo).orElseGet(() -> null);
        long end = System.nanoTime();
        logger.info("max is:{} and cost time:{}ms", max, TimeUnit.NANOSECONDS.toMillis(end - start));
    }

    /**
     * 集合转换
     * 注意用并行流转map的时候需要用concurrent
     */
    @Test
    public void studentsToMap() {
//        Map<String, Student> map = students.stream().collect(Collectors.toMap(student -> student.stuId, student -> student, (left, right) -> left));
        ConcurrentMap<String, Student> map = students.parallelStream().collect(Collectors.toConcurrentMap(student -> student.stuId, student -> student, (left, right) -> left));
        logger.info(String.valueOf(map.get("1008")));
    }

    /**
     * 排序
     */
    @Test
    public void sortByGpa() {
        List<Student> sorted = students.stream().sorted((a, b) -> Double.compare(a.getGpa(), b.getGpa())).collect(Collectors.toList());
        printStudents(sorted);
    }

    /**
     * 集合数据操作
     */
    @Test
    public void setTitle() {
        long start = System.nanoTime();
        students.stream().peek(student -> student.title = "fail")
                .filter(student -> student.getGpa() >= 3.0).peek(student -> student.title = "normal")
                .filter(student -> student.getGpa() >= 4.2).peek(student -> student.title = "elite")
                .count();
//        students.forEach(student -> {
//            if (student.getGpa() >= 4.2) {
//                student.title = "elite";
//            } else if (student.getGpa() >= 3.0) {
//                student.title = "normal";
//            } else {
//                student.title = "fail";
//            }
//        });
        long end = System.nanoTime();
        logger.info("set title cost time:{}ns", end - start);
        List<Student> result = students.stream().sorted((a, b) -> descFloatComparator.compare(a.getGpa(), b.getGpa())).collect(Collectors.toList());
        printStudents(result);
    }

    /**
     * 求平均数
     */
    @Test
    public void averageGpa() {
        long start = System.nanoTime();
        double average = students.stream().mapToDouble(Student::getGpa).average().orElse(-1.0); // 流
//        double average = students.parallelStream().mapToDouble(Student::getGpa).average().orElse(-1.0); // 并行流
//        double average = 0.0; // java7-
//        for (Student student : students) {
//            average += student.getGpa();
//        }
//        average /= students.size();
        long end = System.nanoTime();
        logger.info("set title cost time:{}ns", end - start);
        logger.info("average gpa is:{}", average);
    }

    @Test
    public void boysAverageGpa() {
        double average = students.stream().filter(student -> student.gender == Student.Gender.MALE).mapToDouble(Student::getGpa).reduce((a, b) -> (a + b) / 2).orElse(-1.0);
        logger.info("boys average gpa is:{}", average);
    }

}

class Student {
    String stuId;
    String name;
    Gender gender;
    String title;
    private float gpa;

    public Student(String stuId, String name, Gender gender, float gpa) {
        checkGpa(gpa);
        this.stuId = stuId;
        this.name = name;
        this.gender = gender;
        this.gpa = gpa;
    }

    private void checkGpa(double gpa) {
        if (gpa > 5.0 || gpa < 1.0) {
            throw new IllegalArgumentException("gpa:" + gpa + " is invalid, need between 1.0 to 5.0");
        }
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        checkGpa(gpa);
        this.gpa = gpa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(stuId, student.stuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stuId);
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", title='" + title + '\'' +
                ", gpa=" + gpa +
                '}';
    }

    enum Gender {
        MALE, FEMAIL
    }
}
