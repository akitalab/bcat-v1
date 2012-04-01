package jp.bcat;
import java.io.*;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String step = request.getParameter("step");
		if ("prepare".equals(step))
			prepare(request, response);
		else
			confirm(request, response);
	}

	void prepare(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String errorMessage = "";
		Book book = new Book();
		String title = request.getParameter("title");
		book.setTitle(title);
		if (title == null || title.length() == 0)
			errorMessage += "<P>タイトルを入力してください。</P>";
		String author = request.getParameter("author");
		book.setAuthor(author);
		if (author == null || author.length() == 0)
			errorMessage += "<P>著者を入力してください。</P>";
		String translator = request.getParameter("translator");
		book.setTranslator(translator);
		String publisher = request.getParameter("publisher");
		book.setPublisher(publisher);
		if (publisher == null || publisher.length() == 0)
			errorMessage += "<P>出版社を入力してください。</P>";
		String publicationDate = request.getParameter("publicationDate");
		book.setPublicationDate(publicationDate);
		if (publicationDate == null || publicationDate.length() == 0)
			errorMessage += "<P>出版年月日を入力してください。</P>";
		String code = request.getParameter("code");
		book.setCode(code);
		String memo = request.getParameter("memo");
		book.setMemo(memo);
		String keyword = request.getParameter("keyword");
		book.setKeyword(keyword);
		String dataCreator = request.getParameter("dataCreator");
		book.setDataCreator(dataCreator);
		if (dataCreator == null || dataCreator.length() == 0)
			errorMessage += "<P>登録者名を入力してください。</P>";
		String now = new SimpleDateFormat("yyyy-MM-dd")
			.format(new Date());
		book.setDataCreatedDate(now);

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>図書登録確認</TITLE></HEAD>");
		if (errorMessage.length() == 0) {
			out.println("<BODY>");
			out.println("この内容で登録しますか？");
			out.println("<PRE>");
			new BookWriter(out, "+ ").write(book);
			out.println("</PRE>");
			out.println("<FORM method='POST'>");
			out.println("<INPUT type='hidden' name='step' value='confirm'>");
			out.println("<INPUT type='submit' value='登録'>");
			out.println("</FORM>");
			out.println("<A href='./'>メニューに戻る</A>");
			out.println("</BODY>");
			request.getSession().setAttribute("book", book);
		} else {
			out.println("<BODY>");
			out.println(errorMessage);
			out.println("<A href='./'>メニューに戻る</A>");
			out.println("</BODY>");
		}
		out.println("</HTML>");
	}

	void confirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Book book = (Book)request.getSession().getAttribute("book");
		BookCatalog catalog = BookCatalog.getInstance();
		catalog.addBook(book);

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>図書登録結果</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<P>登録しました。</P>");
		out.println("<A href='./'>メニューに戻る</A>");
		out.println("</BODY>");
		out.println("</HTML>");
	}
}
