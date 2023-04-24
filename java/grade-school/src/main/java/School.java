import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class School {
    private final List<Student> roster = new ArrayList<>();

    public List<String> roster() {
        return roster.stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public void add(String name, int grade) {
        roster.add(new Student(name, grade));
        roster.sort(Student::compareTo);
    }

    public List<String> grade(int grade) {
        return roster.stream()
                .filter(student -> student.grade == grade)
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public static class Student implements Comparable<Student> {
        private final String name;
        private final int grade;

        public Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public int getGrade() {
            return grade;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return grade == student.grade && name.equals(student.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, grade);
        }

        @Override
        public int compareTo(Student o) {
            int dist = grade - o.getGrade();
            if (dist == 0) {
                return name.compareTo(o.getName());
            }
            return dist;
        }
    }

}