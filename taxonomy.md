1. Вариант,где много if-else со всеми вытекающими:
```java
class Employee {
    String name;
    String employeeType; // "perHour", "salary", "percent"

    public double calculatePay() {
        if (employeeType.equals("perHour")) {
          // какая-то логика
        } else if (employeeType.equals("salary")) {
          // какая-то логика
        } else if (employeeType.equals("percent")) {
          // какая-то логика
        }
        return 0;
    }
}
```

2. Наличие веток, проверяющих тип, подсказывает, что здесь можно сделать иерархию классов - вместо ветвления, зашитого в "исполняемой" части кода, мы растаскиваем логику по отдельным наследникам
```java
// класс-шаблон
abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public abstract double calculatePay();
}

// собственно, три варианта поведения из первоначального кода, каждый - в своём классе:
class perHourEmployee extends Employee {
    private double hoursWorked;
    private double perHourRate;

    public perHourEmployee(String name, double hoursWorked, double perHourRate) {
        super(name);
        this.hoursWorked = hoursWorked;
        this.perHourRate = perHourRate;
    }

    @Override
    public double calculatePay() {
        return hoursWorked * perHourRate;
    }
}

class salaryEmployee extends Employee {
    private double monthlySalary;

    public salaryEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class percentEmployee extends Employee {
    private double baseSalary;
    private double percent;

    public percentEmployee(String name, double baseSalary, double percent) {
        super(name);
        this.baseSalary = baseSalary;
        this.percent = percent;
    }

    @Override
    public double calculatePay() {
        return baseSalary + percent;
    }
}

``` 
