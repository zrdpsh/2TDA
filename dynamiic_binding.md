- Первоначально у нас есть интерфейс, определяющий само наличие в реализации самого нужного метода (здесь - parseLine), и абстрактный класс, задающий частичную реализацию других элементов класса - поле, конструктор и метод для обработки распарсенных строк.
Статически (на этапе компиляции) Java будет проверять только, есть у наследников класса BaseLogParser метод с таким названием, в остальном - полная свобода.
Аналогично - с интерфейсом LinkSink для приёма строк.

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

public interface LineSink {
    void accept(ParsedLine line);
}
```


- Далее - создаём завершённые классы, для BaseLogParser - в первом случае, и реализающие интерфейс LinkSink - во втором.
```java
public class ApacheLogParser extends BaseLogParser {
    public ApacheLogParser(LineSink sink) {
        super(sink);
    }

    @Override
    public void parseLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length > 6) {
            String ip = parts[0];
            String msg = parts[6];
            emitParsedLine(msg, ip);
        }
    }
}

public class NginxLogParser extends BaseLogParser {
    public NginxLogParser(LineSink sink) {
        super(sink);
    }

    @Override
    public void parseLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length > 7) {
            String ip = parts[0];
            String msg = parts[7];
            emitParsedLine(msg, ip);
        }
    }
}
```
```java
public class StdoutSink implements LineSink {
    @Override
    public void accept(ParsedLine line) {
        System.out.println("IP: " + line.ip() + " -> " + line.message());
    }
}
import java.time.LocalDateTime;

public class TimestampedSink extends StdoutSink {
    @Override
    public void accept(ParsedLine line) {
        String timestamp = LocalDateTime.now().toString();
        System.out.print("[" + timestamp + "] ");
        super.accept(line);
    }
}
```
Также нужен вспомогательный класс для методов accept внутри sink-ов
```java
public final class ParsedLine {
    private final String message;
    private final String ip;

    public ParsedLine(String message, String ip) {
        this.message = message;
        this.ip = ip;
    }

    public String message() {
        return message;
    }

    public String ip() {
        return ip;
    }
}
```


- Наконец, в итоговом классе можно собрать всё вместе - два поля типа LineSink (simpleSink и timedSink) и два обработчика для этих полей (apacheParser и nginxParser).
Несмотря на то, что в обеих парах внутри переменной лежат *разные* объекты, для Java они определены через общий тип (LineSink и LogParser, соответственно), что позваоляет бесшовно комбинировать их друг с другом - динамика в действии. Одна и та же строка обрабатывает по-разному, в зависимости от того, куда она попадает - во втором случае лог будет выведен вместе с меткой времени.
```java
public class LogAnalyzer {
    public static void main(String[] args) {
        LineSink simpleSink = new StdoutSink();
        LineSink timedSink = new TimestampedSink();

        LogParser apacheParser = new ApacheLogParser(simpleSink);
        LogParser nginxParser = new NginxLogParser(timedSink);

        apacheParser.parseLine("192.168.1.10 - - [date] \"GET /index.html HTTP/1.1\" 200");
        nginxParser.parseLine("192.168.1.10 - - [date] \"GET /index.html HTTP/1.1\" 200");

    }
}
````
