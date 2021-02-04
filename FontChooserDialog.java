package org.example.notepad;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class FontChooserDialog extends JDialog {
    private JScrollPane jScrollPane = null;
    private JScrollPane jScrollPane1 = null;
    private JScrollPane jScrollPane2 = null;
    private JScrollPane jScrollPane3 = null;

    private JPanel jPanel = null;
    private JPanel jPanel1 = null;
    private JPanel jPanel2 = null;

    private static final JLabel fontStyle = new JLabel();

    private JTextField fontNameText = null;
    private JTextField fontItalicText = null;
    private JTextField fontSizeText = null;

    private JButton okButton = null;
    private JButton rigitButton = null;
    private JButton cancleButton = null;

    GraphicsEnvironment getFont = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Font[] fonts = getFont.getAllFonts();

    private JList<String> fontNameList = null;
    private JList<Integer> fontSizeList = null;
    private JList<String> fontItalicList = null;

    private Font defaultFont = new Font("\u5b8b\u4f53", Font.PLAIN, 14);

    private final Integer[] vecSizes = {8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,78};
    private final String[] vecStyles = {"PLAIN", "BOLD", "ITALIC", "BOLD+ITALIC"};
    private final Map<String, Integer> mapsStyle = new HashMap<>(4);

//    public FontChooserDialog() {
//        this(null);
//    }
//
//    public FontChooserDialog(JFrame jFrame) {
//        super(jFrame, true);
//    }
//
//    public FontChooserDialog(JFrame jFrame, boolean boo){
//        this(jFrame, boo, null);
//    }

    public FontChooserDialog(JFrame jFrame, boolean boo, Font font) {
        super(jFrame, boo);
        if(font != null) defaultFont = font;
        initFontNameAddSizeAddStyle();
        initialize();
        initializeFont(false);
        initListener();
        this.setLocationRelativeTo(jFrame);
        this.setVisible(true);
    }

    private void initListener() {
//        fontNameList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                String name = fontNameList.getSelectedValue();
//                fontNameText.setText(name);
//                Font font = returnFontType(name);
//                fontStyle.setFont(font);
//            }
//        });
        fontNameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fontNameList.setSelectedIndex(fontNameList.getAnchorSelectionIndex());
                String name = fontNameList.getSelectedValue();
                fontNameText.setText(name);
                Font font = returnFontType(name);
                fontStyle.setFont(font);
            }
        });
//        fontItalicList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                String style = fontItalicList.getSelectedValue();
//                fontItalicText.setText(fontItalicList.getSelectedValue());
//                Font font = fontStyle.getFont().deriveFont(mapsStyle.get(style));
//                fontStyle.setFont(font);
//            }
//        });
        fontItalicList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String style = fontItalicList.getSelectedValue();
                fontItalicText.setText(fontItalicList.getSelectedValue());
                Font font = fontStyle.getFont().deriveFont(mapsStyle.get(style));
                fontStyle.setFont(font);
            }
        });
//        fontSizeList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                float size = fontSizeList.getSelectedValue();
//                fontSizeText.setText(Integer.toString((int) size));
//                Font font = fontStyle.getFont().deriveFont(size);
//                fontStyle.setFont(font);
//            }
//        });
        fontSizeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                float size = fontSizeList.getSelectedValue();
                fontSizeText.setText(Integer.toString((int) size));
                Font font = fontStyle.getFont().deriveFont(size);
                fontStyle.setFont(font);
            }
        });
    }

    private void initFontNameAddSizeAddStyle() {
        Vector<String> vecNames = new Vector<>();
        for (Font font : fonts) {
            vecNames.add(font.getName());
        }
        fontNameList = new JList<>(vecNames);
        fontSizeList = new JList<>(vecSizes);
        fontItalicList = new JList<>(vecStyles);

        //"PLAIN", "BOLD", "ITALIC", "BOLD+ITALIC"
        mapsStyle.put("PLAIN",0);
        mapsStyle.put("BOLD",1);
        mapsStyle.put("ITALIC",2);
        mapsStyle.put("BOLD+ITALIC",3);
    }

    private void initializeFont(boolean reFont) {
        if(reFont) fontStyle.setFont(new Font("宋体",Font.PLAIN,14));
        else fontStyle.setFont(defaultFont);
        fontNameList.setSelectedValue(defaultFont.getFontName(), true);
        fontSizeList.setSelectedValue(defaultFont.getSize(), true);
        fontItalicList.setSelectedIndex(defaultFont.getStyle());
    }

    public Font returnFontType(String fontName){
        return new Font(fontName,fontStyle.getFont().getStyle(), fontStyle.getFont().getSize());
    }

    private void initialize() {
        this.setContentPane(getJPanel());
        this.setFont(defaultFont);
        this.setBounds(new Rectangle(0,0,440,350));
        this.setTitle("字体选择对话框");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeWindow();
            }
        });
    }

    private void closeWindow() {
        this.setVisible(false);
    }

    private JPanel getJPanel() {
        if(jPanel == null){
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel.setFont(new Font("Dialog", Font.PLAIN, 12));
            jPanel.add(getJPanel1(), null);
            jPanel.add(getJPanel2(),null);
            jPanel.add(getOKButton(),null);
            jPanel.add(getRegitButton(),null);
            jPanel.add(getCancleButton(),null);
        }
        return jPanel;
    }

    private JButton getCancleButton() {
        if(cancleButton == null){
            cancleButton = new JButton();
            cancleButton.setBounds(new Rectangle(355,275,60,20));
            cancleButton.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 12));
            cancleButton.setText("取消");
            cancleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    closeWindow();
                }
            });
        }
        return cancleButton;
    }

    private JButton getRegitButton() {
        if(rigitButton == null){
            rigitButton = new JButton();
            rigitButton.setBounds(new Rectangle(285,275,60,20));
            rigitButton.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 12));
            rigitButton.setText("重置");
            rigitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    initializeFont(true);
                    fontNameText.setText("宋体");
                    fontItalicText.setText("PLAIN");
                    fontSizeText.setText("12");
                }
            });
        }
        return rigitButton;
    }

    private JButton getOKButton() {
        if(okButton == null){
            okButton = new JButton();
            okButton.setBounds(new Rectangle(215,275,60,20));
            okButton.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 12));
            okButton.setText("确定");
            okButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Notepad.font = fontStyle.getFont();
                    closeWindow();
                }
            });
        }
        return okButton;
    }

    private JPanel getJPanel2() {
        if(jPanel2 == null){
            jPanel2 = new JPanel();
            jPanel2.setLayout(null);
            jPanel2.setBounds(new Rectangle(3,180,414,90));
            jPanel2.setBorder(BorderFactory.createTitledBorder(null,
                    "\u6548\u679c\u9884\u89c8",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("\u5b8b\u4f53", Font.PLAIN, 14),
                            new Color(51,51,51)));
            jPanel2.add(getJSCrollPane(), null);
        }
        return jPanel2;
    }

    private JScrollPane getJSCrollPane() {
        if(jScrollPane == null){
            fontStyle.setText("Hello World");
            fontStyle.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 14));
            fontStyle.setHorizontalAlignment(SwingConstants.CENTER);
            fontStyle.setHorizontalTextPosition(SwingConstants.CENTER);

            jScrollPane = new JScrollPane();
            jScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            jScrollPane.setViewportView(fontStyle);
            jScrollPane.setBounds(new Rectangle(5,20,400,60));
        }
        return jScrollPane;
    }

    private JPanel getJPanel1() {
        if(jPanel1 == null){
            JLabel jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(5,5,150,15));
            jLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
            jLabel.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 14));
            jLabel.setText("字体：");

            JLabel jLabel1 = new JLabel();
            jLabel1.setBounds(new Rectangle(160,5,120,15));
            jLabel1.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 14));
            jLabel1.setText("字形：");

            JLabel jLabel2 = new JLabel();
            jLabel2.setBounds(new Rectangle(285,5,120,15));
            jLabel2.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 14));
            jLabel2.setText("大小：");

            jPanel1 = new JPanel();
            jPanel1.setLayout(null);
            jPanel1.setBounds(new Rectangle(5,5,410,175));
            jPanel1.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
            jPanel1.add(jLabel,null);
            jPanel1.add(jLabel1,null);
            jPanel1.add(jLabel2,null);

            jPanel1.add(getFontNameText(),null);
            jPanel1.add(getFontItalicText(),null);
            jPanel1.add(getFontSizeText(),null);

            jPanel1.add(getJScrollPane1(),null);
            jPanel1.add(getJScrollPane2(),null);
            jPanel1.add(getJScrollPane3(),null);
        }
        return jPanel1;
    }

    private Component getJScrollPane3() {
        if(jScrollPane3 == null){
            jScrollPane3 = new JScrollPane();
            jScrollPane3.setViewportView(fontSizeList);
            jScrollPane3.setBounds(new Rectangle(285,45,100,130));
        }
        return jScrollPane3;
    }

    private Component getJScrollPane2() {
        if(jScrollPane2 == null){
            jScrollPane2 = new JScrollPane();
            jScrollPane2.setViewportView(fontItalicList);
            jScrollPane2.setBounds(new Rectangle(160,45,120,130));
        }
        return jScrollPane2;
    }

    private Component getJScrollPane1() {
        if(jScrollPane1 == null){
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(fontNameList);
            jScrollPane1.setBounds(new Rectangle(5,45,150,130));
        }
        return jScrollPane1;
    }

    private JTextField getFontSizeText() {
        if(fontSizeText == null){
            fontSizeText = new JTextField(Integer.toString(defaultFont.getSize()));
            fontSizeText.setBounds(new Rectangle(285,25,100,20));
            fontSizeText.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 14));
            fontSizeText.setEnabled(true);
        }
        return fontSizeText;
    }

    private JTextField getFontItalicText() {
        if(fontItalicText == null){
            fontItalicText = new JTextField(vecStyles[defaultFont.getStyle()]);
            fontItalicText.setBounds(new Rectangle(160,25,120,20));
            fontItalicText.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 14));
            fontItalicText.setEnabled(true);
        }
        return fontItalicText;
    }

    private JTextField getFontNameText() {
        if(fontNameText == null){
            fontNameText = new JTextField(defaultFont.getName());
            fontNameText.setBounds(new Rectangle(5,25,150,20));
            fontNameText.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 14));
            fontNameText.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if(e.getKeyChar() == '\n'){
                        String nameFont = fontNameList.getSelectedValue();
                        fontNameText.setText(nameFont);
                        Font font = returnFontType(nameFont);
                        fontStyle.setFont(font);
                    }else {
                        String oldText = fontNameText.getText();
                        String newText;
                        if ("".equals(fontNameText.getSelectedText()) && null == fontNameText.getSelectedText()) {
                            newText = fontNameText.getText() + e.getKeyChar();
                        } else {
                            newText = oldText.substring(0, fontNameText.getSelectionStart()) + e.getKeyChar() + oldText.substring(fontNameText.getSelectionEnd());
                        }
                        String nameString = getLateIndex(fontNameList, newText);
                        if (nameString != null) {
                            fontNameList.setSelectedValue(nameString, true);
                        }
                    }
                }
            });
        }
        return fontNameText;
    }

    private String getLateIndex(JList<String> jList, String str){
        ListModel<String> list = jList.getModel();
        if(str.equals("")) return null;
        for(int i=0; i<list.getSize(); i++){
            if(list.getElementAt(i).startsWith(str)){
                return list.getElementAt(i);
            }
        }
        return null;
    }
}
