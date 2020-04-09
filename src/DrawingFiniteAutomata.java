
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DrawingFiniteAutomata extends JFrame implements MouseListener, MouseMotionListener, ActionListener, KeyListener {

    public static void main(String[] args) {
        try {
            DrawingFiniteAutomata gui = new DrawingFiniteAutomata();
        } catch (Exception ex) {

        }

        // System.out.println("Ctrl+...");
        // System.out.println(" - S : save");
        // System.out.println(" - O : open");
        // System.out.println(" - N : all step");
        // System.out.println(" - L : sub Language");
        // System.out.println(" - R : DFA to RegularExpression");
        // System.out.println(" - I : Show Infomation Automata");
        // System.out.println(" - A : RegularExpression to NFA");
        // System.out.println("Spacebar drag: draw line");
        // System.out.println("Double click : draw state");
        // System.out.println("Click on state or line");
        // System.out.println(" - type for rename");
        // System.out.println(" - key del for delete it");
        // System.out.println(" - mouse drags to move it");
        // System.out.println("  + click line at character on middle line for select");
        // System.out.println("  + click in circle state for select");
    }
    Canvas c;
    String mode = "state"; //state,transition
    Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 24);
    Object selected = null;

    ArrayList<State> states = new ArrayList<>();
    ArrayList<Transition> transitions = new ArrayList<>();
    ArrayList<String> alphabet = new ArrayList<>();

    Temp temp = null; //temp link

    //-----###s
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    //Calculate the frame location  
    int x = (screenSize.width - getWidth()) / 2;
    int y = (screenSize.height - getHeight()) / 2;
    int x1 = (screenSize.width - getWidth());
    int y1 = (screenSize.height - getHeight());
    //Set the new frame location  
    int index = 0;

    ArrayList<Object> allStep = new ArrayList<Object>();

    JFrame frameInfo = new JFrame("Info");
    JFrame frameStep = new JFrame("Step");
    JFrame frameRun = new JFrame("Run");
    JFrame frameRegToNFA = new JFrame("Regular Expression to NFA");
    JFrame frameShowSublang = new JFrame("Subset Language");
    JFrame frameDFAToReg = new JFrame("DFA to Regular Expression");
    JFrame frameHelp = new JFrame("Help");

    JPanel boxShowInfo = new JPanel();
    JPanel boxShowStep = new JPanel();
    JPanel boxSave = new JPanel();
    JPanel boxOpen = new JPanel();
    JPanel boxRun = new JPanel();
    JPanel boxRegToNFA = new JPanel();
    JPanel boxShowSub = new JPanel();
    JPanel boxShowReg = new JPanel();
    JPanel boxHelp = new JPanel();

    JButton runButt = new JButton();
    JButton showInfoButt = new JButton();
    JButton saveButt = new JButton();
    JButton openButt = new JButton();
    JButton okRunButt = new JButton();
    JButton okRunButt2 = new JButton();
    JButton okRunButt3 = new JButton();
    JButton run_1_Butt = new JButton();
    JButton run_2_Butt = new JButton();
    JButton stepByStepButt = new JButton();
    JButton runContinueButt = new JButton();
    JButton showStringAccButt = new JButton();
    JButton regExpToNFAButt = new JButton();
    JButton DFAToRegExpButt = new JButton();
    JButton helpButt = new JButton();

    JFileChooser pathSave = new JFileChooser();
    JFileChooser pathOpen = new JFileChooser();

    JTextField inString = new JTextField(40);
    JTextField inString2 = new JTextField(40);
    JTextField inString3 = new JTextField(40);

    JTextArea showStringAccLabel = new JTextArea();
    JTextArea showReg = new JTextArea();
    JTextArea showInfoLabel = new JTextArea();

    JScrollPane scrollStringAccLabel = new JScrollPane(boxShowStep, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JScrollPane scrollShowReg = new JScrollPane(boxShowReg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JScrollPane scrollShowInfo = new JScrollPane(boxShowInfo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    //JLabel showStringAccLabel = new JLabel();
    //JLabel showInfoLabel = new JLabel("", SwingConstants.CENTER);
    JLabel msgEnterString = new JLabel();
    JLabel msgEnterString2 = new JLabel();
    JLabel msgFile = new JLabel();
    JLabel showCurrentFile = new JLabel();
    //JLabel showReg = new JLabel();
    JLabel msgInString = new JLabel();
    JLabel myString = new JLabel();
    JLabel helpString = new JLabel();

    JComboBox listPathRun = new JComboBox();
    //-----###e
    JPanel menubar = new JPanel();
    int shift = 50;

    DrawingFiniteAutomata() {
        super("canvas");

        // create a empty canvas 
        c = new Canvas() {
            @Override
            public void paint(Graphics g) {
            }
        };
        c.setBackground(Color.white);

        // add mouse listener 
        c.addMouseListener(this);
        c.addMouseMotionListener(this);

        // add keyboard listener 
        c.addKeyListener(this);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        //-----###s
        //set font 
        int fontSize = 17;
        String font = "Serif";
        Font fontObj = new Font(font, Font.PLAIN, fontSize);

        runButt.setFont(fontObj);
        showInfoButt.setFont(fontObj);
        saveButt.setFont(fontObj);
        openButt.setFont(fontObj);
        okRunButt.setFont(fontObj);
        okRunButt2.setFont(fontObj);
        okRunButt3.setFont(fontObj);
        run_1_Butt.setFont(fontObj);
        run_2_Butt.setFont(fontObj);
        stepByStepButt.setFont(fontObj);
        runContinueButt.setFont(fontObj);
        showStringAccButt.setFont(fontObj);
        regExpToNFAButt.setFont(fontObj);
        DFAToRegExpButt.setFont(fontObj);
        helpButt.setFont(fontObj);

        showStringAccLabel.setFont(fontObj);
        showInfoLabel.setFont(fontObj);
        msgEnterString.setFont(fontObj);
        msgEnterString2.setFont(fontObj);
        msgFile.setFont(fontObj);
        showCurrentFile.setFont(fontObj);
        showReg.setFont(fontObj);
        msgInString.setFont(fontObj);
        myString.setFont(fontObj);
        helpString.setFont(fontObj);

//        scrollStringAccLabel.add(showStringAccLabel);
//        scrollShowReg.add(showReg);
//        scrollShowInfo.add(showInfoLabel);
        //frame panel
        boxShowInfo.setBackground(Color.white);
        //frameInfo.add(boxShowInfo);

        boxShowStep.setBackground(Color.white);

        boxSave.setBackground(Color.white);

        boxOpen.setBackground(Color.white);

        boxRun.setBackground(Color.white);
        frameRun.add(boxRun);

        boxRegToNFA.setBackground(Color.white);
        frameRegToNFA.add(boxRegToNFA);

        boxShowSub.setBackground(Color.white);
        frameShowSublang.add(boxShowSub);

        boxShowReg.setBackground(Color.white);
        //frameDFAToReg.add(boxShowReg);

        boxHelp.setBackground(Color.white);
        frameHelp.add(boxHelp);

        //label
        msgEnterString.setText("Enter String");
        msgEnterString2.setText("Enter Number");

        msgFile.setText("File");
        msgFile.setBounds(x1 - 400 + shift, 130, 80, 23);
        add(msgFile);

        msgInString.setText("Input String");
        msgInString.setBounds(x1 - 400 + shift, 160, 100, 23);
        add(msgInString);

        myString.setText(inString.getText());
        myString.setBounds(x1 - 400 + 100 + shift, 160, 200, 23);
        add(myString);

        showCurrentFile.setBounds(x1 - 400 + 100 + shift, 130, 150, 23);
        getContentPane().add(showCurrentFile);

        //button
        saveButt.setText("save");
        saveButt.setBounds(x1 - 400 + shift, 100, 150, 23);
        getContentPane().add(saveButt);
        saveButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveButtAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(DrawingFiniteAutomata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        openButt.setText("open");
        openButt.setBounds(x1 - 400 + 150 + shift, 100, 150, 23);
        getContentPane().add(openButt);
        openButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    openButtAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(DrawingFiniteAutomata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        runButt.setText("Run");
        runButt.setBounds(x1 - 400 + shift, 200, 300, 23);
        getContentPane().add(runButt);
        runButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runButtAction(e);
            }
        });

        okRunButt.setText("Ok");
        okRunButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okRunButtAction(e);
            }
        });

        okRunButt2.setText("Ok");
        okRunButt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okRunButtAction2(e);
            }
        });

        okRunButt3.setText("Ok");
        okRunButt3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okRunButtAction3(e);
            }
        });

        listPathRun.setBounds(x1 - 400 + shift, 230, 150, 23);
        getContentPane().add(listPathRun);

        stepByStepButt.setText("Step by Step");
        stepByStepButt.setBounds(x1 - 400 + 150 + shift, 230, 150, 23);
        getContentPane().add(stepByStepButt);
        stepByStepButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    stepByStepButtAction(e);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DrawingFiniteAutomata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        runContinueButt.setText("Run continue");
        runContinueButt.setBounds(x1 - 400 + 150 + shift, 260, 150, 23);
        getContentPane().add(runContinueButt);
        runContinueButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    runContinueButtAction(e);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DrawingFiniteAutomata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        showStringAccButt.setText("Show String Accept");
        showStringAccButt.setBounds(x1 - 400 + shift, 320, 300, 23);
        getContentPane().add(showStringAccButt);
        showStringAccButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showSubLang(e);
                } catch (Exception ex) {

                }
            }
        });

        regExpToNFAButt.setText("Regular Expression to NFA");
        regExpToNFAButt.setBounds(x1 - 400 + shift, 350, 300, 23);
        getContentPane().add(regExpToNFAButt);
        regExpToNFAButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    regExpToNFAButtAction(e);
                } catch (Exception ex) {

                }
            }
        });

        DFAToRegExpButt.setText("DFA to Regular Expression");
        DFAToRegExpButt.setBounds(x1 - 400 + shift, 410, 300, 23);
        getContentPane().add(DFAToRegExpButt);
        DFAToRegExpButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("key");
                try {
                    if (isDFA()) {
                        DFAToRegExpButtAction(e);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Automata is not DFA",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Sorry Cannot find Regular Expression from This DFA",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    //ex.printStackTrace();
                }
            }
        });

        showInfoButt.setText(
                "Show Info");
        showInfoButt.setBounds(x1
                - 400 + shift, 600, 300, 23);
        getContentPane()
                .add(showInfoButt);
        showInfoButt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    showInfoButtAction(e);
                } catch (Exception ex) {

                }
            }
        }
        );

        helpButt.setText("Help");
        helpButt.setBounds(x1 - 400 + shift, 40, 300, 23);
        getContentPane().add(helpButt);
        helpButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpButtAction(e);
            }
        });

        //-----###e     
        menubar.setBackground(Color.cyan);
        menubar.setBounds(x1 - 400, 0, 400, y1);
        c.setBounds(0, 0, x1 - 400, y1);
        setBounds(0, 0, x1, y1);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        //setVisible(true);
        add(c);
        add(menubar);
        // setSize(1500, 1000);
        show();
    }

    //-----###s
    void helpButtAction(ActionEvent e) {
        String help = "<html>";
        help += "Double click for create state<br>";
        help += "Double click on state for change state to accept state<br>";
        help += "Double click on accept state for change accept state to state<br>";
        help += "Click on state then type for rename<br>";
        help += "Click on state then press delete for remove state<br>";
        help += "Press and hold spacebar with drag mouse for create link<br>";
        help += "Click on character on link then type for rename<br>";
        help += "Click on character on link then drag mouse for move link<br>";
        help += "Click on character on link then press delete for remove link<br>";
        help += "Press Button save for save automata on canvas to json file<br>";
        help += "Press Button open for open automata json file to canvas<br>";
        help += "Press Button run then enter string and select path in dropdown<br>";
        help += "Press Button step by step for run string each character by click<br>";
        help += "Press Button run continue for run string all<br>";
        help += "Press Button show string accept then enter number of max length string and press ok<br>";
        help += "Press Button regular expression to nfa then enter regular expression for tranformation regular expression to nfa on canvas<br>";
        help += "Press Button dfa to regular expression for create regular expression from dfa on canvass<br>";
        help += "Press Button show info for show short information of automata<br>";
        helpString.setText(help);
        boxHelp.add(helpString);
        boxHelp.setAutoscrolls(true);

        //frameInfo.setSize(200, 500);
        frameHelp.setBounds(x - 450, y - 250, 900, 500);
        frameHelp.setVisible(true);
    }

    void stepByStepButtAction(ActionEvent e) throws InterruptedException {

        ArrayList<Object> path = createPath();
        if (index >= path.size()) {
            index = 0;
        }
        Object obj = path.get(index);
        if (obj instanceof State) {
            selected(((State) obj).x, ((State) obj).y);
        } else if (obj instanceof Transition) {
            selected(((Transition) obj).curveX, ((Transition) obj).curveY);
        } else {
            if (selected instanceof State) {
                State s = (State) selected;
                s.isSelect = false;
            } else if (selected instanceof Transition) {
                Transition t = (Transition) selected;
                t.isSelect = false;
            }
            selected = null;
        }
        draw();
        index++;
        if (index == path.size()) {
            ArrayList<State> accepts = getAcceptState();
            if (accepts.contains(obj)) {
                JOptionPane.showMessageDialog(null,
                        "Accept",
                        "Status",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Not Accept",
                        "Status",
                        JOptionPane.PLAIN_MESSAGE);
            }

        }
        if (index >= path.size()) {
            index = 0;
        }
    }

    ArrayList<Object> createPath() {
        ArrayList<Object> path = new ArrayList<>();
        ArrayList<State> step = getAllStepFromStr(inString.getText().trim()).get(listPathRun.getSelectedIndex());
        for (Transition tt : transitions) {
            if (tt.stateA == null) {
                path.add(tt);
                break;
            }
        }
        path.add(step.get(0));
        for (int i = 1; i < step.size(); i++) {
            if (step.get(i) == null) {
                path.add(null);
                break;
            }
            Transition tt = getTransition(step.get(i - 1), step.get(i));
            path.add(tt);
            path.add(step.get(i));
        }
        return path;
    }

    void runContinueButtAction(ActionEvent e) throws InterruptedException {
        ArrayList<Object> path = createPath();
        for (Object obj : path) {
            if (obj instanceof State) {
                selected(((State) obj).x, ((State) obj).y);
            } else if (obj instanceof Transition) {
                selected(((Transition) obj).curveX, ((Transition) obj).curveY);
            } else {
                if (selected instanceof State) {
                    State s = (State) selected;
                    s.isSelect = false;
                } else if (selected instanceof Transition) {
                    Transition t = (Transition) selected;
                    t.isSelect = false;
                }
                selected = null;
            }
            draw();
            Thread.sleep(400);
        }
        Object obj = path.get(path.size() - 1);
        ArrayList<State> accepts = getAcceptState();
        if (accepts.contains(obj)) {
            JOptionPane.showMessageDialog(null,
                    "Accept",
                    "Status",
                    JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Not Accept",
                    "Status",
                    JOptionPane.PLAIN_MESSAGE);
        }
        index = 0;
    }

    void DFAToRegExpButtAction(ActionEvent e) {
        showReg.setText(createEquation2());
        showReg.setEditable(false);
        boxShowReg.add(showReg);
        boxShowReg.setAutoscrolls(true);

        frameDFAToReg.add(scrollShowReg);

        frameDFAToReg.setSize(500, 200);
        frameDFAToReg.setBounds(x - 250, y - 100, 500, 200);
        frameDFAToReg.setVisible(true);
    }

    void regExpToNFAButtAction(ActionEvent e) {

        boxRegToNFA.add(msgEnterString);
        boxRegToNFA.add(inString2);
        boxRegToNFA.add(okRunButt2);

        frameRegToNFA.add(boxRegToNFA);
        frameRegToNFA.setBounds(x - 250, y - 75, 500, 150);
        //frameRegToNFA.setSize(500, 150);
        frameRegToNFA.setVisible(true);

    }

    void showSubLang(ActionEvent e) {

        boxShowSub.add(msgEnterString2);
        boxShowSub.add(inString3);
        boxShowSub.add(okRunButt3);

        frameShowSublang.add(boxShowSub);
        frameShowSublang.setBounds(x - 250, y - 75, 500, 150);
        frameShowSublang.setVisible(true);

    }

    void okRunButtAction2(ActionEvent e) {
        frameRegToNFA.setVisible(false);
        Automata nfa = regexToNFA(inString2.getText().trim());
        states = nfa.sts;
        transitions = nfa.trs;
        alphabet = nfa.alp;
        Automata.x = 200;
        Automata.y = 200;
        // System.out.println("Regular expression to NFA Successed !!!");
        draw();
    }

    void okRunButtAction3(ActionEvent e) {
        frameShowSublang.setVisible(false);
        showStringAccButtAction(e);
        draw();
    }

    void okRunButtAction(ActionEvent e) {

        frameRun.setVisible(false);
        //showPathButtAction(e);
        listPathRun.removeAllItems();

        ArrayList<ArrayList<State>> ohoh = getAllStepFromStr(inString.getText().trim());
        ArrayList<State> accepts = getAcceptState();
        boolean isAccept = false;
        for (int i = 0; i < ohoh.size(); i++) {

            String s = ohoh.get(i).get(0).text;
            for (int j = 1; j < ohoh.get(i).size(); j++) {
                if (ohoh.get(i).get(j) == null) {
                    s += ", Trap";
                    continue;
                }
                s += "," + ohoh.get(i).get(j).text;
            }
            listPathRun.addItem(s);
            Object obj = ohoh.get(i).get(ohoh.get(i).size() - 1);
            if (accepts.contains(obj)) {
                isAccept = true;
            }
        }
        if (isAccept) {
            myString.setText(inString.getText() + "  is Accepted");
            JOptionPane.showMessageDialog(null,
                    "Accept",
                    "Status",
                    JOptionPane.PLAIN_MESSAGE);
        } else {
            myString.setText(inString.getText() + " is Not Accepted");
            JOptionPane.showMessageDialog(null,
                    "Not Accept",
                    "Status",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    void saveButtAction(ActionEvent e) throws IOException {
        pathSave.setBounds(60, 120, 750, 450);
        boxSave.add(pathSave);

        int ret = pathSave.showDialog(null, "save");
        String path = "";

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathSave.getSelectedFile();
            path = filePath.getPath();
            // System.out.println(path);
            save(path);
        }

    }

    void openButtAction(ActionEvent e) throws IOException {
        pathOpen.setBounds(60, 120, 750, 450);
        boxOpen.add(pathOpen);

        int ret = pathOpen.showDialog(null, "open");
        String path = "";

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathOpen.getSelectedFile();
            path = filePath.getPath();
            // System.out.println(path);
            open(path);
            draw();
        }

        showCurrentFile.setText(nameFile(path));
    }

    void runButtAction(ActionEvent e) {
        boxRun.add(msgEnterString);
        boxRun.add(inString);
        boxRun.add(okRunButt);

        frameRun.add(boxRun);
        frameRun.setBounds(x - 250, y - 75, 500, 150);
        //frameRun.setSize(500, 150);
        frameRun.setVisible(true);

    }

    void showStringAccButtAction(ActionEvent e) {
        int n = 0;
        try {
            n = Integer.parseInt(inString3.getText().trim());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Input must a number",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String strAllStep = "";
        ArrayList<String> allString = getSubLanguage(n);
        strAllStep += allString.get(0);
        for (int i = 1; i < allString.size(); i++) {
            strAllStep += " , " + allString.get(i);
            if (i % 10 == 0) {
                strAllStep += "\n";
            }

        }

        showStringAccLabel.setText(strAllStep);
        showStringAccLabel.setEditable(false);
        //frameStep.add(showStringAccLabel);
        boxShowStep.add(showStringAccLabel);

        frameStep.add(scrollStringAccLabel);

        //frameStep.setSize(600, 500);
        frameStep.setBounds(x - 300, y - 250, 600, 500);
        frameStep.setVisible(true);
    }

    void showInfoButtAction(ActionEvent e) {
        showInfoLabel.setText(getInfoGame());
        showInfoLabel.setEditable(false);
        boxShowInfo.add(showInfoLabel);
        boxShowInfo.setAutoscrolls(true);

        frameInfo.add(scrollShowInfo);

        //frameInfo.setSize(200, 500);
        frameInfo.setBounds(x - 150, y - 200, 300, 400);
        frameInfo.setVisible(true);
    }

    public String nameFile(String path) {
        int index = path.lastIndexOf("\\");
        String name = path.substring(index + 1, path.length());
        return name;
    }

    //-----###e
    public String createEquation2() {
        String textOut = "<html>";
        ArrayList<String> state1 = new ArrayList<>();
        ArrayList<String> alphabet1 = new ArrayList<>();
        ArrayList<String> equation1 = new ArrayList<>();
        for (int i = 0; i < states.size(); i++) {
            String equation = "L" + states.get(i).text.trim() + "=";
            int count = 0;
            for (int j = 0; j < alphabet.size(); j++) {
                if (getNextState(states.get(i), alphabet.get(j).trim()).size() == 1) {
                    if (count == 0) {
                        equation += (alphabet.get(j).trim() + "L" + getNextState(states.get(i), alphabet.get(j).trim()).get(0).text.trim());
                        count++;
                    } else {
                        equation += ("U" + alphabet.get(j).trim() + "L" + getNextState(states.get(i), alphabet.get(j).trim()).get(0).text.trim());
                    }

                }
            }
            if (getAcceptState().contains(states.get(i))) {
                equation += ("Uε");
            }
            equation1.add(equation);
        }
        for (int i = 0; i < states.size(); i++) {
            state1.add(states.get(i).text.trim());
        }
        for (int i = 0; i < alphabet.size(); i++) {
            alphabet1.add(alphabet.get(i).trim());
        }
        // System.out.print("AllState: ");
        for (int i = 0; i < state1.size(); i++) {
            // System.out.print(state1.get(i) + ", ");
            textOut += state1.get(i) + ", ";
        }
        // System.out.println();
        // System.out.print("AllAlphabet: ");
        for (int i = 0; i < alphabet1.size(); i++) {
            // System.out.print(alphabet1.get(i) + ", ");
            textOut += alphabet1.get(i) + ", ";
        }
        // System.out.println();
        // System.out.println("All equation is ");
        for (int i = 0; i < equation1.size(); i++) {
            // System.out.println((i + 1) + ". " + equation1.get(i));
            //textOut += 
        }
        // System.out.println("Start State: " + getStartState().text.trim());
        convertToRegular ctr = new convertToRegular(state1, alphabet1);
        ctr.setStartState(getStartState().text.trim());
        ctr.setEquation(equation1);
        return ctr.convert();

    }

    public boolean isDFA() {
        for (Transition t0 : transitions) {
            if (t0.stateA == null) {
                continue;
            }
            for (Transition t1 : transitions) {
                if (t0 != t1) {
                    if (t0.stateA == t1.stateA && t0.text.equals(t1.text)) {
                        return false;
                    }
                }
            }
            if (t0.text.equals("ε")) {
                return false;
            }
        }

        return true;
    }

//////////////////////////////// 0.Backup ////////////////////////////////
    class Backup {

        ArrayList<State> statesBackup;
        ArrayList<Transition> transitionsBackup;
        ArrayList<String> alphabetBackup;

        public Backup() {
            this.statesBackup = states;
            this.transitionsBackup = transitions;
            this.alphabetBackup = alphabet;
        }

    }

    public void save(String path) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        File fileDir = new File(path);
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));

        Backup backup = new Backup();
        out.write(gson.toJson(backup));
        out.close();
    }

    public void open(String path) throws FileNotFoundException, UnsupportedEncodingException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),
                "UTF8"));

        Backup backup = gson.fromJson(bufferedReader, Backup.class
        );
        states = backup.statesBackup;
        transitions = backup.transitionsBackup;
        alphabet = backup.alphabetBackup;
        for (Transition t : transitions) {
            if (t.stateA != null) {
                String text = t.stateA.text;
                for (State s : states) {
                    if (s.text.equals(text)) {
                        t.stateA = s;
                        break;
                    }
                }
            }
            if (t.stateB != null) {
                String text = t.stateB.text;
                for (State s : states) {
                    if (s.text.equals(text)) {
                        t.stateB = s;
                        break;
                    }
                }
            }
        }
    }

    //-----###
    public String getInfoGame() {
        String info = "";

        info += "states={";
        for (int i = 0; i < states.size() - 1; i++) {
            info += states.get(i).text + " ";
        }
        info += states.get(states.size() - 1).text + "}\n";

        info += "alphabet={";
        for (int i = 0; i < alphabet.size() - 1; i++) {
            info += alphabet.get(i) + " ";
        }
        info += alphabet.get(alphabet.size() - 1) + "}\n";

        info += "transitions={\n";
        for (Transition f : transitions) {
            if (f.stateA == null) {
                continue;
            }
            info += "   " + f.stateA.text + "-" + f.text + "->" + f.stateB.text + "\n";
        }
        info += "}\n";

        info += "initial state=" + getStartState().text + "\n";

        info += "final states={";
        ArrayList<State> acceptStates = getAcceptState();
        for (int i = 0; i < acceptStates.size() - 1; i++) {
            info += acceptStates.get(i).text + " ";
        }
        info += acceptStates.get(acceptStates.size() - 1).text + "}";
        return info;
    }
    //-----###

    public String getInfo() {
        String info = "";

        info += "states={";
        for (int i = 0; i < states.size() - 1; i++) {
            info += states.get(i).text + " ";
        }
        info += states.get(states.size() - 1).text + "}\n";

        info += "alphabet={";
        for (int i = 0; i < alphabet.size() - 1; i++) {
            info += alphabet.get(i) + " ";
        }
        info += alphabet.get(alphabet.size() - 1) + "}\n";

        info += "transitions={\n";
        for (Transition f : transitions) {
            if (f.stateA == null) {
                continue;
            }
            info += "\t" + f.stateA.text + "-" + f.text + "->" + f.stateB.text + "\n";
        }
        info += "}\n";

        info += "initial state=" + getStartState().text + "\n";

        info += "final states={";
        ArrayList<State> acceptStates = getAcceptState();
        for (int i = 0; i < acceptStates.size() - 1; i++) {
            info += acceptStates.get(i).text + " ";
        }
        info += acceptStates.get(acceptStates.size() - 1).text + "}";
        return info;
    }

//////////////////////////////// 1. Management ////////////////////////////////
    public void setAlphabet() {
        alphabet = new ArrayList<>();
        for (Transition t : transitions) {
            String temp = t.text;
            if (!alphabet.contains(temp) && temp != null) {
                alphabet.add(temp);
            }
        }
    }

    public State getStartState() {
        for (Transition t : transitions) {
            if (t.stateA == null) {
                return t.stateB;
            }
        }
        return null;
    }

    public ArrayList<State> getAcceptState() {
        ArrayList<State> accepts = new ArrayList<>();
        for (State s : states) {
            if (s.isAccept) {
                accepts.add(s);
            }
        }
        return accepts;
    }

    public ArrayList<State> getNextState(State s, String a) {
        if (s == null || a == null) {
            return null;
        }

        ArrayList<State> nextState = new ArrayList<>();
        for (Transition t : transitions) {
            if (t.stateA == s) {
                State tempS = t.function(a);
                if (tempS != null) {
                    nextState.add(t.stateB);
                }
            }
        }
        return nextState;
    }

    public ArrayList<State> getNextStateWithF(State s) {

        ArrayList<State> nextState = new ArrayList<>();
        for (Transition t : transitions) {
            if (t.stateA == null) {
                continue;
            }
            if (t.stateA == s && t.text.equals("ε")) {
                nextState.add(t.stateB);
            }
        }
        return nextState;
    }

    public Transition getTransition(State q, State r) {
        for (Transition t : transitions) {
            if (t.stateA == q && t.stateB == r) {
                return t;
            }
        }
        return null;
    }

    public ArrayList<ArrayList<State>> getAllStepFromStr(String str) {
        ArrayList<ArrayList<State>> allStep = new ArrayList<>();
        State s = getStartState();
        ArrayList<State> firstPath = new ArrayList<>();
        firstPath.add(s);
        allStep.add(firstPath);

        for (int i = 0; i < str.length(); i++) {

            boolean hasF = true;

            ArrayList<ArrayList<State>> tempAll0 = (ArrayList<ArrayList<State>>) allStep.clone();

            while (hasF) {
                ArrayList<ArrayList<State>> tempAll0X = new ArrayList<>();

                hasF = false;
                for (ArrayList<State> step : tempAll0) {

                    ArrayList<State> nextState = getNextStateWithF(step.get(step.size() - 1));

                    for (State ss : nextState) {
                        ArrayList<State> st = (ArrayList<State>) step.clone();
                        st.add(ss);

                        tempAll0X.add(st);
                        allStep.add(st);
                    }
                    hasF = true;
                }
                tempAll0 = tempAll0X;
            }

            ArrayList<ArrayList<State>> tempAllStep = new ArrayList<>();
            for (ArrayList<State> step : allStep) {
                if (step.get(step.size() - 1) == null) {
                    ArrayList<State> st = (ArrayList<State>) step.clone();
                    tempAllStep.add(st);
                    continue;
                }
                String a = "" + str.charAt(i);
                boolean hasNext = false;
                ArrayList<State> nextState = getNextState(step.get(step.size() - 1), a);
                for (State ss : nextState) {
                    ArrayList<State> st = (ArrayList<State>) step.clone();
                    st.add(ss);
                    tempAllStep.add(st);
                    hasNext = true;
                }
                if (!hasNext) {
                    ArrayList<State> st = (ArrayList<State>) step.clone();
                    st.add(null);
                    tempAllStep.add(st);
                }
            }
            allStep = tempAllStep;

        }

        //
        boolean hasF = true;

        ArrayList<ArrayList<State>> tempAll0 = (ArrayList<ArrayList<State>>) allStep.clone();

        while (hasF) {
            ArrayList<ArrayList<State>> tempAll0X = new ArrayList<>();

            hasF = false;
            for (ArrayList<State> step : tempAll0) {

                ArrayList<State> nextState = getNextStateWithF(step.get(step.size() - 1));

                for (State ss : nextState) {
                    ArrayList<State> st = (ArrayList<State>) step.clone();
                    st.add(ss);

                    tempAll0X.add(st);
                    allStep.add(st);
                }
                hasF = true;
            }
            tempAll0 = tempAll0X;
        }
        //
        return allStep;
    }

    public ArrayList<String> getSubAlphabetStar(int n) {
        ArrayList<String> alpha = (ArrayList<String>) alphabet.clone();
        if (alpha.contains("ε")) {
            alpha.remove("ε");
        }
        ArrayList<String> subAlStar = (ArrayList<String>) alpha.clone();

        int temp = 0;
        for (int i = 1; i < n; i++) {
            int lenOld = subAlStar.size();
            for (int j = temp; j < lenOld; j++) {
                for (int k = 0; k < alpha.size(); k++) {
                    subAlStar.add(subAlStar.get(j) + alpha.get(k));
                }
            }
            temp = lenOld;
        }

        if (alphabet.contains("ε")) {
            subAlStar.add(0, "ε");
        }
        return subAlStar;
    }

    public boolean checkString(String str) {
        boolean accept = false;
        ArrayList<ArrayList<State>> all = getAllStepFromStr(str);
        for (ArrayList<State> steps : all) {
            if (steps.get(steps.size() - 1) != null && steps.get(steps.size() - 1).isAccept) {
                accept = true;
            }
        }
        return accept;
    }

    public ArrayList<String> getSubLanguage(int n) {
        ArrayList<String> subLang = new ArrayList<>();
        ArrayList<String> subAlStar = getSubAlphabetStar(n);
        
        if(getAcceptState().contains(getStartState())){
            subLang.add("ε");
        }
        for (String str : subAlStar) {
            if (checkString(str)) {
                subLang.add(str);
            }
        }
        return subLang;

    }

    static class Automata {

        static int x = 200;
        static int y = 200;
        ArrayList<State> sts = new ArrayList<>();
        ArrayList<Transition> trs = new ArrayList<>();
        ArrayList<String> alp = new ArrayList<>();

        public Automata() {
        }

        public Automata(String q) {
            alp.add(q);
            State q0 = new State(x, y);
            Transition t0 = new Transition(null, q0);
            t0.x = x - 100;
            t0.y = y;
            changePostion();
            State q1 = new State(x, y);
            q1.isAccept = true;
            changePostion();
            Transition t1 = new Transition(q0, q1);
            t1.text = q;

            sts.add(q0);
            sts.add(q1);

            trs.add(t0);
            trs.add(t1);
        }

        public void changePostion() {
            x = x + 150;
            if (x > 900) {
                x = 150;
                y += 150;
                if (y > 900) {
                    y = 200;
                }
            }
        }

        public State getStartState() {
            for (Transition t : trs) {
                if (t.stateA == null) {
                    return t.stateB;
                }
            }
            return null;
        }

        public ArrayList<State> getAcceptState() {
            ArrayList<State> accepts = new ArrayList<>();
            for (State s : sts) {
                if (s.isAccept) {
                    accepts.add(s);
                }
            }
            return accepts;
        }
    }

    public Automata concatinate(Automata q, Automata r) {
        ArrayList<State> acs = q.getAcceptState();
        State sr = r.getStartState();

        for (State s : r.sts) {
            q.sts.add(s);
        }

        for (Transition t : r.trs) {
            if (t.stateA == null) {
                continue;
            } else {
                q.trs.add(t);
            }
        }

        for (State s : acs) {
            s.isAccept = false;
            Transition tp = new Transition(s, sr);
            q.trs.add(tp);
        }
        if (!q.alp.contains("ε")) {
            q.alp.add("ε");
        }
        for (String al : r.alp) {
            if (!q.alp.contains(al)) {
                q.alp.add(al);
            }
        }
        return q;
    }

    public Automata star(Automata q) {
        ArrayList<State> acs = q.getAcceptState();
        State sr = q.getStartState();

        for (State s : acs) {
            Transition tp = new Transition(s, sr);
            q.trs.add(tp);
        }

        for (Transition t : q.trs) {
            if (t.stateA == null) {
                q.trs.remove(t);
                break;
            }
        }

        State newStart = new State((sr.x - 100) < 90 ? 90 : (sr.x - 100), (sr.y - 100) < 90 ? 90 : (sr.y - 100));
        newStart.isAccept = true;
        Transition tr = new Transition(newStart, sr);
        Transition tr0 = new Transition(null, newStart);

        q.sts.add(newStart);
        q.trs.add(tr);
        q.trs.add(tr0);

        if (!q.alp.contains("ε")) {
            q.alp.add("ε");
        }
        return q;
    }

    public Automata union(Automata q, Automata r) {
        State q0 = q.getStartState();
        State r0 = r.getStartState();
        for (State s : r.sts) {
            q.sts.add(s);
        }

        for (Transition t : r.trs) {
            if (t.stateA == null) {
                continue;
            } else {
                q.trs.add(t);
            }
        }

        for (Transition t : q.trs) {
            if (t.stateA == null) {
                q.trs.remove(t);
                break;
            }
        }

        State newStart = new State((q0.x - 100) < 90 ? 90 : (q0.x - 100), (q0.y + r0.y) / 2);
        Transition newTran = new Transition(null, newStart);
        Transition newTran2 = new Transition(newStart, q0);
        Transition newTran3 = new Transition(newStart, r0);

        q.sts.add(newStart);
        q.trs.add(newTran);
        q.trs.add(newTran2);
        q.trs.add(newTran3);

        if (!q.alp.contains("ε")) {
            q.alp.add("ε");
        }
        for (String al : r.alp) {
            if (!q.alp.contains(al)) {
                q.alp.add(al);
            }
        }
        return q;
    }

    public Automata regexToNFA(String regex) {
        String regPost = RegEx.infixToPostfix(regex);
        String operation = ".U*";
        Stack<Automata> stack = new Stack<>();
        stack.push(new Automata("" + regPost.charAt(0)));
        for (int i = 1; i < regPost.length(); i++) {
            String e = regPost.charAt(i) + "";
            if (!operation.contains(e)) {
                // System.out.println("add automata");
                stack.push(new Automata(e));
            } else if (e.equals("U")) {
                // System.out.println("Union");
                Automata q = stack.pop();
                Automata r = stack.pop();
                Automata qr = union(r, q);
                stack.push(qr);
            } else if (e.equals(".")) {
                // System.out.println("concat");
                Automata q = stack.pop();
                Automata r = stack.pop();
                Automata qr = concatinate(r, q);
                stack.push(qr);
            } else if (e.equals("*")) {
                // System.out.println("star");
                Automata q = stack.pop();
                Automata q_ = star(q);
                stack.push(q_);
            }
        }
        return stack.pop();
    }

//////////////////////////////// 2. GUI ////////////////////////////////
    public void clear() {
        Graphics g = c.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void selected(int x, int y) {
        Object obj = null;
        for (State s : states) {
            if (s.inCircle(x, y)) {
                s.isSelect = true;
                obj = s;
                break;
            }
        }
        if (obj == null) {
            for (Transition t : transitions) {
                if (t.inLine(x, y)) {
                    t.isSelect = true;
                    obj = t;
                    break;
                }
            }
        }
        if (obj == null) {
            if (selected == null) {
                return;
            } else {
                if (selected instanceof State) {
                    State s = (State) selected;
                    s.isSelect = false;
                } else {
                    Transition t = (Transition) selected;
                    t.isSelect = false;
                }
                selected = null;
            }
        } else {
            if (selected == null) {
                selected = obj;
            } else {
                if (obj == selected) {
                    return;
                } else {
                    if (selected instanceof State) {
                        State s = (State) selected;
                        s.isSelect = false;
                    } else {
                        Transition t = (Transition) selected;
                        t.isSelect = false;
                    }
                    selected = obj;
                }
            }
        }
    }
    BufferedImage grid = null;
    public void draw() {
        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setFont(sanSerifFont);
        
        if(grid==null){
            grid = (BufferedImage)createImage(c.getWidth(),c.getHeight());
        }
        
        Graphics2D g2 = grid.createGraphics();
        
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        //clear();
        setAlphabet();


        for (Transition t : transitions) {
            t.draw(g2);
        }
        if (temp != null) {
            temp.line(g2);
        }
        for (State s : states) {
            s.draw(g2);
        }
        g.drawImage(grid, null, 0, 0);
    }

//////////////////////////////// 3. GUI EVENT  ////////////////////////////////
    // 3.1 mouse listener and mouse motion listener mehods 
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        selected(x, y);
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            if (states.contains(selected)) {
                State s = (State) selected;
                s.swapAccept();
            } else {
                State temp = new State(x, y);
                states.add(temp);
            }
        }
        draw();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("state")) {
            if (selected != null) {
                if (selected instanceof State) {
                    State s = (State) selected;
                    for (Transition t : transitions) {
                        if (t.stateA == s || t.stateB == s) {
                            int difx = x - s.x;
                            int dify = y - s.y;
                            if (t.stateA != t.stateB) {
                                if (t.stateA != null) {
                                    t.curveX = (t.stateA.x + t.stateB.x) / 2;
                                    t.curveY = (t.stateA.y + t.stateB.y) / 2;
                                } else {
                                    t.curveX = difx;
                                    t.curveY = dify;
                                }
                            }

                            t.xSelf += difx;
                            t.ySelf += dify;

                        }
                    }
                    s.x = x;
                    s.y = y;
                } else {
                    Transition t = (Transition) selected;
                    if (t.isStart) {
                        t.x = x;
                        t.y = y;
                    } else {
                        t.curveX = x;
                        t.curveY = y;
                    }
                }
            }

        } else if (mode.equals("transition")) {
            try {
                State state = null;
                for (State s : states) {
                    if (s.inCircle(x, y)) {
                        state = s;
                    }
                }
                if (state != null) {
                    if (state != temp.stateA) {
                        double angle = Math.atan2(temp.y0 - state.y, temp.x0 - state.x);
                        double dx = Math.cos(angle);
                        double dy = Math.sin(angle);
                        temp.x1 = state.x + (int) (state.r * dx);
                        temp.y1 = state.y + (int) (state.r * dy);
                    } else {
                        temp.x1 = x;
                        temp.y1 = y;
                    }
                } else {
                    temp.x1 = x;
                    temp.y1 = y;
                }
            } catch (Exception ex) {
                //// System.out.println("error transition mouseDragged");
            }
        }
        draw();
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("state")) {
            temp = null;
        } else if (mode.equals("transition")) {
            try {
                temp.x1 = x;
                temp.y1 = y;
                State stateB = null;
                for (State s : states) {
                    if (s.inCircle(x, y)) {
                        stateB = s;
                        break;
                    }
                }
                if (stateB == null) {
                    temp = null;
                    draw();
                    return;
                }

                Transition tran = new Transition(temp.stateA, stateB);
                if (temp.stateA == null) {
                    tran.isStart = true;
                    tran.x = temp.x0;
                    tran.y = temp.y0;
                }

                tran.rSelf = stateB.r * Math.sqrt(2);
                tran.xSelf = stateB.x - stateB.r;
                tran.ySelf = stateB.y - stateB.r;
                if (stateB == temp.stateA) {
                    tran.curveX = stateB.x + stateB.r + 20;
                    tran.curveY = stateB.y;
                }
                transitions.add(tran);
                temp = null;
            } catch (Exception ex) {
                //// System.out.println("error transition mouse released");
            }
        }
        draw();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("transition")) {
            temp = new Temp(x, y);
            for (State s : states) {
                if (s.inCircle(x, y)) {
                    temp.setA(s);
                    temp.x0 = s.x;
                    temp.y0 = s.y;
                    break;
                }
            }
        }
    }

    // 3.2 keyboard listener and keyboard motion listener mehods 
    @Override
    public void keyTyped(KeyEvent ke) {
        // System.out.println("key " + ke.getKeyChar() + " = " + (int) ke.getKeyChar());
        if ((int) ke.getKeyChar() == 19) {
            try {
                //ctrl + S -- save
                save("backup.json");
                // System.out.println("saved success!!!");

            } catch (IOException ex) {
                // System.out.println("saved fail !!!");
                Logger.getLogger(DrawingFiniteAutomata.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if ((int) ke.getKeyChar() == 15) {
            try {
                //ctrl + O -- open
                open("backup.json");
                // System.out.println("opened success!!!");

            } catch (IOException ex) {
                // System.out.println("opened fail !!!");
                Logger.getLogger(DrawingFiniteAutomata.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if ((int) ke.getKeyChar() == 14) {
            //ctrl + N -- all step
            // System.out.println("Enter your string : ");
            Scanner sc = new Scanner(System.in);
            ArrayList<ArrayList<State>> allStep = getAllStepFromStr(sc.nextLine().trim());
            if (allStep == null) {
                // System.out.println("error allStep is null");
                return;
            }
            for (int i = 0; i < allStep.size(); i++) {
                // System.out.println("path : " + (i + 1));
                ArrayList<State> all = allStep.get(i);
                // System.out.print(all.get(0).text);
                for (int j = 1; j < all.size(); j++) {
                    if (all.get(j) == null) {
                        // System.out.print(" , " + "Tp");
                        break;
                    }
                    // System.out.print(" , " + all.get(j).text);
                }
                // System.out.println();
            }
            // System.out.println("--------------------------");
        } else if ((int) ke.getKeyChar() == 12) {
            //ctrl + L -- sub Language
            // System.out.println("Sub Language : " + getSubLanguage(5));

        } else if ((int) ke.getKeyChar() == 18) {
            //ctrl + R -- dfa to regular
            createEquation();
        } else if ((int) ke.getKeyChar() == 9) {
            // System.out.println("info ");
            // System.out.println(getInfo());
        } else if ((int) ke.getKeyChar() == 1) {
            //ctrl + A -- regular to nfa
            // System.out.println("Enter your string : ");
            Scanner sc = new Scanner(System.in);
            Automata nfa = regexToNFA(sc.nextLine().trim());
            states = nfa.sts;
            transitions = nfa.trs;
            alphabet = nfa.alp;
            Automata.x = 200;
            Automata.y = 200;
            // System.out.println("Regular expression to NFA Successed !!!");
        }

        if (selected instanceof State) {
            State s = (State) selected;
            int status = (int) ke.getKeyChar();
            if (status == 8) { //delete
                if (s.text.length() > 1) {
                    s.text = s.text.substring(0, s.text.length() - 1).trim();
                } else {
                    s.text = "".trim();
                }

            } else if (status == 127) { // space
                ArrayList<Transition> temp = new ArrayList<>();
                for (Transition t : transitions) {
                    if (t.stateA == selected || t.stateB == selected) {
                        temp.add(t);
                    }
                }
                for (Transition t : temp) {
                    transitions.remove(t);
                }
                states.remove(selected);
                selected = null;

            } else {
                s.text += ke.getKeyChar();
                s.text = s.text.trim();
            }

        } else if (selected instanceof Transition) {
            Transition t = (Transition) selected;
            int status = (int) ke.getKeyChar();
            if (status == 8) {
                t.text = "ε";

            } else if (status == 127) {
                transitions.remove(selected);
                selected = null;

            } else {
                if (ke.getKeyChar() == ' ') {
                    return;
                }
                t.text = "" + ke.getKeyChar();
            }

        }
        draw();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if ((int) ke.getKeyChar() == 32) {
            mode = "transition";
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        mode = "state";
    }

    @Override
    public void actionPerformed(ActionEvent ae) { // button
        // System.out.println("exit");
    }

    // 3.3 NFA to regular expression (start)
    public void createEquation() {
        ArrayList<String> state1 = new ArrayList<>();
        ArrayList<String> alphabet1 = new ArrayList<>();
        ArrayList<String> equation1 = new ArrayList<>();
        for (int i = 0; i < states.size(); i++) {
            String equation = "L" + states.get(i).text.trim() + "=";
            int count = 0;
            for (int j = 0; j < alphabet.size(); j++) {
                if (getNextState(states.get(i), alphabet.get(j).trim()).size() == 1) {
                    if (count == 0) {
                        equation += (alphabet.get(j).trim() + "L" + getNextState(states.get(i), alphabet.get(j).trim()).get(0).text.trim());
                        count++;
                    } else {
                        equation += ("U" + alphabet.get(j).trim() + "L" + getNextState(states.get(i), alphabet.get(j).trim()).get(0).text.trim());
                    }

                }
            }
            if (getAcceptState().contains(states.get(i))) {
                equation += ("Uε");
            }
            equation1.add(equation);
        }
        for (int i = 0; i < states.size(); i++) {
            state1.add(states.get(i).text.trim());
        }
        for (int i = 0; i < alphabet.size(); i++) {
            alphabet1.add(alphabet.get(i).trim());
        }
        // System.out.print("AllState: ");
        for (int i = 0; i < state1.size(); i++) {
            // System.out.print(state1.get(i) + ", ");
        }
        // System.out.println();
        // System.out.print("AllAlphabet: ");
        for (int i = 0; i < alphabet1.size(); i++) {
            // System.out.print(alphabet1.get(i) + ", ");
        }
        // System.out.println();
        // System.out.println("All equation is ");
        for (int i = 0; i < equation1.size(); i++) {
            // System.out.println((i + 1) + ". " + equation1.get(i));
        }
        // System.out.println("Start State: " + getStartState().text.trim());
        convertToRegular ctr = new convertToRegular(state1, alphabet1);
        ctr.setStartState(getStartState().text.trim());
        ctr.setEquation(equation1);
        ctr.convert();

    }

    class convertToRegular {

        ArrayList<String> allState;
        ArrayList<String> allAlphabet;
        ArrayList<String> allEquation;
        ArrayList<String> temp;
        String startState;
        private int roundProcess = 0;
        boolean found = false;
        private int maxRound = 10;

        public convertToRegular(ArrayList<String> allState, ArrayList<String> allAlphabet) {
            this.allState = new ArrayList<>(allState);
            this.allAlphabet = new ArrayList<>(allAlphabet);
        }

        public void setEquation(ArrayList<String> equation) {
            allEquation = new ArrayList<>(equation);
            temp = new ArrayList<>(equation);
        }

        public void printAllEquation() {
            for (int i = 0; i < allEquation.size(); i++) {
                // System.out.println((i + 1) + ". " + allEquation.get(i));
            }
        }

        public void printAllTemp() {
            for (int i = 0; i < temp.size(); i++) {
                // System.out.println((i + 1) + ". " + temp.get(i));
            }
        }

        public String convert() {
            while (true) {
                reform();
                // System.out.println("Reform Round (" + roundProcess + ")");
                printAllEquation();
                // System.out.println("================================");

                for (int i = 0; i < allEquation.size(); i++) {
                    String currentBlockEquation[] = allEquation.get(i).split("=");
                    if (!currentBlockEquation[1].contains("L") && currentBlockEquation[0].substring(1).equals(getStartState())) {
                        //                   // System.out.println("INDEX: "+(i+1));
                        String rightSolution[] = allEquation.get(i).split("=");
                        // System.out.println("Regular expression is " + rightSolution[1]);
                        found = true;
                        return rightSolution[1];

                    }
                }
                if (found || roundProcess == maxRound) {
                    if (roundProcess == maxRound) {
                        // System.out.println("Sorry, can't found regular expression");
                    }
                    break;
                }
                enumerate();
                // System.out.println("Enumerate *1 Round (" + roundProcess + ")");
                printAllEquation();
                // System.out.println("================================");
                instead();
                // System.out.println("Instead Round (" + roundProcess + ")");
                printAllEquation();
                // System.out.println("================================");
                enumerate();
                // System.out.println("Enumerate *2 Round (" + roundProcess + ")");
                printAllEquation();
                // System.out.println("================================");
                group();
                // System.out.println("Group Round (" + roundProcess + ")");
                printAllEquation();
                // System.out.println("================================");
                roundProcess++;

            }
            return null;
        }

        public void reform() {
            for (int i = 0; i < allEquation.size(); i++) {
                String[] currentBlockEquation = allEquation.get(i).split("=");
                List<String> currentBlockRightEquation = new ArrayList<>();
                int countState = 0;
                for (int j = 0; j < allState.size(); j++) {
                    if (currentBlockEquation[1].contains(allState.get(j))) {
                        countState++;
                    }
                }
                if (countState > 1 && currentBlockEquation[0].contains(getStartState())) {
                    continue;
                }

                if (currentBlockEquation[1].contains("(") || currentBlockEquation[1].contains(")")) {
                    String statusBlock = "";
                    int countLayer = 0;
                    // for(int k=0; k<currentBlockEquation[1].length(); k++){
                    //    // System.out.print(currentBlockEquation[1].charAt(k)+" ");
                    // }
                    //// System.out.println();
                    for (int k = 0; k < currentBlockEquation[1].length(); k++) {
                        if (currentBlockEquation[1].charAt(k) == '(') {
                            countLayer += 1;
                            statusBlock += "+";
                        } else if (countLayer > 0 && currentBlockEquation[1].charAt(k) == ')') {
                            countLayer -= 1;
                            statusBlock += "+";
                        } else if (countLayer > 0 && currentBlockEquation[1].charAt(k) != ')') {
                            statusBlock += "+";
                        } else {
                            statusBlock += currentBlockEquation[1].charAt(k);
                        }
                    }
                    int start = 0;
                    int startSubString = 0;
                    int indexU;
                    while (true) {
                        indexU = currentBlockEquation[1].indexOf("U", start);
                        if (indexU == -1) {
                            if (currentBlockRightEquation.size() != 0) {
                                currentBlockRightEquation.add(currentBlockEquation[1].substring(startSubString));
                            }
                            break;
                        }
                        if (statusBlock.charAt(indexU) != '+') {
                            currentBlockRightEquation.add(currentBlockEquation[1].substring(startSubString, indexU));
                            startSubString = indexU + 1;
                        }
                        start = indexU + 1;
                    }
                } else {
                    String temp[] = currentBlockEquation[1].split("U");
                    currentBlockRightEquation = Arrays.asList(temp);
                }
                if (currentBlockRightEquation.size() != 0) {
                    int indexFirst = -1;
                    for (int j = 0; j < currentBlockRightEquation.size(); j++) {
                        if (currentBlockRightEquation.get(j).contains(currentBlockEquation[0])) {
                            indexFirst = j;
                        }
                    }
                    if (indexFirst != -1) {
                        String tempEquation = "";
                        int indexL = currentBlockRightEquation.get(indexFirst).indexOf("L");
                        String afterTemp = currentBlockRightEquation.get(indexFirst).substring(indexL);
                        if (afterTemp.length() == currentBlockEquation[0].length()) {
                            String newStar = currentBlockRightEquation.get(indexFirst).substring(0, indexL);
                            if (newStar.length() == 1) {
                                tempEquation += (currentBlockEquation[0] + "=" + currentBlockRightEquation.get(indexFirst).substring(0, indexL) + "*");
                            } else {
                                tempEquation += (currentBlockEquation[0] + "=(" + currentBlockRightEquation.get(indexFirst).substring(0, indexL) + ")*");
                            }
                        }

                        String tempRightEquation = "";
                        int count = 0;
                        for (int j = 0; j < currentBlockRightEquation.size(); j++) {
                            if (indexFirst != j && count == 0) {
                                tempRightEquation += (currentBlockRightEquation.get(j));
                                count++;
                            } else if (indexFirst != j && count != 0) {
                                tempRightEquation += ("U" + currentBlockRightEquation.get(j));
                            } else if (indexFirst == j && afterTemp.length() != currentBlockEquation[0].length() && count == 0) {
                                tempRightEquation += (currentBlockRightEquation.get(j));
                                count++;
                            } else if (indexFirst == j && afterTemp.length() != currentBlockEquation[0].length() && count != 0) {
                                tempRightEquation += ("U" + currentBlockRightEquation.get(j));
                            }
                        }
                        /*
                        if(tempRightEquation.indexOf("U") != -1){
                            tempRightEquation = ("("+tempRightEquation+")")
                        }*/
                        if (tempRightEquation.contains("U")) {
                            tempEquation += ("(" + tempRightEquation + ")");
                        } else {
                            tempEquation += (tempRightEquation);
                        }
                        temp.add(i, tempEquation);
                        temp.remove(i + 1);
                    }
                } else {
                    if (currentBlockEquation[1].contains(currentBlockEquation[0])) {
                        temp.add(i, currentBlockEquation[0] + "=(" + currentBlockEquation[1].substring(0, currentBlockEquation[1].indexOf(currentBlockEquation[0])) + ")*");
                        temp.remove(i + 1);

                    }
                }
                if (temp.get(i).contains("ε") && !temp.get(i).contains("Uε")) {
                    temp.add(i, temp.get(i).replace("ε", ""));
                    temp.remove(i + 1);
                }
            }
            allEquation = new ArrayList<>(temp);
        }

        public void instead() {
            for (int i = 0; i < allEquation.size(); i++) {
                String currentBlockEquation[] = allEquation.get(i).split("=");
                for (int j = 0; j < allEquation.size(); j++) {
                    String tempNewEquation = "";
                    String blockEquation[] = allEquation.get(j).split("=");
                    if (j != i && !blockEquation[0].equals(getStartState()) && currentBlockEquation[1].contains(blockEquation[0])) {
                        ////System.out.println("INDEX: "+(i+1));
                        ////System.out.println("Right equation: "+currentBlockEquation[1]+" , Left: "+blockEquation[0]);
                        ////System.out.println(blockEquation[0]+" "+blockEquation[1]);
                        tempNewEquation += currentBlockEquation[1];
                        if (tempNewEquation.indexOf(blockEquation[0]) != -1 && allAlphabet.contains(tempNewEquation.charAt(tempNewEquation.indexOf(blockEquation[0]) - 1) + "")) {
                            tempNewEquation = tempNewEquation.replace(blockEquation[0], "(" + blockEquation[1] + ")");
                        } else {
                            tempNewEquation = tempNewEquation.replace(blockEquation[0], blockEquation[1]);
                        }
                        ////System.out.println("After: "+tempNewEquation);
                    }
                    if (tempNewEquation.length() > 1) {
                        String newEquation = "";
                        newEquation += (currentBlockEquation[0] + "=" + tempNewEquation);
                        temp.add(newEquation);
                    }
                }
            }
            allEquation = new ArrayList<>(temp);
        }

        public void enumerate() {
            for (int i = 0; i < allEquation.size(); i++) {
                String currentBlockEquation[] = allEquation.get(i).split("=");
                if (currentBlockEquation[1].contains("(") || currentBlockEquation[1].contains(")")) {
                    String statusBlock = "";
                    int countLayer = 0;
                    // for(int j=0; j<currentBlockEquation[1].length(); j++){
                    //    // System.out.print(currentBlockEquation[1].charAt(j)+" ");
                    // }
                    //// System.out.println();
                    for (int j = 0; j < currentBlockEquation[1].length(); j++) {
                        if (currentBlockEquation[1].charAt(j) == '(') {
                            countLayer += 1;
                            statusBlock += "+";
                        } else if (countLayer > 0 && currentBlockEquation[1].charAt(j) == ')') {
                            countLayer -= 1;
                            statusBlock += "+";
                        } else if (countLayer > 0 && currentBlockEquation[1].charAt(j) != ')') {
                            statusBlock += "+";
                        } else {
                            statusBlock += currentBlockEquation[1].charAt(j);
                        }
                    }
                    int start = 0;
                    int startSubString = 0;
                    int indexU;
                    ArrayList<String> currentBlockRightEquation = new ArrayList<>();
                    while (true) {
                        indexU = currentBlockEquation[1].indexOf("U", start);
                        if (indexU == -1) {
                            if (currentBlockRightEquation.size() != 0) {
                                currentBlockRightEquation.add(currentBlockEquation[1].substring(startSubString));
                            }
                            break;
                        }
                        if (statusBlock.charAt(indexU) != '+') {
                            currentBlockRightEquation.add(currentBlockEquation[1].substring(startSubString, indexU));
                            startSubString = indexU + 1;
                        }
                        start = indexU + 1;
                    }
                    if (currentBlockRightEquation.size() == 0) {
                        currentBlockRightEquation.add(currentBlockEquation[1]);
                    }
                    for (int j = 0; j < currentBlockRightEquation.size(); j++) { //1((11*0U0)Lq0U1)
                        if ((currentBlockRightEquation.get(j).contains("(") || currentBlockRightEquation.get(j).contains(")")) && currentBlockRightEquation.get(j).indexOf("(") != 0) {
                            int indexStartBracket = currentBlockRightEquation.get(j).indexOf("(");
                            int indexEndBracket = currentBlockRightEquation.get(j).lastIndexOf(")");
                            int startAlphabet = indexStartBracket - 1;
                            while (true) {
                                if (startAlphabet < 0 || currentBlockRightEquation.get(j).charAt(startAlphabet) == 'U') {
                                    break;
                                }
                                startAlphabet = startAlphabet - 1;
                            }
                            if (startAlphabet == -1) {
                                startAlphabet = 0;
                            } else if (startAlphabet > 0) {
                                startAlphabet = startAlphabet + 1;
                            }
                            String textOriginal = currentBlockRightEquation.get(j).substring(startAlphabet, indexEndBracket + 1); //1((11*0U0)Lq0U1)
                            String textInBracket = currentBlockRightEquation.get(j).substring(indexStartBracket + 1, indexEndBracket); //(11*0U0)Lq0U1
                            String alphabet = textOriginal.substring(0, textOriginal.indexOf("("));
                            ////System.out.println("textOriginal: "+textOriginal);
                            ////System.out.println("textInBracket: "+textInBracket);
                            ////System.out.println("alphabet: "+alphabet);
                            if (textInBracket.contains("(") || textInBracket.contains(")")) {
                                //// System.out.println("CASE1 <");
                                String statusBlockLayer2 = "";
                                int countLayer2 = 0;
                                /*for(int k=0; k<textInBracket.length(); k++){
                                    ////System.out.print(textInBracket.charAt(k)+" ");
                                }*/
                                for (int k = 0; k < textInBracket.length(); k++) {
                                    if (textInBracket.charAt(k) == '(') {
                                        countLayer2 += 1;
                                        statusBlockLayer2 += "+";
                                    } else if (countLayer2 > 0 && textInBracket.charAt(k) == ')') {
                                        countLayer2 -= 1;
                                        statusBlockLayer2 += "+";
                                    } else if (countLayer2 > 0 && textInBracket.charAt(k) != ')') {
                                        statusBlockLayer2 += "+";
                                    } else {
                                        statusBlockLayer2 += textInBracket.charAt(k);
                                    }
                                }
                                ////System.out.println("statusBlockLayer2: "+statusBlockLayer2);
                                start = 0;
                                int startSubStringLayer2 = 0;
                                int indexULayer2;
                                ArrayList<String> currentBlockRightEquationLayer2 = new ArrayList<>();
                                while (true) {
                                    indexULayer2 = textInBracket.indexOf("U", start);
                                    if (indexULayer2 == -1) {
                                        if (currentBlockRightEquationLayer2.size() != 0) {
                                            currentBlockRightEquationLayer2.add(textInBracket.substring(startSubStringLayer2));
                                        } else {
                                            currentBlockRightEquationLayer2.add(textInBracket);
                                        }
                                        break;
                                    }
                                    if (statusBlockLayer2.charAt(indexULayer2) != '+') {
                                        currentBlockRightEquationLayer2.add(textInBracket.substring(startSubStringLayer2, indexULayer2));
                                        startSubStringLayer2 = indexULayer2 + 1;
                                    }
                                    start = indexULayer2 + 1;
                                }
                                //console.log("Current: "+currentBlockRightEquationLayer2) //(11*0U0)Lq0, 1
                                for (int m = 0; m < currentBlockRightEquationLayer2.size(); m++) {
                                    currentBlockRightEquationLayer2.add(m, alphabet + currentBlockRightEquationLayer2.get(m));
                                    currentBlockRightEquationLayer2.remove(m + 1);
                                }
                                for (int m = 0; m < currentBlockRightEquationLayer2.size(); m++) { //ตัดeออก
                                    if (currentBlockRightEquationLayer2.get(m).contains("ε")) {
                                        currentBlockRightEquationLayer2.add(m, currentBlockRightEquationLayer2.get(m).replace("ε", ""));
                                        currentBlockRightEquationLayer2.remove(m + 1);
                                    }
                                }
                                String tempLayer2New = "";
                                countLayer2 = 0;
                                for (int m = 0; m < currentBlockRightEquationLayer2.size(); m++) {
                                    if (countLayer2 == 0) {
                                        tempLayer2New += currentBlockRightEquationLayer2.get(m);
                                        countLayer2++;
                                    } else {
                                        tempLayer2New += ("U" + currentBlockRightEquationLayer2.get(m));
                                    }

                                }
                                allEquation.add(i, allEquation.get(i).replace(textOriginal, tempLayer2New));
                                allEquation.remove(i + 1);

                            } else {  //0Lq0U1 11*0U0
                                if (textInBracket.indexOf("U") != -1) {
                                    ////System.out.println("CASE2 <<");
                                    String currentBlocktextInBracket[] = textInBracket.split("U");
                                    for (int k = 0; k < currentBlocktextInBracket.length; k++) { //แจกแจงค่าข้างหน้าลงในแต่ละช่อง
                                        currentBlocktextInBracket[k] = alphabet + currentBlocktextInBracket[k];
                                        if (currentBlocktextInBracket[k].contains("ε")) {
                                            currentBlocktextInBracket[k] = currentBlocktextInBracket[k].replace("ε", "");
                                        }
                                    }
                                    String tempNew = "";
                                    int count = 0;
                                    for (int m = 0; m < currentBlocktextInBracket.length; m++) {
                                        if (count == 0) {
                                            tempNew += currentBlocktextInBracket[m];
                                            count++;
                                        } else {
                                            tempNew += ("U" + currentBlocktextInBracket[m]);
                                        }

                                    }
                                    allEquation.add(i, allEquation.get(i).replace(textOriginal, tempNew));
                                    allEquation.remove(i + 1);
                                } else {
                                    ////System.out.println("CASE3 <<");
                                    allEquation.add(i, allEquation.get(i).replace(textOriginal, alphabet + textInBracket));
                                    allEquation.remove(i + 1);
                                }
                            }
                        }
                    }
                }
            }
            temp = new ArrayList<>(allEquation);
        }

        public void group() {
            for (int i = 0; i < allEquation.size(); i++) {
                String currentBlockEquation[] = allEquation.get(i).split("=");
                if (!currentBlockEquation[1].contains("U")) {
                    continue;
                }
                List<String> currentBlockRightEquation = new ArrayList<>();
                if (currentBlockEquation[1].contains("(") || currentBlockEquation[1].contains(")")) {
                    String statusBlock = "";
                    int countLayer = 0;
                    // for(int j=0; j<currentBlockEquation[1].length(); j++){
                    //    // System.out.print(currentBlockEquation[1].charAt(j)+" ");
                    // }
                    //// System.out.println();
                    for (int j = 0; j < currentBlockEquation[1].length(); j++) {
                        if (currentBlockEquation[1].charAt(j) == '(') {
                            countLayer += 1;
                            statusBlock += currentBlockEquation[1].charAt(j);
                        } else if (countLayer > 0 && currentBlockEquation[1].charAt(j) == ')') {
                            statusBlock += currentBlockEquation[1].charAt(j);
                            countLayer -= 1;
                        } else if (countLayer > 0 && currentBlockEquation[1].charAt(j) != ')') {
                            statusBlock += "+";
                        } else {
                            statusBlock += currentBlockEquation[1].charAt(j);
                        }
                    }
                    int start = 0;
                    int startSubString = 0;
                    int indexU;
                    while (true) {
                        indexU = currentBlockEquation[1].indexOf("U", start);
                        if (indexU == -1) {
                            currentBlockRightEquation.add(currentBlockEquation[1].substring(startSubString));
                            break;
                        }
                        if (statusBlock.charAt(indexU) != '+') {
                            currentBlockRightEquation.add(currentBlockEquation[1].substring(startSubString, indexU));
                            startSubString = indexU + 1;
                        }
                        start = indexU + 1;
                    }
                } else {
                    String temp[] = currentBlockEquation[1].split("U");
                    currentBlockRightEquation = Arrays.asList(temp);
                }
                //// System.out.println("Equation: "+(i+1));
                // for(int j=0; j<currentBlockRightEquation.size(); j++){
                //    // System.out.print(currentBlockRightEquation.get(j)+", ");
                // }
                //// System.out.println();
                HashMap<String, String> groupState = new HashMap<>();
                HashMap<String, Integer> countMemberGroup = new HashMap<>();
                ArrayList<String> ignoreGroup = new ArrayList<>();
                boolean checkMore2Layer = false;
                for (int j = 0; j < currentBlockRightEquation.size(); j++) {
                    if (currentBlockRightEquation.get(j).length() == 0) {
                        continue;
                    }
                    if ((currentBlockRightEquation.get(j)).charAt(currentBlockRightEquation.get(j).length() - 1) == ')') {
                        checkMore2Layer = true;
                    }
                }
                if (checkMore2Layer) {
                    continue;
                }

                for (int j = 0; j < currentBlockRightEquation.size(); j++) {
                    if (!currentBlockRightEquation.get(j).contains("L") && currentBlockRightEquation.get(j) != "ε") {
                        ignoreGroup.add(currentBlockRightEquation.get(j));
                    }
                }
                for (int j = 0; j < allState.size(); j++) {
                    for (int k = 0; k < currentBlockRightEquation.size(); k++) {
                        if (currentBlockRightEquation.get(k).contains(allState.get(j)) && !groupState.containsKey(allState.get(j))) {
                            groupState.put(allState.get(j), currentBlockRightEquation.get(k).substring(0, currentBlockRightEquation.get(k).indexOf(allState.get(j)) - 1));
                            countMemberGroup.put(allState.get(j), 1);
                        } else if (currentBlockRightEquation.get(k).contains(allState.get(j)) && groupState.containsKey(allState.get(j))) {
                            groupState.replace(allState.get(j), groupState.get(allState.get(j)) + "U" + currentBlockRightEquation.get(k).substring(0, currentBlockRightEquation.get(k).indexOf(allState.get(j)) - 1));
                            countMemberGroup.replace(allState.get(j), countMemberGroup.get(allState.get(j)) + 1);
                        }
                    }
                }
                String newRight = "";
                int count = 0;
                for (int j = 0; j < allState.size(); j++) {
                    if (groupState.containsKey(allState.get(j))) {
                        if (count == 0) {
                            if (countMemberGroup.get(allState.get(j)) != 1) {
                                newRight += ("(" + groupState.get(allState.get(j)) + ")" + "L" + allState.get(j));
                            } else {
                                newRight += (groupState.get(allState.get(j)) + "L" + allState.get(j));
                            }
                            count++;
                        } else {
                            if (countMemberGroup.get(allState.get(j)) != 1) {
                                newRight += ("U(" + groupState.get(allState.get(j)) + ")" + "L" + allState.get(j));
                            } else {
                                newRight += ("U" + groupState.get(allState.get(j)) + "L" + allState.get(j));
                            }
                        }

                    }
                }
                if (ignoreGroup.size() > 0) {
                    for (int j = 0; j < ignoreGroup.size(); j++) {
                        if (newRight.length() == 0) {
                            newRight += (ignoreGroup.get(j));
                        } else {
                            newRight += ("U" + ignoreGroup.get(j));
                        }
                    }
                }
                // if(currentBlockEquation[1].contains("ε")){
                //     newRight += "Uε";
                // }
                if (newRight.length() != 0) {
                    allEquation.add(i, (currentBlockEquation[0] + "=" + newRight));
                    allEquation.remove(i + 1);
                }
            }
            temp = new ArrayList<>(allEquation);
        }

        public void setStartState(String startState) {
            this.startState = startState;
        }

        public String getStartState() {
            return startState;
        }
    }
// 3.3 NFA to regular expression (end)

}

////////////////////////// 4. Temporary Line Transition ////////////////////////////////
class Temp {

    int x0;
    int y0;
    int x1;
    int y1;
    State stateA;

    Temp(int x, int y) {
        this.x0 = x;
        this.y0 = y;
        this.x1 = x;
        this.y1 = y;
    }

    void setA(State a) {
        this.stateA = a;
    }

    void line(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2));
        if (stateA != null && stateA.inCircle(x1, y1)) {

            g2.drawArc(x0, y0, 72, 72, 0, 360);
            double angle = Math.atan2(0, -2 * stateA.r);
            int x = stateA.x + stateA.r;
            int y = stateA.y;

            int e = 12;
            int h = 8;
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
            g.setColor(Color.BLUE);
            int x1[] = {x, (int) (x - e * dx + h * dy), (int) (x - e * dx - h * dy)};
            int y1[] = {y, (int) (y - e * dy - h * dx), (int) (y - e * dy + h * dx)};
            g.fillPolygon(x1, y1, 3);

        } else {
            if (stateA != null) {
                double angle = Math.atan2(y1 - y0, x1 - x0);
                double dx = Math.cos(angle);
                double dy = Math.sin(angle);
                x0 = stateA.x + (int) (stateA.r * dx);
                y0 = stateA.y + (int) (stateA.r * dy);
            }

            g2.drawLine(x0, y0, x1, y1);
            drawArrow(g, x1, y1, Math.atan2(y1 - y0, x1 - x0));
        }
    }

    void drawArrow(Graphics g, int x, int y, double angle) {
        int e = 12;
        int h = 8;
        double dx = Math.cos(angle);
        double dy = Math.sin(angle);
        g.setColor(Color.BLUE);
        int x1[] = {x, (int) (x - e * dx + h * dy), (int) (x - e * dx - h * dy)};
        int y1[] = {y, (int) (y - e * dy - h * dx), (int) (y - e * dy + h * dx)};
        g.fillPolygon(x1, y1, 3);
    }

}

//////////////////////////////// 5. Transition Function Link between State ////////////////////////////////
class Transition {

    State stateA;
    State stateB;
    String text;

    boolean isStart;
    boolean isSelf;

    int cX;
    int cY;
    int R;

    int x;
    int y;
    double r;
    int arrowX;
    int arrowY;
    boolean isSelect;
    /////////////////////////
    int curveX, curveY;
    int radian;

    int xSelf, ySelf;
    double rSelf;

    Transition(State a, State b) {
        this.stateA = a;
        this.stateB = b;
        this.text = "ε";
        this.isStart = a == null;
        this.isSelect = false;
        this.isSelf = a == b;
        if (a == null) {
            this.text = null;
        }
        this.radian = 36;
        this.curveX = -1;
        this.curveY = -1;
        this.R = 36;
        this.cX = x + R;
        this.cY = y + R;

    }

    State function(String a) {
        if (a.equals(text)) {
            return stateB;
        }
        return null;
    }

    boolean inLine(double x0, double y0) {
        double xm = curveX;
        double ym = curveY;
        return ((x0 - xm) * (x0 - xm) + (y0 - ym) * (y0 - ym)) <= radian * radian || ((x0 - cX) * (x0 - cX) + (y0 - cY) * (y0 - cY)) <= R * R;
    }

    void draw(Graphics g) {
        setArrow();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(isSelect ? Color.BLUE : Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        if (!isStart) {
            if (stateA == stateB) {
                double angle2 = Math.atan2(curveY - stateA.y, curveX - stateA.x);
                double dx2 = Math.cos(angle2);
                double dy2 = Math.sin(angle2);

                int e = 12;
                int h = 8;

                double angle = Math.atan2(stateA.y - curveY, stateA.x - curveX);
                double dx = Math.cos(angle);
                double dy = Math.sin(angle);

                double angle3 = Math.atan2(curveY - stateA.y, curveX - stateA.x);
                angle3 -= Math.PI / 4;
                double dx3 = Math.cos(angle3);
                double dy3 = Math.sin(angle3);
                //////////////
                g2.drawArc(xSelf + (int) (rSelf * dx3), ySelf + (int) (rSelf * dy3), 2 * R, 2 * R, 0, 360);
                //  g2.drawRect(xSelf + (int) (rSelf * dx3),ySelf + (int) (rSelf * dy3),2*R,R*2);
                /////////////
                int x = stateB.x + (int) (stateB.r * dx2);
                int y = stateB.y + (int) (stateB.r * dy2);
                g.setColor(isSelect ? Color.BLUE : Color.BLACK);
                int x1[] = {x, (int) (x - e * dx + h * dy), (int) (x - e * dx - h * dy)};
                int y1[] = {y, (int) (y - e * dy - h * dx), (int) (y - e * dy + h * dx)};
                g.fillPolygon(x1, y1, 3);

                // g.drawOval((int)(xSelf-rSelf), (int)(ySelf-rSelf),(int)(2*rSelf), (int)(2*rSelf));
                g.drawString(text, curveX, curveY);
                ////System.out.println(curveX+"  "+curveY+"  "+rSelf);
                return;
            } else {
                g2.draw(new QuadCurve2D.Float(x, y, curveX, curveY, arrowX, arrowY));
                drawArrow(g, Math.atan2(stateB.y - curveY, stateB.x - curveX));
            }

        } else {
            g2.drawLine(x, y, arrowX, arrowY);
            drawArrow(g, Math.atan2(stateB.y - y, stateB.x - x));
        }
        if (isStart) {
            g.drawString("", curveX, curveY);
        } else {
            g.drawString(text, curveX, curveY);
        }
    }

    void drawArrow(Graphics g, double angle) {
        double angle2 = Math.atan2(curveY - stateB.y, curveX - stateB.x);
        double dx2 = Math.cos(angle2);
        double dy2 = Math.sin(angle2);
        int x = stateB.x + (int) (stateB.r * dx2);
        int y = stateB.y + (int) (stateB.r * dy2);
        int e = 12;
        int h = 8;
        double dx = Math.cos(angle);
        double dy = Math.sin(angle);
        g.setColor(isSelect ? Color.BLUE : Color.BLACK);
        int x1[] = {x, (int) (x - e * dx + h * dy), (int) (x - e * dx - h * dy)};
        int y1[] = {y, (int) (y - e * dy - h * dx), (int) (y - e * dy + h * dx)};
        g.fillPolygon(x1, y1, 3);
    }

    void setArrow() {
        x = stateA != null ? stateA.x : x;
        y = stateA != null ? stateA.y : y;
        arrowX = stateB.x;
        arrowY = stateB.y;
        if (curveX == -1 || curveY == -1) {
            curveX = (x + arrowX) / 2;
            curveY = (y + arrowY) / 2;
        } else if (isStart) {
            curveX = x;
            curveY = y;
        }
    }
}

//////////////////////////////// 6. State ////////////////////////////////
class State {

    int x;
    int y;
    String text;
    int r;
    int shift;
    boolean isAccept;
    boolean isSelect;

    State(int x, int y) {
        this.r = 36;
        this.x = x;
        this.y = y;
        this.text = "";
        this.isAccept = false;
        this.isSelect = false;
        this.shift = 24;
    }

    void setText(String text) {
        this.text = text;
    }

    boolean inCircle(int x0, int y0) {
        return ((x0 - x) * (x0 - x) + (y0 - y) * (y0 - y)) <= r * r;
    }

    void swapAccept() {
        this.isAccept = !this.isAccept;
    }

    void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(isSelect ? Color.BLUE : Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        g2.fillOval(x - r, y - r, r * 2, r * 2);

        g2.setColor(Color.WHITE);
        g2.fillOval(x - r + (r - shift - 6) / 2, y - r + (r - shift - 6) / 2, r * 2 - (r - shift - 6), r * 2 - (r - shift - 6));

        if (isAccept) {
            g2.setColor(isSelect ? Color.BLUE : Color.BLACK);
            g2.fillOval(x - r + (r - shift) / 2, y - r + (r - shift) / 2, r * 2 - (r - shift), r * 2 - (r - shift));

            g2.setColor(Color.WHITE);
            g2.fillOval(x - r + (r - shift + 6) / 2, y - r + (r - shift + 6) / 2, r * 2 - (r - shift + 6), r * 2 - (r - shift + 6));
        }
        g2.setColor(isSelect ? Color.BLUE : Color.BLACK);
        g2.drawString(text, x - 10, y + 10);
    }

}
//////////////////////////////// 7. RegExConvert to Postfix ////////////////////////////////

class RegEx {  //reference https://gist.github.com/gmenard/6161825

    private static Integer getPrecedence(Character c) {
        int charC = (int) c;
        switch (charC) {
            case '(':
                return 1;
            case 'U':
                return 2;
            case '.':
                return 3;
            case '*':
                return 4;
            default:
                return 5;
        }
    }

    public static String infixToPostfix(String regex) {

        String postfix = new String();

        Stack<Character> stack = new Stack<Character>();

        String res = new String();
        List<Character> allOperators = Arrays.asList('*', 'U');
        List<Character> binaryOperators = Arrays.asList('U');

        for (int i = 0; i < regex.length(); i++) {
            Character c1 = regex.charAt(i);

            if (i + 1 < regex.length()) {
                Character c2 = regex.charAt(i + 1);

                res += c1;

                if (!c1.equals('(') && !c2.equals(')') && !allOperators.contains(c2) && !binaryOperators.contains(c1)) {
                    res += '.';
                }
            }
        }
        res += regex.charAt(regex.length() - 1);

        String formattedRegEx = res;
        // System.out.println(formattedRegEx);
        for (Character c : formattedRegEx.toCharArray()) {
            switch (c) {
                case '(':
                    stack.push(c);
                    break;

                case ')':
                    while (!stack.peek().equals('(')) {
                        postfix += stack.pop();
                    }
                    stack.pop();
                    break;

                default:
                    while (stack.size() > 0) {
                        Character peekedChar = stack.peek();

                        Integer peekedCharPrecedence = getPrecedence(peekedChar);
                        Integer currentCharPrecedence = getPrecedence(c);

                        if (peekedCharPrecedence >= currentCharPrecedence) {
                            postfix += stack.pop();
                        } else {
                            break;
                        }
                    }
                    stack.push(c);
                    break;
            }

        }

        while (stack.size() > 0) {
            postfix += stack.pop();
        }

        return postfix;
    }
}
