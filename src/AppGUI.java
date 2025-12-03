import Model.*;
import repository.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AppGUI extends JFrame {

    private JTextField idField, emriKlientField, mbiemriKlientField;
    private JButton regjistroButton;

    private JComboBox<String> llojiProduktit;
    private JTextField emriField, cmimiField, ekstraField1, ekstraField2;
    private JLabel lblEkstra1, lblEkstra2;
    private JButton shtoButton, shfaqFatureButton;

    private JTextArea listaTextArea;
    private JLabel totalLabel;

    private Klient klientiAktiv = null;
    private Porosi porosia = null;

    public AppGUI() {
        setTitle("Sistemi i Modës");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        layoutComponents();
        addListeners();

        ndryshoEtiketat(); // Për etiketat e fushave ekstra
    }

    private void initComponents() {
        idField = new JTextField();
        emriKlientField = new JTextField();
        mbiemriKlientField = new JTextField();
        regjistroButton = new JButton("Regjistro Klientin");

        llojiProduktit = new JComboBox<>(new String[]{"Veshje", "Këpucë", "Aksesore"});
        emriField = new JTextField();
        cmimiField = new JTextField();

        ekstraField1 = new JTextField();
        ekstraField2 = new JTextField();

        lblEkstra1 = new JLabel();
        lblEkstra2 = new JLabel();

        shtoButton = new JButton("Shto Produktin");
        shfaqFatureButton = new JButton("Shfaq Faturën");

        listaTextArea = new JTextArea();
        listaTextArea.setEditable(false);
        listaTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));

        totalLabel = new JLabel("Totali: 0€");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalLabel.setForeground(Color.MAGENTA);
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 240, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Klienti
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Klienti:"), gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Emri:"), gbc);
        gbc.gridx = 1;
        panel.add(emriKlientField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Mbiemri:"), gbc);
        gbc.gridx = 1;
        panel.add(mbiemriKlientField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(regjistroButton, gbc);
        gbc.gridwidth = 1;

        // Produkti
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Lloji i Produktit:"), gbc);
        gbc.gridx = 1;
        panel.add(llojiProduktit, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Emri Produktit:"), gbc);
        gbc.gridx = 1;
        panel.add(emriField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Çmimi (€):"), gbc);
        gbc.gridx = 1;
        panel.add(cmimiField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblEkstra1, gbc);
        gbc.gridx = 1;
        panel.add(ekstraField1, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblEkstra2, gbc);
        gbc.gridx = 1;
        panel.add(ekstraField2, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(shtoButton, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(shfaqFatureButton, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JScrollPane scrollPane = new JScrollPane(listaTextArea);
        panel.add(scrollPane, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(totalLabel, gbc);

        add(panel);
    }

    private void addListeners() {
        llojiProduktit.addActionListener(e -> ndryshoEtiketat());

        regjistroButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String emri = emriKlientField.getText().trim();
                String mbiemri = mbiemriKlientField.getText().trim();

                klientiAktiv = new Klient(id, emri, mbiemri);
                porosia = new Porosi(klientiAktiv);

                KlientRepository kr = new KlientRepository();
                kr.shtoKlient(klientiAktiv);

                JOptionPane.showMessageDialog(this, "Klienti u regjistrua me sukses!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID duhet të jetë numër!", "Gabim", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gabim: " + ex.getMessage(), "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        });

        shtoButton.addActionListener(e -> {
            if (klientiAktiv == null) {
                JOptionPane.showMessageDialog(this, "Regjistro klientin së pari!", "Gabim", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String tipi = (String) llojiProduktit.getSelectedItem();
                String emri = emriField.getText().trim();
                double cmimi = Double.parseDouble(cmimiField.getText().trim());

                Produkt produkt = null;
                String ekstra = "";

                switch (tipi) {
                    case "Veshje" -> {
                        String masa = ekstraField1.getText().trim();
                        String materiali = ekstraField2.getText().trim();
                        produkt = new Veshje(emri, cmimi, masa, materiali);
                        ekstra = "Masa: " + masa + ", Materiali: " + materiali;
                    }
                    case "Këpucë" -> {
                        int numri = Integer.parseInt(ekstraField1.getText().trim());
                        produkt = new Kepuce(emri, cmimi, numri);
                        ekstra = "Numri: " + numri;
                    }
                    case "Aksesore" -> {
                        String lloji = ekstraField1.getText().trim();
                        produkt = new Aksesore(emri, cmimi, lloji);
                        ekstra = "Lloji: " + lloji;
                    }
                }

                ProduktRepository pr = new ProduktRepository();
                int produktId = pr.shtoProdukt(produkt, tipi, ekstra);
                produkt.setId(produktId);

                porosia.shtoProdukt(produkt);

                // Përditëso listën dhe totalin
                rifreskoPorosinUI();

                // Pastrim fushash
                emriField.setText("");
                cmimiField.setText("");
                ekstraField1.setText("");
                ekstraField2.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Çmimi dhe fusha numerike duhet të jenë valide!", "Gabim", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gabim: " + ex.getMessage(), "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        });

        shfaqFatureButton.addActionListener(e -> {
            if (klientiAktiv == null || porosia == null || porosia.getProduktet().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nuk ka porosi për faturë!", "Gabim", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                PorosiRepository pr = new PorosiRepository();
                int fatureId = pr.shtoFature(porosia);

                StringBuilder fatura = new StringBuilder();
                fatura.append("FATURË E BLERJES\n");
                fatura.append("============================\n");
                fatura.append(klientiAktiv.toString()).append("\n");
                fatura.append("----------------------------\n");
                for (Produkt p : porosia.getProduktet()) {
                    fatura.append("• ").append(p.toString()).append("\n");
                }
                fatura.append("============================\n");
                fatura.append("Totali: ").append(porosia.llogaritTotalin()).append("€\n");
                fatura.append("Data: ").append(java.time.LocalDateTime.now()).append("\n");

                JTextArea textArea = new JTextArea(fatura.toString());
                textArea.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Fatura", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gabim në DB: " + ex.getMessage(), "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void rifreskoPorosinUI() {
        StringBuilder sb = new StringBuilder();
        for (Produkt p : porosia.getProduktet()) {
            sb.append(p.toString()).append("\n");
        }
        listaTextArea.setText(klientiAktiv.toString() + "\n-------------------------\n" + sb);
        totalLabel.setText("Totali: " + porosia.llogaritTotalin() + "€");
    }

    private void ndryshoEtiketat() {
        String tipi = (String) llojiProduktit.getSelectedItem();
        if ("Veshje".equals(tipi)) {
            lblEkstra1.setText("Masa:");
            lblEkstra2.setText("Materiali:");
            ekstraField1.setVisible(true);
            ekstraField2.setVisible(true);
            lblEkstra1.setVisible(true);
            lblEkstra2.setVisible(true);
        } else if ("Këpucë".equals(tipi)) {
            lblEkstra1.setText("Numri:");
            lblEkstra2.setText("");
            ekstraField1.setVisible(true);
            ekstraField2.setVisible(false);
            lblEkstra1.setVisible(true);
            lblEkstra2.setVisible(false);
        } else { // Aksesore
            lblEkstra1.setText("Lloji:");
            lblEkstra2.setText("");
            ekstraField1.setVisible(true);
            ekstraField2.setVisible(false);
            lblEkstra1.setVisible(true);
            lblEkstra2.setVisible(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppGUI().setVisible(true);
        });
    }
}

