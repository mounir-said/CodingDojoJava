package com.pfe.hypermax.dto;

import java.util.List;

public class BraveSearchResponse {
    private Web web;

    public Web getWeb() {
        return web;
    }

    public void setWeb(Web web) {
        this.web = web;
    }

    public static class Web {
        private List<WebResult> results;

        public List<WebResult> getResults() {
            return results;
        }

        public void setResults(List<WebResult> results) {
            this.results = results;
        }
    }

    public static class WebResult {
        private String title;
        private String url;
        private String description;
        private Thumbnail thumbnail; // Thumbnail is now an object

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Thumbnail thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    public static class Thumbnail {
        private String src;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}