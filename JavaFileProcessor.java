import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaFileProcessor {

    public static void main(String[] args) {
        String inputFileName = "Kod.txt";
        String outputFileName = "NewFile.txt";

        try {
            String processedFileContent = processFile(inputFileName);
            Files.write(Paths.get(outputFileName), processedFileContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для обработки файла: удаление комментариев и замена имен переменных и классов
    private static String processFile(String inputFileName) throws IOException {
        String fileContent = Files.lines(Paths.get(inputFileName))
                .map(JavaFileProcessor::removeSingleLineComments)
                .map(line -> replaceVariableNames(line, getVariableNameMappings()))
                .map(JavaFileProcessor::replaceClassName)
                .collect(Collectors.joining("\n"));
        return fileContent;
    }

    // Метод для удаления однострочных комментариев
    private static String removeSingleLineComments(String line) {
        int commentIndex = line.indexOf("//");
        if (commentIndex != -1) {
            return line.substring(0, commentIndex);
        } else {
            return line;
        }
    }

    // Метод для замены имен переменных
    private static String replaceVariableNames(String line, Map<String, String> variableNameMappings) {
        for (Map.Entry<String, String> entry : variableNameMappings.entrySet()) {
            String oldName = entry.getKey();
            String newName = entry.getValue();
            line = line.replaceAll("\\b" + oldName + "\\b", newName);
        }
        return line;
    }

    // Метод для замены названия класса
    private static String replaceClassName(String line) {
        // Замените "OldClassName" и "NewClassName" на конкретные значения
        return line.replaceAll("\\bKod\\b", "Kodishe");
    }

    // Метод для получения словаря с соответствием старых и новых имен переменных
    private static Map<String, String> getVariableNameMappings() {
        Map<String, String> variableNameMappings = new HashMap<>();
        variableNameMappings.put("oldVariableName", "newVariableName");
        // добавьте другие соответствия по мере необходимости
        return variableNameMappings;
    }
}