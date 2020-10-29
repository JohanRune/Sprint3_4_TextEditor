import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Johan Rune
 * Date: 2020-10-28
 * Time: 15:38
 * Project: IntelliJ IDEA
 * Copyright: MIT
 */
public class TextEditor extends JFrame implements ActionListener{

    JTextField textField = new JTextField();
    JTextArea textArea = new JTextArea(10, 50);
    JLabel jLabel = new JLabel("Filnamn:");
    JButton openButton = new JButton("Öppna");
    JButton saveButton = new JButton("Spara");
    JButton printButton = new JButton("Skriv ut");
    JButton exitButton = new JButton("Avsluta");
    JComboBox comboBox;
    String colors[] = { "röd", "blå", "gul" };

    JPanel topRowPanel = new JPanel();
    JPanel textAreaPanel = new JPanel();


    TextEditor() {
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );

        comboBox = new JComboBox(colors);
        comboBox.setSelectedIndex(-1);


        topRowPanel.setLayout(new GridLayout(1, 6));
        topRowPanel.add(jLabel);
//        topRowPanel.add(textField);
        topRowPanel.add(comboBox);
        topRowPanel.add(openButton);
        topRowPanel.add(saveButton);
        topRowPanel.add(printButton);
        topRowPanel.add(exitButton);

        textAreaPanel.add(scroll);

        setLayout(new BorderLayout());
        add("North", topRowPanel);
        add("Center", textAreaPanel);

        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        printButton.addActionListener(this);
        exitButton.addActionListener(this);
        comboBox.addActionListener(this);

        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


    public void actionPerformed (ActionEvent e) {

        if (e.getSource() == openButton) {
            //läs av vad som står i rutan.
            //sök i textfiler efter en fil med det namnet.
            //öppna den filen som hittas.
            //skriv filen som hittas i textArean.
            //om inte filen finns, lämna felmeddelande och ge chans att skriva om.

            String nameOfText = textField.getText().trim();
            BufferedReader bufferedReader = null;

            try {
                String currentLine;

                bufferedReader = new BufferedReader(new FileReader(nameOfText));

                while ((currentLine = bufferedReader.readLine()) != null) {
                    textArea.append(currentLine);
                    textArea.append("\n");
                }

            } catch (IOException ex) {
                System.out.println("Filen finns inte.");;
            }
        }


        if (e.getSource() == saveButton) {
            //först läs in vad som står i textArea.
            //sedan skriv det till filen.
            //kanske bäst att göra en rad i taget.


            String nameOfText = textField.getText().trim();
            BufferedWriter bw = null;

            try {

                File file = new File(nameOfText);

                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file, true);
                bw = new BufferedWriter(fw);

                for (String line : textArea.getText().split("\\n")) {
                    bw.write(line);
                    bw.newLine();
                }

                System.out.println("File written Successfully");

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            finally
            {
                try{
                    if(bw!=null)
                        bw.close();
                }catch(Exception ex){
                    System.out.println("Error in closing the BufferedWriter"+ex);
                }
            }
        }



        if (e.getSource() == printButton) {

            try {
                textArea.print();
            }

            catch (Exception es){
                es.printStackTrace();
            }

//            for (String line : textArea.getText().split("\\n")) {
  //              System.out.println(line);

            }



        if (e.getSource() == exitButton) {
            System.exit(0);
        }

    }



    public static void main(String[] args) {
        TextEditor txtEditor = new TextEditor();


    }


}