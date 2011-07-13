<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<doc>
  <metadata>   	
  	 	 <info name="page-orient">horizontal</info>
        <info name="excel-table-id">excel-table=Utenti</info>
        <info name="excel-width-multiplier">450</info>
  </metadata> 
  <body>
    <para size="20">
      Test document 
    </para>
    
    <table columns="5"  colwidths="20;20;20;20;20"  width="100" id="excel-table">
    	<c:forTokens var="current" items="1;2;3;4;5;6;7;8;9;10" delims=";">
    		<row>
    			<cell colspan="5"><para>${current}</para></cell>
    		</row>
    	</c:forTokens>
    </table>
    
   
  </body>
</doc>