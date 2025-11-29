package com.github.project.test;

/**
 * a
 *
 * @author gaoxinyu
 * @date 2025/10/28 16:50
 **/
import java.util.*;

public class Test {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        //write your code here......
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("小明", 2500));
        list.add(new Employee("小军", 8000));
        list.add(new Employee("小红", 100000));

        for (Employee e : list) {
            double res = calc(e.getSalary());

             System.out.println(e.getName() + "应该缴纳的个人所得税是：" + res);
            //System.out.printf("%s应该缴纳的个人所得税是:%.2f", e.getName(), res);
        }

    }

    public static double calc(double salary) {
        double should = salary - 3500;
        double res = 0.0;
        if (should <= 0) {
            res = 0;
        } else if (should <= 1500) {
            res = should * 0.03;
        } else if (should <= 4500) {
            res = should * 0.1 - 105;
        } else if (should <= 9000) {
            res = should * 0.2 - 555;
        } else if (should <= 35000) {
            res = should * 0.25 - 1005;
        } else if (should <= 55000) {
            res = should * 0.3 - 2755;
        } else if (should <= 80000) {
            res = should * 0.35 - 5505;
        } else {
            res = should * 0.45 - 13505;
        }
        return res;
    }
}
class Employee{
    private String name;
    private double salary;
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }
}
