
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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.Arrays;

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

public class MeenemenFrame extends JFrame {
    public MeenemenFrame() {
        setTitle("Meenemen");
        setSize(500, 100);
        getContentPane().setBackground(Color.YELLOW);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Label boven en onder het frame
        JLabel label1 = new JLabel("U heeft gekozen voor de optie 'Meenemen'. Maak uw keuze en bevestig.");
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
                            // Vul de tweede combobox met subtypen voor ANTENNE
                            String[] antenneSubtypen = MeenemenFrame.getNames(AntenneType.class);
                            comboBoxSubtype.setModel(new DefaultComboBoxModel<>(antenneSubtypen));
                            break;
                        case "BEKABELING":
                            // Vul de tweede combobox met subtypen voor BEKABELING
                            String[] bekabelingSubtypen = MeenemenFrame.getNames(BekabelingType.class);
                            comboBoxSubtype.setModel(new DefaultComboBoxModel<>(bekabelingSubtypen));
                            break;
                        case "USB_STICK":
                            // Voeg de tweede combobox met subtypen voor USB
                            String[] usbSubtypen = MeenemenFrame.getNames(USBstickType.class);
                            comboBoxSubtype.setModel(new DefaultComboBoxModel<>(usbSubtypen));
                            break;
                        // Voeg de tweede combobox met subtypen voor SDR
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
                JFrame invoerFrame = new JFrame("Aantal meenemen");
                invoerFrame.setSize(300, 100);
                invoerFrame.setLayout(new FlowLayout());

                JLabel aantalLabel = new JLabel("Voer het aantal (in hele getallen) in:");
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
                        decreaseAantalInJSON(geselecteerdMateriaal, geselecteerdSubtype, ingevoerdAantal);
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
    private void decreaseAantalInJSON(String geselecteerdMateriaal, String geselecteerdSubtype, int ingevoerdAantal) {
        try {
            Path path = Paths.get("C:\\Users\\wreke\\Documents\\programmeren\\database.json");
            String fileContent = Files.readString(path);
            JSONArray jsonArray = new JSONArray(fileContent);

            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                String jsonMateriaal = jsonObject.getString("Materiaal");
                String jsonSubtype = jsonObject.getString("Subtype");
                int aantal = jsonObject.getInt("Aantal");

                // Controle van het aantal in het JSON-bestand
                if (jsonMateriaal.equals(geselecteerdMateriaal) && jsonSubtype.equals(geselecteerdSubtype)) {
                    if (aantal > 0) {
                        // Controle of het ingevoerde aantal niet groter is dan de huidige hoeveelheid
                        if (ingevoerdAantal <= aantal) {
                            jsonObject.put("Aantal", aantal - ingevoerdAantal);
                            // Als de invoer groter is dan de beschikbaarheid van het materiaal, dan de
                            // volgende foutmelding
                        } else {
                            JOptionPane.showMessageDialog(this, "U kunt niet meer meenemen dan er beschikbaar is.",
                                    "Fout",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Het aantal is 0, dus er moet een foutmelding gegeven worden
                        JOptionPane.showMessageDialog(this,
                                "Dit materiaal is binnen Ops niet aanwezig, maak een andere keus.",
                                "Fout", JOptionPane.ERROR_MESSAGE);
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

    // Haalt alle namen van de elementen in de enum op en retourneert als een array
    // van strings (benodigd voor de tweede JComboBox)
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
