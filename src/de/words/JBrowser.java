package de.words;


import javax.swing.*; 
import javax.swing.event.*; 
import java.io.*; 
import java.net.*; 
 
public class JBrowser extends JEditorPane implements HyperlinkListener 
{ 
  JBrowser( String url ) 
  { 
    setEditable( false ); 
    addHyperlinkListener( this ); 
 
    try 
    { 
      setPage( new URL(url) ); 
    } 
    catch ( IOException e ) { e.printStackTrace(); } 
  } 
 
  public void hyperlinkUpdate( HyperlinkEvent event ) 
  { 
    HyperlinkEvent.EventType typ = event.getEventType(); 
 
    if ( typ == HyperlinkEvent.EventType.ACTIVATED ) 
    { 
      try 
      { 
        setPage( event.getURL() ); 
      } 
      catch( IOException e ) { 
        JOptionPane.showMessageDialog( this, 
                                      "Can't follow link to " 
                                        + event.getURL().toExternalForm(), 
                                      "Error", 
                                      JOptionPane.ERROR_MESSAGE); 
      } 
 
    } 
  } 
 
  public static void main( String[] args ) 
  { 
    JFrame f = new JFrame(); 
    f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
    f.setSize( 600, 500 ); 
    f.add( new JScrollPane(new JBrowser("http://dict.tu-chemnitz.de/?service=deen;query=$"+ "fate")));
 
    f.setVisible( true ); 
  } 
}