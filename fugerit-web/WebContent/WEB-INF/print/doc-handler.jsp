<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<% request.setAttribute( "doc.writer.old", out );
	java.io.StringWriter writer = new java.io.StringWriter();
	out = pageContext.pushBody( writer );
	request.setAttribute( "doc.writer", writer );%>	
<!DOCTYPE doc SYSTEM "http://www.morozko.org/data/java/mod/doc/dtd/doc-1-0.dtd">
<% String pathJsp = "/WEB-INF/print/"+request.getAttribute( "doc-handler-name" )+".jsp"; %>
<jsp:include page="<%= pathJsp %>" flush="false"/>
