/*
 * <SOURCE HEADER>
 * 
 * <NAME>
 * 
 * <RCS_KEYWORD>
 * $SOURCE:
 * $REVISION:
 * $DATE:
 * </RCS_kEYWORD>
 * 
 * <COPYRIGHT>
 * The following source code is protected under all standard copyright laws.
 * </COPTRIGHT>
 * 
 * </SOURCE HEADER>
 */

package guiops;

import java.awt.BorderLayout;
import java.awt.Color;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Deze klasse genereert een JFrame met verschillende parameters.
 * Binnen dit JFrame wordt een JTextArea getoond met de waarden uit het
 * JSON-bestand. Het frame wordt gesloten bij het aanklikken van het kruisje.
 */

public class OverzichtFrame extends JFrame {
    private JTextArea textArea;
    private JLabel label1;
    // Om de gegevens uit het JSON-bestand en het frame te laten zien, moeten deze
    // private gemaakt worden, zodat de methode er gebruik van kan maken, maar ook
    // de constructor JFrame

    public OverzichtFrame() {
        setTitle("Overzicht");
        setSize(300, 300);
        getContentPane().setBackground(Color.MAGENTA);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        // Label boven het frame
        label1 = new JLabel(" U heeft gekozen voor de optie 'Overzicht'.");

        // textArea in het frame
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Weergeven van de gegevens vanuit het JSON-bestand
        displayJSONData();

        // Toevoegen van de JLabel en de JtextArea
        add(label1, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    // Methode voor het weergeven van de informatie vanuit het JSON-bestand
    public void displayJSONData() {
        try {
            Path path = Paths.get("C:\\Users\\wreke\\Documents\\programmeren\\database.json");
            String fileContent = Files.readString(path);
            JSONArray jsonArray = new JSONArray(fileContent);

            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                String materiaal = jsonObject.getString("Materiaal");
                String subtype = jsonObject.getString("Subtype");
                int aantal = jsonObject.getInt("Aantal");

                // Het weergeven van de informatie (/n/n bij aantal zodat er een dubbele enter
                // komt)
                textArea.append("Materiaal: " + materiaal + "\n");
                textArea.append("Subtype: " + subtype + "\n");
                textArea.append("Aantal: " + aantal + "\n\n");
            }
            // Het weergeven van problemen in de terminal voor probleemoplossing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OverzichtFrame());
    }
}
