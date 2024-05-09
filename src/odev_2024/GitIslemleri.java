/**
*
* @author Sinem Şaziye Karaca - saziye.karaca@org.sakarya.edu.tr
* @since 03.04.2024
* <p>
* cloneRepository metodu, kullanıcıdan alınan repo URL'sini klonlar. Bu işlemin gerçekleşmesinden önce
* klonların saklanacağı dizinin dolu olması halinde bu dizdeki tüm dosyalar ve
* alt dizinler temizlenir, ardından git clone komutu kullanılarak 
* belirtilen dizine klonlama işlemi gerçekleştirilir.
* </p>
*/

package odev_2024;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GitIslemleri {
	public static final String clonedDirectoryPATH = "/path/to/cloned_directory";
	@SuppressWarnings("unused")
	public static void cloneRepository(String repoUrl) {
		try {
			Path clonedDirPath = Paths.get(clonedDirectoryPATH);
			if(Files.exists(clonedDirPath)) {
				cleanDirectory(clonedDirPath);
			}
			else {
				Files.createDirectories(clonedDirPath);
			}
			
			ProcessBuilder builder = new ProcessBuilder("git", "clone", repoUrl, clonedDirectoryPATH);
			builder.redirectErrorStream(true);
			
			Process process = builder.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			
			int exitCode = process.waitFor();
			System.out.println("Git klonlama islemi tamamlandi. Cikis kodu..: " + exitCode);
		} 
		
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void cleanDirectory(Path directory) throws IOException {
		Files.walk(directory)
			.sorted(java.util.Comparator.reverseOrder())
			.map(Path::toFile)
			.forEach(File::delete);
	}
}
