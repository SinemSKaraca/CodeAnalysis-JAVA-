/**
*
* @author Sinem Şaziye Karaca - saziye.karaca@org.sakarya.edu.tr
* @since 03.04.2024
* <p>
* Kullanıcısan alınan repo URL'sinin üzerinde çalışmak üzere GitIslemleri sınıfına 
* gönderilmesi ardından bu sınıf aracılığıyla alınan klon dosya yolunun java dosyalarının 
* bulunabilmesi için JavaDosyalariBul sınıfına gönderilmesi. Dosya bulunamaması durumunda Input 
* Ouput Exception hatası verilir.
* </p>
*/

package odev_2024;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

	public static void main(String[] args) {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.println("Github depo linkini giriniz..: ");
			String repoUrl = bReader.readLine(); // Kullanicinin Github deposunun URL'si
			GitIslemleri.cloneRepository(repoUrl); 
			
			File clonedDirectory = new File(GitIslemleri.clonedDirectoryPATH);
			JavaDosyalariBul.javaFiles(clonedDirectory);
			
			bReader.close();
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
