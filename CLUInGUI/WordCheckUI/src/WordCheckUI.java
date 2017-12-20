import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import com.company.Ngram;

public class WordCheckUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton Export;
    private JTextPane checkOut;
    private JTextPane checkIn;
    private JTextField Files;
    private JButton fileButton;
    private JButton SPEAK;
    private Ngram ngramModel;
    private StringBuffer outText;

    //public String[] GMwords;
    private StringBuffer voice_transcripted_line = new StringBuffer();
    private ArrayList<String> string_to_n_gram = new ArrayList<String>();
    private HashSet<Integer> indices_to_highlight = new HashSet<>();


    public WordCheckUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        ngramModel = new Ngram("./dataset/NGramData/");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCheck();
            }
        });

        Export.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onExport(); }
        });
        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { Files.setText(fileBrowse()); }
        });

        SPEAK.addMouseListener(new MouseListener() {
            audioCapture recorder = new audioCapture();
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) {
                recorder.start();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                recorder.finish();
                voice_transcripted_line = helperFunctions.getTranscibedText(false);
                set_text();
            }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });


        // call shutDown() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                shutDown();
            }
        });

        // call shutDown() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shutDown();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        Files.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                buttonOK.grabFocus();
                fileBrowse();
            }
        });

    }

    private void set_text(){
        try{
            checkIn.setText(voice_transcripted_line.toString().substring(15));
        }catch (Exception e){
            checkIn.setText("");
            System.out.println("no transcription found");
        }
    }

    private void onCheck() {
        outText = new StringBuffer();
        string_to_n_gram = helperFunctions.getParsedString(checkIn.getText());
        indices_to_highlight = ngramModel.highlightWrongWords(string_to_n_gram);
        checkOut.setText("");
        for(int ii = 0;ii < string_to_n_gram.size();ii++){
            if(!string_to_n_gram.get(ii).equals("1") && !string_to_n_gram.get(ii).equals("2")){
                outText.append(string_to_n_gram.get(ii) + " ");
            }
        }
        checkOut.setText(outText.toString());
        highlight_new();
    }


    private void highlight_new(){
        Highlighter h = checkOut.getHighlighter();
        h.removeAllHighlights();
        int count = 0;
        try{
            for(int index = 0;index < string_to_n_gram.size();index++){
                if(indices_to_highlight.contains(index)){
                    if(!string_to_n_gram.get(index).equals("2") && !string_to_n_gram.get(index).equals("1")){
                        h.addHighlight(count, count+string_to_n_gram.get(index).length(), DefaultHighlighter.DefaultPainter);
                    }
                }
                if(!string_to_n_gram.get(index).equals("1") && !string_to_n_gram.get(index).equals("2")){
                    count = count + string_to_n_gram.get(index).length() + 1;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //output file name and directory
    private String fileBrowse() {
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fc.setFileFilter(filter);
        int filechosed = fc.showOpenDialog(WordCheckUI.this);
        if (filechosed == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            checkIn.setText(helperFunctions.RTxtfile(file).toString());
        } else {
            //log.append("Open command cancelled by user." + newline);
        }
        return fc.getSelectedFile().toString();
    }

    private void onExport() {
        // add your code here
        //dispose();
    }

    private void shutDown() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        WordCheckUI dialog = new WordCheckUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}