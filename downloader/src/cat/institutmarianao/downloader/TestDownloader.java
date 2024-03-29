package cat.institutmarianao.downloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestDownloader {
	private static final Logger LOGGER = Logger.getLogger(TestDownloader.class.getName());

	static {
		DownloaderLogger.setup(LOGGER);
	}

	private static List<Thread> resourceThreads;

	/**
	 * @param args the command line arguments
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		resourceThreads = new ArrayList<>();
		Downloader saver = new Downloader();
		saver.addResourceType(".*/pdf", "pdfs");
		saver.addResourceType("image/.*", "images");
		saver.addResourceType("audio/.*", "audios");
		saver.addResourceType(".*zip", "zips");
		saver.addResourceType("text/plain.*", "txts");
		saver.addResourceType(".*html", "htmls");
		saver.addResourceType("video/.*", "videos");

		try {
			saver.createFolderTree();
			save("ftp://mirror.vexxhost.com/apache/activemq/apache-nms/1.6.0/Apache.NMS-1.6.0-src.zip", saver);
			save("https://mirror.team-cymru.org/ubuntu/indices/override.breezy-backports.extra.main", saver);
			save("https://www.nic.funet.fi/pub/gnu/ftp.gnu.org/pub/gnu/Licenses/lgpl-2.1.txt", saver);
			save("ftp://ftp.man.lodz.pl/pub/security/README.html", saver);
			save("https://www.uscis.gov/files/form/i-9.pdf", saver);
			save("https://farm8.staticflickr.com/7076/7301495298_ee121d2013_s_d.jpg", saver);
			save("https://www.mediacollege.com/downloads/sound-effects/city/traffic-02.mp3", saver);
			save("https://archive.org/download/TheCaseOfTheKangarooKid1963/TheKangarooKid.mp4", saver);

			for (Thread thread : resourceThreads) {
				thread.join();
			}
			LOGGER.info("All resources are saved");
		} catch (IOException e) {
			LOGGER.severe("Input/Output error");
		}
	}

	public static void save(String resource, Downloader saver) {
		try {
			resourceThreads.add(saver.saveResource(resource));
		} catch (Exception e) {
			LOGGER.severe("Unknown error");
		}
	}
}
