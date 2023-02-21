package com.eulerity.hackathon.imagefinder;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(
    name = "ImageFinder",
    urlPatterns = {"/main"}
)
public class ImageFinder extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected static final Gson GSON = new GsonBuilder().create();

	ExecutorService es;

	/**
	 * Accept post request and initiate scraping
	 * @param req request
	 * @param resp response
	 */
	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/json");
		String url = req.getParameter("url");
		System.out.println("Parsing" + url);

		try {
			if(ParserThread.es != null) {
				ParserThread.es.shutdownNow();
				ParserThread.stop();
				Thread.sleep(500);
				System.err.println("WAKING UP!!");
				ParserThread.reset();
			}
		} catch (InterruptedException e){
			System.err.println("SLEEP INTERRUPTED: While Previous Threads Closing");
		}

		try{
			Thread.sleep(1500);
			es = Executors.newCachedThreadPool();
			ParserThread.es = es;
			System.out.println("New Scrapper Starting!");

			es.execute(new Runnable() {
				@Override
				public void run() {
				}
				ParserThread initialThread = new ParserThread(url);
			});
		}
		catch (RejectedExecutionException e) {
			System.err.println("Initial Thread Was Prevented on URL: " + url);
		}
		catch (InterruptedException e){
			System.err.println("SLEEP INTERRUPTED: While Opening New Threads");
		}
		System.out.println("Finished Scrape!");
		resp.getWriter().print(GSON.toJson(ParserThread.scrapedIMGS));
	}
}
