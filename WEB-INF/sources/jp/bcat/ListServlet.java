package jp.bcat;
import java.io.*;
import javax.servlet.http.*;

public class ListServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BookCatalog catalog = BookCatalog.getInstance();
		Book result[] = catalog.getBooks();

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>図書一覧</TITLE></HEAD>");
		out.println("<BODY><P>" + result.length + "項目</P>");
		BookWriter w = new BookWriter(out, " ");
		for (int i = 0; i < result.length; i++) {
			out.println("<PRE>[" + (i+1) + "]:");
			out.println(" 図書ID: " + result[i].getBookId());
			w.write(result[i]);
			out.println("</PRE>");
		}
		out.println("<A href='./'>メニューに戻る</A>");
		out.println("</BODY></HTML>");
	}
}