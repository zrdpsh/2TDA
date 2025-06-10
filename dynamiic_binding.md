Первоначально у нас есть интерфейс, определяющий само наличие в реализации самого нужного метода (здесь - parseLine), и абстрактный класс, задающий поле, конструктор и метод для обработки распарсенных строк.
Статически (на этапе компиляции) Java будет проверять только, есть у наследников класса BaseLogParser метод с таким названием, в остальном - полная свобода.

```java
public interface LogParser {
    void parseLine(String line);
}

public abstract class BaseLogParser implements LogParser {
    private final LineSink sink;

    protected BaseLogParser(LineSink sink) {
        this.sink = sink;
    }

    protected void emitParsedLine(String message, String ip) {
        sink.accept(new ParsedLine(message, ip));
    }
}
```
