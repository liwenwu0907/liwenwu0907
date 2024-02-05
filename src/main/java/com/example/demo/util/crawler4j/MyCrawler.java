//package com.example.demo.util.crawler4j;
//
//import edu.uci.ics.crawler4j.crawler.CrawlConfig;
//import edu.uci.ics.crawler4j.crawler.CrawlController;
//import edu.uci.ics.crawler4j.crawler.Page;
//import edu.uci.ics.crawler4j.crawler.WebCrawler;
//import edu.uci.ics.crawler4j.fetcher.PageFetcher;
//import edu.uci.ics.crawler4j.parser.HtmlParseData;
//import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
//import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
//import edu.uci.ics.crawler4j.url.WebURL;
//
//import java.util.Set;
//import java.util.regex.Pattern;
//
//public class MyCrawler extends WebCrawler {
//
//    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
//            + "|png|mp3|mp4|zip|gz))$");
//
//    @Override
//    public boolean shouldVisit(Page referringPage, WebURL url) {
//        String href = url.getURL().toLowerCase();
//        return !FILTERS.matcher(href).matches()
//                && href.startsWith("https://www.officeplus.cn/PPT/template/");
//    }
//
//    @Override
//    public void visit(Page page) {
//        String url = page.getWebURL().getURL();
//        System.out.println("URL: " + url);
//
//        if (page.getParseData() instanceof HtmlParseData) {
//            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//            Set<WebURL> links = htmlParseData.getOutgoingUrls();
//
//            System.out.println("Text length: " + text.length());
//            System.out.println(text);
//            System.out.println("Html length: " + html.length());
//            System.out.println(html);
//            System.out.println("Number of outgoing links: " + links.size());
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        String crawlStorageFolder = "E:\\crawler4j";
//        int numberOfCrawlers = 7;
//
//        CrawlConfig config = new CrawlConfig();
//        config.setCrawlStorageFolder(crawlStorageFolder);
//
//        // Instantiate the controller for this crawl.
//        PageFetcher pageFetcher = new PageFetcher(config);
//        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
//        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
//        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
//
//        // For each crawl, you need to add some seed urls. These are the first
//        // URLs that are fetched and then the crawler starts following links
//        // which are found in these pages
////        controller.addSeed("https://www.ics.uci.edu/~lopes/");
////        controller.addSeed("https://www.ics.uci.edu/~welling/");
//        controller.addSeed("https://www.officeplus.cn/PPT/template/");
//
//        // The factory which creates instances of crawlers.
//        CrawlController.WebCrawlerFactory<MyCrawler> factory = MyCrawler::new;
//
//        // Start the crawl. This is a blocking operation, meaning that your code
//        // will reach the line after this only when crawling is finished.
//        controller.start(factory, numberOfCrawlers);
//    }
//
//}
