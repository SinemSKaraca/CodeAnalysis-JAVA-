/**
*
* @author Sinem Şaziye Karaca - saziye.karaca@org.sakarya.edu.tr
* @since 03.04.2024
* <p>
* İsterlere uygun olarak alınan java dosyasının analiz edilerek içerisindeki fonksiyon, yorum satırı, javadoc yorum satırı,
* kod satırı ve toplam kod satırı sayılarının tespit edilmesi ve bu değerler kullanılarak yorum sapma yüzdesinin hesaplanması.
* Bu işlemlerin gerçekleştirilmesi aşamasında fonksiyon sayısının tespiti, tek satırda koddan sonra gelen javadoc ve çoklu yorum satırı  
* tespiti için regular expression, diğer isterlerin tespiti içinse java dosyalama işlemlerinden faydalanılmıştır. En son ekrana çıktı olarak
* basılacak değerlerin virgülden sonra yalnızca iki basamağının gösterilmesi için 'DecimalFormat' sınıfının 'format' ve replaca
* metodları kullanılmıştır.
* </p>
*/

package odev_2024;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.util.regex.*;

public class DosyaAnaliz {
	public static void analiz(String javaPath) throws IOException {
		File file = new File(javaPath);
		System.out.println("Sinif: " + file.getName());
		
		FileReader fileReader = new FileReader(file);
		String icerik = icerikToString(file);
		String line;
		int spaceCounter = 0;
		int lineOfCode = 0;
		int javadocCounter = 0;
		int javadocTotal = 0;
		int commentLineCounter = 0;
		int commentLineTotal = 0;
		int methodCounter = 0;
		int codeLineCounter = 0;
		boolean javadocDetected = false;
		boolean commentLineDetected = false;
		//int index = 1;
		
		// FONKSIYON REGEX'I
		String regexMethod = "(public|private)\\s+(static\\s+)?([A-z]|[0-9]|_|<.*?>|[.*?])*\\s*([A-z]|[0-9]|_)*\\s*\\(.*?\\).*?\\s*\\{";
		
		Pattern patternMethod = Pattern.compile(regexMethod);
		Matcher matchMethod = patternMethod.matcher(icerik);
		
		while(matchMethod.find()) {
			//System.out.println("regex control method");
			methodCounter++;
		}
		
		//--------------------------------------------------------------
	
		BufferedReader br = new BufferedReader(fileReader);
		
		while((line = br.readLine()) != null) {
			lineOfCode++;
			// Bos satirlari bulduk
			if(line.trim().isBlank()) {
				spaceCounter += 1;
				//System.out.println(index); index++;
			}
			//else { index++; }
			
			if(line.contains("//")) {
				commentLineCounter++;
				if(!(line.trim().startsWith("//")))
					codeLineCounter++;
			}
			
			// Ayni satirda hem javadoc hem de normal kod var
			if(line.matches("\\s*\\w*/\\*\\*.*?\\*/\\w+\\s*") || line.matches("\\s*\\w+/\\*\\*.*?\\*/\\w*\\s*")) {
				javadocCounter++;
				codeLineCounter++;
			}
				
			if(line.contains("/**") || javadocDetected == true) {
				// Tek satirli javadoc (satirda sadece javadoc kodu var)
				if(line.trim().startsWith("/**") && line.trim().endsWith("*/")) {
					javadocCounter++;
				}
				
				// Cok satirli javadoc
				javadocDetected = true;
				if(!(line.contains(" */"))) {
					javadocCounter++;
				}
				else {
					javadocCounter -= 1;
					javadocTotal += 2;
					javadocDetected = false;
				}
			}
			
			// Tek satirda coklu yorum (satirda hem yorum hem kod var)
			if(line.matches("\\s*\\w*/\\*.*?\\*/\\w+\\s+") || line.matches("\\s*\\w+/\\*.*?\\*/\\w*\\s*")) {
				commentLineCounter++;
				codeLineCounter++;
			}
				
			if(line.contains("/*") && line.trim().endsWith("/*") || commentLineDetected == true) {
				// Tek satirli coklu satir yorumu (satirda sadece coklu satir yorumu var)
				if(line.matches("/\\*\\*.*?\\*/")) {
					commentLineCounter++;
				}
				// Coklu yorum satiri
				commentLineDetected = true;
				if(!(line.contains(" */"))) {
					commentLineCounter++;
				}
				else {
					commentLineCounter -= 1;
					commentLineTotal += 2;
					commentLineDetected = false;
					
				}
			}
		}
		
		print(lineOfCode, spaceCounter, javadocCounter, commentLineCounter, methodCounter, codeLineCounter, javadocTotal, commentLineTotal);
		br.close();
	}
	
	public static void print(int _lineOfCode, int _spaceCounter, int _javadocCounter, int _commentLineCounter, int _methodCounter
			, int _codeLineCounter, int _javadocTotal, int _commentLineTotal) {
		_codeLineCounter = _codeLineCounter + _lineOfCode - _spaceCounter - _javadocCounter - _javadocTotal - _commentLineCounter - _commentLineTotal;
		
		double calculation = yorumSapmaHesapla(_javadocCounter, _commentLineCounter, _methodCounter, _codeLineCounter);
		
		//System.out.println(_spaceCounter);
		System.out.println("Javadoc Satir Sayisi: " + _javadocCounter);
		System.out.println("Yorum Satir Sayisi: " + _commentLineCounter);
		System.out.println("Kod Satir Sayisi: " + _codeLineCounter);
		System.out.println("LOC: " + _lineOfCode);
		System.out.println("Fonksiyon Sayisi: " + _methodCounter);
		System.out.println("Yorum Sapma Yuzdesi: % " + new DecimalFormat("##.00").format(calculation).replace(",", "."));
		System.out.println("------------------------");
	}
	
	public static double yorumSapmaHesapla(int _javadocCounter, int _commentLineCounter, int _methodCounter, int _codeLineCounter) {
		double YG = ((_javadocCounter + _commentLineCounter) * 0.8) / _methodCounter;
		//System.out.println(YG);
		double YH = ((double)_codeLineCounter / _methodCounter) * 0.3;
		//System.out.println(YH);
		
		double calculation = ((100 * YG) / YH) - 100;
		return calculation;
	}
	
	public static String icerikToString(File file) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		String line;
		String icerik = "";
		
		while((line = br.readLine()) != null) {
			icerik = icerik + line;
		}
		//System.out.println(icerik);
		return icerik;
	}
}
