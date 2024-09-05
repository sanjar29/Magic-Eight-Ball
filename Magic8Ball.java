import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Magic8Ball {
    private JFrame frame;
    private JPanel startPanel, questionPanel, resultPanel;
    private JButton yesButton, noButton, readyButton, continueButton, exitButton;
    private JTextArea resultArea;
    private Random roller = new Random();
    private int magicNumber;
    private Timer resultTimer;

    public Magic8Ball() {
        // Set up the frame
        frame = new JFrame("Magic 8 Ball");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());
        frame.getContentPane().setBackground(new Color(0x485D18));

        // Set up the start panel
        startPanel = new JPanel();
        startPanel.setLayout(new GridBagLayout());
        startPanel.setBackground(new Color(0x485D18));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Spacing between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JTextArea startMessage = new JTextArea("Are you ready to have your future read?");
        startMessage.setFont(new Font("Arial", Font.PLAIN, 18));
        startMessage.setEditable(false);
        startMessage.setBackground(new Color(0x485D18));
        startMessage.setForeground(Color.WHITE);
        startMessage.setLineWrap(true);
        startMessage.setWrapStyleWord(true);
        startMessage.setOpaque(false);
        startMessage.setPreferredSize(new Dimension(400, 100));
        startPanel.add(startMessage, gbc);

        gbc.gridy = 1;
        JPanel startButtons = new JPanel();
        startButtons.setLayout(new FlowLayout());
        startButtons.setBackground(new Color(0x485D18));

        yesButton = new JButton("Yes");
        yesButton.setBackground(Color.GREEN);
        yesButton.setForeground(Color.WHITE);
        yesButton.setFocusPainted(false);
        yesButton.setBorderPainted(false);
        yesButton.setOpaque(true);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToQuestionPanel();
            }
        });

        noButton = new JButton("No");
        noButton.setBackground(Color.RED);
        noButton.setForeground(Color.WHITE);
        noButton.setFocusPainted(false);
        noButton.setBorderPainted(false);
        noButton.setOpaque(true);
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        startButtons.add(yesButton);
        startButtons.add(noButton);
        startPanel.add(startButtons, gbc);

        // Set up the question panel
        questionPanel = new JPanel();
        questionPanel.setLayout(new GridBagLayout());
        questionPanel.setBackground(new Color(0x485D18));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JTextArea questionMessage = new JTextArea(
                "Now think of a question to yourself. When ready, click this button.");
        questionMessage.setFont(new Font("Arial", Font.PLAIN, 18));
        questionMessage.setEditable(false);
        questionMessage.setBackground(new Color(0x485D18));
        questionMessage.setForeground(Color.WHITE);
        questionMessage.setLineWrap(true);
        questionMessage.setWrapStyleWord(true);
        questionMessage.setOpaque(false);
        questionMessage.setPreferredSize(new Dimension(400, 100));
        questionPanel.add(questionMessage, gbc);

        gbc.gridy = 1;
        readyButton = new JButton("Ready");
        readyButton.setBackground(Color.BLUE);
        readyButton.setForeground(Color.WHITE);
        readyButton.setFocusPainted(false);
        readyButton.setBorderPainted(false);
        readyButton.setOpaque(true);
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showResult();
            }
        });

        JPanel questionButtons = new JPanel();
        questionButtons.setLayout(new FlowLayout());
        questionButtons.setBackground(new Color(0x485D18));
        questionButtons.add(readyButton);
        questionPanel.add(questionButtons, gbc);

        // Set up the result panel
        resultPanel = new JPanel();
        resultPanel.setLayout(new GridBagLayout());
        resultPanel.setBackground(new Color(0x485D18));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 16));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(0x485D18));
        resultArea.setForeground(Color.WHITE);
        resultArea.setPreferredSize(new Dimension(400, 100));
        resultPanel.add(resultArea, gbc);

        gbc.gridy = 1;
        JPanel resultButtons = new JPanel();
        resultButtons.setLayout(new FlowLayout());
        resultButtons.setBackground(new Color(0x485D18));

        continueButton = new JButton("Continue");
        continueButton.setBackground(Color.GREEN);
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        continueButton.setBorderPainted(false);
        continueButton.setOpaque(true);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
                switchToQuestionPanel();
            }
        });

        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setOpaque(true);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        resultButtons.add(continueButton);
        resultButtons.add(exitButton);
        resultPanel.add(resultButtons, gbc);

        // Add panels to the frame
        frame.add(startPanel, "Start");
        frame.add(questionPanel, "Question");
        frame.add(resultPanel, "Result");

        // Display the frame
        frame.setVisible(true);
    }

    private void switchToQuestionPanel() {
        CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
        cl.show(frame.getContentPane(), "Question");
    }

    private void showResult() {
        magicNumber = roller.nextInt(100);
        String result = decidePolarity(magicNumber);
        resultArea.setText(result);

        // Display result for 3 seconds, then show continue prompt
        Timer resultTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("Wanna continue?");
                continueButton.setVisible(true);
                exitButton.setVisible(true);
            }
        });
        resultTimer.setRepeats(false);
        resultTimer.start();

        CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
        cl.show(frame.getContentPane(), "Result");

        // Hide continue and exit buttons initially
        continueButton.setVisible(false);
        exitButton.setVisible(false);
    }

    private String decidePolarity(int magicNum) {
        if (magicNum % 3 == 0) {
            return positiveAnswer(magicNum);
        } else if (magicNum % 2 == 0) {
            return negativeAnswer(magicNum);
        } else {
            return neutralAnswer(magicNum);
        }
    }

    private String positiveAnswer(int magicNum) {
        if (magicNum < 12) {
            return "You may rely on it.";
        } else if (magicNum == 12) {
            return "As I see it, yes.";
        } else if (magicNum >= 13 && magicNum <= 27) {
            return "Signs point to yes.";
        } else if (magicNum > 27 && magicNum < 78) {
            return "Outlook good.";
        } else {
            return "Without a doubt.";
        }
    }

    private String negativeAnswer(int magicNum) {
        if (magicNum < 5) {
            return "Very doubtful";
        } else if (magicNum == 5) {
            return "Don't count on it.";
        } else if (magicNum >= 6 && magicNum <= 43) {
            return "My reply is no.";
        } else if (magicNum > 43 && magicNum < 89) {
            return "My sources say no.";
        } else {
            return "Outlook not so good.";
        }
    }

    private String neutralAnswer(int magicNum) {
        if (magicNum < 22) {
            return "Concentrate and ask again.";
        } else if (magicNum == 22) {
            return "Reply hazy, try again.";
        } else if (magicNum >= 23 && magicNum <= 35) {
            return "Ask again later.";
        } else if (magicNum > 35 && magicNum < 62) {
            return "Better not tell you now.";
        } else {
            return "Cannot predict now.";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Magic8Ball();
            }
        });
    }
}
