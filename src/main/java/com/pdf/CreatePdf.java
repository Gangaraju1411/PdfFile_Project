package com.pdf;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdf {
	

	final static String url = "jdbc:mysql://localhost:3306/raju";
	final static String user_host = "root";
	final static String pwd = "141199";
	private static final String select = " select * from books" ;
	
	
	public static void main(String[] args) throws Exception {
		
		
		Connection con = DriverManager.getConnection(url,user_host,pwd);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(select);
		System.out.println("data fetched");
		
		
		 // Get column names
	      ResultSetMetaData metadata = rs.getMetaData();
	      int columnCount = metadata.getColumnCount();
	      String[] columnNames = new String[columnCount];
	      for (int i = 1; i <= columnCount; i++) {
	          columnNames[i - 1] = metadata.getColumnName(i);
	      }
	     
		
		
 	   Document document = new Document();
  	   PdfWriter.getInstance(document, new FileOutputStream("Data.pdf"));
  	   document.open();
    
  	   PdfPTable table = new PdfPTable(3);
   	   PdfPCell header = new PdfPCell();
		
    
    
    //Add header row with column names
    for (String columnName : columnNames) {
        header.setPhrase(new Phrase(columnName));
        table.addCell(header);
    }
    
    System.out.println(" added the column name to the table");
   
    while (rs.next()) {
        table.addCell(String.valueOf(rs.getInt(1)));
        table.addCell(rs.getString(2));
        table.addCell(String.valueOf(rs.getString(3)));
    }
		
		
    document.add(table);
    document.close();
    System.out.println("PDF file generated successfully!");
			
	}
}
