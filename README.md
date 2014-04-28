# Building web applications with Clojure - code

This repository contains source codes for the [Building web applications with Clojure](http://www.packtpub.com/building-web-applications-with-clojure/video) screencast, published
by [PacktPub](https://www.packtpub.com).

*Please note, that this code is provided for demonstrational purposes only and was written live during short videos. Therefore, it is kind of pointless to use it without commentary provided in the video course itself.*

Each video has a separate git tag for version of code available at the end of it,
so you can walk through the progress of the tutorials or jump to the one you're currently browsing.

For example, video 2 of section 3, is tagged as SECTION3_VIDEO2, which video 1 of section 5 is tagged as SECTION5_VIDEO1.

Each section and video is described below, so you can easily browse the code and access external links to tools and libraries mentioned in each video.

Feel free to fork and play around with this repo!

## Section 1: Getting Started
In this section, the reader will learn how to create a Leiningen project, configure Ring dependencies and plugins and start the web server from a command line.

Reference links:

* [Leiningen build tool](http://www.leiningen.org), [https://github.com/technomancy/leiningen](https://github.com/technomancy/leiningen)
* [Ring](https://github.com/ring-clojure/ring)
* [Leiningen Ring plugin](https://github.com/weavejester/lein-ring)

Videos:

* [Video 1: project setup](../../tree/SECTION1_VIDEO1)
* [Video 2: lein-ring plugin](../../tree/SECTION1_VIDEO2)
* [Video 3: simple Ring handler](../../tree/SECTION1_VIDEO3)
* [Video 4: running Ring server](../../tree/SECTION1_VIDEO4)
* [Video 5: building web application packages](../../tree/SECTION1_VIDEO5)

## Section 2: Ring Handlers
This section will explain how handlers in Ring work, what do they do and how we can access HTTP
request manipulate an HTTP response. We will also see how to dispatch calls to different functions
and introduce general mechanisms.

Reference links:

* [Leiningen Ring plugin configuration](https://github.com/weavejester/lein-ring)
* [Ring handlers](https://github.com/ring-clojure/ring/wiki/Concepts#handlers)
* [Ring response and request map attributes](https://github.com/ring-clojure/ring/wiki/Concepts#requests)

Videos:

* [Video 1: Ring handler anatomy](../../tree/SECTION2_VIDEO1)
* [Video 2: request map](../../tree/SECTION2_VIDEO2)
* [Video 3: response map](../../tree/SECTION2_VIDEO3)
* [Video 4: routing](../../tree/SECTION2_VIDEO4)
* [Video 5: not found and error handling](../../tree/SECTION2_VIDEO5)

## Section 3: Middleware
In this section, I will explain how Ring middleware works. Then, I will implement an example middleware.
Finally, use of existing middleware will be shown.

Reference links:

* [Ring middleware concept](https://github.com/ring-clojure/ring/wiki/Concepts#middleware)
* [Ring file middleware](https://github.com/ring-clojure/ring/wiki/Static-Resources)

Videos:

* [Video 1: introduction](../../tree/SECTION3_VIDEO1)
* [Video 2: example](../../tree/SECTION3_VIDEO2)
* [Video 3: altering request and response](../../tree/SECTION3_VIDEO3)
* [Video 4: using existing middleware](../../tree/SECTION3_VIDEO4)

## Section 4: Forms & sessions handling
In this section, I will show to handle POST/GET parameters from Clojure web application, generate
HTML content from Clojure level, manipulate cookies and manage Ring sessions.

Reference links:

* [Ring parameter support](https://github.com/ring-clojure/ring/wiki/Parameters)
* [Ring file upload](https://github.com/ring-clojure/ring/wiki/File-Uploads)
* [Ring cookies](https://github.com/ring-clojure/ring/wiki/Cookies)
* [Ring sessions](https://github.com/ring-clojure/ring/wiki/Sessions)

Videos:

* [Video 1: Form handling](../../tree/SECTION4_VIDEO1)
* [Video 2: Generating HTML content](../../tree/SECTION4_VIDEO2)
* [Video 3: Manipulating cookies](../../tree/SECTION4_VIDEO3)
* [Video 4: Ring sessions](../../tree/SECTION4_VIDEO4)

## Section 5: Building RESTful API
This section will show how we can easily build a RESTful, JSON-based API with Clojure/Ring.

Reference links:

* [REST page on Wikipedia](http://en.wikipedia.org/wiki/Representational_state_transfer)

Videos:
 
* [Video 1: API introduction and routing](../../tree/SECTION5_VIDEO1)
* [Video 2: Example RESTful API](../../tree/SECTION5_VIDEO2)
* [Video 3: Handling errors in REST API](../../tree/SECTION5_VIDEO3)

## Section 6: Database connectivity
In this section, I will showcase how can we integrate a popular databases - PostgreSQL and
MongoDB with Clojure web app. For PostgreSQL, popular Clojure ORM framework - Korma, will be
introduced.

Reference links:

* [PostgreSQL home page](http://www.postgresql.org)
* [clojure.java.jdbc](https://github.com/clojure/java.jdbc)
* [Korma](http://sqlkorma.com/)
* [MongoDB](http://www.mongodb.org/)
* [Monger](http://clojuremongodb.info/)
* [Congomongo](https://github.com/aboekhoff/congomongo)

Videos:

* [Video 1: Connecting to PostgreSQL](../../tree/SECTION6_VIDEO1)
* [Video 2: Using Korma](../../tree/SECTION6_VIDEO2)
* [Video 3: Connecting to MongoDB](../../tree/SECTION6_VIDEO3)

## Section 7: ClojureScript
This section will cover how to build basic ClojureScript logic, access JavaScript functions and how to
integrate it with Clojure RESTful interface server-side.

Reference links:

* [ClojureScript](https://github.com/clojure/clojurescript)
* [lein-cljsbuild plugin](https://github.com/emezeske/lein-cljsbuild)

Videos:

* [Video 1: ClojureScript](../../tree/SECTION7_VIDEO1)
* [Video 2: Manipulating DOM tree with ClojureScript](../../tree/SECTION7_VIDEO2)
* [Video 3: Accessing REST API from ClojureScript](../../tree/SECTION7_VIDEO3)

## Section 8: Useful web libraries in Clojure
In this section, I will show most popular and useful web libraries available for Clojure - Compojure for
routing, Hiccup and Enlive for HTML templating and Liberator for building RESTful APIs.

Reference links:

* [Compojure](https://github.com/weavejester/compojure)
* [Hiccup](https://github.com/weavejester/hiccup)
* [enlive](https://github.com/cgrand/enlive)
* [Liberator](http://clojure-liberator.github.io/liberator/)

Videos:

* [Video 1: Routing with Compojure](../../tree/SECTION8_VIDEO1)
* [Video 2: HTML generation with Hiccup](../../tree/SECTION8_VIDEO2)
* [Video 3: HTML templating with enlive](../../tree/SECTION8_VIDEO3)
* [Video 4: REST API with Liberator](../../tree/SECTION8_VIDEO4)
