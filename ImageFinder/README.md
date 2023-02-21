## About 
ImageFinder is a JSoup based web crawler can crawl images from a website with format https://www.XXXX.com

- It can crawl all images on the site or on sub-pages
- It uses multi-threaded parser to crawl multiple pages at the same time (but not the same page)
- It displays crawled images on the web page directly

### Setup
- Make sure maven and Java 8 is installed on your computer
- Go to the root directory of imageFinder and build the project using the following commands:
> `mvn clean`
> `mvn package -DskipTests`
> `mvn jetty:run`
- Now the website will be available on `localhost:8080`

## Failures and Issues
- Use `ctrl + c` to stop the parsing of current thread, if the thread is not stoppable, stop the terminal using `cmd + q`
- For websites, where the urls are not allowed to be crawled, so our crawler finished quickly with a rejection printed on command line
- For any websites with tons of images to be crawled, it took ImageFinder super long time to finish and might end up unfinished
