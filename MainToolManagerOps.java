/*
 * <SOURCE HEADER>
 * 
 * <NAME>               R WREKENHORST
 * 
 * <RCS_KEYWORD>
 * $SOURCE:             GITHUB
 * $REVISION:           1.0
 * $DATE:               28102013
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
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Deze klasse genereert een JFrame met verschillende parameters.
 * Binnen dit JFrame zitten drie JButtons met elk een
 * ActionListener/ActionPerformed.
 * Bij het aanklikken van de button, zal een nieuwe klasse aangesproken worden.
 * Er wordt een nieuw JFrame geopend, aan de hand van de gekozen JButton.
 */

public class MainToolManagerOps {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Keuzemenu");
        frame.setLayout(new BorderLayout());
        frame.setSize(450, 200);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Label boven en onder het frame
        JLabel label1 = new JLabel(" Welkom bij de manager-tool van Ops, maak uw keuze:");
        JLabel label2 = new JLabel(" Deze tool wordt gebruikt voor het managen van materiaal binnen Ops.");

        // Button voor de keuze Toevoegen, inclusief grootte (alle buttons even groot)
        JButton toevoegenButton = new JButton("Toevoegen");
        Dimension buttonSize = new Dimension(150, 50);
        toevoegenButton.setPreferredSize(buttonSize);

        // Button voor de keuze Meenemen
        JButton meenemenButton = new JButton("Meenemen");
        meenemenButton.setPreferredSize(buttonSize);

        // Button voor de keuze Overzicht
        JButton overzichtButton = new JButton("Overzicht");
        overzichtButton.setPreferredSize(buttonSize);

        // Respectievelijke frame openen door de ActionListener methode
        toevoegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ToevoegenFrame().setVisible(true);
            }
        });

        meenemenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MeenemenFrame().setVisible(true);
            }
        });

        overzichtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OverzichtFrame().setVisible(true);
            }
        });

        // Toevoegen van de verschillende JLabels en JButtons
        frame.add(label1, BorderLayout.NORTH);
        frame.add(toevoegenButton, BorderLayout.EAST);
        frame.add(meenemenButton, BorderLayout.CENTER);
        frame.add(overzichtButton, BorderLayout.WEST);
        frame.add(label2, BorderLayout.SOUTH);

        // Het visueel maken van het frame
        frame.setVisible(true);
    }
}