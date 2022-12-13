import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

public class Demo {

    @Test
    public void testDemo() {

        String[] args = new String[]{"-star", "desc for star"};

        Options options = new Options();
        options.addOption("star", true, "desc for star");

        DefaultParser parser = new DefaultParser();
        CommandLine cl;
        try {
            cl = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String value = cl.getOptionValue("star");
        System.out.println(value);
    }
}
