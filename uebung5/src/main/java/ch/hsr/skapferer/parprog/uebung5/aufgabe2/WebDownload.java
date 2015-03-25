package ch.hsr.skapferer.parprog.uebung5.aufgabe2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class WebDownload {
	public String downloadUrl(String link) throws IOException {
		URL url = new URL(link);
		StringBuffer stringBuffer = new StringBuffer();
		try (Reader reader = new InputStreamReader(url.openStream())) {
			int i;
			while ((i = reader.read()) >= 0) {
				stringBuffer.append((char) i);
			}
		}
		return stringBuffer.toString();
	}
}
