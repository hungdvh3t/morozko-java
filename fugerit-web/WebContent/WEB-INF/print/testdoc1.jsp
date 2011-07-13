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
    	<row>
	        <cell header="true">
	          <para>A1</para>
	        </cell>
	        <cell header="true">
	          <para>A2</para>
	        </cell>
	        <cell header="true">
	          <para>A3</para>
	        </cell>
	        <cell header="true">
	          <para>A4</para>
	        </cell>
	        <cell header="true">
	          <para>A5</para>
	        </cell>
      	</row>    
    	<row>
	        <cell>
	          <para>B1</para>
	        </cell>
	        <cell>
	          <para>B2</para>
	        </cell>
	        <cell>
	          <para>B3</para>
	        </cell>
	        <cell>
	          <para>B4</para>
	        </cell>
	        <cell>
	          <para>A5</para>
	        </cell>
      	</row>     
    	<row>
	        <cell rowspan="3">
	          <para>C1</para>
	        </cell>
	        <cell>
	          <para>C2</para>
	        </cell>
	        <cell>
	          <para>C3</para>
	        </cell>
	        <cell>
	          <para>C4</para>
	        </cell>
	        <cell>
	          <para>C5</para>
	        </cell>
      	</row>    
    	<row>
	        <cell>
	          <para>D2</para>
	        </cell>
	        <cell colspan="2">
	          <para>D3</para>
	        </cell>
	        <cell>
	          <para>D5</para>
	        </cell>
      	</row>     
    	<row>
	        <cell>
	          <para>E2</para>
	        </cell>
	        <cell>
	          <para>E3</para>
	        </cell>
	        <cell>
	          <para>E4</para>
	        </cell>
	        <cell>
	          <para>E5</para>
	        </cell>
      	</row>         	   	      	    
    	<row>
	        <cell>
	          <para>F1</para>
	        </cell>
	        <cell>
	          <para>F2</para>
	        </cell>
	        <cell>
	          <para>F3</para>
	        </cell>
	        <cell>
	          <para>F4</para>
	        </cell>
	        <cell>
	          <para>F5</para>
	        </cell>
      	</row>          		
    </table>
    
   
  </body>
</doc>