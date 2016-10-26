package me.tony.java8.streamapi;

import me.tony.java8.Base;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by tony on 2016/10/26.
 */
public class StreamExamples extends Base {

    static List<Student> students = Arrays.asList(new Student("1001", "Bob", 3.7f),
            new Student("1002", "Tom", 4.2f), new Student("1003", "John", 4.4f),
            new Student("1004", "Mary", 2.9f), new Student("1005", "Lily", 1.0f),
            new Student("1006", "Sam", 5.0f), new Student("1007", "Bob", 4.9f),
            new Student("1008", "Bob", 3.3f), new Student("1009", "Bob", 3.7f),
            new Student("1010", "Bob", 3.6f), new Student("1011", "Bob", 3.4f),
            new Student("1012", "Bob", 3.4f), new Student("1013", "Bob", 2.9f));

    static void printStudents(List<Student> students) {
        students.forEach(student -> logger.info(student.toString()));
    }

    /**
     * String Array filter
     */
    @Test
    public void rebuildStringArray() {
        String[] orig = new String[]{"abc", "def", "ghi", "java", "f**k", "", null, "", "hello", null, "boring"};
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

    @Test
    public void pickEvenNumber() {
        List<Integer> integers = IntStream.rangeClosed(1, 10000000).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
//        long start = System.currentTimeMillis();
//        List<Integer> evenNumbers = integers.stream().filter(Base::isEvenInteger).collect(Collectors.toList());
//        long end = System.currentTimeMillis();
        List<Integer> evenNumbers = new ArrayList<>(integers.size());
        long start = System.currentTimeMillis();
        evenNumbers.addAll(integers.stream().filter(Base::isEvenInteger).collect(Collectors.toList()));
        long end = System.currentTimeMillis();
        logger.info("Even Number size:{}, time cose:{}ms", evenNumbers.size(), end - start);
    }

    @Test
    public void setElite() {
        students.stream().peek(student -> student.title = "bad")
                .filter(student -> student.getGpa() >= 3.0f).peek(student -> student.title = "normal")
                .filter(student -> student.getGpa() >= 4.2f).peek(student -> student.title = "elite")
                .count();
        List<Student> result = students.stream().sorted((a, b) -> a.getGpa() - b.getGpa() > 0 ? -1 : 1).collect(Collectors.toList());
        printStudents(result);
    }

    @Test
    public void sortByGpa() {
        List<Student> sorted = students.stream().sorted((a, b) -> a.getGpa() - b.getGpa() > 0 ? 1 : -1).collect(Collectors.toList());
        printStudents(sorted);
    }

//    @Test
//    public void pickEvenNumberParallel() {
//        List<Integer> integers = IntStream.rangeClosed(1, 10000000).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
//        long start = System.currentTimeMillis();
//        List<Integer> evenNumbers = integers.parallelStream().filter(Base::isEvenInteger).collect(Collectors.toList());
//        long end = System.currentTimeMillis();
//        logger.info("Even Number size:{}, time cose:{}ms by parallel stream", evenNumbers.size(), end - start);
//    }
}

class Student {
    String stuId;
    String name;
    String title;
    private float gpa;

    public Student(String stuId, String name, float gpa) {
        checkGpa(gpa);
        this.stuId = stuId;
        this.name = name;
        this.gpa = gpa;
    }

    private void checkGpa(float gpa) {
        if (gpa > 5.0f || gpa < 1.0f) {
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
                ", title='" + title + '\'' +
                ", gpa=" + gpa +
                '}';
    }
}
