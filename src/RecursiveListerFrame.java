import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecursiveListerFrame extends JFrame
{
    JPanel mainPanel, titlePanel, displayPanel, buttonPanel;
    JLabel titleLabel;
    JTextArea displayTextArea;
    JScrollPane scroller;
    JButton quitButton, fileButton;

    public RecursiveListerFrame()
    {
        setTitle("Recursive File Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        createTitlePanel();
        createDisplayPanel();
        createButtonPanel();

        setVisible(true);
    }

    private void createTitlePanel()
    {
        titlePanel = new JPanel();

        titleLabel = new JLabel("Recursive File Lister");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));

        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }

    private void createDisplayPanel()
    {
        displayPanel = new JPanel();

        displayTextArea = new JTextArea(50, 80);
        displayTextArea.setEditable(false);

        scroller = new JScrollPane();

        displayPanel.add(scroller);
        displayPanel.add(displayTextArea);
        mainPanel.add(displayPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel()
    {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        fileButton = new JButton("Search Directory");
        fileButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        fileButton.addActionListener(e -> directorySearch());

        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        quitButton.addActionListener(new ActionListener()
        {
            JOptionPane optionPane = new JOptionPane();
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int result = JOptionPane.showConfirmDialog(optionPane, "You sure you want to exit?", "Exiting", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
                else
                {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });

        buttonPanel.add(fileButton);
        buttonPanel.add(quitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void directorySearch()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Pick your directory: ");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File chosenDirectory = chooser.getSelectedFile();
            displayTextArea.setText("Chosen Directory: " + chosenDirectory + "\n");
            displayTextArea.append("Files and sub directories from the directory you chose are listed below. \n");

            fileList(chosenDirectory);
        }
        else
        {
            displayTextArea.append("Couldn't find that file, try another");
        }
    }

    private void fileList(File file)
    {
        File FileNames[] = file.listFiles();

        if (FileNames != null)
        {
            for (File fileName : FileNames)
            {
                displayTextArea.append(fileName + "\n");

                if (fileName.isDirectory())
                {
                    fileList(fileName);
                }
            }
        }
    }
}