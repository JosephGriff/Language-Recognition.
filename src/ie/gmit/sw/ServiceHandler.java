package ie.gmit.sw;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import javax.servlet.*;
import javax.servlet.http.*;


/*
 * To compile this servlet, open a command prompt in the web application directory and execute the following commands:
 *
 * Linux/Mac																	Windows
 * ---------																	---------
 * cd WEB-INF/classes/															cd WEB-INF\classes\
 * javac -cp .:$TOMCAT_HOME/lib/servlet-api.jar ie/gmit/sw/*.java				javac -cp .:%TOMCAT_HOME%/lib/servlet-api.jar ie/gmit/sw/*.java
 * cd ../../																	cd ..\..\
 * jar -cf ngrams.war *															jar -cf ngrams.war *
 *
 * Drag and drop the file ngrams.war into the webapps directory of Tomcat to deploy the application. It will then be
 * accessible from http://localhost:8080.
 *
 * NOTE: the text file containing the 253 different languages needs to be placed in /data/wili-2018-Edited.txt. This means
 * that you must have a "data" directory in the root of your file system that contains a file called "wili-2018-Edited.txt".
 * Do NOT submit the wili-2018 text file with your assignment!
 *
*/

/***
 * the Service handler class receives and responds to Http requests
 */
public class ServiceHandler extends HttpServlet {
	private String languageDataSet = null; //This variable is shared by all HTTP requests for the servlet
	private static long jobNumber = 0; //The number of the task in the async queue

	private File f;
	private Map<String, Language> outQueue = new ConcurrentSkipListMap<>();
	private List<Request> inQueue = new LinkedList<>();

	/***
	 * gets the handle on the application context
	 * then reads the value from the <context-param> in web.xml
	 * @throws ServletException
	 */
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		languageDataSet = ctx.getInitParameter("LANGUAGE_DATA_SET");

		//You can start to build the subject database at this point. The init() method is only ever called once during the life cycle of a servlet

		f = new File(languageDataSet);


	}

	/***
	 * gets the request
	 * firstly outputs the MIME type
	 * Writes out text can be in binary this changes the MIME type
	 * intialize the request variables then chnages the options to whatever the programmer believed to be right for the assignment
	 * if tast number is == null add new job number to task (adds job to in-queue)
	 * else checks the out queue
	 * @param req the http request
	 * @param resp the http response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html"); //Output the MIME type
		PrintWriter out = resp.getWriter(); //Write out text. We can write out binary too and change the MIME type...

		//Initialise some request varuables with the submitted form info. These are local to this method and thread safe...
		String option = req.getParameter("cmbOptions"); //Change options to whatever you think adds value to your assignment...
		String s = req.getParameter("query");
		String taskNumber = req.getParameter("frmTaskNumber");


		out.print("<html><head><title>Advanced Object Oriented Software Development Assignment</title>");
		out.print("</head>");
		out.print("<body>");

		if (taskNumber == null){
			taskNumber = new String("T" + jobNumber);
			jobNumber++;
			//Add job to in-queue

			//inQueue.addFirst(new Request());
		}else{
			//Check out-queue for finished job
		}



		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<div id=\"r\"></div>");

		out.print("<font color=\"#993333\"><b>");
		out.print("Language Dataset is located at " + languageDataSet + " and is <b><u>" + f.length() + "</u></b> bytes in size");
		out.print("<br>Option(s): " + option);
		out.print("<br>Query Text : " + s);
		out.print("</font><p/>");

		out.print("<br>This servlet should only be responsible for handling client request and returning responses. Everything else should be handled by different objects. ");
		out.print("Note that any variables declared inside this doGet() method are thread safe. Anything defined at a class level is shared between HTTP requests.");
		out.print("</b></font>");

		out.print("<P> Next Steps:");
		out.print("<OL>");
		out.print("<LI>Generate a big random number to use a a job number, or just increment a static long variable declared at a class level, e.g. jobNumber.");
		out.print("<LI>Create some type of an object from the request variables and jobNumber.");
		out.print("<LI>Add the message request object to a LinkedList or BlockingQueue (the IN-queue)");
		out.print("<LI>Return the jobNumber to the client web browser with a wait interval using <meta http-equiv=\"refresh\" content=\"10\">. The content=\"10\" will wait for 10s.");
		out.print("<LI>Have some process check the LinkedList or BlockingQueue for message requests.");
		out.print("<LI>Poll a message request from the front of the queue and pass the task to the language detection service.");
		out.print("<LI>Add the jobNumber as a key in a Map (the OUT-queue) and an initial value of null.");
		out.print("<LI>Return the result of the language detection system to the client next time a request for the jobNumber is received and the task has been complete (value is not null).");
		out.print("</OL>");

		out.print("<form method=\"POST\" name=\"frmRequestDetails\">");
		out.print("<input name=\"cmbOptions\" type=\"hidden\" value=\"" + option + "\">");
		out.print("<input name=\"query\" type=\"hidden\" value=\"" + s + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
		out.print("</script>");


	}

	/***
	 * posts the response
	 * @param req the http request
	 * @param resp the http response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}