package org.example.notepad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {

    File file = null;
    Color color = Color.BLACK;
    JTextPane textPane = new JTextPane();
    JDialog about = new JDialog(this);
    JFileChooser fileChooser = new JFileChooser();
    JTextArea text = new JTextArea();
    public static Font font = new Font("宋体", Font.PLAIN, 16);

    public static void main(String[] args) {
        Notepad frame = new Notepad();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Notepad(){
        JMenuItem jminew, jmiopen, jmisave, jmisaveas, jmiexit;
        JMenuItem jmicut, jmicopy, jmiplaster, jmiall;
        JMenuItem jmifont, jmicolor;
        JMenuItem jminotepad, jmicalculator, jmipowershell;
        JMenuItem jmiabout;

        setTitle("记事本");
        setLocation(100,50);
        JMenuBar jmb = new JMenuBar();
        setJMenuBar(jmb);

        JMenu filemenu = new JMenu("File");
        jmb.add(filemenu);

        JMenu editmenu = new JMenu("Edit");
        jmb.add(editmenu);

        JMenu formatmenu = new JMenu("Format");
        jmb.add(formatmenu);

        JMenu toolmenu = new JMenu("Tool");
        jmb.add(toolmenu);

        JMenu helpmenu = new JMenu("Help");
        jmb.add(helpmenu);

        filemenu.add(jminew = new JMenuItem("New",'N'));
        filemenu.add(jmiopen = new JMenuItem("Open",'O'));
        filemenu.add(jmisave = new JMenuItem("Save",'S'));
        filemenu.addSeparator();
        filemenu.add(jmisaveas = new JMenuItem("Save as"));
        filemenu.add(jmiexit = new JMenuItem("Exit",'Q'));

        jminew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        jmiopen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        jmisave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        jmiexit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));

        editmenu.add(jmicut = new JMenuItem("Cut",'X'));
        editmenu.add(jmicopy = new JMenuItem("Copy",'C'));
        editmenu.add(jmiplaster = new JMenuItem("Plaster",'V'));
        editmenu.addSeparator();
        editmenu.add(jmiall = new JMenuItem("All"));

        jmicut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        jmicopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        jmiplaster.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
        jmiall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        formatmenu.add(jmifont = new JMenuItem("Font"));
        formatmenu.add(jmicolor = new JMenuItem("Color"));

        toolmenu.add(jminotepad = new JMenuItem("MS Notepad"));
        toolmenu.add(jmicalculator = new JMenuItem("MS Calculator"));
        toolmenu.add(jmipowershell = new JMenuItem("MS PowerShell"));

        helpmenu.add(jmiabout = new JMenuItem("About"));

        textPane.add(text);
        text.setFont(font);

        fileChooser.setFileFilter(new MyFileFilter());

        JScrollPane scrollPane = new JScrollPane(text);
        scrollPane.setPreferredSize(new Dimension(1000,500));
        getContentPane().add(scrollPane);

        jminew.addActionListener(this);
        jmiopen.addActionListener(this);
        jmisave.addActionListener(this);
        jmisaveas.addActionListener(this);
        jmiexit.addActionListener(this);

        jmicut.addActionListener(this);
        jmicopy.addActionListener(this);
        jmiplaster.addActionListener(this);
        jmiall.addActionListener(this);

        jmifont.addActionListener(this);
        jmicolor.addActionListener(this);

        jminotepad.addActionListener(this);
        jmicalculator.addActionListener(this);
        jmipowershell.addActionListener(this);

        jmiabout.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        if(e.getSource() instanceof JMenuItem){
            if("New".equals(name)){
                text.setText("");
                file = null;
            }else if("Open".equals(name)){
                if(file != null) fileChooser.setSelectedFile(file);
                int returnVal = fileChooser.showOpenDialog(Notepad.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    FileReader fr = null;
                    BufferedReader burd = null;
                    try{
                        fr = new FileReader(file);
                        burd = new BufferedReader(fr);
                        String data;
                        while ((data = burd.readLine()) != null){
                           text.append(data + "\n");
                        }
                    }catch (Exception e_open){
                        e_open.printStackTrace();
                    }finally {
                        try {
                            if (burd != null) burd.close();
                            if (fr != null) fr.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }else if("Save".equals(name)){
                if(file == null){
                    int returnVal = fileChooser.showSaveDialog(Notepad.this);
                    if(returnVal == JFileChooser.APPROVE_OPTION){
                        file = fileChooser.getSelectedFile();
                    }
                }
                writeToFile();
            }else if("Save as".equals(name)){
                if(file != null) fileChooser.setSelectedFile(file);
                int returnVal = fileChooser.showSaveDialog(Notepad.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                }
                writeToFile();
            }else if("Cut".equals(name)){
                text.cut();
            }else if("Copy".equals(name)){
                text.copy();
            }else if("Plaster".equals(name)){
                text.paste();
            }else if("All".equals(name)){
                text.selectAll();
            }else if("Font".equals(name)){
                new FontChooserDialog(new JFrame(),true,font);
                text.setFont(font);
            }else if("Color".equals(name)){
                color = JColorChooser.showDialog(Notepad.this,"颜色选择对话框",color);
                text.setForeground(color);
            }else if("MS Notepad".equals(name)){
                try{
                    String command = "notepad.exe";
                    Runtime.getRuntime().exec(command);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }else if("MS Calculator".equals(name)){
                try{
                    String command = "calc.exe";
                    Runtime.getRuntime().exec(command);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }else if("MS PowerShell".equals(name)){
                try{
                    String[] command = {"cmd", "/c", "start", "powershell.exe", "-NoExit"};
                    Runtime.getRuntime().exec(command);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }else if("About".equals(name)){
                about.setLayout(new GridLayout(6,1));
                about.setTitle("About...");
                about.setSize(320,280);
                about.getContentPane().setBackground(Color.WHITE);
                about.getContentPane().add(new JLabel("Star"));
                about.getContentPane().add(new JLabel("Edition 2.0"));
                about.getContentPane().add(
                        new JLabel(
                                "copyright possession (c) super Star Corp."
                        ));
                about.getContentPane().add(
                        new JLabel("Thank you to use!"));
                about.setLocation(450,450);
                about.setModal(true);
                about.setVisible(true);
            }else if("Exit".equals(name)){
                System.exit(0);
            }
        }
    }

    private void writeToFile() {
        FileWriter fw = null;
        BufferedWriter bufw = null;
        try{
            fw = new FileWriter(file);
            bufw = new BufferedWriter(fw);
            bufw.write(text.getText());
            bufw.flush();
        }catch (Exception e_save){
            e_save.printStackTrace();
        }finally {
            try {
                if (bufw != null) bufw.close();
                if (fw != null) fw.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
