# URLRedirectionManager-Redirect-URL-Shortlink
A Java library for managing URL redirection in Android applications. This library uses WebView to handle URL redirection and provides listeners for success and error notifications..
## Features
- Add a list of video URLs.
- Process URL redirections and return updated URLs.
- Set listeners to handle success and failure of URL processing.
- Build an instance of `URLRedirectionManager` using the builder pattern.
## Installation
Copy `URLRedirectionManager.java` into your project.
## How to use 
# Array
```Context context = this; // or obtain the context from your activity or application

URLRedirectionManager redirectionManager = new URLRedirectionManager.Builder(context)
        .addURL(new URLRedirectionManager.VideoURL("Video Title 1", "http://example.com/video1"))
        .addURL(new URLRedirectionManager.VideoURL("Video Title 2", "http://example.com/video2"))
        .setListener(new URLRedirectionManager.OnURLProcessedListener() {
            @Override
            public void onSuccess(ArrayList<URLRedirectionManager.VideoURL> videoURLList) {
                // Handle success
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        })
        .build();
## Single

