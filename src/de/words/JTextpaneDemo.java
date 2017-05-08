package de.words;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class JTextpaneDemo extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

static SimpleAttributeSet ITALIC_GRAY = new SimpleAttributeSet();

  static SimpleAttributeSet BOLD_BLACK = new SimpleAttributeSet();

  static SimpleAttributeSet BLACK = new SimpleAttributeSet();

  JTextPane textPane = new JTextPane();

  // Best to reuse attribute sets as much as possible.
  static {
    StyleConstants.setForeground(ITALIC_GRAY, Color.gray);
    StyleConstants.setItalic(ITALIC_GRAY, true);
    StyleConstants.setFontFamily(ITALIC_GRAY, "Helvetica");
    StyleConstants.setFontSize(ITALIC_GRAY, 14);

    StyleConstants.setForeground(BOLD_BLACK, Color.black);
    StyleConstants.setBold(BOLD_BLACK, true);
    StyleConstants.setFontFamily(BOLD_BLACK, "Helvetica");
    StyleConstants.setFontSize(BOLD_BLACK, 14);

    StyleConstants.setForeground(BLACK, Color.black);
    StyleConstants.setFontFamily(BLACK, "Helvetica");
    StyleConstants.setFontSize(BLACK, 14);
  }

  public JTextpaneDemo(String word) {
    super("JTextPane Demo");

    JScrollPane scrollPane = new JScrollPane(textPane);
    getContentPane().add(scrollPane, BorderLayout.CENTER);

//    setEndSelection();
//    textPane.insertIcon(new ImageIcon("java2sLogo.GIF"));
//    insertText("\nWebsite for: www.java2s.com \n\n", BOLD_BLACK);
//
//    setEndSelection();
//    insertText("                                    ", BLACK);
//    setEndSelection();
//    insertText("\n      Java            "
//        + "                                    " + "Source\n\n",
//        ITALIC_GRAY);
//
//    insertText(" and Support. \n", BLACK);
//
//    setEndSelection();
//    JButton manningButton = new JButton("Load the web site for www.java2s.com");
//    manningButton.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        textPane.setEditable(false);
//        try {
////          textPane.setPage("http://www.java2s.com");
//          textPane.setPage("http://www.merriam-webster.com/cgi-bin/dictionary?book=Dictionary&va=airy");
//        } catch (IOException ioe) {
//          ioe.printStackTrace();
//        }
//      }
//    });
//    textPane.insertComponent(manningButton);
    textPane.setEditable(false);
    try {
//		textPane.setPage("http://www.merriam-webster.com/cgi-bin/dictionary?book=Dictionary&va=airy");
    	textPane.setContentType("text/html");
     	textPane.setPage(word);
//     	textPane.read(new StringReader("<html><body><table><tr><td>aaaa</td><td>aaaa</td></tr><tr><td>aaaa</td><td>aaaa</td></tr></table></body></html>"), null);
			} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
//	       MutableAttributeSet attrs = textPane.getInputAttributes();
//	        StyledDocument doc = textPane.getStyledDocument();
//
//	        doc.setCharacterAttributes(0, doc.getLength() + 1, attrs, false);
//
//		textPane.getStyledDocument();
	}

    setSize(600, 450);
    setVisible(true);
  }

  protected void insertText(String text, AttributeSet set) {
    try {
      textPane.getDocument().insertString(
          textPane.getDocument().getLength(), text, set);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }

  // Needed for inserting icons in the right places
  protected void setEndSelection() {
    textPane.setSelectionStart(textPane.getDocument().getLength());
    textPane.setSelectionEnd(textPane.getDocument().getLength());
  }

  public static void main(String argv[]) {
    new JTextpaneDemo("airy");
  }
}
