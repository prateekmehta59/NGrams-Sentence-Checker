Web crawler using Google API:
---
Run webscrapper project in IntelliJ, make sure to add `jsoup-1.10.3.jar` in the list of `External Libraries`.

Web scraper for Gutenberg:
---
Data scraping from Gutenberg Project - The following command gets all English books in .zip format.

Command

```
wget -H -w 2 -m http://www.gutenberg.org/robot/harvest?filetypes[]=txt&langs[]=en \
--referer="http://www.google.com" \
--user-agent="Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.6) Gecko/20070725 Firefox/2.0.0.6" \
--header="Accept: text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5" \
--header="Accept-Language: en-us,en;q=0.5" \
--header="Accept-Encoding: gzip,deflate" \
--header="Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7" \
--header="Keep-Alive: 300"
```
