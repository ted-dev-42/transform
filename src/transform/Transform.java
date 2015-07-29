package transform;

//import java.io.File;
//import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class Transform {

    public static void main(String[] args) {
        
        if(args.length != 3)
        {
            System.out.println("Proper Usage is: java -jar transform.jar xmlfile xsltfile htmlfile");
            System.exit(1);
        }
        
        String XmlFileName = null;
        String XsltFileName = null;
        String HtmlFileName = null;
        
        try {
            XmlFileName = args[0];
            XsltFileName = args[1];
            HtmlFileName = args[2];
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException caught");
            System.exit(1);
        }
        
        System.out.println("xml=" + XmlFileName + ",xslt=" + XsltFileName +",html=" + HtmlFileName);
        
        TransformerFactory factory = TransformerFactory.newInstance();
        FileInputStream xmlSrc = null;
        FileOutputStream htmlDest = null;
        
        boolean isSuccess = false;

        try {

            xmlSrc = new FileInputStream(XmlFileName);
            // Now your raw files are accessible here.
            Source xmlInput = new StreamSource(xmlSrc);
            
            InputStream xslInputStream = new FileInputStream(XsltFileName);
                    
            Source xslInput = new StreamSource(xslInputStream);

            Transformer transformer = factory.newTransformer(xslInput);

            htmlDest = new FileOutputStream(HtmlFileName);
            
            Result result = new StreamResult(htmlDest);
            transformer.transform(xmlInput, result);
            
            isSuccess = true;
            
        } catch (Exception e) {
            System.out.println("Transform error!! " + e.toString());
        } finally {
            try {
                if (xmlSrc != null) {
                    xmlSrc.close();
                }
                if (htmlDest != null) {
                    htmlDest.close();
                }                
            } catch (Exception e) {
                System.out.println("Transform close error!! " + e.toString());
            }
        }
        
        if (isSuccess) {
            System.out.println("Transform success ");
            System.exit(0);
        } else {
            System.exit(1);
        }

    }

}
