import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import static java.nio.file.Files.write;
import static java.util.Arrays.asList;

public class Example{
    public static void main(String[] args) throws IOException, InterruptedException {
        long start,end;
        start = new Date().getTime();

        String command = "google-chrome --headless --disable-gpu --print-to-pdf=file1.pdf /home/calibraint/Desktop/template.html";
        Process proc = Runtime.getRuntime().exec(command);
        proc.waitFor();
        end = new Date().getTime();

        System.out.println(end-start);
        System.out.println("ALTERNATIVE");
        printToPdf();
    }

    static void printToPdf() throws IOException {
        Launcher launcher = new Launcher();
        long start, end;
        start = new Date().getTime();
        Path file = Path.of("/home/calibraint/Desktop/webchart1.pdf");

        try (SessionFactory factory = launcher.launch(asList("--headless", "--disable-gpu"))) {
            String context = factory.createBrowserContext();
            try (Session session = factory.create(context)) {
                session.navigate("file:///home/calibraint/Desktop/template.html");
                session.waitDocumentReady();
                byte[] content = session
                        .getCommand()
                        .getPage()
                        .printToPDF();
                write(file, content);
                end = new Date().getTime();
                System.out.println(end-start);
            }
        }
    }
}