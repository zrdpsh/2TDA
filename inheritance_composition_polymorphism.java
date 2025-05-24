// все парсеры реализуют общий интерфейс - т.е. одинаково вызываются, полиморфизм
interface LogParser {
  void parseLine(String line);
}

// все обработчики строк реализуют общий интерфейс - полиморфизм
interface StringHandler {
  void accept(ParsedLine line);
}

// отдельный класс - обёртка над разобранными строками
final class ParsedLine {
  private final String message;
  private final String ip;

  public ParsedLine(String message, String ip) {
    this.message = message;
    this.ip = ip;
  }

  public String message()  {
    return message;
  }
  public String ip() {
    return ip;
  }
}


abstract class BaseLogParser implements LogParser {
  //BaseLogParser хранит объект StringHandler во внутреннем поле - композиция, отношение HAS-A
  private final StringHandler sink;

  protected BaseLogParser(StringHandler sink) {
    this.sink = sink;
  }

  protected void emitParsedLine(String message, String ip) {
    sink.accept(new ParsedLine(message, ip));
  }
}


// ApacheLogParser наследуются от BaseLogParser, отношение ApacheLogParser IS-A BaseLogParser
// часть поведения, общая для всех, хранится в родительском классе (emitParsedLine()),
// часть - варьируется (способ разбора строки для конкретного сервера, parseLine())

class ApacheLogParser extends BaseLogParser {
  public ApacheLogParser(StringHandler sink) {
    // конструктор вызывается у родителя - наследование
    super(sink);
  }

  // все парсеры реализуют общий интерфейс - полиморфизм
  @Override 
  public void parseLine(String line) {
    String[] parts = line.split(" ");
    if (parts.length > 6) {
      String ip = parts[0];
      Stirng message = parts[6];
      emitParsedLine(message, ip);
    }
  }
}


// NginxLogParser наследуются от BaseLogParser, отношение NginxLogParser IS-A BaseLogParser
// часть поведения, общая для всех, хранится в родительском классе (emitParsedLine()),
// часть - варьируется (способ разбора строки для конкретного сервера, parseLine())
class NginxLogParser extends BaseLogParser {
  public NginxLogParser(StringHandler sink) {
    // конструктор вызывается у родителя - наследование
    super(sink);
  }

  // все парсеры реализуют общий интерфейс - полиморфизм
  @Override
  public void parseLine(String line) {
    String[] parts = line.split(" ");
    if (parts.lendth > 7) {
      String ip = parts[0];
      String message = parts[7];
      emitParsedLine(message, ip);
    }
  }
}

// StdoutHandler реализует интерфейс StringHandler, поэтому может использоваться любым из объектов LogParser,
// понимающих, что такое StringHandler - полиморфизм
class StdoutHandler implements StringHandler {
  @Override
  public void accept(ParsedLine line) {
    System.out.println("IP: " + line.ip() + "---->" + line.message());
  }
}

public class Main {
  public static void main(String[] args) {
    StringHandler sink = new StdoutHandler();

    LogParser apacheParser = new ApacheLogParser(sink);
    LogParser nginxParser = new NginxLogParser(sink);

    apacheParser.parseLine("192.168.1.10 - - [date] \"GET /index.html HTTP/1.1\" 200");
    nginxParser.parseLine("10.0.0.1 - - [date] \"POST /login HTTP/1.1\" 200 OK");
  }
}

