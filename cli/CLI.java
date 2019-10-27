/**
 *  @file    CLI.java
 *  @author  Mahmoud Ahmed Tawfik - 20160227
 *  @date    11/10/2019
 *  @version 1.0
 */
package cli;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 


public class CLI 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Window kc = new Window();
        try
        {
            boolean shouldContinue = true;
            Scanner scanner = new Scanner(System.in);
            System.out.println("This tool generate compression and decompression using LZ-78 method.");
            
            
            while(true)
            {
                
                printMenu();
                char choice = scanner.next().charAt(0);

                switch(choice)
                {
                    case '0': 
                    {
                        shouldContinue = false; 
                        break;
                    }  
                    case '1':
                    {
                        String fileName;
                        
                        System.out.print("Enter file name: ");
                        fileName = scanner.next();

                        Compress(fileName); 
                        
                        break;
                    }
                    case '2':
                    {
                        String fileName;
                        
                        System.out.print("Enter file name: ");
                        fileName = scanner.next();
                        
                        Decompress(fileName); 
                        break;
                    } 
                    default:
                    {
                        printError();   
                    }  
                }
                
                if(!shouldContinue)
                    break;
            }
            
            printBye();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    private static void printMenu()
    {
        System.out.println("1) Compression");
        System.out.println("2) Decompression");
        System.out.println("0) Exit\n\n");
        System.out.print("Please Enter Your Choice: ");
    }
    
    private static void printError()
    {
        System.out.println("ERROR...");
    }
    
    private static void printBye()
    {
        System.out.println("Bye...");
    }
    
    private static File openFile(String _fileName) 
    {
        File file = null;
        
        try 
        {
            file = new File(_fileName);
        } 
        catch(Exception e) 
        {
            System.out.println("trouble opening file:" + _fileName);
            System.exit(1);
        }
        
        return file;
    }
    
    private static String readFromFile(String _fileName) throws Exception 
    {
        File file = openFile(_fileName);
        Scanner sc = new Scanner(file); 
        String result = "";
        
        while(sc.hasNextLine())
        {
            result += sc.nextLine();
            //System.out.println(sc.nextInt() + 1);
            //System.out.println(sc.nextLine());
            //System.out.println(sc.nextLine());
        }

        return result; 
    }

    private static void writeToFile(String _fileName, String _str) throws Exception 
    {
        File file = openFile(_fileName);
        
        FileWriter writer = new FileWriter(file);
        
        writer.write(_str);
        writer.flush();
        writer.close();
    }
    
    public static void Compress(String _fileName) throws Exception 
    {
        String _inputStream = readFromFile(_fileName);
        File file = openFile(_fileName + ".newex");
        
        FileWriter writer = new FileWriter(file);
        
        
        
        int next = 0;
        ArrayList<String> dictionary = new ArrayList<String>();
        
        dictionary.add(null);

        for(int i = 0;i < _inputStream.length();i++)
        {
            if(dictionary.contains(_inputStream.charAt(i) + ""))
            {
                String tt = "";
                next = dictionary.indexOf(_inputStream.charAt(i) + "");
                int j;
                for(j = i;j < _inputStream.length();j++)
                {
                    tt += _inputStream.charAt(j);
                    if(!dictionary.contains(tt))
                    {
                        break;
                    }
                    next = dictionary.indexOf(tt);
                }
                i = j;
                dictionary.add(tt);
                
            
                if(j == _inputStream.length())
                {
                    String a = next + "";
                    writer.write(a);
                    writer.write("\r\n");
                    writer.write("NULL");
                    System.out.println("<" + next + ", NULL>");
                }
                else
                {
                    String a = next + "";
                    writer.write(a);
                    writer.write("\r\n");
                    writer.write(tt.charAt(tt.length()-1));
                    writer.write("\r\n");
                    System.out.println("<" + next + "," + tt.charAt(tt.length()-1) + ">");   
                } 
            }
            else
            {
                String a = next + "";
                writer.write(a);
                writer.write("\r\n");
                writer.write(_inputStream.charAt(i));
                writer.write("\r\n");
                dictionary.add(_inputStream.charAt(i) + "");
                System.out.println("<" + next + "," + _inputStream.charAt(i) + ">");
            }
            next = 0;
        }
        
        
        writer.flush();
        writer.close();
        
    }
    
    public static void Decompress(String _fileName) throws Exception 
    {
        File file = openFile(_fileName);
        Scanner sc = new Scanner(file); 
        
        ArrayList<String> dictionary = new ArrayList<String>();
        String result = ""; 
        
        dictionary.add("");
        
        while(sc.hasNextLine())
        {
            int position = sc.nextInt();
            sc.nextLine();
            String nextSymbol = sc.nextLine();
            
            if(nextSymbol == "NULL")
            {
                result += dictionary.get(position);
                dictionary.add(dictionary.get(position));
            }
            else
            {
                result += dictionary.get(position) + nextSymbol;
                dictionary.add(dictionary.get(position) + nextSymbol);
            }
        }
        
        writeToFile(_fileName + ".txt", result);
        System.out.println(result);
    }
    
}
