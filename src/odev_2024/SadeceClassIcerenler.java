/**
*
* @author Sinem Şaziye Karaca - saziye.karaca@org.sakarya.edu.tr
* @since 03.04.2024
* <p>
* Klonlanan git reposundan alınan java dosyalarından içinde yalnızsa class bulunan dosyaların analiz edilmek
* üzere alınması. (enum, interface gibi yapılar içeren dosyalar analiz edilmek için alınmaz.)
* </p>
*/

package odev_2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class SadeceClassIcerenler {
	// .java uzantili bir dosya geldi. Iceriğinde class haricinde bir yapi olup olmadigi kontrol ediliyor.
	public static boolean dosyaIcerigi(String javaPath) {
		//System.out.println("SadeceClassIcerenler Sinifindasin" + javaPath);
		
		File file = new File(javaPath);
		
		try {
			FileReader fReader = new FileReader(file);
			String line;
			
			BufferedReader br = new BufferedReader(fReader);
			
			while((line = br.readLine()) != null) {
				// Class haricinde yapilar iceriyor
				if(line.contains("enum") || line.contains("interface")) {
					//System.out.println("Sadece Class Iceren Bir Dosya Degil -- " + file.getName());
					return false;
				}
			}
			// İcinde sadece Class yapisi bulunan dosya. Analiz asamasina gonderilir.
			DosyaAnaliz.analiz(javaPath);
			
			br.close();
		} 
		catch (IOException e) {
			System.out.println("Dosya bulunamadi!");
			e.printStackTrace();
		}
		return true;
	}
}
