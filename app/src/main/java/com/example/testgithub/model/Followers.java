package com.example.testgithub.model;


import com.google.gson.annotations.SerializedName;


public class Followers{

	@SerializedName("gists_url")
	public String gistsUrl;

	@SerializedName("repos_url")
	public String reposUrl;

	@SerializedName("following_url")
	public String followingUrl;

	@SerializedName("starred_url")
	public String starredUrl;

	@SerializedName("login")
	public String login;

	@SerializedName("followers_url")
	public String followersUrl;

	@SerializedName("type")
	public String type;

	@SerializedName("url")
	public String url;

	@SerializedName("subscriptions_url")
	public String subscriptionsUrl;

	@SerializedName("received_events_url")
	public String receivedEventsUrl;

	@SerializedName("avatar_url")
	public String avatarUrl;

	@SerializedName("events_url")
	public String eventsUrl;

	@SerializedName("html_url")
	public String htmlUrl;

	@SerializedName("site_admin")
	public boolean siteAdmin;

	@SerializedName("id")
	public int id;

	@SerializedName("gravatar_id")
	public String gravatarId;

	@SerializedName("node_id")
	public String nodeId;

	@SerializedName("organizations_url")
	public String organizationsUrl;


}