/**
*
* @author Sinem Şaziye Karaca - saziye.karaca@org.sakarya.edu.tr
* @since 03.04.2024
* <p>
* Klonlanan git reposunun içerisinden yalnızca java uzantılı dosyaların alındığı sınıf.
* Klonlanan dosya yolunun alınmasının ardından git klasörü kontrol edilir. Klasörün içinde yeni
* bir klasör varsa bu klasör açılır ve içi kontrol edilir. Her bir klasörün içindeki dosyalardan
* java uzantılı dosyaların tespiti için bir .java uzantısı içeren bir string oluşturulur.
* Ardından dosyanın ismini almak için getName() metodu kullanılır. Alınan dosya ismiyle string içeriği
* karşılaştırılır. İçeriğin eşleşmesi halinde dosyanın içeriğinde sadece class olup olmadığını tespit
* etmek üzere SadeceClassIcerenler sınıfı çağrılır.
* </p>
*/

package odev_2024;

import java.io.File;

public class JavaDosyalariBul {
	public static void javaFiles(File directory) {
		String ext = ".java";
		
		if(directory.isDirectory()) {
			File[] listOfFiles = directory.listFiles();
			if(listOfFiles != null) {
				for(File file : listOfFiles) {
					// file normal dosya ise adini kontrol et
					if(file.isFile()) {
						String fileName = file.getName();
						// Dosya adinda noktanin son goruldugu index
						int extIndex = fileName.lastIndexOf(".");
						
						if(extIndex > 0) {
							// Dosya adinin uzanti substringini al
							String fileExt = fileName.substring(extIndex);
							
							// Dosya uzantisiyla belirledigimiz uzantiyi karsilastir
							// (Harf hassasiyeti olmadan karsilastir)
							// .java uzantili dosyalar
							if(fileExt.equalsIgnoreCase(ext)) {
								//System.out.println(file.getAbsolutePath());
								
								boolean b = SadeceClassIcerenler.dosyaIcerigi(file.getAbsolutePath());
							}
							else {
								//System.out.println("Bu bir java dosyasi degil!");
							}
						}
					}
					// file dizin ise o dizini ac
					else {
						javaFiles(file);
					}
				}
			}
		}
		else {
			//System.out.println("Dizin Bulunamadi!");
		}
	}
}
