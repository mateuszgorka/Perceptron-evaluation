import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


// WORKING ON...

public class GUI extends JFrame implements ActionListener {



    private JTextField learnButton, testButton, epochsButton;
    private JButton trainButton;
    private JTextArea results;
    private Teacher teacher;




    public GUI(){

        super("PERCEPTRON by s31289");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,500);
        setLayout(new GridLayout(0,2));

        learnButton = new JTextField();
        testButton = new JTextField();
        epochsButton = new JTextField();
        trainButton = new JButton("Train the motherfucker");


        results = new JTextArea();
        results.setEditable(false);   // -> zeby sie tylko wyświetlalo


        add(new JLabel("Training file: "));
        add(learnButton);

        add(new JLabel("Testing file: "));
        add(testButton);

        add(new JLabel("Epochs: "));
        add(epochsButton);

        add(trainButton);
        trainButton.addActionListener(this);

        add(new JLabel("Results: "));
        add(new JScrollPane(results)); // -> scrolling trolling

        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == trainButton){

            String learn = learnButton.getText();
            String test = testButton.getText();
            String epochsString = epochsButton.getText();
            int epochs = Integer.parseInt(epochsString);


            // -> trzeba sprawdzic czy sa

            if (!new File(learn).exists() || !new File(test).exists()) {
                JOptionPane.showMessageDialog(this, "Missing file");
            }







        }
    }





}
