Как работает наследование вида на примере БПЛА.

1. Базовый класс - собственно, само устройство, дрон:
```java
class Drone {
    private MissionType mission;
    private PlatformType platform;
    private ControlType control;

    public Drone(MissionType mission, PlatformType platform, ControlType control) {
        this.mission = mission;
        this.platform = platform;
        this.control = control;
    }

    public void performMission() {
        mission.start();
    }

    public void maneuver() {
        platform.fly();
    }

    public void controlBehavior() {
        control.operate();
    }
}
```

2. Для примера выделяем три оси - для чего мы используем устройство, Назначение (MissionType), способ полёта (PlatformType), способ управления (ControlType).
2.1 Назначение:
```java
interface MissionType {
    void start();
}

class MonitoringMission implements MissionType {
    public void start() {
        System.out.println("Облетаем вышки");
    }
}

class EnvironmentalMission implements MissionType {
    public void start() {
        System.out.println("Собираем пробы воздуха");
    }
}

class AerialMappingMission implements MissionType {
    public void start() {
        System.out.println("Снимаем в высоком разрешении");
    }
}
```
2.2 Способ полёта:
```java
interface PlatformType {
    void fly();
}

class FixedWingPlatform implements PlatformType {
    public void fly() {
        System.out.println("У меня крылья");
    }
}

class MultirotorPlatform implements PlatformType {
    public void fly() {
        System.out.println("У меня винты");
    }
}
```
2.3 Управление:
```java
interface ControlType {
    void operate();
}

class AutonomousControl implements ControlType {
    public void operate() {
        System.out.println("Лечу сам");
    }
}

class OperatorControl implements ControlType {
    public void operate() {
        System.out.println("Мне помогает оператор");
    }
}
```
3. Теперь можно завести класс с полями под каждую из "осей":
```java
class NewDrone {
    private final MissionType mission;
    private final PlatformType platform;
    private final ControlType control;

    public NewDrone(MissionType mission, PlatformType platform, ControlType control) {
        this.mission = mission;
        this.platform = platform;
        this.control = control;
    }

    public void performMission() {
        control.operate();
        platform.fly();
        mission.start();
    }
}
```
4. ... и собрать новое устройство, свободно сочетая созданные свойства:
```java
public class Main {
    public static void main(String[] args) {
        NewDrone drone = new NewDrone(
            new EnvironmentalMission(),
            new MultirotorPlatform(),
            new AutonomousControl()
        );

        drone.performMission();
    }
}

```
