package tool.test;

public class Test1 {

	public static void main( String[] args ) {
		String text = "test.pdf.zip";
		int index = text.lastIndexOf( (int)'.' );
		System.out.println( "index : "+index );
	}
	
}
