/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;

/**
 *
 * @author Mahmoud Ahmed
 */
import static cli.CLI.Decompress;
import javax.swing.*;    // Needed for Swing classes
import java.awt.event.*; // Needed for ActionListener Interface
import java.util.logging.Level;
import java.util.logging.Logger;

/**
   The Window class displays a JFrame
*/

public class Window extends JFrame
{
   private JPanel panel;             // To reference a panel
   private JLabel messageLabel;      // To reference a label
   private JTextField fileName;      // To reference a text field
   private JButton compress;         // To reference a button
   private JButton decompress;       // To reference a button
   private final int WINDOW_WIDTH = 200;  // Window width
   private final int WINDOW_HEIGHT = 200; // Window height

   /**
      Constructor
   */

   public Window()
   {
      // Set the window title.
      setTitle("Compress / Decompress");

      // Set the size of the window.
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

      // Specify what happens when the close button is clicked.
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Build the panel and add it to the frame.
      buildPanel();

      // Add the panel to the frame's content pane.
      add(panel);

      // Display the window.
      setVisible(true);
   }

   /**
      The buildPanel method adds a label, text field, and
      and a button to a panel.
   */

   private void buildPanel()
   {
      // Create a label to display instructions.
      messageLabel = new JLabel("Enter File Name");
      
      // Create a text field 10 characters wide.
      fileName = new JTextField(10);

      // Create a button with the caption "Compress".
      compress   = new JButton("Compress");
      
      // Create a button with the caption "Decompress".
      decompress = new JButton("Decompress");
      
      
      // Add an action listener to the button.
      compress.addActionListener(new Compressor());
      
      decompress.addActionListener(new Decompressor());
      
      // Create a JPanel object and let the panel
      // field reference it.
      panel = new JPanel();

      // Add the label, text field, and button
      // components to the panel.
      panel.add(messageLabel);
      panel.add(fileName);
      panel.add(compress);
      panel.add(decompress);
   }

   /**
      compress is an action listener class for
      the Compressor button.
   */

   private class Compressor implements ActionListener
   {
      /**
         The actionPerformed method executes when the user
         clicks on the compress button.
         @param e The event object.
      */

      public void actionPerformed(ActionEvent e)
      {
          String input;  // To hold the user's input

          // Get the text entered by the user into the
          // text field.
          input = System.getProperty("user.dir") + '\\' + fileName.getText();
          System.out.println("\nFrom GUI " + input);
          try 
          {
              CLI.Compress(input);
              // Display the Done.
              JOptionPane.showMessageDialog(null, "Done!");
          } 
          catch (Exception ex) 
          {
              System.out.println(ex.getMessage());
          }
      }
      
   }
   
   private class Decompressor implements ActionListener
   {
      /**
         The actionPerformed method executes when the user
         clicks on the decompress button.
         @param e The event object.
      */

      public void actionPerformed(ActionEvent e)
      {
          String input;  // To hold the user's input

          // Get the text entered by the user into the
          // text field.
          input = System.getProperty("user.dir") + '\\' + fileName.getText();
          System.out.println("\nFrom GUI " + input);
          try 
          {
              CLI.Decompress(input);
              // Display the Done.
              JOptionPane.showMessageDialog(null, "Done!");
          } 
          catch (Exception ex) 
          {
              System.out.println(ex.getMessage());
          }
      }
   }
}
