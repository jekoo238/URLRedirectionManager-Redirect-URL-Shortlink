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
### Single

```
URLRedirectionManager redirectionManager = new URLRedirectionManager(context);

redirectionManager.redirectURL("https://example.com", new URLRedirectionManager.OnStringResponseListener() {
    @Override
    public void onSuccess(String url) {
        // URL berhasil diambil
        Log.d("Redirected URL", url);
    }

    @Override
    public void onError(String errorMessage) {
        // Kesalahan dalam mengambil URL
        Log.e("Redirection Error", errorMessage);
    }
});
```
### Batch
```
URLRedirectionManager redirectionManager = new URLRedirectionManager.Builder(context)
    .addURL(new URLRedirectionManager.VideoURL("Title", "https://example.com"))
    .setListener(new URLRedirectionManager.OnURLProcessedListener() {
        @Override
        public void onSuccess(ArrayList<URLRedirectionManager.VideoURL> videoURLList) {
            // Handle successful redirection
        }

        @Override
        public void onError(String errorMessage) {
            // Handle error during redirection process
        }
    })
    .build();
```
You can add more:
```
redirectionManager.addURL(new URLRedirectionManager.VideoURL("Video 1", "https://example.com/video1"));

redirectionManager.addURL(new URLRedirectionManager.VideoURL("Video 2", "https://example.com/video2"));
```
Final:
```
redirectionManager.attachList();
```
