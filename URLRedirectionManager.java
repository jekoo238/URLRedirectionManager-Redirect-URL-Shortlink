package org.comic.verse;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import java.util.ArrayList;

public class URLRedirectionManager {
	
	private ArrayList<VideoURL> videoURLList = new ArrayList<>();
	private int processedURLCount;
	private Context appContext;
	private OnURLProcessedListener urlProcessedListener;
	private OnStringResponseListener stringResponseListener;
	
	public boolean succesResponse;
	
	public interface OnURLProcessedListener {
		void onSuccess(ArrayList<VideoURL> videoURLList);
		void onError(String errorMessage);
	}
	
	public interface OnStringResponseListener {
		void onSuccess(String url);
		void onError(String errorMessage);
	}
	
	public void setListener(OnURLProcessedListener listener) {
		this.urlProcessedListener = listener;
	}
	
	public URLRedirectionManager(Context context) {
		this.appContext = context;
	}
	
	public void redirectURL(String url, OnStringResponseListener responseListener) {
		this.stringResponseListener = responseListener;
		WebView webView = new WebView(appContext);
		webView.getSettings().setLoadsImagesAutomatically(false);
		webView.getSettings().setJavaScriptEnabled(false);
		webView.setWebViewClient(new CustomStringWebViewClient());
		webView.loadUrl(url);
	}
	
	public void addURL(VideoURL videoURL) {
		videoURLList.add(videoURL);
	}
	
	public void attachList() {
		this.processedURLCount = 0;
		this.succesResponse = false;
		for (int i = 0; i < videoURLList.size(); i++) {
			WebView webView = new WebView(appContext);
			webView.getSettings().setLoadsImagesAutomatically(false);
			webView.getSettings().setJavaScriptEnabled(false);
			webView.setWebViewClient(new CustomWebViewClient(i));
			webView.loadUrl(videoURLList.get(i).getUrl());
		}
	}
	
	public ArrayList<VideoURL> getList() {
		return videoURLList;
	}
	
	private class CustomWebViewClient extends WebViewClient {
		private int urlPosition;
		
		public CustomWebViewClient(int position) {
			this.urlPosition = position;
		}
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			String actualUrl = url;
			videoURLList.get(urlPosition).setFixUrl(actualUrl);
			processedURLCount++;
			if (processedURLCount == videoURLList.size()) {
				if (urlProcessedListener != null && !succesResponse) {
					urlProcessedListener.onSuccess(videoURLList);
					succesResponse = true;
				}
			}
			view.destroy();
			return true; //must be true
		}
		
		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
			super.onReceivedError(view, request, error);
			if (urlProcessedListener != null && !succesResponse) {
				urlProcessedListener.onError(error.getDescription().toString());
				succesResponse = true;
			}
		}
	}
	
	private class CustomStringWebViewClient extends WebViewClient {
		public CustomStringWebViewClient() {}
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			String actualUrl = url;
			if (stringResponseListener != null) {
				stringResponseListener.onSuccess(actualUrl);
			}
			view.destroy();
			return true; //must be true
		}
		
		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
			super.onReceivedError(view, request, error);
			if (stringResponseListener != null) {
				stringResponseListener.onError(error.getDescription().toString());
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder urlListStringBuilder = new StringBuilder();
		for (VideoURL videoURL : videoURLList) {
			urlListStringBuilder.append(videoURL.toString()).append("\n");
		}
		return urlListStringBuilder.toString();
	}
	
	public static class Builder {
		private Context builderContext;
		private URLRedirectionManager redirectionManager;
		
		public Builder(Context context) {
			this.builderContext = context;
			this.redirectionManager = new URLRedirectionManager(context);
		}
		
		public Builder addURL(VideoURL videoURL) {
			this.redirectionManager.addURL(videoURL);
			return this;
		}
		
		public Builder setListener(OnURLProcessedListener listener) {
			this.redirectionManager.setListener(listener);
			return this;
		}
		
		public URLRedirectionManager build() {
			return this.redirectionManager;
		}
	}
	
	public static class VideoURL {
	    private String title;
        private String url;
        private String fixUrl;

        public VideoURL(String title, String url) {
            this.url = url;
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setFixUrl(String fixUrl) {
            this.fixUrl = fixUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "VideoURL{" +
                    "url='" + url + '\'' +
                    ", fixUrl='" + fixUrl + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
