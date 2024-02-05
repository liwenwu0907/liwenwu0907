//package com.example.demo.util.page;
//
//import javax.servlet.ReadListener;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.Enumeration;
//
//
//public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
//
//    private final byte[] body;
//
//    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request,InputStream inputStream) throws IOException {
//        super(request);
//        body = HttpHelper.getBodyString(inputStream).getBytes(StandardCharsets.UTF_8);
//    }
//
//    @Override
//    public BufferedReader getReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(getInputStream()));
//    }
//
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//
//        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
//
//        return new ServletInputStream() {
//            /**
//             * Returns true when all the data from the stream has been read else
//             * it returns false.
//             *
//             * @return <code>true</code> when all data for this particular request
//             * has been read, otherwise returns <code>false</code>.
//             * @since Servlet 3.1
//             */
//            @Override
//            public boolean isFinished() {
//                return false;
//            }
//
//            /**
//             * Returns true if data can be read without blocking else returns
//             * false.
//             *
//             * @return <code>true</code> if data can be obtained without blocking,
//             * otherwise returns <code>false</code>.
//             * @since Servlet 3.1
//             */
//            @Override
//            public boolean isReady() {
//                return false;
//            }
//
//            /**
//             * Instructs the <code>ServletInputStream</code> to invoke the provided
//             * {@link ReadListener} when it is possible to read
//             *
//             * @param readListener the {@link ReadListener} that should be notified
//             *                     when it's possible to read.
//             * @throws IllegalStateException if one of the following conditions is true
//             *                               <ul>
//             *                               <li>the associated request is neither upgraded nor the async started
//             *                               <li>setReadListener is called more than once within the scope of the same request.
//             *                               </ul>
//             * @throws NullPointerException  if readListener is null
//             * @since Servlet 3.1
//             */
//            @Override
//            public void setReadListener(ReadListener readListener) {
//
//            }
//
//            @Override
//            public int read() throws IOException {
//                return bais.read();
//            }
//        };
//    }
//
//    @Override
//    public String getHeader(String name) {
//        return super.getHeader(name);
//    }
//
//    @Override
//    public Enumeration<String> getHeaderNames() {
//        return super.getHeaderNames();
//    }
//
//    @Override
//    public Enumeration<String> getHeaders(String name) {
//        return super.getHeaders(name);
//    }
//
//}
