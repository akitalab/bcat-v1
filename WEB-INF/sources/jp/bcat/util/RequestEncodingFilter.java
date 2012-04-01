package jp.bcat.util;
import java.io.IOException;
import javax.servlet.*;

public class RequestEncodingFilter implements Filter {
	private String encoding;

	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		if (encoding != null)
			req.setCharacterEncoding(encoding);
		chain.doFilter(req, res); 
	}

	public void destroy() {
	}
}
