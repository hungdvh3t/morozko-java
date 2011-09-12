<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Sql Servlet</title>
	</head>
	<body>
		<h3>Sql Servlet v 1.0.1</h3>
		<c:choose>
			<form action="sql" method="post">
				<select name="query">
					<c:forEach items="${sqlConfig.queryCollection}" var="query">
						<option value="${query.name}">${query.description}</option>
					</c:forEach>
				</select>
				<input type="submit" name="esegui"/>
			</form>
			<c:when test="${output != null}">
				<table width="100%">
				<c:if test="${output.hasHead eq 'true'}">
					<tr>
						<c:forEach items="${output.head}" var="currentCell">
							<th style="border: 1px solid black">${currentCell}</th>
						</c:forEach>
					</tr>
					<c:forEach items="${output}" var="currentRow">
						<tr>
						<c:forEach items="${currentRow}" var="currentCell">
							<td style="border: 1px solid black">${currentCell}</td>
						</c:forEach>
						</tr>
					</c:forEach>
				</c:if>
				</table>
			</c:when>
			<c:otherwise>
				<p>Esegui una query</p>
			</c:otherwise>
		</c:choose>
	</body>
</html>