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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Deze klasse genereert een JFrame met verschillende parameters.
 * Binnen dit JFrame worden 2 JComboBoxen getoond. De eerste is voor de
 * materiaaltypen (enum OptiesMateriaal).
 * De tweede JComboBox is voor de materiaalsubtypen (enum AntenneType,
 * BekabelingType, et cetera.).
 * De tweede JCombobox door if-switch, zodat het subtype matcht met het type
 * materiaal.
 * Bij het bevestigen (JButton) van het materiaaltype en -subtype, wordt een
 * nieuw frame geopend.
 * In dit frame moet het aantal, door middel van het toetsenbord, worden
 * toegevoegd.
 * Als op OK wordt geklikt, vindt de verwerking plaats in het JSON-bestand en
 * worden beide frames gesloten.
 */

public class ToevoegenFrame extends JFrame {
    public ToevoegenFrame() {
        setTitle("Toevoegen");
        setSize(500, 100);
        getContentPane().setBackground(Color.GREEN);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Label boven en onder het frame
        JLabel label1 = new JLabel("U heeft gekozen voor de optie 'Toevoegen'. Maak uw keuze en bevestig.");
        JLabel label2 = new JLabel("Druk op het kruisje (rechtsbovein) om een andere optie te kiezen.");

        // Eerste combobox om het type materiaal te kiezen
        JComboBox<OptiesMateriaal> comboBoxMateriaal = new JComboBox<>();
        comboBoxMateriaal.setModel(new DefaultComboBoxModel<>(OptiesMateriaal.values()));

        // Tweede combobox om het subtype materiaal te kiezen
        JComboBox<String> comboBoxSubtype = new JComboBox<>();

        // ActionListener aan de eerste combobox om de tweede combobox bij te werken
        comboBoxMateriaal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String geselecteerdMateriaal = comboBoxMateriaal.getSelectedItem().toString();
                if (geselecteerdMateriaal != null) {
                    switch (geselecteerdMateriaal) {
                        case "ANTENNE":
                            String[] antenneSubtypen = MeenemenFrame.getNames(AntenneType.class);
                            comboBoxSubtype.setModel(new DefaultComboBoxModel<>(antenneSubtypen));
                            break;
                        case "BEKABELING":
                            String[] bekabelingSubtypen = MeenemenFrame.getNames(BekabelingType.class);
                            comboBoxSubtype.setModel(new DefaultComboBoxModel<>(bekabelingSubtypen));
                            break;
                        case "USB_STICK":
                            String[] usbSubtypen = MeenemenFrame.getNames(USBstickType.class);
                            comboBoxSubtype.setModel(new DefaultComboBoxModel<>(usbSubtypen));
                            break;
                        case "SDR":
                            String[] sdrSubtypen = MeenemenFrame.getNames(SoftwaredefinedradioType.class);
                            comboBoxSubtype.setModel(new DefaultComboBoxModel<>(sdrSubtypen));
                            break;
                    }
                }
            }
        });

        // Bevestigingsbutton voor materiaaltype en -subtype
        JButton bevestigingsButton = new JButton("Bevestigen");

        // Vertaling van het type en subtype naar een string
        bevestigingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String geselecteerdMateriaal = comboBoxMateriaal.getSelectedItem().toString();
                String geselecteerdSubtype = comboBoxSubtype.getSelectedItem().toString();

                // Nieuw frame voor het invoeren van het aantal
                JFrame invoerFrame = new JFrame("Aantal Invoeren");
                invoerFrame.setSize(300, 100);
                invoerFrame.setLayout(new FlowLayout());

                JLabel aantalLabel = new JLabel("Voer het aantal in:");
                JTextField aantalField = new JTextField(10);
                JButton okButton = new JButton("OK");

                invoerFrame.add(aantalLabel);
                invoerFrame.add(aantalField);
                invoerFrame.add(okButton);

                // ActionListener voor de invoer van getallen
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String aantalText = aantalField.getText();
                        int ingevoerdAantal = Integer.parseInt(aantalText);

                        // Sluiten van beide frames
                        invoerFrame.dispose();
                        dispose();

                        // Verwerking van het ingevoerde aantal (methode staat hieronder)
                        increaseAantalInJSON(geselecteerdMateriaal, geselecteerdSubtype, ingevoerdAantal);
                    }
                });

                invoerFrame.setVisible(true);
            }
        });

        // Toevoegen van de JLabels, JComboBoxen en de JButton aan het MeenemenFrame
        add(label1, BorderLayout.PAGE_START);
        add(comboBoxMateriaal, BorderLayout.WEST);
        add(comboBoxSubtype, BorderLayout.CENTER);
        add(bevestigingsButton, BorderLayout.EAST);
        add(label2, BorderLayout.SOUTH);
    }

    // Methode voor het veranderen van de waarden in het JSON-bestand aan de hand
    // van het materiaaltype, -subtype en het aantal
    private void increaseAantalInJSON(String geselecteerdMateriaal, String geselecteerdSubtype, int ingevoerdAantal) {
        try {
            Path path = Paths.get("C:\\Users\\wreke\\Documents\\programmeren\\database.json");
            String fileContent = Files.readString(path);
            JSONArray jsonArray = new JSONArray(fileContent);

            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                String jsonMateriaal = jsonObject.getString("Materiaal");
                String jsonSubtype = jsonObject.getString("Subtype");
                int aantal = jsonObject.getInt("Aantal");

                // Verhoog het aantal met ingevoerdAantal
                if (jsonMateriaal.equals(geselecteerdMateriaal) && jsonSubtype.equals(geselecteerdSubtype)) {
                    if (ingevoerdAantal >= 0) {
                        jsonObject.put("Aantal", aantal + ingevoerdAantal);
                    }
                    break;
                }
            }

            // Het weergeven van problemen in de terminal voor probleemoplossing
            Files.write(path, jsonArray.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
