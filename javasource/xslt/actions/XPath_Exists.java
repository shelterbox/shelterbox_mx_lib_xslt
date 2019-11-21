// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package xslt.actions;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.stream.StreamSource;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathExecutable;
import net.sf.saxon.s9api.XPathSelector;

public class XPath_Exists extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String Source;
	private java.lang.String Namespaces;
	private java.lang.String Xpath;

	public XPath_Exists(IContext context, java.lang.String Source, java.lang.String Namespaces, java.lang.String Xpath)
	{
		super(context);
		this.Source = Source;
		this.Namespaces = Namespaces;
		this.Xpath = Xpath;
	}

	@Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
		Processor proc = new Processor( false );
        XdmNode source = proc.newDocumentBuilder().build( new StreamSource( new StringReader( Source ) ) );
        
		XPathCompiler comp = proc.newXPathCompiler();
		String[] namespaces = Namespaces.split(" ");
		
		Pattern regex = Pattern.compile( "xmlns(?::([^=]+))?=\"([^\"]+)\"" );
				
		for( String namespace : namespaces ) {
			Matcher m = regex.matcher( namespace );
			if( m.find()  ) {
				if( m.group(1) != "" ) {
					// Namespace prefix was supplied
					comp.declareNamespace( m.group(1), m.group(2));
				}
			}
		}
		
        XPathExecutable exp = comp.compile( Xpath );
        XPathSelector trans = exp.load();
        trans.setContextItem( source );

        XdmValue result = trans.evaluate();

        if( result.size() > 0 ) return true;       
		return false;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "XPath_Exists";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}