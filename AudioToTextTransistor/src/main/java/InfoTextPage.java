import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;

class InfoTextPage extends javax.swing.JFrame {
    private JTextArea textArea;

    public InfoTextPage() {
        ImageIcon icon= new ImageIcon("icon.png");

        textArea =new JTextArea();
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Your video/audio's text is down below",TitledBorder.CENTER,TitledBorder.CENTER));

        JScrollPane jScrollPane=new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.setTitle("Your Video Transcript!");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setLocation(0, 0);
        this.setResizable(false);
        this.setBackground(Color.lightGray);
        this.setIconImage(icon.getImage());
        this.getContentPane().add(jScrollPane);

        getTextFromFile();
    }
    //method takes the script then write to the gui
    private void getTextFromFile(){
        String sentence;
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.getFileLocation()))){
            while(true){
                sentence = reader.readLine();
                textArea.append(sentence);
                if(sentence.equals("")){
                    break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            //Bir sey yok devamke
        }
    }

    public static void main(String[] args) {
        new InfoTextPage();
    }
}
