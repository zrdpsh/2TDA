### Принципы повторного использования модуля

1. Модуль задаёт некоторый базовый тип, потенциально допускающий параметризацию:
   - в Java классы и интерфейсы могут быть параметризованы другими типами с помощью дженериков (public class SomeClass < T > {})
    
2. Модуль включает функции, которые обращаются друг к другу:
   - в Java это, по сути, реализовано, с помощью packages - предполагается, что в одном пакете нужно размещать логически и функционально связанные классы, которые так или иначе будут использовать работу друг друга.
     
3. Новый модуль включается в ряд других модулей, посвящённых одной задаче:
   - организация Java-проекта: com.app.coffeemachine.dispenser, com.app.coffeemachine.grinder - уже не классы внутри ппкета, а пакеты сами по себе увязываются в одну иерархию
     
4. Разделения на родительские пакеты и пакеты-наследнки в Java на заложено - видимо, для такого рода наследования доожно хватать классов. Но с большой натяжкой можно добавить и этот пункт, если руками разместить abstract class выше по иерархии пакетов, чем класс наследник. Параметризовать использование пакетов нельзя никак.
   
6. Модуль интегрирует поведение нескольких модулей, различающихся только деталями:
   - в Java естественно конструируется:
````java
// общий интерфейс и два класса с разной реализацией одной команды
public interface DataStorage {
    void save(String data);
}

public class FileStorage implements DataStorage {
    @Override
    public void save(String data) {
        System.out.println("сохраняем в файл: " + data);
    }
}

public class DbStorage implements DataStorage {
    @Override
    public void save(String data) {
        System.out.println("сохраняем в базу: " + data);
    }

}

// класс-обработчик, привязанный к интерфейсу, т.е. ожидающий любую из реализаций
public class DataProcessor {
    private final DataStorage storage;

    public DataProcessor(DataStorage storage) {
        this.storage = storage;
    }

    public void process(String input) {
        storage.save(input);
    }
}

// ради чего все затевалось - закидываем *что-то* в обработчик одной командой, нюансы живут отдельно
public class Main {
    public static void main(String[] args) {
        DataStorage storage = new FileStorage(); // или DbStorage, не имеет значения
        DataProcessor processor = new DataProcessor(storage);

        processor.process("какой-то текст");
    }
}


````
