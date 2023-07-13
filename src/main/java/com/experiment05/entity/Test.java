package com.experiment05.entity;
import com.experiment05.entity.College;
import com.experiment05.entity.Student;
import com.experiment05.entity.Teacher;
import resource.DatabaseUtils;


import java.util.List;
import java.util.Optional;
public class Test {
    public static void main(String[] args) {
        System.out.println(getCollegeName(DatabaseUtils.listStudents().get(0)));
        System.out.println(isTeacher(DatabaseUtils.listStudents().get(1),"惠玲"));
        System.out.println(getCollegeName(DatabaseUtils.listStudents(),201103));
        printCollegeName(DatabaseUtils.listStudents(),201104,1002);
        System.out.println(add(5,6));
    }
    private static int add(int a,int b) {
        return a + b;
    }

    /**
     * 基于给定学生，获取学生的导师的所在学院名称。
     * 任何一项不存在或失败，返回 未知学院
     * @param student
     * @return
     */
    private static String getCollegeName(Student student) {
        return Optional.ofNullable(student)
                .map(Student::getTeacher)
                .map(Teacher::getCollege)
                .map(College::getName)
                .orElse("未知学院");
    }

    /**
     * 基于给定学生及教师姓名，判断是否为给定学生的指导教师。
     * 不匹配或不存在，均返回false
     * @param student
     * @param teacherName
     * @return
     */
    private static boolean isTeacher(Student student, String teacherName) {
        return Optional.ofNullable(student)
                .map(Student::getTeacher)
                .filter(teacher -> teacher.getName().equals(teacherName))
                .isPresent();
    }

    /**
     * 基于给定学生集合，学生编号，实现获取学生的导师的所在学院名称。
     * 任何一项不存在或失败，返回 未知学院
     * 结合collection stream findFirst()方法实现
     * 可直接通过一条方法链实现
     *
     * @param students
     * @param sNumber
     * @return
     */
    private static String getCollegeName(List<Student> students, int sNumber) {
        return students.stream()
                .filter(student -> student.getNumber() == sNumber)
                .findFirst()
                .map(Student::getTeacher)
                .map(Teacher::getCollege)
                .map(College::getName)
                .orElse("未知学院");
    }

    /**
     * 实现在给定学生集合中查询指定编号学生，如果学生导师编号为指定编号，打印显式导师所在学院名称。
     * 任何一项不存在或失败，打印显式 未知学院
     * 结合collection stream findFirst()方法实现
     * 可直接通过一条方法链实现
     *
     * @param students
     * @param sNumber，学生编号
     * @param tNumber，教师编号
     */
    private static void printCollegeName(List<Student> students, int sNumber, int tNumber) {
        students.stream()
                .filter(student -> student.getNumber() == sNumber)
                .filter(student -> student.getTeacher().getNumber() == tNumber)
                .findFirst()
                .map(Test::getCollegeName)
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("未知学院"));
    }
}
