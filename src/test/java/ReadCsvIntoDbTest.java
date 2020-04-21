
import co.za.distance.util.CsvReaderUtil;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadCsvIntoDbTest {

    @Test
    public void readCsvTest() {
        List<Path> paths = new ArrayList<>();
        Map<String, List<String>> columnNames = new HashMap<>();

        paths.add(Paths.get("src/main/resources/input/planet.csv"));
        paths.add(Paths.get("src/main/resources/input/route"));
        paths.add(Paths.get("src/main/resources/input/traffic"));

        CsvReaderUtil readerUtil = new CsvReaderUtil();

        paths.forEach(path ->
            readerUtil.read(path));
    }
}
