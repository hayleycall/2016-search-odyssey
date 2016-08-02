package com.searchodyssey.app;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class WikiCrawler {
	// keeps track of where we started
	private final String source;
	
	
	// queue of URLs to be indexed
	private Queue<String> queue = new LinkedList<String>();
	
	// fetcher used to get pages from Wikipedia
	final static WikiFetcher wf = new WikiFetcher();

	/**
	 * Constructor.
	 * 
	 * @param source
	 * @param index
	 */
	public WikiCrawler(String source) {
		this.source = source;
		queue.offer(source);
	}

	/**
	 * Returns the number of URLs in the queue.
	 * 
	 * @return
	 */
	public int queueSize() {
		return queue.size();	
	}

	/**
	 * Gets a URL from the queue and indexes it.
	 * @param b 
	 * 
	 * @return Number of pages indexed.
	 * @throws IOException
	 */
	   public String crawl(boolean testing) throws IOException {
        // filled in
                String url=queue.remove();
		System.out.println("URL is: "+url);
                Elements paragraphs=null;
                if(testing){
                        paragraphs=wf.readWikipedia(url);
                }
                else{
                        if(index.isIndexed(url))
                                return null;
                        paragraphs=wf.fetchWikipedia(url);
                }
                index.indexPage(url, paragraphs);
                queueInternalLinks(paragraphs);
                return url;
        }

        /**
         * Parses paragraphs and adds internal links to the queue.
         *
         * @param paragraphs
         */
        // NOTE: absence of access level modifier means package-level
        void queueInternalLinks(Elements paragraphs) {
        // filled in
                String url="";
                for(Element e : paragraphs){
                        Iterable<Node> iter = new WikiNodeIterable(e);
                        for (Node node: iter) {
                                if (node instanceof Element) {
                                        Elements links=((Element) node).select("a");
                                        for(Element f : links) {
                                                url=((Element) node).attr("href");
                                                if(url.startsWith("/wiki/"))
                                                	// we dont want too many links
                                                	if (queuesize() <= 500) {
                                                        	queue.add("https://en.wikipedia.org"+url);
                                                	} else {
                                                		return;
                                                	}
                                        }
                                }
                        }
                }
                System.out.println("size: "+queue.size());

        }

	public static void main(String[] args) throws IOException {
		
		// make a WikiCrawler
		String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		WikiCrawler wc = new WikiCrawler(source);
		
		// for testing purposes, load up the queue
	//	Elements paragraphs = wf.fetchWikipedia(source);
	//	wc.queueInternalLinks(paragraphs);
		
	}
}
