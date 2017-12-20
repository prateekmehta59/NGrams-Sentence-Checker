import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;


public class WordCheckUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton Export;
    private JTextPane checkOut;
    private JTextPane checkIn;
    private JTextField Files;
    private JButton fileButton;
    public String[] GMwords;
    private Ngram NgramModel;


    public WordCheckUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        NgramModel = new Ngram("./Dataset/w1.txt");
    }

    private void onCheck() {
        // highlight words
        checkOut.setText("");
        checkOut.setText(checkIn.getText());
//        Highlighter h = checkOut.getHighlighter();
//        h.removeAllHighlights();
//        String text = checkOut.getText();
//        String words[] = text.split(" ");
//        for(int i = 0;i<words.length;i++){
//            String temp = words[i];
//
//            if(temp.equals("word")){
//                try{
//                    h.addHighlight(i, temp.length(), DefaultHighlighter.DefaultPainter);
//                }
//                catch(Exception e){
//                }
//            }
//        }
        highlight(1, checkOut.getText(),0.0);
    }

    //highlight with color code. Chance of wrong will increase the redness
    private void highlight(int iTarget, String allText, double chanceOfWrong){
        Highlighter h = checkOut.getHighlighter();
        h.removeAllHighlights();
        Double result = NgramModel.stringProb(allText);
        System.out.println(allText);
        if(result <= NgramModel.stringProb("a car?")){
            try {
                h.addHighlight(0, allText.length(), DefaultHighlighter.DefaultPainter);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        /*
        String eachWords[] = allText.split(" ");
        String Target = eachWords[iTarget];
        int index = 0;
        for (int i = 0; i<iTarget;i++){
            index +=eachWords[i].length();
        }
        index++;
        try{
            h.addHighlight(index, index+eachWords[iTarget].length(), DefaultHighlighter.DefaultPainter);
        }
        catch(Exception e){
        }
        */
    }

    //output file name and directory
    private String fileBrowse() {
        final JFileChooser fc = new JFileChooser();
        int filechosed = fc.showOpenDialog(WordCheckUI.this);

        if (filechosed == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            //logging.append("Opening: " + file.getName() + "." + newline);
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

}
